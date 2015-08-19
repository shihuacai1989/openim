package com.openim.common.util;

/**
 * Created by shihc on 2015/8/19.
 */
public class FileUtil {
    public static String getSuffix(String fileName){
        if(fileName == null || fileName.trim().length() == 0){
            throw new IllegalArgumentException("参数不可为空");
        }

        int i = fileName.lastIndexOf('.');
        int leg = fileName.length();

        if(i < 0){//形如 gitignore
            return null;
        }else if(i == 0){//形如 .gitignore
            return  fileName.substring(1);
        }else if((i + 1) == leg){//形如 readme.
            return null;
        }else{
            return fileName.substring(i + 1);
        }
    }
}
