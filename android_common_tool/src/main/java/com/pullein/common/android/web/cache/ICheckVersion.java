package com.pullein.common.android.web.cache;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/9/27
 */
public interface ICheckVersion {
    /**
     * 比较版本号
     *
     * @param localVersion  本地版本号
     * @param remoteVersion 远端版本号
     * @return true 意思是localVersion < remoteVersion,需要下载远端文件，反之为false
     */
    boolean compare(String localVersion, String remoteVersion);
}
