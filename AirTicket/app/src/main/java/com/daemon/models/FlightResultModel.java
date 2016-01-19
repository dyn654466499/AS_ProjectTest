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
import com.daemon.beans.FlightInfoContainer;
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
//                           String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                                   "<string xmlns=\"http://policy.jinri.cn/\"><?xml version=\"1.0\" encoding=\"gb2312\"?><JIT-Flight-Response>" +
//                                   "<Response Sdate=\"2016-03-29\" Scity=\"CKG\" Ecity=\"NNG\" FlightNo=\"CZ8151\" AirLine=\"CZ\" FlightType=\"320\" Stime=\"08:10\" Etime=\"09:35\" Stop=\"0\" EPiao=\"E\" Tax=\"50\" AirTax=\"50\" Fees=\"0\" AirTerminal=\"2A,--\"><Cabin C=\"Y\" N=\"A\" D=\"100\" P=\"950\" T=\"0\" L=\"Y\" K=\"0.50\" RID=\"JVajWCSASPygSHYA+znrjw==\" ID=\"N9BdXJELpXQ=\" XF=\"0\" PI=\"0\" RT=\"0\" RM=\"\" OfficeNum=\"\" Change=\"\" Return=\"\" /><Cabin C=\"W\" N=\"A\" D=\"100\" P=\"950\" T=\"0\" L=\"Y\" K=\"0.50\" RID=\"JVajWCSASPygSHYA+znrjw==\" ID=\"N9BdXJELpXQ=\" XF=\"0\" PI=\"0\" RT=\"0\" RM=\"\" OfficeNum=\"\" Change=\"\" Return=\"\" /><Cabin C=\"J\" N=\"A\" D=\"231\" P=\"2190\" T=\"0\" L=\"C\" K=\"0.50\" RID=\"JVajWCSASPygSHYA+znrjw==\" ID=\"N9BdXJELpXQ=\" XF=\"0\" PI=\"0\" RT=\"0\" RM=\"\" OfficeNum=\"\" Change=\"\" Return=\"\" /></Response>"+
//                                   "</JIT-Flight-Response></string>";
                            String xml = s;
                            try {
                                if (xml.contains("<JIT-Flight-Response>")) {
                                    xml = xml.replace(header, "");
                                    xml = xml.replace(footer, "");
                                    //Log.e("sdfsdf", xml);
                                    FlightInfoContainer model = gsonXml.fromXml(xml, FlightInfoContainer.class);
                                    Message.obtain(handler, Constants.VIEW_FLIGHT_SEARCH, model).sendToTarget();
                                    //Log.e("sdfsdf",model.infos.get(0).cabinInfo.size()+"");
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
                requestQueue.add(request);
                break;
        }
    }

}
