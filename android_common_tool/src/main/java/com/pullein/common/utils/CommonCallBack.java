package com.pullein.common.utils;

/**
 * Common-Tools<br>
 * describe ：不建议使用此类,使用新接口{@link com.pullein.common.android.listener.OneParameterListener}
 *
 * @author xugang
 * @date 2019/8/1
 * @see com.pullein.common.android.listener.NoParameterListener
 * @see com.pullein.common.android.listener.TwoParameterListener
 * @see com.pullein.common.android.listener.MulResultListener
 */
@Deprecated
public interface CommonCallBack<T> {
    void onResult(T t);
}
