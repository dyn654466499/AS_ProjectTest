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
import com.daemon.beans.Resp_OrderCateringDetail;
import com.daemon.beans.Resp_OrderCateringDishesDetail;
import com.daemon.beans.Resp_OrderCateringList;
import com.daemon.beans.Resp_OrderTicketInfo;
import com.daemon.beans.Resp_OrderTicketList;
import com.daemon.beans.Resp_OrderTicketQueryInfo;
import com.daemon.consts.Constants;
import com.daemon.utils.ErrorCodeUtil;
import com.daemon.utils.VolleyUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class OrderModel extends BaseModel {
    private Context mContext;

    public OrderModel(Handler handler, Context context) {
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
        JsonObjectRequest jRequest;
        switch (changeStateMessage.what) {
            /**
             * 机票模块，提交订单
             */
            case Constants.MODEL_ORDER_TICKET_COMMIT:
                params_map = (HashMap<String, String>) changeStateMessage.obj;
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
                                    //Log.e("sdfsdf", xml);
                                    Resp_OrderTicketInfo model = gsonXml.fromXml(xml, Resp_OrderTicketInfo.class);
                                    Message.obtain(handler, Constants.VIEW_ORDER_TICKET_COMMIT, model).sendToTarget();
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
                //request.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
                break;

            /**
             *  机票模块，查询订单详情
             */
            case Constants.MODEL_ORDER_TICKET_QUERY:
                final Resp_OrderTicketList order_info= (Resp_OrderTicketList) changeStateMessage.obj;
                requestQueue = Volley.newRequestQueue(mContext);
                final List<Resp_OrderTicketQueryInfo> infos = new LinkedList<>();
                /**
                 *  每次请求响应，该list的size+1，当与订单列表总数相等时，更新UI。
                 */
                final List<String> requests = new LinkedList<>();

                for (Resp_OrderTicketList.Rows row:order_info.rows) {
                    params_map = new HashMap<String, String>();
                    /**
                     * UserName 到时候要改
                     */
                    params_map.put("UserName", "wang87654321");
                    params_map.put("orderno", row.Field_OrderId);
                    url = "http://121.40.116.51:9000/OrderAPI/getOrderInfo" + VolleyUtil.formatGetParams(params_map);
                    final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            requests.add("");
                            //Log.e("onResponse","requests.size()="+requests.size());
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
                                            infos.add(model);
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
                                                            Log.e(getTAG(), "查询机票订单详情返回错误信息:" +message);
                                                            Message.obtain(handler, Constants.VIEW_ORDER_TICKET_QUERY, message).sendToTarget();
                                                        }
                                                        break;
                                                }
                                                eventType = xmlPullParser.next();
                                            }
                                        }
                                        /**
                                         * 等到请求全部完成，更新列表
                                         */
                                        if(requests.size()==order_info.rows.size()){
                                            Message.obtain(handler, Constants.VIEW_ORDER_TICKET_QUERY, infos).sendToTarget();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        String message = "解析xml出错";
                                        Message.obtain(handler, Constants.VIEW_ORDER_TICKET_QUERY, message).sendToTarget();
                                    }
                                }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            requests.add("");
                            Log.e("onErrorResponse", "requests.size()=" + requests.size());
                            String message = "网络出错";
                            Message.obtain(handler, Constants.VIEW_ORDER_TICKET_QUERY, message).sendToTarget();
                            Log.e(getTAG(), "查询机票订单详情"+message+",onErrorResponse=" + volleyError.getMessage());

                        }
                    });
                    //stringRequest.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(stringRequest);
                }

                break;
            /**
             *  机票模块，添加订单到后台数据库
             */
            case Constants.MODEL_ORDER_TICKET_ADD:
                params_map = (HashMap<String, String>) changeStateMessage.obj;
                url = "http://www.icityto.com/X_UserLogic/yesicity2015/ticket_Add" + VolleyUtil.formatGetParams(params_map);
                requestQueue = Volley.newRequestQueue(mContext);
                jRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String errorCode = "";
                        String message= "";
                        try {
                            errorCode = jsonObject.getString("errorCode");
                            if("0".equals(errorCode)){
                                message = "保存成功";
                                Message.obtain(handler, Constants.VIEW_ORDER_TICKET_ADD, errorCode).sendToTarget();
                            }else{
                                message = jsonObject.getString("message");
                                Message.obtain(handler, Constants.VIEW_ORDER_TICKET_ADD, message).sendToTarget();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e(getTAG(), "onResponse:messgage=" + message);
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

            /**
             * 机票模块，查询订单号列表
             */
            case Constants.MODEL_ORDER_TICKET_ORDER_NO_QUERY:
                params_map = (HashMap<String, String>) changeStateMessage.obj;
                url = "http://www.icityto.com/X_UserLogic/yesicity2015/ticket_Page/" + VolleyUtil.formatGetParams(params_map);
                requestQueue = Volley.newRequestQueue(mContext);
                request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Gson gson = new Gson();
                            java.lang.reflect.Type type = new TypeToken<Resp_OrderTicketList>() {}.getType();
                            Resp_OrderTicketList info = gson.fromJson(s, type);
                            Message.obtain(handler, Constants.VIEW_ORDER_TICKET_ORDER_NO_QUERY, info).sendToTarget();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Message.obtain(handler, Constants.VIEW_ORDER_TICKET_ORDER_NO_QUERY, "解析机票订单号列表json出错").sendToTarget();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = "网络出错";
                        Message.obtain(handler, Constants.VIEW_ORDER_TICKET_ORDER_NO_QUERY, message).sendToTarget();
                        Log.e(getTAG(), "onErrorResponse=" + volleyError.getMessage());
                    }
                });
                //request.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
                break;

            case Constants.MODEL_ORDER_CATERING_QUERY:
                params_map = (HashMap<String, String>) changeStateMessage.obj;
                url = "http://www.icityto.com/X_UserLogic/yesicity2015/MyCater_Page/?" + VolleyUtil.formatGetParams(params_map);
                requestQueue = Volley.newRequestQueue(mContext);

                request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Gson gson = new Gson();
                            java.lang.reflect.Type type = new TypeToken<Resp_OrderCateringList>() {}.getType();
                            Resp_OrderCateringList info = gson.fromJson(s, type);
                            Message.obtain(handler, Constants.VIEW_ORDER_CATERING_QUERY, info).sendToTarget();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Message.obtain(handler, Constants.VIEW_ORDER_CATERING_QUERY, "解析餐饮列表json出错").sendToTarget();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = "网络出错";
                        Message.obtain(handler, Constants.VIEW_ORDER_CATERING_QUERY, message).sendToTarget();
                        Log.e(getTAG(), "onErrorResponse=" + volleyError.getMessage());
                    }
                });
                //request.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
                break;
            /**
             *  餐饮订单详情
             */
            case Constants.MODEL_ORDER_CATERING_DETAIL_QUERY:
                params_map = (HashMap<String, String>) changeStateMessage.obj;
                url = "http://a.ezhanyun.com/X_UserLogic/yesicity2015/dishes_Page/?" + VolleyUtil.formatGetParams(params_map);
                requestQueue = Volley.newRequestQueue(mContext);
                request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Gson gson = new Gson();
                            java.lang.reflect.Type type = new TypeToken<Resp_OrderCateringDetail>() {}.getType();
                            Resp_OrderCateringDetail info = gson.fromJson(s, type);
                            Message.obtain(handler, Constants.VIEW_ORDER_CATERING_DETAIL_QUERY, info).sendToTarget();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Message.obtain(handler, Constants.VIEW_ORDER_CATERING_DETAIL_QUERY, "解析餐饮订单详情json出错").sendToTarget();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = "网络出错";
                        Message.obtain(handler, Constants.VIEW_ORDER_CATERING_DETAIL_QUERY, message).sendToTarget();
                        Log.e(getTAG(), "onErrorResponse=" + volleyError.getMessage());
                    }
                });
                //request.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);

                /**
                 *  ------------------------------    同步请求      ---------------------------
                 */
