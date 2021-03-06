/**
 * Copyright (c) 2012-2013, Michael Yang 杨福海 (www.yangfuhai.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * 我将view的exception 重写了下 将父类的转换为RuntimeException by Aym
 */
package com.jason.feick.widget;

public class ViewException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String strMsg = null;

	public ViewException(String strExce) {
		strMsg = strExce;
	}

	public void printStackTrace() {
		if (strMsg != null)
			System.err.println(strMsg);

		super.printStackTrace();
	}
}
