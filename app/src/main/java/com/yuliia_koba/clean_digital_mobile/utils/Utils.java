package com.yuliia_koba.clean_digital_mobile.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.TypedArrayUtils;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static <T> T[] concat(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int getMinutes(LocalDateTime dateTime){
        return (int) (dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()/60000);
    }
}
