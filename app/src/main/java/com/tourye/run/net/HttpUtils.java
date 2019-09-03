package com.tourye.run.net;

import android.text.TextUtils;
import android.util.Log;
import com.tourye.run.utils.SaveUtil;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Title:HttpUtils
 * <p>
 * Description:网络请求工具类
 * </p>
 * Author dragon.
 * Date 2018/4/16 10:32
 */

public class HttpUtils {

    private OkHttpClient mOkHttpClient;

    private HttpUtils(){
        mOkHttpClient=new OkHttpClient.Builder()
                //添加拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        return chain.proceed(request);
                    }
                })
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return chain.proceed(chain.request());
                    }
                })
                //连接超时
                .connectTimeout(20,TimeUnit.SECONDS)
                //读取超时
                .readTimeout(20, TimeUnit.SECONDS)
                //写出超时
                .writeTimeout(20,TimeUnit.SECONDS)
                .build();

    }

    //单例获取网络请求对象
    public static HttpUtils getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    //get访问
    public <T> void get(String url,Map<String,String> requestParams,HttpCallback<T> callback){

        Set<String> keySet = requestParams.keySet();
        Iterator<String> iterator = keySet.iterator();
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(url+"?");
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = requestParams.get(key);
            stringBuffer.append(key+"="+value+"&");
        }
        url=stringBuffer.toString();
        url=url.substring(0,url.length()-1);

        Log.d("request","#######################################################################");
        Log.d("request",url);
        Log.d("request","#######################################################################");

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.addHeader("Accept","application/json,text/plain,*/*");

        String authorization = SaveUtil.getString("Authorization", "");
        if (!TextUtils.isEmpty(authorization)) {
            Log.d("Authorization","Bearer "+authorization);
            builder.addHeader("Authorization","Bearer "+authorization);
        }

        builder.method("GET",null);
        //创建Request
        Request request = builder.build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    //post请求
    public <T> void post(String url,Map<String,String> requestParams,HttpCallback<T> callback){

        Set<String> keySet = requestParams.keySet();
        Iterator<String> iterator = keySet.iterator();
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(url+"?");

        //表单请求体
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = requestParams.get(key);
            stringBuffer.append(key+"="+value+"&");

            bodyBuilder.add(key,value);
        }
        String urlFinal=stringBuffer.toString();
        urlFinal=urlFinal.substring(0,urlFinal.length()-1);

        Log.d("request","#######################################################################");
        Log.d("request:",urlFinal);
        Log.d("request","#######################################################################");

        RequestBody requestBody = bodyBuilder.build();

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.addHeader("Accept","application/json,text/plain,*/*");

        String authorization = SaveUtil.getString("Authorization", "");
        if (!TextUtils.isEmpty(authorization)) {
            Log.d("Authorization","Bearer "+authorization);
            builder.addHeader("Authorization","Bearer "+authorization);
        }

        builder.post(requestBody);
        //创建Request
        Request request = builder.build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    //post请求
    public <T> void post_json(String url,String json,HttpCallback<T> callback){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);

        Log.d("url",url+"--");
        Log.d("json",json+"--");
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.addHeader("Accept","application/json,text/plain,*/*");
        String authorization = SaveUtil.getString("Authorization", "");
        if (!TextUtils.isEmpty(authorization)) {
            Log.d("Authorization","Bearer "+authorization);
            builder.addHeader("Authorization","Bearer "+authorization);
        }
        builder.post(requestBody);
        //创建Request
        Request request = builder.build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * //上传文件
     * @param url 访问地址
     * @param requestParams  请求参数
     * @param param  文件类型
     * @param file  文件
     * @param callback
     * @param <T>
     */
    public <T> void upload(String url, Map<String,String> requestParams, String param, File file, HttpCallback<T> callback){
        Set<String> keySet = requestParams.keySet();
        Iterator<String> iterator = keySet.iterator();

        //表单请求体
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        while (iterator.hasNext()) {
            //参数请求体
            String key = iterator.next();
            String value = requestParams.get(key);
            bodyBuilder.addFormDataPart(key,value);
        }

        Log.d("request","#######################################################################");
        Log.d("request:",url);
        Log.d("request","#######################################################################");

        //文件请求体
        MediaType type=MediaType.parse("application/octet-stream");//"text/xml;charset=utf-8"
        RequestBody fileBody=RequestBody.create(type,file);
        bodyBuilder.addFormDataPart(param,file.getName(),fileBody);
        MultipartBody requestBody = bodyBuilder.build();

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.addHeader("Accept","application/json,text/plain,*/*");
        String authorization = SaveUtil.getString("Authorization", "");
        if (!TextUtils.isEmpty(authorization)) {
            Log.d("Authorization","Bearer "+authorization);
            builder.addHeader("Authorization","Bearer "+authorization);
        }
        builder.post(requestBody);
        //创建Request
        Request request = builder.build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);

    }

    /**
     * 下载文件
     * @param url  请求地址
     * @param requestParams  请求参数
     * @param callback
     */
    public void download(String url, Map<String,String> requestParams, Callback callback){

        Set<String> keySet = requestParams.keySet();
        Iterator<String> iterator = keySet.iterator();
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(url+"?");

        //表单请求体
        FormBody.Builder builder = new FormBody.Builder();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = requestParams.get(key);
            stringBuffer.append(key+"="+value+"&");

            builder.add(key,value);
        }
        String urlFinal=stringBuffer.toString();
        urlFinal=urlFinal.substring(0,urlFinal.length()-1);

        Log.d("request","#######################################################################");
        Log.d("request:",urlFinal);
        Log.d("request","#######################################################################");

        RequestBody requestBody = builder.build();

        //创建Request
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);

    }

    /**
     * 枚举单列获取请求对象
     */
    private static enum Singleton{

        INSTANCE;

        private HttpUtils mHttpUtils=new HttpUtils();

        private Singleton(){

        }

        public HttpUtils getInstance() {
            return mHttpUtils;
        }

    }

}