//                RequestFuture<String> future = RequestFuture.newFuture();
//                request = new StringRequest(url, future, future);
//                requestQueue.add(request);
//                try {
//                    String result = future.get();
//                    if(!TextUtils.isEmpty(result)){
//                        try {
//                            Gson gson = new Gson();
//                            java.lang.reflect.Type type = new TypeToken<Resp_OrderCateringDetail>() {}.getType();
//                            Resp_OrderCateringDetail info = gson.fromJson(s, type);
//                            Message.obtain(handler, Constants.VIEW_ORDER_CATERING_DETAIL_QUERY, info).sendToTarget();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Message.obtain(handler, Constants.VIEW_ORDER_CATERING_DETAIL_QUERY, "解析餐饮订单详情json出错").sendToTarget();
//                        }
//                    }else{
//
//                    }
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
                break;
            /**
             * 餐饮菜品详情
             */
            case Constants.MODEL_ORDER_CATERING_GOODS_DETAIL_QUERY:
                params_map = (HashMap<String, String>) changeStateMessage.obj;
                url = "http://www.icityto.com/X_UserLogic/yesicity2015/dishesdata_Page/?" + VolleyUtil.formatGetParams(params_map);
                requestQueue = Volley.newRequestQueue(mContext);
                request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Gson gson = new Gson();
                            java.lang.reflect.Type type = new TypeToken<Resp_OrderCateringDishesDetail>() {}.getType();
                            Resp_OrderCateringDishesDetail info = gson.fromJson(s, type);
                            Message.obtain(handler, Constants.VIEW_ORDER_CATERING_GOODS_DETAIL_QUERY, info).sendToTarget();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Message.obtain(handler, Constants.VIEW_ORDER_CATERING_GOODS_DETAIL_QUERY, "解析餐饮菜品详情json出错").sendToTarget();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = "网络出错";
                        Message.obtain(handler, Constants.VIEW_ORDER_CATERING_GOODS_DETAIL_QUERY, message).sendToTarget();
                        Log.e(getTAG(), "onErrorResponse=" + volleyError.getMessage());
                    }
                });
                //request.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
                break;

        }
    }
}
