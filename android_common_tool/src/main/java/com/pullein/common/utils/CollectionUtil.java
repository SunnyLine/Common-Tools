package com.pullein.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Common-Tools<br>
 * describe ：集合工具
 *
 * @author xugang
 * @date 2019/4/23
 */
public class CollectionUtil {

    public static <T> boolean isEmpty(T... array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <T> void remove(List<T> list, int index) {
        try {
            list.remove(index);
        } catch (Exception e) {
            Log.e("remove" + e.toString());
        }
    }

    public static void remove(List list, Object obj) {
        try {
            list.remove(obj);
        } catch (Exception e) {
            Log.e("remove" + e.toString());
        }
    }

    public static <T> void printList(List<T> list) {
        if (isEmpty(list)) {
            Log.d("list is Empty");
            return;
        }
        for (T t : list) {
            Log.d(t);
        }
    }

    public static <K, V> void printMap(Map<K, V> map) {
        if (isEmpty(map)) {
            Log.d("map is empty");
            return;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            Log.d("key = " + entry.getKey() + "\tvalue = " + entry.getValue());
        }
    }

    /**
     * 去除重复数据
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Collection<T> removeDuplicateData(Collection<T> list) {
        if (!isEmpty(list)) {
            Set<T> uniques = new HashSet<>(list);
            return new ArrayList<>(uniques);
        }
        return null;
    }
}
