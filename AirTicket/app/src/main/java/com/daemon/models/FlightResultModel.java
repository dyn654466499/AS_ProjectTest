package com.daemon.models;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daemon.beans.Resp_FlightContainerInfo;
import com.daemon.consts.Constants;
import com.daemon.utils.ErrorCodeUtil;
import com.daemon.utils.VolleyUtil;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.HashMap;

public class FlightResultModel extends BaseModel {
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
        switch (changeStateMessage.what) {
            case Constants.MODEL_FLIGHT_SEARCH:
                HashMap<String, String> params_map = (HashMap<String, String>) changeStateMessage.obj;
                String url = Constants.URL_FLIGHT_LIST + VolleyUtil.formatGetParams(params_map);
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
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
                                    .setSameNameLists(true)
                                    .create();

                            String header = "<string xmlns=\"http://policy.jinri.cn/\"><?xml version=\"1.0\" encoding=\"gb2312\"?>";
                            String footer = "</string>";
                            String xml = s;
                            try {
                                if (xml.contains("<JIT-Flight-Response>")) {
                                    xml = xml.replace(header, "");
                                    xml = xml.replace(footer, "");
                                    //Log.e("sdfsdf", xml);
                                    Resp_FlightContainerInfo model = gsonXml.fromXml(xml, Resp_FlightContainerInfo.class);
                                    Message.obtain(handler, Constants.VIEW_FLIGHT_SEARCH, model).sendToTarget();
                                    //Log.e("sdfsdf", model.infos.get(0).respCabinInfo.size() + "");
                                } else {
                                    XmlPullParser xmlPullParser = parserCreator.createParser();
                                    xmlPullParser.setInput(new StringReader(xml));
                                    int eventType = xmlPullParser.getEventType();
                                    while (eventType != XmlPullParser.END_DOCUMENT) {
                                        switch (eventType) {
                                            case XmlPullParser.START_TAG:
                                                if ("string".equals(xmlPullParser.getName())) {
                                                    String message = ErrorCodeUtil.getErrorMessage(mContext, xmlPullParser.nextText());
                                                    Message.obtain(handler, Constants.VIEW_FLIGHT_SEARCH, message).sendToTarget();
                                                }
                                                break;
                                        }
                                        eventType = xmlPullParser.next();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                String message = "解析xml出错";
                                Message.obtain(handler, Constants.VIEW_FLIGHT_SEARCH, message).sendToTarget();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = "网络请求出错";
                        Message.obtain(handler, Constants.VIEW_FLIGHT_SEARCH, message).sendToTarget();
                        //Log.e(getTAG(), TextUtils.isEmpty(volleyError.getMessage()) ? "" : volleyError.getMessage());
                    }
                });
                //request.setRetryPolicy(new DefaultRetryPolicy(Constants.VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
                break;
        }
    }

}
