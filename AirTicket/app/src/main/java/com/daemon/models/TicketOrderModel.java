package com.daemon.models;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daemon.consts.Constants;
import com.daemon.utils.VolleyUtil;

import java.util.HashMap;

public class TicketOrderModel extends BaseModel{
    private Context mContext;
	public TicketOrderModel(Handler handler, Context context) {
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
			case Constants.MODEL_TICKET_ORDER_COMMIT:
				HashMap<String,String> params_map = (HashMap<String,String>)changeStateMessage.obj;
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

				final String request_xml = "<?xml version=\"1.0\" encoding=\"gb2312\" ?>\n" +
						"<JIT-CreateOrder>\n" +
						"<PolicyId=\"3095284\" Name=\"张三\" IDCard=\"NI1234567489\"\n" +
						"Cabins=\"Y\"Airline=\"CZ613\"sDate=\"2016-06-27\" UserName=\"username\" SystemId=\"654321\"\n" +
						"sCity=\"PEK\" eCity=\"DLC\"dotNum=\"6.4\" IsallowPnr=\"0\" Jounery=\"0\" IsFront=\"0\" Voyagetype=\"0\"\n" +
						"Rateway=\"0\" AutoPay=\"F\" AirChangedContact=”1347528xx14|”/>\n" +
						"</JIT-CreateOrder>";
                String url = "http://121.40.116.51:9000/OrderAPI/createOrder"+ VolleyUtil.formatGetParams(params_map);
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
						Log.e("sdfsdfsdfsd","onResponse="+s);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
						Log.e("sdfsdfsdfsd","onErrorResponse="+volleyError.getMessage());
					}
                });
                 requestQueue.add(request);
				break;
		}
	}

}
