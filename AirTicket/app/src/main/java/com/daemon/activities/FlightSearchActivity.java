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
import com.daemon.interfaces.Commands;
import com.daemon.models.FlightSearchModel;
import com.daemon.utils.CommonUtil;
import com.daemon.utils.DialogUtil;

import static com.daemon.consts.Constants.KEY_CITY;
import static com.daemon.consts.Constants.KEY_CITY_ARRIVE;
import static com.daemon.consts.Constants.KEY_CITY_LEAVE;
import static com.daemon.consts.Constants.KEY_DATE_ARRIVE;
import static com.daemon.consts.Constants.KEY_DATE_LEAVE;
import static com.daemon.consts.Constants.KEY_TITLE;
import static com.daemon.consts.Constants.KEY_TITLE_DATE;
import static com.daemon.consts.Constants.KEY_TYPE;
import static com.daemon.consts.Constants.KEY_TYPE_CABIN;
import static com.daemon.consts.Constants.KEY_TYPE_CABIN_POSITION;
import static com.daemon.consts.Constants.KEY_TYPE_DATE;
import static com.daemon.consts.Constants.REQUEST_CODE_CABIN;
import static com.daemon.consts.Constants.REQUEST_CODE_CITY_ARRIVE;
import static com.daemon.consts.Constants.REQUEST_CODE_CITY_LEAVE;
import static com.daemon.consts.Constants.REQUEST_CODE_DATE_ARRIVE;
import static com.daemon.consts.Constants.REQUEST_CODE_DATE_LEAVE;
/**
 * 航班搜索界面
 * @author 邓耀宁
 * @since 2016.1.8
 */
public class FlightSearchActivity extends BaseActivity {
	/**
	 * 单程按钮
	 */
	private Button btn_flight_search_oneWay;
	/**
	 * 往返按钮
	 */
	private Button btn_flight_search_goAndBack;
	/**
	 * 搜索按钮
	 */
	private Button btn_flight_search_startSeach;
	/**
	 * 出发城市按钮
	 */
	private Button btn_flight_search_leaveCity;
	/**
	 * 到达城市按钮
	 */
	private Button btn_flight_search_arriveCity;
	/**
	 * 出发日期按钮
	 */
	private Button btn_flight_search_leaveDate;
	/**
	 * 返回日期按钮
	 */
	private Button btn_flight_search_arriveDate;
	/**
	 * 返回
	 */
	private Button btn_title_back;
	/**
	 * 舱位选择
	 */
	private Button btn_flight_search_cabinType;
	/**
	 * 城市反向按钮
	 */
	private ImageButton imageBtn_flight_search_fanxiang;
	/**
	 * 返回日期的layout
	 */
	private LinearLayout linearLayout_flight_search_arriveDate;
	/**
	 * 记录舱位类型位置
	 */
	private int position_cabinType = 0;
    /**
     * 出发日期
     */
	private long leave_date = 0;
    /**
     * 到达日期
     */
	private long arrive_date = 0;

