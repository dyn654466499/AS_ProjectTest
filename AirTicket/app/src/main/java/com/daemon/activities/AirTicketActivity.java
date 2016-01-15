package com.daemon.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daemon.airticket.R;
import com.daemon.models.AirTicketModel;
import com.daemon.utils.CommonUtil;

import java.util.HashMap;

import static com.daemon.consts.Constants.KEY_ARRIVE_CITY;
import static com.daemon.consts.Constants.KEY_ARRIVE_DATE;
import static com.daemon.consts.Constants.KEY_CITY;
import static com.daemon.consts.Constants.KEY_LEAVE_CITY;
import static com.daemon.consts.Constants.KEY_LEAVE_DATE;
import static com.daemon.consts.Constants.KEY_TITLE_DATE;
import static com.daemon.consts.Constants.KEY_TYPE;
import static com.daemon.consts.Constants.KEY_TYPE_CABIN;
import static com.daemon.consts.Constants.KEY_TYPE_CABIN_POSITION;
import static com.daemon.consts.Constants.KEY_TYPE_DATE;
import static com.daemon.consts.Constants.MODEL_TICKET_SEARCH;
import static com.daemon.consts.Constants.REQUEST_CODE_CABIN;
import static com.daemon.consts.Constants.REQUEST_CODE_CITY_ARRIVE;
import static com.daemon.consts.Constants.REQUEST_CODE_CITY_LEAVE;
import static com.daemon.consts.Constants.REQUEST_CODE_DATE_ARRIVE;
import static com.daemon.consts.Constants.REQUEST_CODE_DATE_LEAVE;
import static com.daemon.consts.Constants.VIEW_TICKET_SEARCH;

/**
 * 机票界面
 * @author 邓耀宁
 * @since 2016.1.8
 */
public class AirTicketActivity extends BaseActivity {
	/**
	 * 单程按钮
	 */
	private Button btn_airticket_oneWay;
	/**
	 * 往返按钮
	 */
	private Button btn_airticket_goAndBack;
	/**
	 * 搜索按钮
	 */
	private Button btn_airticket_search;
	/**
	 * 出发城市按钮
	 */
	private Button btn_airticket_leaveCity;
	/**
	 * 到达城市按钮
	 */
	private Button btn_airticket_arriveCity;
	/**
	 * 出发日期按钮
	 */
	private Button btn_airticket_leaveDate;
	/**
	 * 返回日期按钮
	 */
	private Button btn_airticket_arriveDate;
	/**
	 * 返回
	 */
	private Button btn_back;
	/**
	 * 舱位选择
	 */
	private Button btn_airticket_cabin;
	/**
	 * 城市反向按钮
	 */
	private ImageButton imageBtn_airticket_fanxiang;
	/**
	 * 返回日期的layout
	 */
	private LinearLayout linearLayout_airticket_arriveDate;
	/**
	 * 记录舱位类型位置
	 */
	private int position_spaceType = 0;

	private long leave_date = 0;
	private long arrive_date = 0;

