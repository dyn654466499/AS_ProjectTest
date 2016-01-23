package com.daemon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daemon.adapters.OrderInsureAdapter;
import com.daemon.adapters.OrderPassengerAdapter;
import com.daemon.adapters.OrderTicketAdapter;
import com.daemon.airticket.R;
import com.daemon.beans.Req_FlightInfo;
import com.daemon.beans.Req_PassengerInfo;
import com.daemon.beans.Resp_OrderTicketInfo;
import com.daemon.interfaces.Commands;
import com.daemon.models.OrderTicketModel;
import com.daemon.utils.DialogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daemon.consts.Constants.*;

/**
 * 机票订单界面
 * @author 邓耀宁
 *
 */
public class OrderTicketActivity extends BaseActivity{
	/**
	 * 增加乘机人
	 */
	private Button btn_order_morePassenger;
	/**
	 * 配送方式按钮
	 */
	private Button btn_order_destribute;
	/**
	 * 城市选择
	 */
	private Button btn_order_city; 
	/**
	 * 保存各个乘机人的证件类型位置
	 */
	private SparseIntArray certType_positions;
	/**
	 * 记录配送方式位置
	 */
	private int position_destribute = 0;
	/**
	 * 乘机人适配器
	 */
	private OrderPassengerAdapter passengerAdapter;
	/**
	 * 如果选择快递配送。则显示此界面
	 */
	private LinearLayout linearLayout_order_destribute;
	/**
	 * 乘机人列表
	 */
	private ListView lv_order_passengerInfo;
	/**
	 * 空险列表
	 */
	private ListView lv_order_insure;
	/**
	 * 乘机人信息链表
	 */
	private ArrayList<Req_PassengerInfo> passenger_infos;
	
	private TextView tv_order_total;
	/**
	 * 每个乘机人的单价总和
	 */
	private int ticket_unit_price = 0;

