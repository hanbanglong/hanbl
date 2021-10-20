package org.example.utils;

import java.lang.reflect.Array;
import java.util.Collection;

public class CommonUtils {

    public static <T> boolean isArrayEmpty(T[] array){
        return getLength(array)==0;
    }
    private static <T> int getLength(T[] array){
        return array==null ? 0: Array.getLength(array);
    }
    public static <T> boolean isCollectionEmpty(Collection<T> collection){
        return collection==null || collection.isEmpty();
    }
}
