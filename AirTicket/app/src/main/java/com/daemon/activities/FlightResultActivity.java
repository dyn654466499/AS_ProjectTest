package com.daemon.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.daemon.adapters.FlightResultAdapter;
import com.daemon.airticket.R;
import com.daemon.beans.CabinInfo;
import com.daemon.beans.FlightInfo;
import com.daemon.beans.FlightInfoContainer;
import com.daemon.beans.FlightResponseInfo;
import com.daemon.consts.Constants;
import com.daemon.interfaces.Commands;
import com.daemon.models.FlightResultModel;
import com.daemon.utils.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.daemon.consts.Constants.KEY_CITY_ARRIVE;
import static com.daemon.consts.Constants.KEY_CITY_LEAVE;
import static com.daemon.consts.Constants.KEY_DATE_ARRIVE;
import static com.daemon.consts.Constants.KEY_DATE_LEAVE;
import static com.daemon.consts.Constants.KEY_PARCELABLE;
import static com.daemon.consts.Constants.KEY_SP_AIR_LINE;
import static com.daemon.consts.Constants.KEY_SP_AIR_PORT;
import static com.daemon.consts.Constants.KEY_SP_CABIN;
import static com.daemon.consts.Constants.KEY_SP_THREE_WORD;
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
    private List<FlightInfo> flightInfos_group;
    private List<List<FlightInfo>> flightInfos_child;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flight_result);
		/**
		 * 自定义的框架
		 */
		setModelDelegate(new FlightResultModel(handler, this));
		setViewChangeListener(this);

		SharedPreferences sp_three_word = getSharedPreferences(KEY_SP_THREE_WORD, Context.MODE_PRIVATE);
		SharedPreferences sp_cabin = getSharedPreferences(KEY_SP_CABIN, Context.MODE_PRIVATE);

		String Scity = sp_three_word.getString(getIntent().getStringExtra(KEY_CITY_LEAVE), "");
		String Ecity = sp_three_word.getString(getIntent().getStringExtra(KEY_CITY_ARRIVE), "");
		String cabin = sp_cabin.getString(getIntent().getStringExtra(KEY_TYPE_CABIN), "");
		String date_leave = CommonUtil.getFormatDate(getIntent().getLongExtra(KEY_DATE_LEAVE, System.currentTimeMillis()));
		String date_arrive = CommonUtil.getFormatDate(getIntent().getLongExtra(KEY_DATE_ARRIVE, System.currentTimeMillis()));

		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText(String.format(getString(R.string.title_flight_result),
				getIntent().getStringExtra(KEY_CITY_LEAVE),
				getIntent().getStringExtra(KEY_CITY_ARRIVE),
				getIntent().getStringExtra(KEY_TITLE)
		));

		Button btn_title_back = (Button)findViewById(R.id.btn_title_back);
		btn_title_back.setOnClickListener(this);

		TextView tv_ticket_result_leaveDate = (TextView)findViewById(R.id.tv_ticket_result_leaveDate);
		tv_ticket_result_leaveDate.setText(CommonUtil.getFormatDateOnlyYear(getIntent().getLongExtra(KEY_DATE_LEAVE, System.currentTimeMillis())));

		/**
		 * 封装请求参数传给model
		 */
		HashMap<String, String> map = new HashMap<>();
		map.put(KEY_CITY_LEAVE, Scity);
		map.put(KEY_CITY_ARRIVE, Ecity);
		map.put(KEY_USERNAME, "wangjunyi");
		map.put(KEY_DATE_LEAVE, date_leave);
		map.put(KEY_TYPE_CABIN, cabin);

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
				if(msg.obj instanceof  String){
                   new AlertDialog.Builder(FlightResultActivity.this)
						   .setTitle(getString(R.string.title_flight_result))
						   .setMessage((String)msg.obj)
						   .create()
						   .show();
				}else {
					FlightInfoContainer container = (FlightInfoContainer)msg.obj;
					SharedPreferences sp_airLine = getSharedPreferences(KEY_SP_AIR_LINE,Context.MODE_PRIVATE);
					SharedPreferences sp_airPort = getSharedPreferences(KEY_SP_AIR_PORT,Context.MODE_PRIVATE);

					flightInfos_group = new ArrayList<FlightInfo>();
					flightInfos_child = new ArrayList<List<FlightInfo>>();
					for (FlightResponseInfo reInfo : container.infos) {
						ArrayList<FlightInfo> flightInfos_child_ = new ArrayList<FlightInfo>();
						String[] airTerminal = reInfo.AirTerminal.split(",");

						FlightInfo info = new FlightInfo();
						info.N = reInfo.cabinInfo.get(0).N;
						info.D = CommonUtil.getFormatDiscount(reInfo.cabinInfo.get(0).D);
						info.P = "￥" + reInfo.cabinInfo.get(0).P;

						info.ariLinesIcon = getResources().getDrawable(R.drawable.submit_edit_clear_normal);
						info.AirLine = sp_airLine.getString(reInfo.AirLine, "");
						info.Ecity = sp_airPort.getString(reInfo.Ecity,"")+airTerminal[1];
						info.Etime = reInfo.Etime;
						info.Scity = sp_airPort.getString(reInfo.Scity,"")+airTerminal[0];
						info.Stime = reInfo.Stime;
						info.FlightNo = reInfo.FlightNo;
						info.FlightType = reInfo.FlightType;
						info.planeSize = "(中)";

						for (CabinInfo cabinInfo : reInfo.cabinInfo) {
							FlightInfo childInfo = new FlightInfo();
							childInfo.Sdate = reInfo.Sdate;
							childInfo.D = CommonUtil.getFormatDiscount(cabinInfo.D);
							childInfo.P = "￥" + cabinInfo.P;
							childInfo.cabinType = getSharedPreferences(KEY_SP_CABIN, Context.MODE_PRIVATE).getString(cabinInfo.L, "");
							info.ariLinesIcon = getResources().getDrawable(R.drawable.submit_edit_clear_normal);
							childInfo.oilPrice = "燃油￥" +reInfo.Fees;
							childInfo.airPortBuildPrice = "民航基金￥" +reInfo.AirTax;
							childInfo.AirLine = sp_airLine.getString(reInfo.AirLine, "");
							childInfo.Ecity = sp_airPort.getString(reInfo.Ecity,"")+airTerminal[1];
							childInfo.Etime = reInfo.Etime;
							childInfo.Scity = sp_airPort.getString(reInfo.Scity,"")+airTerminal[0];
							childInfo.Stime = reInfo.Stime;
							childInfo.FlightNo = reInfo.FlightNo;
							flightInfos_child_.add(childInfo);
						}

						flightInfos_group.add(info);
						flightInfos_child.add(flightInfos_child_);
					}


					elv_flight_result = (ExpandableListView) findViewById(R.id.elv_flight_result);
					final FlightResultAdapter adapter = new FlightResultAdapter(this, flightInfos_group, flightInfos_child);
					adapter.setExpandableListView(elv_flight_result);
					elv_flight_result.setAdapter(adapter);

					adapter.setTicketBookCommands(new Commands() {
						@Override
						public void executeCommand(Message msg_params) {
							Intent intent = new Intent(FlightResultActivity.this,TicketOrderActivity.class);
							ArrayList<FlightInfo> flightInfos = (ArrayList<FlightInfo>)msg_params.obj;
							intent.putExtra(KEY_PARCELABLE,flightInfos);
							startActivity(intent);
						}
					});
				}
				break;

			case Constants.VIEW_TICKET_BOOK:

				break;
		}
	}

}
