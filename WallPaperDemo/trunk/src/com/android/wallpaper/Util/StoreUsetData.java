package com.android.wallpaper.Util;

public class StoreUsetData {
	
	public static final int IMAGEURLS_REQ=1;
	public static final String FORMATTEXT="format";
	public static final String FORMATVAL="xml";
	
	public static final String RESULTTEXT="results_per_page";
	public static final String RESULTVAL="24";
	
	public static final String TYPETEXT="type";
	public static final String TYPEVAL="png,jpg";
	
	public static final String EQUALSIGN="=";
	public static final String EMPSIGN="&";
	
	public static final String GETURL="http://thecatapi.com/api/images/get?"+FORMATTEXT+EQUALSIGN+FORMATVAL+EMPSIGN+RESULTTEXT+EQUALSIGN+RESULTVAL+EMPSIGN+TYPETEXT+EQUALSIGN+TYPEVAL;
	public static final String FILEPATH="/sdcard/1.png";
	

}