	private AlertDialog dialog;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flight_search);
		/**
		 * 设置默认日期
		 */
		arrive_date = leave_date =  System.currentTimeMillis();

		/**
		 * 自定义的框架
		 */
		setModelDelegate(new FlightSearchModel(handler, this));
		setViewChangeListener(this);

		btn_flight_search_oneWay = (Button) findViewById(R.id.btn_flight_search_oneWay);
		btn_flight_search_oneWay.setOnClickListener(this);

		btn_flight_search_goAndBack = (Button) findViewById(R.id.btn_flight_search_goAndBack);
		btn_flight_search_goAndBack.setOnClickListener(this);

		btn_flight_search_leaveCity = (Button) findViewById(R.id.btn_flight_search_leaveCity);
		btn_flight_search_leaveCity.setOnClickListener(this);

		btn_flight_search_arriveCity = (Button) findViewById(R.id.btn_flight_search_arriveCity);
		btn_flight_search_arriveCity.setOnClickListener(this);

		btn_flight_search_leaveDate = (Button) findViewById(R.id.btn_flight_search_leaveDate);
		btn_flight_search_leaveDate.setOnClickListener(this);
		btn_flight_search_leaveDate.setText(CommonUtil.getFormatDateOnlyMonth(leave_date));

		btn_flight_search_arriveDate = (Button) findViewById(R.id.btn_flight_search_arriveDate);
		btn_flight_search_arriveDate.setOnClickListener(this);
		btn_flight_search_arriveDate.setText(CommonUtil.getFormatDateOnlyMonth(arrive_date));

		btn_flight_search_cabinType = (Button) findViewById(R.id.btn_flight_search_cabinType);
		btn_flight_search_cabinType.setOnClickListener(this);
		//默认选第一个舱位类型
		btn_flight_search_cabinType.setText(getResources().getStringArray(R.array.TypeCabin)[0]);

		btn_flight_search_startSeach = (Button) findViewById(R.id.btn_flight_search_startSeach);
		btn_flight_search_startSeach.setOnClickListener(this);

		imageBtn_flight_search_fanxiang = (ImageButton) findViewById(R.id.imageBtn_flight_search_fanxiang);
		imageBtn_flight_search_fanxiang.setOnClickListener(this);

        btn_title_back = (Button) findViewById(R.id.btn_title_back);
		btn_title_back.setOnClickListener(this);

		linearLayout_flight_search_arriveDate = (LinearLayout) findViewById(R.id.linearLayout_flight_search_arriveDate);

		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(getString(R.string.title_flight_search));

		btn_flight_search_oneWay.callOnClick();

		Button btn_flight_search_test = (Button) findViewById(R.id.btn_flight_search_test);
		btn_flight_search_test.setOnClickListener(this);

        //startActivity(new Intent(this, com.daemon.pay.unionpay.BaseActivity.class));
