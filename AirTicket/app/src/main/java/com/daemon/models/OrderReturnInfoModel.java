package com.daemon.models;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daemon.consts.Constants;
import com.daemon.utils.ErrorCodeUtil;
import com.daemon.utils.VolleyUtil;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.HashMap;

public class OrderReturnInfoModel extends BaseModel {
    private Context mContext;

    public OrderReturnInfoModel(Handler handler, Context context) {
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
        HashMap<String, String> params_map = null;
        RequestQueue requestQueue;
        String url;
        StringRequest request;
        switch (changeStateMessage.what) {
            case Constants.MODEL_ORDER_TICKET_ENDORSE:

                break;


            case Constants.MODEL_ORDER_TICKET_RETURN:
                params_map = (HashMap<String, String>) changeStateMessage.obj;
                url = "http://121.40.116.51:9000/OrderAPI/tuiFeiOrder" + VolleyUtil.formatGetParams(params_map);
                requestQueue = Volley.newRequestQueue(mContext);
                request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                            if (!TextUtils.isEmpty(s)) {
                                Log.e(getTAG(),s);
                                XmlParserCreator parserCreator = new XmlParserCreator() {
                                    @Override
                                    public XmlPullParser createParser() {
                                        try {
                                            return XmlPullParserFactory.newInstance().newPullParser();
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                };
                                try {
                                        XmlPullParser xmlPullParser = parserCreator.createParser();
                                        xmlPullParser.setInput(new StringReader(s));
                                        int eventType = xmlPullParser.getEventType();
                                        while (eventType != XmlPullParser.END_DOCUMENT) {
                                            switch (eventType) {
                                                case XmlPullParser.START_TAG:
                                                    if ("string".equals(xmlPullParser.getName())) {
                                                    if ("Result".equals(xmlPullParser.getName())) {
                                                        String message = "";
                                                        if("T".equals(xmlPullParser.nextText())){
                                                             message= "退废票成功";
                                                        }else if("F".equals(xmlPullParser.nextText())){
                                                             message = "退废票失败";
                                                        }else{
                                                             message = "未知错误";
                                                        }
                                                        Message.obtain(handler, Constants.VIEW_ORDER_TICKET_RETURN, message).sendToTarget();
                                                        return;
                                                    }else{
                                                        String message = ErrorCodeUtil.getErrorMessage(mContext, xmlPullParser.nextText());
                                                        Message.obtain(handler, Constants.VIEW_ORDER_TICKET_RETURN, message).sendToTarget();
                                                    }
                                                    }
                                                    break;
                                            }
                                            eventType = xmlPullParser.next();
                                        }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    String message = "解析xml出错";
                                    Message.obtain(handler, Constants.VIEW_ORDER_TICKET_RETURN, message).sendToTarget();
                                }
                            }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = "网络出错";
                        Message.obtain(handler, Constants.VIEW_ORDER_TICKET_RETURN, message).sendToTarget();
                        Log.e("sdfsdfsdfsd", "onErrorResponse=" + volleyError.getMessage());
                    }
                });
                requestQueue.add(request);
                break;
        }
    }

}
