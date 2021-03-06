package com.shruti.searchapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class Util {

    public static boolean isNetworkAvailable(Context context) {
        Log.d("Inside NetworkUtil", "isNetworkAvailable ");
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable() && cm
                .getActiveNetworkInfo().isConnected());
    }

}
