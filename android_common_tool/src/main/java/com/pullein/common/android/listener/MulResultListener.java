package com.pullein.common.android.listener;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/8/26
 */
public interface MulResultListener<S, F> {
    void onSuccess(S s);

    void onFail(F f);
}
