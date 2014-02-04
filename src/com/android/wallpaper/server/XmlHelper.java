package com.android.wallpaper.server;

import java.io.InputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class XmlHelper {

	WallPaperResponse response = new WallPaperResponse();

	public WallPaperResponse parseXmlStream(InputStream is) {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();

			parser.setInput(is, "UTF-8");
			int eventType = parser.getEventType();
			ArrayList<String> urls = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				String name = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					urls = new ArrayList<String>();
					break;

				case XmlPullParser.START_TAG:
					name = parser.getName();

					if (name.equalsIgnoreCase("url"))
						urls.add(parser.nextText());

				case XmlPullParser.END_TAG:
					break;
				}
				eventType = parser.next();
			}

			response.urls=urls;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
