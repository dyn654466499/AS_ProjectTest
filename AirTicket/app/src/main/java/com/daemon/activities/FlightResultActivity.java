package com.daemon.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.daemon.adapters.FlightResultAdapter;
import com.daemon.airticket.R;
import com.daemon.beans.FlightDetailInfo;
import com.daemon.beans.FlightInfo;
import com.daemon.consts.Constants;
import com.daemon.interfaces.Commands;
import com.daemon.models.FlightResultModel;

import java.util.ArrayList;
import java.util.List;

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
    private List<FlightInfo> flightInfos_child;
    
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
		
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("空");

		Button btn_title_back = (Button)findViewById(R.id.btn_title_back);
		btn_title_back.setOnClickListener(this);

		flightInfos_group = new ArrayList<FlightInfo>();
		for (int i = 0; i < 6; i++) {
			FlightInfo info = new FlightInfo();
			info.AirLine = "南方航空";
			info.N = "3";
			info.ariLinesIcon = getResources().getDrawable(R.drawable.submit_edit_clear_normal);
			info.D ="5折";
			info.Ecity = "宝安机场";
			info.Etime = "16:30";
			info.P = "￥"+"1350";
			info.Scity = "吴圩机场";
			info.Stime = "12:00";
			info.FlightNo = "MU5214";
			info.FlightType = "333";
			info.planeSize = "(中)";
			flightInfos_group.add(info);
		}

		flightInfos_child = new ArrayList<FlightInfo>();
		for (int i = 0; i < 3; i++) {
			FlightDetailInfo info = new FlightDetailInfo();
			info.D = "5折";
			info.P = "￥"+"1350";
			info.cabinType = "头等舱";
			flightInfos_child.add(info);
		}
		
		elv_flight_result = (ExpandableListView)findViewById(R.id.elv_flight_result);
		final FlightResultAdapter adapter = new FlightResultAdapter(this, flightInfos_group, flightInfos_child);
		adapter.setExpandableListView(elv_flight_result);
		elv_flight_result.setAdapter(adapter);

        adapter.setTicketBookCommands(new Commands() {
			@Override
			public void executeCommand(Message msg_params) {
                 //notifyModelChange(msg_params);
			}
		});
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
			case Constants.VIEW_TICKET_BOOK:

				break;
		}
	}

}
