package com.mobo.zggkgisandroid.WebInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

/**
 * http 访问工具
 * 
 * 
 */

public class HttpUtils {

	public HttpUtils() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * get访问接口
	 * 
	 * @param adress_Http
	 *            地址路径
	 * @return String 返回类型
	 */
	public String getSendRequest(String adress_Http) {

		String returnLine = "";
		try {

			System.out.println("**************开始http通讯**************");
			System.out.println("**************调用的接口地址为**************"
					+ adress_Http);
			URL my_url = new URL(adress_Http);
			HttpURLConnection connection = (HttpURLConnection) my_url
					.openConnection();

			connection.setReadTimeout(600000);
			connection.setRequestMethod("GET");

			connection.connect();

			if (connection.getResponseCode() == 200) {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream(),
								"utf-8"));

				String line = "";
				//System.out.println("Contents of post request start");
				while ((line = reader.readLine()) != null) {
					returnLine += line;
					//System.out.println(line);
				}

				//System.out.println("Contents of post request ends");
				reader.close();
				connection.disconnect();
			}
			System.out.println("========返回的结果的为========" + returnLine);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnLine;

	}
}
