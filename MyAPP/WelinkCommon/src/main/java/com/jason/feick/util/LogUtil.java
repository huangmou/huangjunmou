package com.jason.feick.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


 
/**
 * @author JasonHu<xiaoxiangtata#gmail.com>
 *
 */
public class LogUtil {
 
	public static boolean debug = true;
	
	public static void setDebug(boolean isDebug){
		debug = isDebug;
	}
	/**
	 * 打印lo，同时在屏幕上输出msg通知
	 * @param context
	 * @param tag
	 * @param msg
	 */
	public static void Log(Context context,String tag,String msg) {
		if (debug) {
			android.util.Log.d(tag, msg);		
			showToast(context,msg);
		}
	}
	
	/**
	 * 打印log到console
	 * @param tag
	 * @param msg
	 */
	public static void Log(String tag,String msg) {
		if (debug) {
			if (msg != null && !TextUtils.isEmpty(msg.trim())) {
				android.util.Log.d(tag,  msg);
			}else {
				android.util.Log.d(tag,  "为空");	
			}	
		}
	}
	
	/**
	 * 输出提示到屏幕通知
	 * @param context
	 * @param message
	 */
	public static void showToast(Context context, String message) {
//		if (debug) {
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//		}
		
	}

	public static void Log(String tag, String message, Exception exception) {
		if (debug) {
		android.util.Log.e(tag, message, exception);
		}
	}

}
