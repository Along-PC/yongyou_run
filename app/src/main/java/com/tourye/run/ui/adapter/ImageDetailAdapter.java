package com.tourye.run.ui.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.photoview.PhotoView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.run.R;
import com.tourye.run.base.BaseApplication;
import com.tourye.run.ui.dialogs.DownloadImageDialog;
import com.tourye.run.utils.PermissionDialogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by longlongren on 2018/10/31.
 * <p>
 * introduce:图片详情适配器
 */

public class ImageDetailAdapter extends PagerAdapter {
    private Activity mContext;
    private LayoutInflater mLayoutInflater;
    private List<String> mStringList = new ArrayList<>();

    private OnImageClickListener mOnImageClickListener;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, "图片成功下载至相册当前应用文件夹下", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public ImageDetailAdapter(Activity context, List<String> stringList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mStringList = stringList;
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = mLayoutInflater.inflate(R.layout.item_activity_image_detail, container, false);
        PhotoView photoView = inflate.findViewById(R.id.photo_item_image_detail);
        final String url = mStringList.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(BaseApplication.mApplicationContext).load(url).apply(requestOptions).into(photoView);
        container.addView(inflate);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnImageClickListener!=null) {
                    mOnImageClickListener.onClick();
                }
            }
        });
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DownloadImageDialog downloadImageDialog = new DownloadImageDialog(mContext);
                downloadImageDialog.setDownloadCallback(new DownloadImageDialog.DownloadCallback() {
                    @Override
                    public void download() {
                        Glide.with(BaseApplication.mApplicationContext)
                                .asBitmap()
                                .load(url)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        getPermission(url,resource);
                                    }
                                });

                    }
                });
                downloadImageDialog.show();
                return true;
            }
        });
        return inflate;
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }

    /**
     * 获取权限
     */
    public void getPermission(final String url, final Bitmap resource) {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            downloadImage(resource);
                        } else {
                            PermissionDialogUtil.showPermissionDialog(mContext, "缺少存储权限，请前往手机设置开启");
                        }
                    }
                });

    }

    //下载图片
    public void downloadImage(Bitmap resource){
        FileOutputStream fileOutputStream = null;

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "tourye";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            long time = System.currentTimeMillis();
            File imageFile = new File(file, time + ".jpeg");
            fileOutputStream = new FileOutputStream(imageFile);
            resource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(imageFile);
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            mHandler.sendEmptyMessage(1);
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(2);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    public interface OnImageClickListener{
        public void onClick();
    }


}
