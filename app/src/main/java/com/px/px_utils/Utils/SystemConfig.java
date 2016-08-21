package com.px.px_utils.Utils;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/8/19.
 */
public class SystemConfig {

    //判断系统是否已Root
    public static boolean isRoot () {
        Process process = null;
        DataOutputStream dataOutputStream = null;
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes("check"+"\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("----px----","system no root");
            return false;
        }finally {
            try {
                if(dataOutputStream != null){
                    dataOutputStream.close();
                }
                if(process != null) {
                    process.destroy();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("----px----","system have root");
        return true;
    }

    public static boolean isNetWorkConnected() {
        return true;
    }


}
