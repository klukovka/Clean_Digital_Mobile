package com.yuliia_koba.clean_digital_mobile.services;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesService {


    public static final String STORAGE_NAME = "CleanDigital";
    public static final String TOKEN = "TOKEN";

    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;
    private static Context context;

    public static void init( Context cntxt ){
        context = cntxt;
    }

    private static void init(){
        settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public static void setToken( String value ){
        if( settings == null ){
            init();
        }
        editor.putString( TOKEN, value );
        editor.commit();
    }

    public static String getToken(){
        if( settings == null ){
            init();
        }
        return settings.getString( TOKEN, "" );
    }

    public static String getHeader(){
        if( settings == null ){
            init();
        }
        return "Bearer " + settings.getString( TOKEN, "" );
    }
}