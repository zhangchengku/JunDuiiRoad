package com.mobo.zggkgisandroid.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import android.util.Base64;

public class Gzip {

	private static final int BUFFERSIZE = 1024;
	private static final int MAXLENGTH = 1024 * 1024;

	/***
	 * 
	 * 接口返回的字符串
	 * 
	 * @param str
	 * @return json字符串
	 * @throws IOException
	 */

	public static String unGzip(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		byte[] decode = Base64.decode(str, Base64.DEFAULT);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(decode);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[1024];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)

		return out.toString();
	}

}
