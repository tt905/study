package com.mo.study.ui2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mo.study.R;

/**
 *  Bitmap.Config  用来描述图片的像素是怎么被存储的:
    1 - ARGB_8888: 每个像素4字节. 共32位，默认设置。
    2 - Alpha_8:   只保存透明度，共8位，1字节。
    3 - ARGB_4444: 共16位，2字节。
    4 - RGB_565:   共16位，2字节，只存储RGB值。
 */
public class BitmapActivity extends AppCompatActivity {

    private String TAG = "BitmapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        sizeBitmap();
        Bitmap b = decodeSampledBitmapFromResource(getResources(), R.drawable.mm, 200,200);
        Log.d(TAG, "count: " + b.getByteCount());
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 设置inJustDecodeBounds属性为true，只获取Bitmap原始宽高，不分配内存；
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 计算inSampleSize值；
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 真实加载Bitmap；
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // 计算出 实际的宽和高比所需要的宽高大的前提下 最大的inSampleSize
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 计算bitmap占用内存大小： with * height * (inTargetDensity/inDensity) * 每个像素占用字节
     */
    private void sizeBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mm, options);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.i(TAG, "bitmap：ByteCount = " + bitmap.getByteCount() + "---  bitmap：AllocationByteCount = " + bitmap.getAllocationByteCount());
        }else {
            Log.i(TAG, "bitmap：ByteCount = " + bitmap.getByteCount() + "---  bitmap：getByteCount = " + bitmap.getByteCount());
        }
        Log.i(TAG, "width:" + bitmap.getWidth() + "--- height:" + bitmap.getHeight());
        Log.i(TAG, "inDensity:" + options.inDensity + "--- inTargetDensity:" + options.inTargetDensity);

//        BitmapFactory.Options optionsDensity = new BitmapFactory.Options();
//        optionsDensity.inDensity = 320;
//        optionsDensity.inTargetDensity = 320;
//
//        Bitmap bitmapDensity = BitmapFactory.decodeResource(getResources(), R.drawable.mm, optionsDensity);
//        Log.e(TAG, "-----------------------");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Log.i(TAG, "bitmap：ByteCount = " + bitmapDensity.getByteCount() + "---  bitmap：AllocationByteCount = " + bitmapDensity.getAllocationByteCount());
//        }else {
//            Log.i(TAG, "bitmap：ByteCount = " + bitmapDensity.getByteCount() + "---  bitmap：getByteCount = " + bitmapDensity.getByteCount());
//        }
//        Log.i(TAG, "width:" + bitmapDensity.getWidth() + "--- height:" + bitmapDensity.getHeight());
//        Log.i(TAG, "inDensity:" + optionsDensity.inDensity + "--- inTargetDensity:" + optionsDensity.inTargetDensity);
    }
}
