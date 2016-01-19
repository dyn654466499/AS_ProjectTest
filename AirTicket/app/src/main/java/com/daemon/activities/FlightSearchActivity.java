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
import com.daemon.models.FlightSearchModel;
import com.daemon.utils.CommonUtil;

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
//		/**
//		 * 城市名和三字码键值对，城市名为key，用于航班搜索请求
//		 */
//		SharedPreferences sp_three_word = getSharedPreferences(Constants.KEY_SP_THREE_WORD ,Context.MODE_PRIVATE);
//		/**
//		 * 机场名和三字码键值对，三字码为key，用于显示三字码相应的机场名
//		 */
//		SharedPreferences sp_air_port = getSharedPreferences(Constants.KEY_SP_AIR_PORT ,Context.MODE_PRIVATE);
//		if(!sp_three_word.getBoolean("hasEdited",false)){
//			SharedPreferences.Editor editor_three_word = sp_three_word.edit();
//			SharedPreferences.Editor editor_air_port = sp_air_port.edit();
//			InputStream is=null;
//			try {
//				is=getResources().openRawResource(R.raw.three_word);
//				Workbook wb=Workbook.getWorkbook(is);
//				Sheet sheet=wb.getSheet(0);
//				int row=sheet.getRows();
//				for(int i=0;i<row;++i) {
//					Cell cellCity = sheet.getCell(0, i);
//					Cell cellWord = sheet.getCell(1, i);
//					Cell cellAirPort = sheet.getCell(2, i);
//					editor_three_word.putString(cellCity.getContents().trim(), cellWord.getContents().trim());
//					editor_air_port.putString(cellWord.getContents().trim(), cellAirPort.getContents().trim());
//					//Log.e("sssssdfsdsdss", cellCity.getContents().trim() + "," + cellWord.getContents().trim() + "," + cellAirPort.getContents().trim() + ",");
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return;
//			}
//			editor_three_word.putBoolean("hasEdited",true);
//			editor_three_word.commit();
//			editor_air_port.commit();
//		}
//
//		/**
//		 * 航空公司和其缩写键值对存储
//		 */
//		SharedPreferences sp_air_line = getSharedPreferences(Constants.KEY_SP_AIR_LINE,Context.MODE_PRIVATE);
//		if(!sp_air_line.getBoolean("hasEdited",false)){
//			SharedPreferences.Editor editor_air_line = sp_air_line.edit();
//			InputStream is=null;
//			try {
//				is=getResources().openRawResource(R.raw.air_line);
//				Workbook wb=Workbook.getWorkbook(is);
//				Sheet sheet=wb.getSheet(0);
//				int row=sheet.getRows();
//				for(int i=0;i<row;++i)
//				{
//					Cell cellName=sheet.getCell(0, i);
//					Cell cellWord=sheet.getCell(1, i);
//					editor_air_line.putString(cellWord.getContents().trim(),cellName.getContents().trim());
//					//Log.e("sssssdfsdsdss", cellName.getContents().trim() + "," + cellWord.getContents().trim() + ",");
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return;
//			}
//			editor_air_line.putBoolean("hasEdited", true);
//			editor_air_line.commit();
//		}
//
//		/**
//		 * 舱位类型和其缩写键值对存储
//		 */
//		SharedPreferences sp_cabin = getSharedPreferences(KEY_SP_CABIN,Context.MODE_PRIVATE);
//		if(!sp_cabin.getBoolean("hasEdited",false)){
//			SharedPreferences.Editor editor_cabin = sp_cabin.edit();
//			String[] cabins = getResources().getStringArray(R.array.TypeCabin);
//			editor_cabin.putString("A",cabins[0]);
//			editor_cabin.putString(cabins[0],"A");
//
//			editor_cabin.putString("Y",cabins[1]);
//			editor_cabin.putString(cabins[1],"Y");
//
//			editor_cabin.putString("C",cabins[2]);
//			editor_cabin.putString(cabins[2],"C");
//
//			editor_cabin.putString("F",cabins[3]);
//			editor_cabin.putString(cabins[3], "F");
//
//			editor_cabin.putBoolean("hasEdited", true);
//			editor_cabin.commit();
//		}
		/**
		 * 设置默认日期
		 */
		leave_date = arrive_date = System.currentTimeMillis();

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

//		new Thread(){
//			@Override
//			public void run() {
//				super.run();
//				payurl.payTest();
//			}
//		}.start();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
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
					intent.putExtra(KEY_TITLE, btn_flight_search_goAndBack.getText().toString());
					intent.putExtra(KEY_DATE_ARRIVE, arrive_date);
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
