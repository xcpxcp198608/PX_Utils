package com.px.px_utils.ApkAutoUpdate.Utils;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/8/19.
 */
public class FormatNumber {
    //将long类型的文件大小转化为2位小数单位为Mb的数值；
    public static String long2Mb (long fileSize){
        return new DecimalFormat("#.00").format(fileSize*0.01d/1024/1024*100);
    }


}
