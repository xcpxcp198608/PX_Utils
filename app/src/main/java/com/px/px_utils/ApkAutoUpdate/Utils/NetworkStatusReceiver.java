package com.px.px_utils.ApkAutoUpdate.Utils;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 *  需要添加权限:<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 *  需要对广播进行注册：<receiver android:name=".NetworkStatusReceiver">
                            <intent-filter>
                            <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
                            </intent-filter>
                    </receiver>
 *
 */
public class NetworkStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(networkInfo == null){
            Toast.makeText(context , "Network connect error" ,Toast.LENGTH_LONG).show();
            return;
        }
        if(wifiNetworkInfo.isConnected()){
            Toast.makeText(context , "Wifi was connected" ,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context , "Wifi connected interruption" ,Toast.LENGTH_LONG).show();
        }
        if(mobileNetworkInfo.isConnected()){
            //Toast.makeText(context , "Mobile network was connected" ,Toast.LENGTH_LONG).show();
        }else {

        }
    }
}
