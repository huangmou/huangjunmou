package com.jason.feick.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 
 * @author xumy
 * 
 */
public class AndroidUtils {
	/**
	 * Returns version code
	 * 
	 * @param context
	 * @return
	 */
	public static int getAppVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns version name
	 * 
	 * @return
	 */
	public static String getAppVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieve device ID
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceID(Context context) {
		String IMEI = "";
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		IMEI = telephonyManager.getDeviceId();

		return IMEI;
	}
	
	public static void call(Context context,String telNo){
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri  
				.parse("tel:" + telNo));  	
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	public static void dowloadBySystem(Context context, String url){
		Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse(url));
		context.startActivity(i);
	}
	
	public static void showSoftPad(EditText editText){
		InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED); 
	}
}
