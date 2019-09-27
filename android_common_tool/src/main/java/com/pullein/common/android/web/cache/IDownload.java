package com.pullein.common.android.web.cache;

import java.io.IOException;
import java.io.InputStream;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/9/27
 */
public interface IDownload {
    InputStream download(String requestUri) throws IOException;
}
