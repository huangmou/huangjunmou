package com.jason.feick.task;

import android.content.Context;

import com.jason.feick.net.ResponseCallBack;
import com.jason.feick.net.ThreadPoolManager;

import java.util.HashMap;

public class TaskManager {
	
	
	
	public static synchronized MyTask getConfigure(Context context, ResponseCallBack callBack, String method, HashMap<String, Object> par, int mark,String token,String userId){
		MyTask task  = new MyTask(context, callBack, par, method,"",  mark,token,userId);
		ThreadPoolManager.getInstance().execute(task);
		task.log();
		return task;
	}
	

	
	/**
	 * 获取客户端版本信息
	 * @param context
	 * @param callBack1
	 * @param par
	 * @param mark
	 */
	public static void startGetAppVersionTask(Context context, ResponseCallBack callBack1,  HashMap<String, Object> par, int mark,String token,String userId){
		MyTask task  = new MyTask(context, callBack1, par,"GetAppVersionOrGray","Config",  mark,token,userId);
		ThreadPoolManager.getInstance().execute(task);
		task.log();
	}
	
	/**
	 * 获取手机验证码
	 * @param context
	 * @param callBack1
	 * @param par
	 * @param mark
	 */
	public static MyTask startGetPhoneCodeByPhoneTask(Context context, ResponseCallBack callBack1,  HashMap<String, Object> par, int mark,String token,String userId){
		MyTask task  = new MyTask(context, callBack1, par,"GetPhoneCodeByPhone","Customer",  mark,token,userId);
		ThreadPoolManager.getInstance().execute(task);
		task.log();
		return task;
	}
	
	/**
	 * 用户登录，获得customerId
	 * @param context
	 * @param callBack1
	 * @param par
	 * @param mark
	 * @return
	 */
	public static MyTask startLoginTask(Context context, ResponseCallBack callBack1,  HashMap<String, Object> par, int mark,String token,String userId){
		MyTask task  = new MyTask(context, callBack1, par,"Login", "Customer", mark,token,userId);
		ThreadPoolManager.getInstance().execute(task);
		task.log();
		return task;
	}
	
	/**
	 * 获取商家信息
	 * @param context
	 * @param callBack
	 * @param par
	 * @param mark
	 * @return
	 */
	public static MyTask startGetMerchantInfo(Context context, ResponseCallBack callBack,  HashMap<String, Object> par, int mark,String token,String userId){
		MyTask task  = new MyTask(context, callBack, par,"GetMerchantInfo", "Customer", mark,token,userId);
		ThreadPoolManager.getInstance().execute(task);
		task.log();
		return task;
	}
	
	/**
	 * 保存商家信息
	 * customerId 7252
	 * @param context
	 * @param callBack
	 * @param par
	 * @param mark
	 * @return
	 */
	public static MyTask startSaveMerchantInfoTask(Context context, ResponseCallBack callBack,  HashMap<String, Object> par, int mark,String token,String userId){
		MyTask task  = new MyTask(context, callBack, par,"SaveMerchantInfo", "Customer", mark,token,userId);
		ThreadPoolManager.getInstance().execute(task);
		task.log();
		return task;
	}
	
	/**
	 * 获取商户类型
	 * @param context
	 * @param callBack
	 * @param mark
	 * @return
	 */
	public static MyTask startGetMerchantTypesTask(Context context, ResponseCallBack callBack, int mark,String token,String userId){
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("sBuffer", "{}");
		MyTask task  = new MyTask(context, callBack, param,"GetMerchantTypes", "Config", mark,token,userId);
		ThreadPoolManager.getInstance().execute(task);
		task.log();
		return task;
	}

	/**
	 * 发布订单
	 * @param context
	 * @param callBack
	 * @param par
	 * @param mark
	 * @return
	 */
	public static MyTask startCustomerSubmitOrderTask(Context context, ResponseCallBack callBack,  HashMap<String, Object> par, int mark,String token,String userId){
		MyTask task  = new MyTask(context, callBack, par,"CustomerSubmitOrder", "Customer", mark,token,userId);
		ThreadPoolManager.getInstance().execute(task);
		task.log();
		return task;
	}
	
	/**
	 * 发件端订单查询
	 * @param context
	 * @param callBack
	 * @param par
	 * @param mark
	 * @return
	 */
	public static MyTask startGetCustomerOrdersTask(Context context, ResponseCallBack callBack,  HashMap<String, Object> par, int mark,String token,String userId){
		MyTask task  = new MyTask(context, callBack, par,"GetCustomerOrders", "Customer", mark,token,userId);
		ThreadPoolManager.getInstance().execute(task);
		task.log();
		return task;
	}
	
	/**
	 * CustomerCancelOrder发件人取消订单
	 * @param context
	 * @param callBack
	 * @param par
	 * @param mark
	 * @return
	 */
	public static MyTask startCustomerCancelOrderTask(Context context, ResponseCallBack callBack,  HashMap<String, Object> par, int mark,String token,String userId){
		MyTask task  = new MyTask(context, callBack, par,"CustomerCancelOrder", "Customer", mark,token,userId);
		ThreadPoolManager.getInstance().execute(task);
		task.log();
		return task;
	}
	
	/**
	 * CustomerConfirmOrder发件人确认订单
	 * @param context
	 * @param callBack
	 * @param par
	 * @param mark
	 * @return
	 */
	public static MyTask startCustomerConfirmOrderTask(Context context, ResponseCallBack callBack,  HashMap<String, Object> par, int mark,String token,String userId){
		MyTask task  = new MyTask(context, callBack, par,"CustomerConfirmOrder", "Customer", mark,token,userId);
		ThreadPoolManager.getInstance().execute(task);
		task.log();
		return task;
	}
	
	


}
