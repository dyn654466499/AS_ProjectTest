package com.daemon.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daemon.adapters.FlightResultAdapter;
import com.daemon.airticket.R;
import com.daemon.beans.Resp_CabinInfo;
import com.daemon.beans.Req_FlightInfo;
import com.daemon.beans.Resp_FlightContainerInfo;
import com.daemon.beans.Resp_FlightInfo;
import com.daemon.consts.Constants;
import com.daemon.interfaces.Commands;
import com.daemon.models.FlightResultModel;
import com.daemon.utils.AutoLoadingUtil;
import com.daemon.utils.CommonUtil;
import com.daemon.utils.SPUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.daemon.consts.Constants.KEY_CITY_ARRIVE;
import static com.daemon.consts.Constants.KEY_CITY_LEAVE;
import static com.daemon.consts.Constants.KEY_DATE_ARRIVE;
import static com.daemon.consts.Constants.KEY_DATE_LEAVE;
import static com.daemon.consts.Constants.KEY_PARCELABLE;
import static com.daemon.consts.Constants.KEY_TITLE;
import static com.daemon.consts.Constants.KEY_TYPE_CABIN;
import static com.daemon.consts.Constants.KEY_USERNAME;
import static com.daemon.consts.Constants.MODEL_FLIGHT_SEARCH;
import static com.daemon.consts.Constants.VIEW_FLIGHT_SEARCH;

/**
 * 机票航班查询结果
 * @author 邓耀宁
 */
