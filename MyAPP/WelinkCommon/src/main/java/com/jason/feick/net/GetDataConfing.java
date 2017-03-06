/**
 * Copyright (c) 2013 An Yaming,  All Rights Reserved
 */
package com.jason.feick.net;


import android.app.Activity;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取数据信息的配置信息
 *
 * @author Jason
 *
 */
public class  GetDataConfing {
	/**
	 * 获取数据的服务器地址
	 */

	/*{"method":"configure.get", "timestamp":"2014-12-20 17:15:23", "format":"json", "version":"1.0", "charset":"UTF-8",
	"sign":"xxxxx", "sign_method":"md5", "app_key":"keyxxxxxxx", "app_session":"sessionxxxxxx",
	 "params":"{\"configure_key\":\"abcdefdfad\"}"}*/

	public static String url;
	public static String  methodKey = "method";
	public static String  timestampKey = "timestamp";
	public static String  formatKey = "format";
	public static String  formatValue = "json";
	public static String  versionKey = "version";
	public static String  versionValue = "1.0";
	public static String  charsetKey = "charset";
	public static String  charsetValue = "UTF-8";
	public static String  signKey = "sign";
	public static String  sign_methodKey = "sign_method";
	public static String  sign_methodValue = "md5";
	public static String  app_keyKey = "app_key";
	public static String  app_keyValue = "c41041841691b85a37802ffef6e9c417";
	public static String  app_sessionKey = "app_session";
	public static String  app_sessionValue = "806ab23cd2e4f6390c8cf79014a3dbdf";
	public static String  paramsKey = "params";
	public static String  app_screen_size = "phone_size";
	public static String  app_secretValue = "f490c4de27b60dd17b8476dd185d7d32";
	public static boolean isBussinessUrl=false;


	public static void setAppConfig(String appKeyValue, String appSessionValue, String appSecretValue){
		app_keyValue = appKeyValue;
		app_sessionValue = appSessionValue;
		app_secretValue = appSecretValue;
	}


	public static String getUrl(Context context){
		Properties pro = new Properties();
		InputStream is = null;
		try {
			is = context.getAssets().open("system.properties");
			pro.load(is);
			if (isBussinessUrl){
				url=pro.getProperty("rice_back_url");
			}else {
				url = pro.getProperty("rice_pay_url");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}

}
