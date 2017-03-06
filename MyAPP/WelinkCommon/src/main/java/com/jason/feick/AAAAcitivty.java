/**
 * Copyright (c) 2013 An Yaming,  All Rights Reserved
 */
package com.jason.feick;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jason.feick.util.ToastUtil;
import com.jason.feick.widget.LoadingDataDialogManager;

/**
 * 同时包含了afinal的AfinalActivity和AYMActivity的功能
 * 
 * @author 亚明
 * 
 */
public class AAAAcitivty extends FragmentActivity {
	/**
	 * 提示toast的展示对象 可直接调用期show方法展示文字
	 */
	public ToastUtil toast;

	/**
	 * 模拟toast展示加载数据的对话框，使界面更加友好
	 */
	public LoadingDataDialogManager loadingToast;

	/**
	 * 初始化了toast 同时也初始化加载数据对话框
	 * 
	 * @see Activity#onCreate(Bundle);
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		toast = ToastUtil.initToast(this);
		loadingToast = LoadingDataDialogManager.init(this,"努力加载中...");
	}

	/**
	 * 将展示的加载数据的对话框释放掉
	 * 
	 * @see Activity#onDestroy();
	 */
	@Override
	protected void onDestroy() {
		if (null != loadingToast) {
			loadingToast.destroy();
		}
		super.onDestroy();
	}
}
