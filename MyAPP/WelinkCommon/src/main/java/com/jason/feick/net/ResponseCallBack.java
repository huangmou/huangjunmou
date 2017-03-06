package com.jason.feick.net;

public interface ResponseCallBack {
	/**
		 * 
		 * @param msage
		 *            返回请求数据
		 * @param what
		 *            此次请求id
		 * @param index
		 *            请求状态-1为请求成功
		 */
		public void response(String msage, int what, int index);

	}