package com.jason.feick.task;

import android.content.Context;
import android.util.Log;

import com.jason.feick.net.GetData;
import com.jason.feick.net.GetDataConfing;
import com.jason.feick.net.ResponseCallBack;

import java.util.HashMap;

public class MyTask extends Thread {

	protected ResponseCallBack callBack;
	protected HashMap<String, Object> par;
	protected String use;
	protected String function;
	protected String userId;
	protected int mark;
	protected String token;
	protected GetData getData;
	protected Context context;
	
	public MyTask(Context mContext,ResponseCallBack callBack1,  HashMap<String, Object> parm, String function, String usage, int mark,String token,String userId) {
		super();
		callBack = callBack1;
		par = parm;
		use = usage;
		this.token=token;
		this.function = function;
		this.mark = mark;
		this.userId = userId;
		this.context = mContext;
		this.getData = new GetData(context);
	}
	
	

	@Override
	public void run() {
		getData.doPost(callBack,  GetDataConfing.getUrl(context), par,null,function, mark,token,userId);
		super.run();
	}



	@Override
	public String toString() {
		return "Thread: "+getFunction();
	}

	public void log(){
		Log.e("Params", "MyTask [callBack=" + callBack + ", par=" + par + ", use=" + use
				+ ", function=" + function + ", mark=" + mark + ", getData="
				+ getData + "]");
	}


	public ResponseCallBack getCallBack() {
		return callBack;
	}

	public void setCallBack(ResponseCallBack callBack) {
		this.callBack = callBack;
	}

	public HashMap<String, Object> getPar() {
		return par;
	}

	public void setPar(HashMap<String, Object> par) {
		this.par = par;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public GetData getGetData() {
		return getData;
	}

	public void setGetData(GetData getData) {
		this.getData = getData;
	}
	
	
 

}
