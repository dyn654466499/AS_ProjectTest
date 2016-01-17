package com.daemon.models;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.daemon.consts.Constants;
import com.daemon.utils.VolleyUtil;
import com.daemon.volley.XMLRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

public class FlightSearchModel extends BaseModel{
    private Context mContext;
	public FlightSearchModel(Handler handler, Context context) {
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
			case Constants.MODEL_FLIGHT_SEARCH:
				HashMap<String,String> params_map = (HashMap<String,String>)changeStateMessage.obj;
				String url =  Constants.URL_FLIGHT_LIST+ VolleyUtil.formatGetParams(params_map);
				RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                XMLRequest request = new XMLRequest(url, new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser xmlPullParser) {

                        try {
                            int eventType = xmlPullParser.getEventType();
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
//                                        if("string".equals(xmlPullParser.getName())){
//                                            String message = ErrorCodeUtil.getErrorMessage(mContext, xmlPullParser.nextText());
//                                            Message.obtain(handler,Constants.VIEW_FLIGHT_SEARCH,message).sendToTarget();
//                                        }
                                        Log.e(getTAG(), xmlPullParser.getName());
                                        break;
                                    case XmlPullParser.END_TAG:

                                        break;
                                }
                                eventType = xmlPullParser.next();
                            }
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = volleyError.getMessage();
                        Message.obtain(handler,Constants.VIEW_FLIGHT_SEARCH,message).sendToTarget();
                    }
                });
				requestQueue.add(request);
				break;
		}
	}

}
