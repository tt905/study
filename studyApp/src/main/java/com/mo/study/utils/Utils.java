package com.mo.study.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by motw on 2016/12/22.
 */
public class Utils {


    /**
     * Convert Dp to Pixel
     */
    public static int dpToPx(float dp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

}
