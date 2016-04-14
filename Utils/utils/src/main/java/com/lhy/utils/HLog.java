package com.lhy.utils;

/**
 * 
* @ClassName: HSLog
* @Description: TODO(描述)
* @author 长腿叔叔
* @date 2015-1-29 下午4:34:08
*
 */
public class HLog {

	private static final String CLASS_NAME = "com.hs.adsdk.base.utils.HSLog";
	public static boolean isDebug = true;

	private HLog() {
		throw new UnsupportedOperationException("Can't be instantiated");
	}

	public static void e(String msg) {
		if (!isDebug)
			return;
		try {
			String className = getInfo().getClassName();
			android.util.Log.e(getTaget(className), StringUtil.nullStrToEmpty(msg));
		} catch (Exception e) {
			android.util.Log.e("", StringUtil.nullStrToEmpty(msg));
		}
	}
	
	public static void e(Integer msg) {
		e(msg.toString());
	}
	
	public static void e(Boolean msg) {
		e(msg.toString());
	}

	public static void i(String msg) {
		if (!isDebug)
			return;
		try {
			String className = getInfo().getClassName();
			android.util.Log.i(getTaget(className), StringUtil.nullStrToEmpty(msg));
		} catch (Exception e) {
			android.util.Log.i("", StringUtil.nullStrToEmpty(msg));
		}
	}
	
	public static void i(Integer msg) {
		i(msg.toString());
	}
	public static void i(Boolean msg) {
		i(msg.toString());
	}

	public static void d(String msg) {
		if (!isDebug)
			return;
		try {
			String className = getInfo().getClassName();
			android.util.Log.i(getTaget(className), StringUtil.nullStrToEmpty(msg));
			
		} catch (Exception e) {
			android.util.Log.i("", StringUtil.nullStrToEmpty(msg));
		}
	}

	public static void d(Integer msg) {
		d(msg.toString());
	}
	
	public static void d(Boolean msg) {
		d(msg.toString());
	}
	public static void v(String msg) {
		if (!isDebug)
			return;
		try {
			
			String className = getInfo().getClassName();
			android.util.Log.i(getTaget(className), StringUtil.nullStrToEmpty(msg));
		} catch (Exception e) {
			android.util.Log.i("", StringUtil.nullStrToEmpty(msg));
		}
	}

	public static String getTaget(String className) {
		return className.substring(className.lastIndexOf(".") + 1) + " 第"
				+ getInfo().getLineNumber()+"行,日志内容: ";
	}

	public static void v(Integer msg) {
		v(msg.toString());
	}
	
	public static void v(Boolean msg) {
		v(msg.toString());
	}
	public static void log() {
		if (!isDebug)
			return;
		try {
			String className = getInfo().getClassName();
			android.util.Log.e(getTaget(className), "-----log()-----");
		} catch (Exception e) {
			android.util.Log.e("", "-----log()-----");
		}
	}

	private static StackTraceElement getInfo() {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		for (int i = 0; i < stacks.length; i++) {
			if(!stacks[i].getClassName().equals(CLASS_NAME)){
				return stacks[i];
			}
		}
		return null;
	}
}
