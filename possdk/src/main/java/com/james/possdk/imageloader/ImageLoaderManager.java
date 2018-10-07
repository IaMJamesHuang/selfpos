package com.james.possdk.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.james.possdk.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by James on 2018/9/17.
 */
public class ImageLoaderManager {

    private final static int THREAD_COUNT = 4;
    private final static int PRIORITY = 2;
    private final static int DISK_CACHE_SIZE = 50 * 1024;
    private final static int CONNECTING_TIME_OUT = 5*1000;
    private final static int READ_TIME_OUT = 30 * 1000;

    private static ImageLoaderManager mInstance;
    private static ImageLoader mImageLoader;


    public static ImageLoaderManager getInstance(Context context){
        if (mInstance == null) {
            synchronized (ImageLoaderManager.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderManager(context);
                }
            }
        }
        return mInstance;
    }

    private ImageLoaderManager(Context context) {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(THREAD_COUNT)
                .threadPriority(Thread.NORM_PRIORITY-PRIORITY)
                .denyCacheImageMultipleSizesInMemory() //禁止缓存多套尺寸
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(DISK_CACHE_SIZE)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //缓存文件命名
                .tasksProcessingOrder(QueueProcessingType.LIFO) //图片下载顺序
                .defaultDisplayImageOptions(getDefaultOptions())
                .imageDownloader(new BaseImageDownloader(context, CONNECTING_TIME_OUT, READ_TIME_OUT)) //设置下载器
                .writeDebugLogs()
                .build();
         mImageLoader = ImageLoader.getInstance();
         mImageLoader.init(configuration);
    }

    private DisplayImageOptions getDefaultOptions() {
        return  new DisplayImageOptions.Builder()
                .showImageForEmptyUri(getFailOrErrorImg())
                .showImageOnFail(getFailOrErrorImg())
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public void displayImage(ImageView imageView, String url) {
        displayImage(imageView, url, null, null);
    }

    public void displayImage(ImageView imageView, String url, ImageLoadingListener listener) {
        displayImage(imageView, url, null, listener);
    }

    public void displayImage(ImageView imageView, String url, DisplayImageOptions options, ImageLoadingListener listener) {
        if (mImageLoader != null) {
            mImageLoader.displayImage(url, imageView, options, listener);
        }
    }

    protected int getFailOrErrorImg() {
        return R.drawable.ic_launcher_background;
    }

}
