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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daemon.beans.Resp_OrderTicketInfo;
import com.daemon.beans.Resp_OrderTicketQueryInfo;
import com.daemon.consts.Constants;
import com.daemon.utils.ErrorCodeUtil;
import com.daemon.utils.VolleyUtil;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.HashMap;

public class OrderTicketModel extends BaseModel {
    private Context mContext;

    public OrderTicketModel(Handler handler, Context context) {
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
            /**
             * 提交订单
             */
            case Constants.MODEL_ORDER_TICKET_COMMIT:
                params_map = (HashMap<String, String>) changeStateMessage.obj;
//				HashMap<String,String> params_map = new HashMap<String,String>();
//				params_map.put("RateId","uK6eamCoRWc=");
//				params_map.put("PolicyId","4GO9GqZUMs5JjaGavAfxuQ==");
//				params_map.put("Name","邓耀宁");
//				params_map.put("UserName","wang87654321");
//				params_map.put("IDCard","NI452122199001140014");
//				params_map.put("Cabins","Y");
//				params_map.put("dotNum","1.60");
//				params_map.put("sCity","NGB");
//				params_map.put("eCity","PEK");
//				params_map.put("sDate","2016-01-21");
//				params_map.put("AirChangedContact","15277104415");
//				params_map.put("AutoPay","F");
//				params_map.put("Rateway","0");
//				params_map.put("Airline","CA1542");

                url = "http://121.40.116.51:9000/OrderAPI/createOrder" + VolleyUtil.formatGetParams(params_map);
                requestQueue = Volley.newRequestQueue(mContext);
                request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        if (!TextUtils.isEmpty(s)) {
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
                            GsonXml gsonXml = new GsonXmlBuilder()
                                    .setXmlParserCreator(parserCreator)
                                    .create();

                            String header = "<string xmlns=\"http://policy.jinri.cn/\"><?xml version=\"1.0\" encoding=\"gb2312\"?>";
                            String footer = "</string>";
                            String xml = s;
                            try {
                                if (xml.contains("<JIT-Order-Response>")) {
                                    xml = xml.replace(header, "").replace("<JIT-Order-Response>", "");
                                    xml = xml.replace(footer, "").replace("</JIT-Order-Response>", "");
                                    Log.e("sdfsdf", xml);
                                    Resp_OrderTicketInfo model = gsonXml.fromXml(xml, Resp_OrderTicketInfo.class);
                                    Message.obtain(handler, Constants.VIEW_ORDER_TICKET_COMMIT, model).sendToTarget();
                                    Log.e("sdfsdf", model.OrderNo + "");
                                } else {
                                    XmlPullParser xmlPullParser = parserCreator.createParser();
                                    xmlPullParser.setInput(new StringReader(xml));
                                    int eventType = xmlPullParser.getEventType();
                                    while (eventType != XmlPullParser.END_DOCUMENT) {
                                        switch (eventType) {
                                            case XmlPullParser.START_TAG:
                                                if ("string".equals(xmlPullParser.getName())) {
                                                    String message = ErrorCodeUtil.getErrorMessage(mContext, xmlPullParser.nextText());
                                                    Message.obtain(handler, Constants.VIEW_ORDER_TICKET_COMMIT, message).sendToTarget();
                                                }
                                                break;
                                        }
                                        eventType = xmlPullParser.next();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                String message = "解析xml出错";
                                Message.obtain(handler, Constants.VIEW_ORDER_TICKET_COMMIT, message).sendToTarget();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = "网络出错";
                        Message.obtain(handler, Constants.VIEW_ORDER_TICKET_COMMIT, message).sendToTarget();
                        Log.e(getTAG(), "onErrorResponse=" + volleyError.getMessage());
                    }
                });
                requestQueue.add(request);
                break;

            /**
             * 查询订单详情
             */
            case Constants.MODEL_ORDER_TICKET_QUERY:
                params_map = (HashMap<String, String>) changeStateMessage.obj;
                url = "http://121.40.116.51:9000/OrderAPI/getOrderInfo" + VolleyUtil.formatGetParams(params_map);

                requestQueue = Volley.newRequestQueue(mContext);

                request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        {
                            if (!TextUtils.isEmpty(s)) {
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
                                GsonXml gsonXml = new GsonXmlBuilder()
                                        .setXmlParserCreator(parserCreator)
                                        .create();

                                String header = "<string xmlns=\"http://policy.jinri.cn/\"><?xml version=\"1.0\" encoding=\"gb2312\"?>";
                                String footer = "</string>";
                                String xml = s;
                                try {
                                    if (xml.contains("<JIT-Order-Response>")) {
                                        xml = xml.replace(header, "").replace("<JIT-Order-Response>", "");
                                        xml = xml.replace(footer, "").replace("</JIT-Order-Response>", "");
                                        //Log.e("sdfsdf",xml);
                                        Resp_OrderTicketQueryInfo model = gsonXml.fromXml(xml, Resp_OrderTicketQueryInfo.class);
                                        Message.obtain(handler, Constants.VIEW_ORDER_TICKET_QUERY, model).sendToTarget();
                                        //Log.e("sdfsdf", model.OrderNo + "");
                                    } else {
                                        XmlPullParser xmlPullParser = parserCreator.createParser();
                                        xmlPullParser.setInput(new StringReader(xml));
                                        int eventType = xmlPullParser.getEventType();
                                        while (eventType != XmlPullParser.END_DOCUMENT) {
                                            switch (eventType) {
                                                case XmlPullParser.START_TAG:
                                                    if ("string".equals(xmlPullParser.getName())) {
                                                        String message = ErrorCodeUtil.getErrorMessage(mContext, xmlPullParser.nextText());
                                                        Message.obtain(handler, Constants.VIEW_ORDER_TICKET_QUERY, message).sendToTarget();
                                                    }
                                                    break;
                                            }
                                            eventType = xmlPullParser.next();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    String message = "解析xml出错";
                                    Message.obtain(handler, Constants.VIEW_ORDER_TICKET_QUERY, message).sendToTarget();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = "网络出错";
                        Message.obtain(handler, Constants.VIEW_ORDER_TICKET_QUERY, message).sendToTarget();
                        Log.e("sdfsdfsdfsd", "onErrorResponse=" + volleyError.getMessage());
                    }
                });
                requestQueue.add(request);
                break;
            /**
             * 添加订单到后台数据库
             */
            case Constants.MODEL_ORDER_TICKET_ADD:
                params_map = (HashMap<String, String>) changeStateMessage.obj;
                url = "http://www.icityto.com/X_UserLogic/yesicity2015/ticket_Add" + VolleyUtil.formatGetParams(params_map);
                requestQueue = Volley.newRequestQueue(mContext);
                JsonObjectRequest jRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String errorCode = "";
                        String message= "";
                        try {
                            errorCode = jsonObject.getString("errorCode");
                            if("0".equals(errorCode)){
                                Message.obtain(handler, Constants.VIEW_ORDER_TICKET_ADD, errorCode).sendToTarget();
                            }else{
                                message = jsonObject.getString("message");
                                Message.obtain(handler, Constants.VIEW_ORDER_TICKET_ADD, message).sendToTarget();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e(getTAG(), "onResponse =" + message);
                    }
                },new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                                                String message = "网络出错";
                Message.obtain(handler, Constants.VIEW_ORDER_TICKET_ADD, message).sendToTarget();
                Log.e(getTAG(), "onErrorResponse=" + volleyError.getMessage());
                    }
                });

                requestQueue.add(jRequest);
                break;
        }
    }
}
