package com.tourye.run.net;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.tourye.run.Constants;
import com.tourye.run.SaveConstants;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.ui.activities.LoginActivity;
import com.tourye.run.utils.RecordUtils;
import com.tourye.run.utils.SaveUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Title:HttpCallback
 * <p>
 * Description:网络请求回调
 * </p>
 * Author dragon.
 * Date 2018/4/16 11:29
 */

public abstract class HttpCallback<T> implements Callback {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(Call call, IOException e) {

        Log.d("onFailure", "#######################################################################");
        Log.d("onFailure", "错误信息：" + e.getMessage());
        Log.d("onFailure", "#######################################################################");

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailureExecute();
            }
        });

    }

    /**
     * 网络请求失败
     */
    public void onFailureExecute() {
        onPreExcute();
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (response.isSuccessful()) {

            Log.d("onResponse", "#######################################################################");
            final String responseText = response.body().string();
            Log.d("onResponse", responseText);
            Logger.json(responseText);
            Log.d("onResponse", "#######################################################################");

            Gson gson = new Gson();

            //获取泛型的class对象
            Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            Class<T> entityClass = (Class<T>) actualTypeArguments[0];
            try {
                final T t = gson.fromJson(responseText, entityClass);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        onPreExcute();

                        onForceExcute(responseText);

                        onSuccessExecute(t);

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            Log.d("onResponse", "#######################################################################");
            Log.d("onResponse", "响应码：" + response.code() + "响应信息：" + response.message());
            Log.d("onResponse", "#######################################################################");
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    onPreExcute();

                    onErrorResponse(response);

                }
            });

        }
    }

    /**
     * 异地登录，强制退出
     *
     * @param response
     */
    private void onForceExcute(String response) {
        synchronized (String.class){
            try {
                JSONObject jsonObject = new JSONObject(response);
                int status = jsonObject.getInt("status");
                String message = "账号在另外设备登录";
                if (status == 10001) {
                    ActivityManager am = (ActivityManager) BaseApplication.mApplicationContext.getSystemService(ACTIVITY_SERVICE);
                    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                    if (!cn.getClassName().equalsIgnoreCase(LoginActivity.class.getName())) {
                        //清空token
                        SaveUtil.putString("Authorization", "");
                        Toast.makeText(BaseApplication.mApplicationContext, message , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BaseApplication.mApplicationContext, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        BaseApplication.mApplicationContext.startActivity(intent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 得到服务器响应，统一处理一些操作，比如：终止刷新、加载更多。。。
     */
    public void onPreExcute() {

    }

    /**
     * 网络请求成功，http状态码不为200
     */
    public void onErrorResponse(Response response) {
        if (response.code() == 401) {
            ActivityManager am = (ActivityManager) BaseApplication.mApplicationContext.getSystemService(ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            if (!cn.getClassName().equalsIgnoreCase(LoginActivity.class.getName())) {
                //清空token
                SaveUtil.putString("Authorization", "");
                Toast.makeText(BaseApplication.mApplicationContext, "账号在另外设备登录" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BaseApplication.mApplicationContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                BaseApplication.mApplicationContext.startActivity(intent);
            }
        }
    }

    /**
     * 网络请求成功，http状态码200
     *
     * @param t
     */
    public abstract void onSuccessExecute(T t);

}
