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

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static void remove(List list, int index) {
        if (!isEmpty(list) && index >= 0 && index < list.size()) {
            list.remove(index);
        } else {
            Log.d("LIST ERROR ", list + " remove " + index);
        }
    }

    public static void remove(List list, Object obj) {
        if (!isEmpty(list) && null != obj) {
            list.remove(obj);
        }
    }

    public static void printList(List<? extends Object> list) {
        if (isEmpty(list)) {
            Log.d("CollectionUtil", "list is Empty");
            return;
        }
        for (Object o : list) {
            Log.d("CollectionUtil", o.toString());
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
        if (list != null) {
            Set<T> uniques = new HashSet<>();
            for (T t : list) {
                uniques.add(t);
            }
            return new ArrayList<T>(uniques);
        }
        return null;
    }
}
