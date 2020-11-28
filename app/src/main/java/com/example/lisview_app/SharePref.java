package com.example.lisview_app;

import android.content.Context;
import android.content.SharedPreferences;


public class SharePref {
    SharedPreferences sharedPreferences;
    public SharePref(Context context){
        sharedPreferences=context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }
    //Métodos para guardar el estado del modo dia/noche
    public void setNightModeState(Boolean estado){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("NightMode",estado);
        editor.commit();
    }
    //Metodo para cargar el estado actual del modo dia/noche
    public Boolean loadNightModeState(){
        Boolean state= sharedPreferences.getBoolean("NightMode",false);
        return state;
    }

}
