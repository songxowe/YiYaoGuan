package com.example.yiyao.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
/**
 * Created by MBENBEN on 2016-5-1.
 */
public class NetService {
    public static final String BASE_URL="http://192.168.191.1:8080/Yiyao/";
    //public static final String BASE_URL="http://192.168.191.1:8080/Lagou/";
    //public static final String BASE_URL="http://192.168.43.46:8080/Lagou/";
    public static final String TAG=NetService.class.getSimpleName();
    private Context context;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static NetService mInstance;

    /**
     * 构成方法 定义成私有的 在其他类里面就不能new出来
     * @param context
     */
    private NetService(final Context context) {
        this.context = context;
        this.mRequestQueue=getRequestQueue();
        this.mImageLoader=new ImageLoader(this.getRequestQueue(), new ImageLoader.ImageCache() {
            private final int cacheSize=10*1024*1024;
            private LruCache<String,Bitmap> cache=new LruCache<>(cacheSize);
            @Override
            public Bitmap getBitmap(String s) {
                //从缓存中根据 url获取图片
                return cache.get(s) ;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                //缓存对应url的图片
                   cache.put(s,bitmap);
            }
        });

    }

    /**
     *  通过此方法获取Volley单实列，并且同步锁，确保一个Activity中只有一个实列
     * @param context
     * @return
     */
    public synchronized static NetService getInstance(Context context){
        if(mInstance==null){
            mInstance=new NetService(context);
        }
        return mInstance;
    }

    /**
     * 获取网络请求队列 获取当前程序唯一的上下文 就是application的上下文
     * @return
     */
    public  RequestQueue getRequestQueue(){
       if(mRequestQueue==null){
           mRequestQueue= Volley.newRequestQueue(context.getApplicationContext());
       }
        return mRequestQueue;
    }

    /**
     * 把网络请求添加到请求队列中,并且设置标记
     * @param request
     * @param tag
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> request,String tag){
        request.setTag(TextUtils.isEmpty(tag) ? TAG:tag);
        this.getRequestQueue().add(request);

    }

    /**
     * 如果没有设置标记 也有默认的标记
     * @param request
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> request){
        request.setTag(TAG);
        this.getRequestQueue().add(request);

    }

    public void cancelRequest(Object tag){
       if(mRequestQueue!=null){
           mRequestQueue.cancelAll(tag);
       }
    }

  public ImageLoader getImageLoader(){
      return this.mImageLoader;
  }


    public static  final String getAbsoluteUrl(String relativeUrl){

       return  BASE_URL+relativeUrl;
   }
    public RequestQueue getQueue(){


        return getRequestQueue();
    }
}
