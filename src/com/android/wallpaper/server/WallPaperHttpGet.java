package com.android.wallpaper.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

import com.android.wallpaper.Util.StoreUsetData;

public class WallPaperHttpGet {

	private XmlHelper xmlHelper = null;
	private WallPaperResponse response;
	private String tag = "wallpaper-HttpGet";
	int timeoutConnection = 10000; // 10seconds
	int statuscode;
	private static final int RESULT_OK = 200;
	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";

	public WallPaperHttpGet() {
		xmlHelper = new XmlHelper();
		response = new WallPaperResponse();
	}

	public WallPaperResponse handleRequestData(int command,
			WallPaperRequest _wallpaperRequest) throws Exception {

		switch (command) {

		case StoreUsetData.IMAGEURLS_REQ:

			WallPaperResponse getresponse = GetData(_wallpaperRequest.requrl,
					command);
			Log.i(tag, "response=" + getresponse);

			break;
		}
		return response;
	}

	private WallPaperResponse GetData(String reqURL, int commmand) {
		InputStream is = null;
		HttpResponse httpResponse = null;
		HttpGet request = null;
		HttpParams httpParameters = new BasicHttpParams();
		HttpClient httpClient = new DefaultHttpClient(httpParameters);
		

		// set socket timeout
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);

		HttpConnectionParams.setSoTimeout(httpParameters, timeoutConnection);

		try {
			request = new HttpGet(reqURL);
			request.addHeader("Accept", "application/xml");
			httpResponse = httpClient.execute(request);
			is = httpResponse.getEntity().getContent();
			statuscode = httpResponse.getStatusLine().getStatusCode();
			if(statuscode==RESULT_OK)
			{
				response=xmlHelper.parseXmlStream(is);
				response.isSuccess=true;
			}
			else
			{
				response.errorcode=statuscode;
				response.isSuccess=false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return response;

	}

}
