package com.jason.feick.net;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class TwoWaysAuthenticationSSLSocketFactory {
	private static final String KEY_STORE_TYPE_BKS = "bks";
	private static final String KEY_STORE_TYPE_P12 = "PKCS12";

	private static final String keyStoreFileName = "4zlink.p12";
	private static final String keyStorePassword = "fafnir";
	private static final String trustStoreFileName = "server.bks";
	private static final String trustStorePassword = "fafnir";
	private static final String alias = "1";//"client";
	private static Context pContext = null;

	/**
	 * 获取SSLContext
	 *
	 * @param context 上下文
	 * @return SSLContext
	 */
	public static SSLContext getSSLContext(Context context) {
		try {
			// 服务器端需要验证的客户端证书
			KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
			// 客户端信任的服务器端证书
			KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);

			InputStream ksIn = context.getResources().getAssets().open(keyStoreFileName);
			InputStream tsIn = context.getResources().getAssets().open(trustStoreFileName);
			try {
				keyStore.load(ksIn, keyStorePassword.toCharArray());
				trustStore.load(tsIn, trustStorePassword.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					ksIn.close();
				} catch (Exception ignore) {
				}
				try {
					tsIn.close();
				} catch (Exception ignore) {
				}
			}
			SSLContext sslContext = SSLContext.getInstance("TLS");
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(trustStore);
//			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());
			sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
			return sslContext;
		} catch (Exception e) {
			Log.e("tag", e.getMessage(), e);
		}
		return null;
	}


}