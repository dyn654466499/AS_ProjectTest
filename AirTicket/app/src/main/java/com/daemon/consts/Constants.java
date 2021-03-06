package com.daemon.consts;

import java.util.HashMap;

public class Constants {
	public static final int VOLLEY_TIME_OUT = 15000;

	/**
	 * 城市三字码的SharePreference key
	 */
	public static final String KEY_SP_THREE_WORD = "three_word";
	public static final String KEY_SP_CITY = "city";
	public static final String KEY_SP_AIR_PORT = "airPort";
	public static final String KEY_SP_AIR_LINE = "airLine";
	public static final String KEY_SP_CABIN = "cabin";
	public static final String KEY_SP_ORDER_STATUS = "order_status";
	public static final String KEY_SP_PAY_STATUS = "pay_status";

	public static final String KEY_TYPE = "类型";
	public static final String KEY_TYPE_CABIN = "Cabin";
	public static final String KEY_TYPE_CERT = "证件类型";
	public static final String KEY_TYPE_TICKET_DISTRIBUTE = "机票配送方式";
	
	public static final String KEY_TYPE_CABIN_POSITION = "舱位记录位置";
	public static final String KEY_TYPE_PASSENGER_CERT_POSITION = "第几个乘机人证件";
	
	public static final String KEY_TYPE_DATE = "日期";
    public static final String KEY_DATE_LEAVE = "Date";
    public static final String KEY_DATE_ARRIVE = "到达日期";
	
	public static final String KEY_TITLE_DATE = "标题日期";
	public static final String KEY_TITLE = "标题";
	
	public static final String KEY_CITY = "城市";
    public static final String KEY_CITY_LEAVE = "Scity";
    public static final String KEY_CITY_ARRIVE = "Ecity";
	public static final String KEY_USERNAME = "Username";

	public static final String KEY_CHANGE = "Change";
	public static final String KEY_RETURN = "Return";

	public static final String KEY_INSURE_PRICE = "保险价格";
	public static final String KEY_INSURE_NAME = "保险名称";
	
	public static final String KEY_PARCELABLE = "parcelable";
	/**
	 * activity的result code
	 */
	public static final int REQUEST_CODE_CABIN = 100;
	public static final int REQUEST_CODE_CERTIFICATE = 101;
	public static final int REQUEST_CODE_DATE_LEAVE = 102;
	public static final int REQUEST_CODE_DATE_ARRIVE = 103;
	public static final int REQUEST_CODE_DISTRIBUTE = 104;
	public static final int REQUEST_CODE_CITY_LEAVE = 105;
	public static final int REQUEST_CODE_CITY_ARRIVE = 106;
	public static final int REQUEST_CODE_CITY = 107;
	public static final int REQUEST_CODE_SET_HEAD = 108;

	/* 请求识别码 */
	public static final int REQUEST_CODE_GALLERY = 109;
	public static final int REQUEST_CODE_CAMERA = 110;
	public static final int REQUEST_CODE_RESULT = 111;
    /**
     * model请求参数
     */
    public static final int MODEL_FLIGHT_SEARCH = 200;
    public static final int MODEL_TICKET_BOOK = 201;
    public static final int MODEL_ORDER_TICKET_COMMIT = 202;

	public static final int MODEL_ORDER_TICKET_QUERY = 203;
	//退废票
	public static final int MODEL_ORDER_TICKET_RETURN = 204;
	//改签
	public static final int MODEL_ORDER_TICKET_ENDORSE = 205;
	public static final int MODEL_ORDER_TICKET_ADD = 206;

	public static final int MODEL_ORDER_TICKET_ORDER_NO_QUERY = 207;

	public static final int MODEL_ORDER_CATERING_QUERY = 208;

	public static final int MODEL_ORDER_CATERING_DETAIL_QUERY = 209;
	public static final int MODEL_ORDER_CATERING_GOODS_DETAIL_QUERY = 210;

	public static final int MODEL_ORDER_LOCAL_CITY_QUERY = 211;
	public static final int MODEL_ORDER_LOCAL_CITY_DETAIL_QUERY = 212;
    /**
     *  view状态改变参数
     */
    public static final int VIEW_FLIGHT_SEARCH = 300;
    public static final int VIEW_TICKET_BOOK = 301;
    public static final int VIEW_ORDER_TICKET_COMMIT = 302;
	public static final int VIEW_ORDER_TICKET_QUERY = 303;
	//退废票
	public static final int VIEW_ORDER_TICKET_RETURN = 304;
	//改签
	public static final int VIEW_ORDER_TICKET_ENDORSE =305;
	public static final int VIEW_ORDER_TICKET_ADD =306;
	public static final int VIEW_ORDER_TICKET_ORDER_NO_QUERY = 307;
	public static final int VIEW_ORDER_CATERING_QUERY = 308;
	public static final int VIEW_ORDER_CATERING_DETAIL_QUERY = 309;
	public static final int VIEW_ORDER_CATERING_GOODS_DETAIL_QUERY = 310;

	public static final int VIEW_ORDER_LOCAL_CITY_QUERY = 311;
	public static final int VIEW_ORDER_LOCAL_CITY_DETAIL_QUERY = 312;
    /**
     * 请求URL
     */
    public static final String URL_FLIGHT_LIST_V1 = "http://121.40.116.51:9000/FlightAPI/getFlightList_V1";
    public static final String URL_FLIGHT_LIST = "http://121.40.116.51:9000/FlightAPI/getFlightList";

	public static HashMap<String, String>  HOLIDAYS = new HashMap<String, String>();
	static {
		HOLIDAYS.put("2014-1-1", "元旦");
		HOLIDAYS.put("2014-1-30", "除夕");
		HOLIDAYS.put("2014-1-31", "春节");
		HOLIDAYS.put("2014-2-14", "元宵节");
		HOLIDAYS.put("2014-3-8", "妇女节");
		HOLIDAYS.put("2014-4-1", "愚人节");
		HOLIDAYS.put("2014-4-5", "清明节");
		HOLIDAYS.put("2014-5-1", "劳动节");
		HOLIDAYS.put("2014-6-2", "端午节");
		HOLIDAYS.put("2014-8-2", "七夕");
		HOLIDAYS.put("2014-9-10", "教师节");
		HOLIDAYS.put("2014-9-19", "中秋节");
		HOLIDAYS.put("2014-10-1", "国庆节");
		HOLIDAYS.put("2014-10-2", "重阳节");
		HOLIDAYS.put("2014-11-11", "光棍节");
		HOLIDAYS.put("2014-12-24", "平安夜");
		HOLIDAYS.put("2014-12-25", "圣诞节");
	}
}
