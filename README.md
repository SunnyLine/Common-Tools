# Common-Tools
## 工具库
## 项目使用
**在项目跟目录build.gradle中添加如下代码**
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
**在对应的model的gradle文件添加如下依赖**
```
dependencies {
	        implementation 'com.github.SunnyLine:Common-Tools:1.0.11'
	}
```
**本Model中，使用了com.android.support:appcompat-v7:28.0.0库，如果项目中包冲突了，如下设置：**
```
dependencies {
	        implementation('com.github.SunnyLine:Common-Tools:1.0.11') {
        		exclude group: "com.android.support"
    		}
	}
```
### 内容如下：
|类名|说明|
|---|----|
|Base64Util|Base64 编码工具|
|MD5Util|MD5 工具类|
|DesUtil|DES 对称加解密|
|RSAUtil|RSA 加解密工具|
|SHA1Util|SHA1 工具|
|SPUtil|SharedPreference 工具|
|BitmapUtil|Bitmap 工具|
|CloseableUtil|IO 关闭工具，避免多次try-catch|
|CollectionUtil|集合工具，判空，去重，打印之类|
|HandlerUtil|Handler 工具，回主线程工具|
|HexUtil|十六进制工具|
|Log|日志，有开关控制打不打印|
|KeyboardUtil|软键盘工具，打开关闭软键盘|
|FileUtil|文件工具类，创建文件夹，删除文件、文件夹，读写文件等|
|DensityUtil|密度尺寸工具类，获取屏幕宽高，密度；dp,px单位之间的转换|
|AppUtil|一些基础工具，当前前台的进程，打开相册、摄像头，打开应用，安装apk，发短信打电话等|
|ViewHelper|查找View等一些操作|
|CommonPopupWindow|公共的泡泡框|
|LimitLengthFilter|限制长度的过滤器，EditText使用|
|AutoAddZeroFilter|EditText 输入Float类型，输入.自动在前面补0|
|DateFormatUtil|时间格式转化工具类|
|ZipUtil|解压工具|
|PhoneUtil|获取手机信息工具类|