	private ArrayList<Req_FlightInfo> resqFlightInfos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_ticket);
		/**
		 * 自定义的框架
		 */
		setModelDelegate(new OrderTicketModel(handler, this));
		setViewChangeListener(this);

		/**
		 * --------------------------------空险列表start---------------------------------
		 */
		lv_order_insure = (ListView)findViewById(R.id.lv_order_insure);
		String[] insures = getResources().getStringArray(R.array.TypeInsure);
		List<Map<String, String>> data = new ArrayList<Map<String,String>>();
		for (String insure : insures) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_INSURE_NAME, insure);
			map.put(KEY_INSURE_PRICE, "40");
			data.add(map);
		}
		final OrderInsureAdapter insureAdapter = new OrderInsureAdapter(this, data);
		lv_order_insure.setAdapter(insureAdapter);
		lv_order_insure.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				insureAdapter.setPosition(position);
				insureAdapter.notifyDataSetChanged();
			}
		});
		/**
		 * --------------------------------空险列表end---------------------------------
		 */
		
		/**
		 * --------------------------------乘机人列表start---------------------------------
		 */
		passenger_infos = new ArrayList<Req_PassengerInfo>();
		for (int i = 0; i < 1; i++) {
			Req_PassengerInfo info = new Req_PassengerInfo();
			info.certNum="";
			info.certType="身份证";
			info.name="";
			passenger_infos.add(info);
		}		
		lv_order_passengerInfo = (ListView)findViewById(R.id.lv_order_passengerInfo);
		passengerAdapter = new OrderPassengerAdapter(this, passenger_infos, certType_positions);
		lv_order_passengerInfo.setAdapter(passengerAdapter);
		passengerAdapter.setSizeChangeCommand(new Commands() {
			@Override
			public void executeCommand(Message msg_params) {
				int count = passenger_infos.size();
				tv_order_total = (TextView)findViewById(R.id.tv_order_total);
				tv_order_total.setText(String.format(
						getString(R.string.order_peopleAndPrice),
						String.valueOf(passenger_infos.size()),
						String.valueOf(ticket_unit_price * count)));
			}
		});
		lv_order_insure.requestFocus();
		/**
		 * --------------------------------乘机人列表end---------------------------------
		 */
		
		/**
		 * --------------------------------航班信息列表start---------------------------------
		 */
		ListView lv_order_ticketInfo = (ListView)findViewById(R.id.lv_order_ticketInfo);
		resqFlightInfos = getIntent().getParcelableArrayListExtra(KEY_PARCELABLE);
		for (Req_FlightInfo info: resqFlightInfos) {
			ticket_unit_price +=Integer.valueOf(info.P)+Integer.valueOf(info.airPortBuildPrice)+Integer.valueOf(info.oilPrice);
		}
		OrderTicketAdapter orderTicketAdapter = new OrderTicketAdapter(this, resqFlightInfos);
		lv_order_ticketInfo.setAdapter(orderTicketAdapter);
		/**
		 * --------------------------------航班信息列表end---------------------------------
		 */

		btn_order_morePassenger = (Button) findViewById(R.id.btn_order_morePassenger);
		btn_order_morePassenger.setOnClickListener(this);
		
		btn_order_destribute = (Button) findViewById(R.id.btn_order_destribute);
		btn_order_destribute.setOnClickListener(this);
		
		linearLayout_order_destribute = (LinearLayout)findViewById(R.id.linearLayout_order_destribute);
		linearLayout_order_destribute.setVisibility(View.GONE);
		
		Button btn_order_commit = (Button) findViewById(R.id.btn_order_commit);
		btn_order_commit.setOnClickListener(this);
		
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText(getString(R.string.title_order_edit));
		
		Button btn_back = (Button)findViewById(R.id.btn_title_back);
		btn_back.setOnClickListener(this);
		
		tv_order_total = (TextView)findViewById(R.id.tv_order_total);
		tv_order_total.setText(String.format(
				getString(R.string.order_peopleAndPrice),
				String.valueOf(passenger_infos.size()),
				String.valueOf(ticket_unit_price)));

		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				/**
				 * 使scrollView向顶部滑动
				 */
				ScrollView sv_order = (ScrollView) findViewById(R.id.sv_order);
				sv_order.fullScroll(ScrollView.FOCUS_UP);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		/**
		 * 增加乘客人
		 */
		case R.id.btn_order_morePassenger:
			passenger_infos.add(new Req_PassengerInfo());
			passengerAdapter.notifyDataSetChanged();
			break;
		/**	
		  * 返回
		  */
		case R.id.btn_title_back:
			DialogUtil.showDialog(OrderTicketActivity.this, getString(R.string.title_order_edit), getString(R.string.tips_exitOrder), new Commands() {
				
				@Override
				public void executeCommand(Message msg_params) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			break;
		/**	
		 * 选择配送方式
		 */
		case R.id.btn_order_destribute:
			intent = new Intent(OrderTicketActivity.this, TypeSelectActivity.class);
			intent.putExtra(KEY_TYPE, KEY_TYPE_TICKET_DISTRIBUTE);
			intent.putExtra(KEY_TYPE_CABIN_POSITION, position_destribute);
			startActivityForResult(intent,REQUEST_CODE_DISTRIBUTE);
			overridePendingTransition(0, 0);
			break;
		/**
		 * 如有配送方式，选择城市
		 */
		case R.id.btn_order_city:
			intent = new Intent();
			intent.setClass(OrderTicketActivity.this, CitySearchActivity.class);
			startActivityForResult(intent, REQUEST_CODE_CITY);
			break;
		/**
		 * 提交订单	
		 */
		case R.id.btn_order_commit:
            if(resqFlightInfos.size()==1){
				String Name = "",IDCard = "";
				/**
				 * 获取每个editText
				 */
				for (int i = 0; i < lv_order_passengerInfo.getChildCount(); i++) {
					LinearLayout layout = (LinearLayout)lv_order_passengerInfo.getChildAt(i);// 获得子item的layout
					EditText et_order_passengers = (EditText) layout.findViewById(R.id.et_order_passengers);// 从layout中获得控件,根据其id
					EditText et_order_certNum = (EditText) layout.findViewById(R.id.et_order_certNum);//或者根据位置,在这我假设TextView在前，EditText在后
					TextView tv_order_certType = (TextView) layout.findViewById(R.id.tv_order_certType);
					Name += et_order_passengers.getText().toString()+"|";
					IDCard += "NI"+et_order_certNum.getText().toString()+"|";
					Log.e(getTAG(), "name="+Name+
							",cert_num ="+IDCard+
							",certType="+tv_order_certType.getText());
				}
				HashMap<String,String> params_map = new HashMap<String,String>();
				params_map.put("RateId", resqFlightInfos.get(0).ID);
				params_map.put("PolicyId", resqFlightInfos.get(0).RID);
				params_map.put("Name",Name);
				params_map.put("UserName","wang87654321");
				params_map.put("IDCard",IDCard);
				params_map.put("Cabins", resqFlightInfos.get(0).cabinType);
				params_map.put("dotNum", resqFlightInfos.get(0).K);
				params_map.put("sCity", resqFlightInfos.get(0).Scity);
				params_map.put("eCity", resqFlightInfos.get(0).Ecity);
				params_map.put("sDate", resqFlightInfos.get(0).Sdate);
				params_map.put("AirChangedContact",((EditText)findViewById(R.id.et_order_phoneNum)).getText().toString());
				params_map.put("AutoPay","F");
				params_map.put("Rateway","0");
				params_map.put("Airline", resqFlightInfos.get(0).AirLine);

				Log.e(getTAG(),",ID="+ resqFlightInfos.get(0).ID+",RID="+
						resqFlightInfos.get(0).RID+",cabinType="+
						resqFlightInfos.get(0).cabinType+",K="+
						resqFlightInfos.get(0).K+",Scity="+
						resqFlightInfos.get(0).Scity+",Ecity="+
						resqFlightInfos.get(0).Ecity+",Sdate="+
						resqFlightInfos.get(0).Sdate+",AirLine="+
						resqFlightInfos.get(0).AirLine);
				notifyModelChange(Message.obtain(handler, MODEL_ORDER_TICKET_COMMIT, params_map));
			}else{
				DialogUtil.showDialog(OrderTicketActivity.this, getString(R.string.title_order_edit), "暂时不支持往返创建订单！", new Commands() {
					@Override
					public void executeCommand(Message msg_params) {

					}
				});
			}

			break;
		
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
            DialogUtil.showDialog(OrderTicketActivity.this, getString(R.string.title_order_edit), getString(R.string.tips_exitOrder), new Commands() {
				
				@Override
				public void executeCommand(Message msg_params) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			/**
			 * 如果是证书请求码
			 */
			case REQUEST_CODE_CERTIFICATE:
				int type_position = data.getIntExtra(KEY_TYPE_CABIN_POSITION, 0);
				int view_position = data.getIntExtra(KEY_TYPE_PASSENGER_CERT_POSITION, 0);
				String certType = data.getStringExtra(KEY_TYPE_CERT);
				passenger_infos.get(view_position).certType = certType;
				passenger_infos.get(view_position).cert_position = type_position;

				//lv_order_insure.requestFocus();
				passengerAdapter.notifyDataSetChanged();
				break;
			/**
			  * 如果是配送请求码
			  */	
			case REQUEST_CODE_DISTRIBUTE:
				position_destribute = data.getIntExtra(KEY_TYPE_CABIN_POSITION, 0);
				if(position_destribute == 0){
					linearLayout_order_destribute.setVisibility(View.GONE);
				}else{
					linearLayout_order_destribute.setVisibility(View.VISIBLE);
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							/**
							 * 使scrollView向底部滑动
							 */
							ScrollView sv_order = (ScrollView)findViewById(R.id.sv_order);
							sv_order.fullScroll(ScrollView.FOCUS_DOWN);
						}
					});
				}
				btn_order_destribute.setText(data.getStringExtra(KEY_TYPE_TICKET_DISTRIBUTE));
				
				btn_order_city = (Button) findViewById(R.id.btn_order_city);
				btn_order_city.setOnClickListener(this);
				break;
				/**
				 * 如果是城市请求码
				 */
			case REQUEST_CODE_CITY:
				btn_order_city.setTextColor(getResources().getColor(R.color.ticket_black));
				btn_order_city.setText(data.getStringExtra(KEY_CITY));
				break;
				
			default:
				break;
			}
			
		}
	}
	
	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what){
			case VIEW_ORDER_TICKET_COMMIT:
                if(msg.obj instanceof String){
					DialogUtil.showDialog(OrderTicketActivity.this, getString(R.string.title_order_edit), (String)msg.obj, new Commands() {
						@Override
						public void executeCommand(Message msg_params) {

						}
					});
				}else{
					/**
					 * 将订单号添加到后台数据库
					 */
					Resp_OrderTicketInfo info = (Resp_OrderTicketInfo)msg.obj;
					HashMap<String,String> params_map = new HashMap<String,String>();
					params_map.put("OrderId", info.OrderNo);
					params_map.put("Field_YHID", "1");
					params_map.put("Yesicity","1");
					notifyModelChange(Message.obtain(handler,MODEL_ORDER_TICKET_ADD,params_map));
				}
				break;

			case VIEW_ORDER_TICKET_ADD:
						if(!"0".equals((String) msg.obj)){
							String message = (String)msg.obj;
							DialogUtil.showDialog(OrderTicketActivity.this, "提示", message, new Commands() {
								@Override
								public void executeCommand(Message msg_params) {

								}
							});
						}
				break;
		}
	}
	

}
