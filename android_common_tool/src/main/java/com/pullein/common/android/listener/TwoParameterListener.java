package com.pullein.common.android.listener;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/8/26
 */
public interface TwoParameterListener<T, E> {
    void onResult(T t, E e);
}