	private AlertDialog dialog;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket);
		/**
		 * 设置默认日期
		 */
		leave_date = arrive_date = System.currentTimeMillis();

		/**
		 * 自定义的框架
		 */
		setModelDelegate(new AirTicketModel(handler, this));
		setViewChangeListener(this);

		btn_airticket_oneWay = (Button) findViewById(R.id.btn_airticket_oneWay);
		btn_airticket_oneWay.setOnClickListener(this);

		btn_airticket_goAndBack = (Button) findViewById(R.id.btn_airticket_goAndBack);
		btn_airticket_goAndBack.setOnClickListener(this);

		btn_airticket_leaveCity = (Button) findViewById(R.id.btn_airticket_leaveCity);
		btn_airticket_leaveCity.setOnClickListener(this);

		btn_airticket_arriveCity = (Button) findViewById(R.id.btn_airticket_arriveCity);
		btn_airticket_arriveCity.setOnClickListener(this);

		btn_airticket_leaveDate = (Button) findViewById(R.id.btn_airticket_leaveDate);
		btn_airticket_leaveDate.setOnClickListener(this);
		btn_airticket_leaveDate.setText(CommonUtil.getFormatDateOnlyMonth(leave_date));

		btn_airticket_arriveDate = (Button) findViewById(R.id.btn_airticket_arriveDate);
		btn_airticket_arriveDate.setOnClickListener(this);
		btn_airticket_arriveDate.setText(CommonUtil.getFormatDateOnlyMonth(arrive_date));

		btn_airticket_cabin = (Button) findViewById(R.id.btn_airticket_cabin);
		btn_airticket_cabin.setOnClickListener(this);
		//默认选第一个舱位类型
		btn_airticket_cabin.setText(getResources().getStringArray(R.array.TypeCabin)[0]);

		btn_airticket_search = (Button) findViewById(R.id.btn_airticket_search);
		btn_airticket_search.setOnClickListener(this);

		imageBtn_airticket_fanxiang = (ImageButton) findViewById(R.id.imageBtn_airticket_fanxiang);
		imageBtn_airticket_fanxiang.setOnClickListener(this);

		btn_back = (Button) findViewById(R.id.btn_title_back);
		btn_back.setOnClickListener(this);

		linearLayout_airticket_arriveDate = (LinearLayout) findViewById(R.id.linearLayout_airticket_backDate);

		TextView textView_title = (TextView) findViewById(R.id.tv_title);
		textView_title.setText(getString(R.string.title_airTicket));

		btn_airticket_oneWay.callOnClick();


	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
			/**
			 * 为了先实现效果，先动态设置selector，后续再改进
			 */
			case R.id.btn_airticket_oneWay:
				btn_airticket_oneWay.setSelected(true);
				btn_airticket_oneWay.setTextColor(getResources().getColor(R.color.ticket_title_color));

				btn_airticket_goAndBack.setSelected(false);
				btn_airticket_goAndBack.setTextColor(Color.WHITE);

				linearLayout_airticket_arriveDate.setVisibility(View.GONE);
				break;

			case R.id.btn_airticket_goAndBack:
				btn_airticket_oneWay.setSelected(false);
				btn_airticket_oneWay.setTextColor(Color.WHITE);

				btn_airticket_goAndBack.setSelected(true);
				btn_airticket_goAndBack.setTextColor(getResources().getColor(R.color.ticket_title_color));

				linearLayout_airticket_arriveDate.setVisibility(View.VISIBLE);
				break;

			case R.id.btn_airticket_leaveCity:
				intent = new Intent();
				intent.setClass(AirTicketActivity.this, CitySearchActivity.class);
				startActivityForResult(intent, REQUEST_CODE_CITY_LEAVE);
				break;

			case R.id.btn_airticket_arriveCity:
				intent = new Intent();
				intent.setClass(AirTicketActivity.this, CitySearchActivity.class);
				startActivityForResult(intent, REQUEST_CODE_CITY_ARRIVE);
				break;

			case R.id.btn_airticket_leaveDate:
				intent = new Intent(AirTicketActivity.this, DatePickActivity.class);
				intent.putExtra(KEY_TITLE_DATE, getString(R.string.title_leaveDate));
				startActivityForResult(intent, REQUEST_CODE_DATE_LEAVE);
				overridePendingTransition(0, R.anim.activity_up);
				break;
			case R.id.btn_airticket_arriveDate:
				intent = new Intent(AirTicketActivity.this, DatePickActivity.class);
				intent.putExtra(KEY_TITLE_DATE, getString(R.string.title_date_back));
				startActivityForResult(intent, REQUEST_CODE_DATE_ARRIVE);
				overridePendingTransition(0, R.anim.activity_up);
				break;

			case R.id.btn_airticket_cabin:
				intent = new Intent(AirTicketActivity.this, TypeSelectActivity.class);
				intent.putExtra(KEY_TYPE, KEY_TYPE_CABIN);
				intent.putExtra(KEY_TYPE_CABIN_POSITION, position_spaceType);
				startActivityForResult(intent, REQUEST_CODE_CABIN);
				overridePendingTransition(0, 0);
				break;

			case R.id.btn_airticket_search:
				String city_leave = btn_airticket_leaveCity.getText().toString();
				String city_arrive = btn_airticket_arriveCity.getText().toString();
				String date_leave = CommonUtil.getFormatDate(leave_date);
				String date_arrive = CommonUtil.getFormatDate(arrive_date);

				/**
				 * 封装请求参数传给model
				 */
				HashMap<String, String> map = new HashMap<>();
				map.put(KEY_LEAVE_CITY, city_leave);
				map.put(KEY_ARRIVE_CITY, city_arrive);
				map.put(KEY_LEAVE_DATE, date_leave);
				if (linearLayout_airticket_arriveDate.getVisibility() == View.VISIBLE)
					map.put(KEY_ARRIVE_DATE, date_arrive);

            dialog = new AlertDialog.Builder(AirTicketActivity.this).setTitle("test").setMessage("ssss").create();
				dialog.show();
			/**
			 * 通知model发送请求
			 */
			Message msg = Message.obtain(handler, MODEL_TICKET_SEARCH, map);
				notifyModelChange(msg);


			break;

		case R.id.btn_title_back:
			finish();
			break;

		case R.id.imageBtn_airticket_fanxiang:
			String temp = btn_airticket_leaveCity.getText().toString();
			btn_airticket_leaveCity.setText(btn_airticket_arriveCity.getText()
					.toString());
			btn_airticket_arriveCity.setText(temp);
			break;

		default:
			break;
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case REQUEST_CODE_CABIN:
				btn_airticket_cabin.setText(data.getStringExtra(KEY_TYPE_CABIN));
				position_spaceType = data.getIntExtra(KEY_TYPE_CABIN_POSITION, 0);
				break;

			case REQUEST_CODE_DATE_LEAVE:
				leave_date = data.getLongExtra(KEY_TYPE_DATE,0);
				//setResult(RESULT_OK, getIntent().putExtra(KEY_TYPE_DATE, time));
				btn_airticket_leaveDate.setText(CommonUtil.getFormatDateOnlyMonth(leave_date));
				break;

			case REQUEST_CODE_DATE_ARRIVE:
				arrive_date = data.getLongExtra(KEY_TYPE_DATE,0);
				//setResult(RESULT_OK, getIntent().putExtra(KEY_TYPE_DATE, time_back));
				btn_airticket_arriveDate.setText(CommonUtil.getFormatDateOnlyMonth(arrive_date));
				break;

			case REQUEST_CODE_CITY_LEAVE:
				String city_leave = data.getStringExtra(KEY_CITY);
				btn_airticket_leaveCity.setText(city_leave);
				break;
				/**
				 * 到达城市请求码
 				 */
			case REQUEST_CODE_CITY_ARRIVE:
				String city_arrive = data.getStringExtra(KEY_CITY);
				btn_airticket_arriveCity.setText(city_arrive);
				break;

			default:
				break;
			}

		}
	}

	/**
	 * 收到model的通知
	 * @param msg
	 */
	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub
       switch (msg.what){
		   case VIEW_TICKET_SEARCH:
              dialog.setMessage((String)msg.obj);

//            Intent intent = new Intent(AirTicketActivity.this,TicketResultActivity.class);
//            startActivity(intent);
			   break;
	   }
	}
}
