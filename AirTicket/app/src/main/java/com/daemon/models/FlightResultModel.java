package com.daemon.models;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.daemon.consts.Constants;

public class FlightResultModel extends BaseModel{
    private Context mContext;
	public FlightResultModel(Handler handler, Context context) {
		super(handler);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	public void changeModelState(int changeState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeModelState(Message changeStateMessage) {
		// TODO Auto-generated method stub
		switch (changeStateMessage.what){
			case Constants.MODEL_TICKET_BOOK:

//				HashMap<String,String> params_map = (HashMap<String,String>)changeStateMessage.obj;
//				//String url =  Constants.URL_FLIGHT_LIST_V1+VolleyUtil.formatGetParams(params_map);
//                String url = "http://121.40.116.51:9000/FlightAPI/getFlightList?Username=zhangsan&Scity=CNH&Ecity=SHA&Date=2016-01-29";
//				RequestQueue requestQueue = Volley.newRequestQueue(mContext);
//                XMLRequest request = new XMLRequest(url, new Response.Listener<XmlPullParser>() {
//                    @Override
//                    public void onResponse(XmlPullParser xmlPullParser) {
//                        //Log.e(getTAG(),xmlPullParser.getText());
//                        try {
//                            int eventType = xmlPullParser.getEventType();
//                            while (eventType != XmlPullParser.END_DOCUMENT) {
//                                switch (eventType) {
//                                    case XmlPullParser.START_TAG:
//                                        if("string".equals(xmlPullParser.getName())){
//                                            Log.e(getTAG(), ErrorCodeUtil.getErrorMessage(mContext, xmlPullParser.nextText()));
//                                        }
//
//                                        break;
//                                }
//                                eventType = xmlPullParser.next();
//                            }
//                        } catch (XmlPullParserException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        //Log.e(getTAG(),volleyError.getMessage());
//                    }
//                });
//				requestQueue.add(request);
				break;
		}
	}

}