//        new Thread(){
//			@Override
//			public void run() {
//				super.run();
//				ICBCPay.startPostForPay();
//			}
//		}.start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
			case R.id.btn_flight_search_test:
				intent = new Intent(FlightSearchActivity.this,MyConsumeActivity.class);
				startActivity(intent);
				break;

			/**
			 *  单程选择（为了先实现效果，先动态设置selector，后续再改进）
			 */
			case R.id.btn_flight_search_oneWay:
				btn_flight_search_oneWay.setSelected(true);
				btn_flight_search_oneWay.setTextColor(getResources().getColor(R.color.ticket_title_color));

				btn_flight_search_goAndBack.setSelected(false);
				btn_flight_search_goAndBack.setTextColor(Color.WHITE);

				linearLayout_flight_search_arriveDate.setVisibility(View.GONE);
				break;
            /**
             * 往返选择
             */
			case R.id.btn_flight_search_goAndBack:
				btn_flight_search_oneWay.setSelected(false);
				btn_flight_search_oneWay.setTextColor(Color.WHITE);

				btn_flight_search_goAndBack.setSelected(true);
				btn_flight_search_goAndBack.setTextColor(getResources().getColor(R.color.ticket_title_color));

				linearLayout_flight_search_arriveDate.setVisibility(View.VISIBLE);
				break;
            /**
             * 出发城市选择
             */
			case R.id.btn_flight_search_leaveCity:
				intent = new Intent();
				intent.setClass(FlightSearchActivity.this, CitySearchActivity.class);
				startActivityForResult(intent, REQUEST_CODE_CITY_LEAVE);
				break;
            /**
             * 到达城市选择
             */
			case R.id.btn_flight_search_arriveCity:
				intent = new Intent();
				intent.setClass(FlightSearchActivity.this, CitySearchActivity.class);
				startActivityForResult(intent, REQUEST_CODE_CITY_ARRIVE);
				break;
            /**
             * 出发日期选择
             */
			case R.id.btn_flight_search_leaveDate:
				intent = new Intent(FlightSearchActivity.this, DatePickActivity.class);
				intent.putExtra(KEY_TITLE_DATE, getString(R.string.title_leaveDate));
				startActivityForResult(intent, REQUEST_CODE_DATE_LEAVE);
				overridePendingTransition(0, R.anim.activity_up);
				break;
            /**
             * 到达日期选择
             */
			case R.id.btn_flight_search_arriveDate:
				intent = new Intent(FlightSearchActivity.this, DatePickActivity.class);
				intent.putExtra(KEY_TITLE_DATE, getString(R.string.title_date_back));
				startActivityForResult(intent, REQUEST_CODE_DATE_ARRIVE);
				overridePendingTransition(0, R.anim.activity_up);
				break;
            /**
             * 舱位选择
             */
			case R.id.btn_flight_search_cabinType:
				intent = new Intent(FlightSearchActivity.this, TypeSelectActivity.class);
				intent.putExtra(KEY_TYPE, KEY_TYPE_CABIN);
				intent.putExtra(KEY_TYPE_CABIN_POSITION, position_cabinType);
				startActivityForResult(intent, REQUEST_CODE_CABIN);
				overridePendingTransition(0, 0);
				break;
            /**
             * 开始搜索
             */
			case R.id.btn_flight_search_startSeach:
				intent = new Intent(FlightSearchActivity.this,FlightResultActivity.class);
				intent.putExtra(KEY_CITY_LEAVE, btn_flight_search_leaveCity.getText().toString());
				intent.putExtra(KEY_CITY_ARRIVE,btn_flight_search_arriveCity.getText().toString());
				intent.putExtra(KEY_DATE_LEAVE, leave_date);
				if (linearLayout_flight_search_arriveDate.getVisibility() == View.VISIBLE){
					if(arrive_date >= leave_date ||
							btn_flight_search_leaveDate.getText().toString().equals(CommonUtil.getFormatDateOnlyMonth(arrive_date))){
						intent.putExtra(KEY_TITLE, btn_flight_search_goAndBack.getText().toString());
						intent.putExtra(KEY_DATE_ARRIVE, arrive_date);
					}else{
						DialogUtil.showDialog(FlightSearchActivity.this, "哎呀，出错了", "亲，返回日期必须大于出发日期，请重新选择", new Commands() {
							@Override
							public void executeCommand(Message msg_params) {

							}
						});
						return;
					}

				}
				else
					intent.putExtra(KEY_TITLE, btn_flight_search_oneWay.getText().toString());
				intent.putExtra(KEY_TYPE_CABIN, btn_flight_search_cabinType.getText().toString());
				startActivity(intent);
			break;
            /**
             * 返回
             */
		case R.id.btn_title_back:
			finish();
			break;
            /**
             * 城市反向
             */
		case R.id.imageBtn_flight_search_fanxiang:
			String temp = btn_flight_search_leaveCity.getText().toString();
			btn_flight_search_leaveCity.setText(btn_flight_search_arriveCity.getText()
                    .toString());
			btn_flight_search_arriveCity.setText(temp);
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
                /**
                 * 舱位选择请求码
                 */
			case REQUEST_CODE_CABIN:
				btn_flight_search_cabinType.setText(data.getStringExtra(KEY_TYPE_CABIN));
				position_cabinType = data.getIntExtra(KEY_TYPE_CABIN_POSITION, 0);
				break;
                /**
                 * 出发日期请求码
                 */
			case REQUEST_CODE_DATE_LEAVE:
				leave_date = data.getLongExtra(KEY_TYPE_DATE,0);
				btn_flight_search_leaveDate.setText(CommonUtil.getFormatDateOnlyMonth(leave_date));
				break;
                /**
                 * 到达日期请求码
                 */
			case REQUEST_CODE_DATE_ARRIVE:
				arrive_date = data.getLongExtra(KEY_TYPE_DATE,0);
				btn_flight_search_arriveDate.setText(CommonUtil.getFormatDateOnlyMonth(arrive_date));
				break;
                /**
                 * 出发城市请求码
                 */
			case REQUEST_CODE_CITY_LEAVE:
				String city_leave = data.getStringExtra(KEY_CITY);
				btn_flight_search_leaveCity.setText(city_leave);
				break;
				/**
				 * 到达城市请求码
 				 */
			case REQUEST_CODE_CITY_ARRIVE:
				String city_arrive = data.getStringExtra(KEY_CITY);
				btn_flight_search_arriveCity.setText(city_arrive);
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
	}
}
