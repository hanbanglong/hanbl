package org.example.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

public class PathUtils {

    public static final String JAR = ".jar";
    public static final String CLASS = ".class";
    private static final String CLASSES = "classes";

    public static String buildJsonPath(List<String> compilePath,String applicationJsonName){
        return getCompilePathFirst(compilePath)
                + File.separator
                +applicationJsonName;
    }
    private static String getCompilePathFirst(List<String> compilePath){
        if (CommonUtils.isCollectionEmpty(compilePath)){
            return StringUtils.EMPTY;
        }
        return compilePath.get(0);
    }

}