public class FlightResultActivity extends BaseActivity{
	/**
	 * 航班的可展开列表
	 */
    private ExpandableListView elv_flight_result;
    private List<Req_FlightInfo> resqFlightInfos_group;
    private List<List<Req_FlightInfo>> flightInfos_child;
	String Scity;
	String Ecity;
	String cabin;
	String date_leave;
	Req_FlightInfo resqFlightInfo_goAndBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flight_result);
		LinearLayout linearLayout_flight_result = (LinearLayout) findViewById(R.id.linearLayout_flight_result);
		AutoLoadingUtil.setAutoLoadingView(linearLayout_flight_result);
		/**
		 * 自定义的框架
		 */
		setModelDelegate(new FlightResultModel(handler, this));
		setViewChangeListener(this);

		SharedPreferences sp_three_word = SPUtil.getThreeWord(this);//getSharedPreferences(KEY_SP_THREE_WORD, Context.MODE_PRIVATE);
		SharedPreferences sp_cabin = SPUtil.getCabin(this);//getSharedPreferences(KEY_SP_CABIN, Context.MODE_PRIVATE);

		if(getIntent().hasExtra(KEY_PARCELABLE))
			resqFlightInfo_goAndBack = getIntent().getParcelableExtra(KEY_PARCELABLE);

		TextView tv_ticket_result_leaveDate = (TextView)findViewById(R.id.tv_ticket_result_leaveDate);


		   Scity = sp_three_word.getString(getIntent().getStringExtra(KEY_CITY_LEAVE), "");
		    Ecity = sp_three_word.getString(getIntent().getStringExtra(KEY_CITY_ARRIVE), "");
		    cabin = sp_cabin.getString(getIntent().getStringExtra(KEY_TYPE_CABIN), "");
		    date_leave = CommonUtil.getFormatDate(getIntent().getLongExtra(KEY_DATE_LEAVE, System.currentTimeMillis()));
			TextView tv_title = (TextView)findViewById(R.id.tv_title);
			tv_title.setText(String.format(getString(R.string.title_flight_result),
					getIntent().getStringExtra(KEY_CITY_LEAVE),
					getIntent().getStringExtra(KEY_CITY_ARRIVE),
					getIntent().getStringExtra(KEY_TITLE)
			));
			tv_ticket_result_leaveDate.setText(CommonUtil.getFormatDateOnlyYear(getIntent().getLongExtra(KEY_DATE_LEAVE, System.currentTimeMillis())));

		Button btn_title_back = (Button)findViewById(R.id.btn_title_back);
		btn_title_back.setOnClickListener(this);



		/**
		 * 封装请求参数传给model
		 */
		HashMap<String, String> map = new HashMap<>();
		map.put(KEY_CITY_LEAVE, Scity);//三字码
		map.put(KEY_CITY_ARRIVE, Ecity);
		map.put(KEY_USERNAME, "wangjunyi");
		map.put(KEY_DATE_LEAVE, date_leave);//yyyy-mm-dd
		map.put(KEY_TYPE_CABIN, cabin);//字母

		notifyModelChange(Message.obtain(handler, MODEL_FLIGHT_SEARCH, map));

	}

	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_title_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what){
			case VIEW_FLIGHT_SEARCH:
				AutoLoadingUtil.cancelAutoLoadingView();
				if(msg.obj instanceof  String){
					if(isActive)
                   new AlertDialog.Builder(FlightResultActivity.this)
						   .setTitle("哎呀，出错了！")
						   .setMessage((String)msg.obj)
						   .create()
						   .show();
				}else {
					if(!isDestroyed) {
						Resp_FlightContainerInfo container = (Resp_FlightContainerInfo) msg.obj;
						SharedPreferences sp_airLine = SPUtil.getAirLine(this);//getSharedPreferences(KEY_SP_AIR_LINE,Context.MODE_PRIVATE);
						SharedPreferences sp_airPort = SPUtil.getAirPort(this);//getSharedPreferences(KEY_SP_AIR_PORT,Context.MODE_PRIVATE);

						resqFlightInfos_group = new ArrayList<Req_FlightInfo>();
						flightInfos_child = new ArrayList<List<Req_FlightInfo>>();
						for (Resp_FlightInfo reInfo : container.infos) {
							ArrayList<Req_FlightInfo> resqFlightInfos_child_ = new ArrayList<Req_FlightInfo>();
							String[] airTerminal = reInfo.AirTerminal.split(",");

							Req_FlightInfo info = new Req_FlightInfo();
							info.N = reInfo.respCabinInfo.get(0).N;
							info.D = CommonUtil.getFormatDiscount(reInfo.respCabinInfo.get(0).D);
							info.P = reInfo.respCabinInfo.get(0).P;

							info.ariLinesIcon = getResources().getDrawable(R.drawable.submit_edit_clear_normal);
							info.AirLine = sp_airLine.getString(reInfo.AirLine, "");
							info.Ecity = sp_airPort.getString(reInfo.Ecity, "") + airTerminal[1];
							info.Etime = reInfo.Etime;
							info.Scity = sp_airPort.getString(reInfo.Scity, "") + airTerminal[0];
							info.Stime = reInfo.Stime;
							info.FlightNo = reInfo.FlightNo;
							info.FlightType = reInfo.FlightType;
							info.planeSize = "";

							for (Resp_CabinInfo respCabinInfo : reInfo.respCabinInfo) {
								Req_FlightInfo childInfo = new Req_FlightInfo();
								childInfo.Sdate = reInfo.Sdate;
								childInfo.D = CommonUtil.getFormatDiscount(respCabinInfo.D);
								childInfo.P = respCabinInfo.P;
								childInfo.cabinType = respCabinInfo.L;
								childInfo.Change = respCabinInfo.Change;
								childInfo.Return = respCabinInfo.Return;
								childInfo.RID = respCabinInfo.RID;
								childInfo.ID = respCabinInfo.ID;
								childInfo.K = respCabinInfo.K;

								childInfo.ariLinesIcon = getResources().getDrawable(R.drawable.submit_edit_clear_normal);
								childInfo.oilPrice = reInfo.Fees;
								childInfo.airPortBuildPrice = reInfo.AirTax;
								childInfo.AirLine = reInfo.AirLine;//sp_airLine.getString(reInfo.AirLine, "");
								childInfo.Ecity = reInfo.Ecity;//sp_airPort.getString(reInfo.Ecity, "") + airTerminal[1];
								childInfo.Etime = reInfo.Etime;
								childInfo.Scity = reInfo.Scity;//sp_airPort.getString(reInfo.Scity, "") + airTerminal[0];
								childInfo.Stime = reInfo.Stime;
								childInfo.FlightNo = reInfo.FlightNo;
								childInfo.AirTerminal = reInfo.AirTerminal;
								resqFlightInfos_child_.add(childInfo);
							}

							resqFlightInfos_group.add(info);
							flightInfos_child.add(resqFlightInfos_child_);
						}


						elv_flight_result = (ExpandableListView) findViewById(R.id.elv_flight_result);
						final FlightResultAdapter adapter = new FlightResultAdapter(this, resqFlightInfos_group, flightInfos_child);
						adapter.setExpandableListView(elv_flight_result);
						elv_flight_result.setAdapter(adapter);

						adapter.setTicketBookCommands(new Commands() {
							@Override
							public void executeCommand(Message msg_params) {
								if (getIntent().hasExtra(KEY_DATE_ARRIVE) && resqFlightInfo_goAndBack == null) {
									/**
									 * 如果是往返，再跳回查询结果界面
									 */
									Intent intent = new Intent(FlightResultActivity.this, FlightResultActivity.class);
									ArrayList<Req_FlightInfo> resqFlightInfos = (ArrayList<Req_FlightInfo>) msg_params.obj;
									intent.putExtra(KEY_PARCELABLE, resqFlightInfos.get(0));

									intent.putExtra(KEY_CITY_LEAVE, getIntent().getStringExtra(KEY_CITY_ARRIVE));
									intent.putExtra(KEY_CITY_ARRIVE, getIntent().getStringExtra(KEY_CITY_LEAVE));
									intent.putExtra(KEY_DATE_LEAVE, getIntent().getLongExtra(KEY_DATE_ARRIVE, System.currentTimeMillis()));
									intent.putExtra(KEY_TYPE_CABIN, getIntent().getStringExtra(KEY_TYPE_CABIN));
									intent.putExtra(KEY_TITLE, getIntent().getStringExtra(KEY_TITLE));

									startActivity(intent);
								} else {
									/**
									 * 如果不是往返，直接跳到订单界面
									 */
									Intent intent = new Intent(FlightResultActivity.this, OrderTicketActivity.class);
									ArrayList<Req_FlightInfo> resqFlightInfos = (ArrayList<Req_FlightInfo>) msg_params.obj;
									if (resqFlightInfo_goAndBack != null) {
										resqFlightInfos.add(resqFlightInfo_goAndBack);
										Collections.reverse(resqFlightInfos);
									}
									intent.putExtra(KEY_PARCELABLE, resqFlightInfos);
									startActivity(intent);
									//resqFlightInfo_goAndBack = null;
								}

							}
						});
					}
				}
				break;

			case Constants.VIEW_TICKET_BOOK:

				break;
		}
	}

}
