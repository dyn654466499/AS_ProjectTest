package com.daemon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daemon.adapters.SelectAdapter;
import com.daemon.airticket.R;

import static com.daemon.consts.Constants.KEY_TYPE;
import static com.daemon.consts.Constants.KEY_TYPE_CABIN;
import static com.daemon.consts.Constants.KEY_TYPE_CABIN_POSITION;
import static com.daemon.consts.Constants.KEY_TYPE_CERT;
import static com.daemon.consts.Constants.KEY_TYPE_PASSENGER_CERT_POSITION;
import static com.daemon.consts.Constants.KEY_TYPE_TICKET_DISTRIBUTE;

public class TypeSelectActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_type_select);
		String type = getIntent().getStringExtra(KEY_TYPE);
		LinearLayout linearLayout_select_blank = (LinearLayout)findViewById(R.id.linearLayout_select_blank);
		linearLayout_select_blank.setOnClickListener(this);
		/**
		 * 先实现功能，后续可抽取公共的代码进行优化，减少代码量。
		 */
		
		/**
		 * 如果是舱位选择
		 */
		if (KEY_TYPE_CABIN.equals(type)) {
			TextView textView_select_title = (TextView)findViewById(R.id.tv_select_title);
			textView_select_title.setText(getString(R.string.title_select_cabin));
			
			int position_spaceType = getIntent().getExtras().getInt(
					KEY_TYPE_CABIN_POSITION, 0);
			final SelectAdapter adapter = new SelectAdapter(this,
					R.layout.item_select, getResources().getStringArray(R.array.TypeCabin));
			ListView listView = (ListView) findViewById(R.id.listView_select);
			adapter.setSelectedPosition(position_spaceType);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					adapter.setSelectedPosition(position);
					adapter.notifyDataSetChanged();
					Intent intent = new Intent();
					intent.putExtra(KEY_TYPE_CABIN, adapter.getItem(position));
					intent.putExtra(KEY_TYPE_CABIN_POSITION, position);
					setResult(RESULT_OK, intent);
					finish();
				}
			});
		}
        /**
         * 如果是证件选择
         */
		if (KEY_TYPE_CERT.equals(type)) {
			TextView textView_select_title = (TextView)findViewById(R.id.tv_select_title);
			textView_select_title.setText(getString(R.string.title_select_cert));
			
			int position_spaceType = getIntent().getExtras().getInt(
					KEY_TYPE_CABIN_POSITION, 0);
			final int view_position = getIntent().getIntExtra(KEY_TYPE_PASSENGER_CERT_POSITION, 0);
			final SelectAdapter adapter = new SelectAdapter(this,
					R.layout.item_select, getResources().getStringArray(R.array.TypeCert));
			ListView listView = (ListView) findViewById(R.id.listView_select);
			adapter.setSelectedPosition(position_spaceType);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					adapter.setSelectedPosition(position);
					adapter.notifyDataSetChanged();
					Intent intent = new Intent();
					/**
					 * 证件号名字
					 */
					intent.putExtra(KEY_TYPE_CERT, adapter.getItem(position));
					/**
					 * 记录证件类型位置
					 */
					intent.putExtra(KEY_TYPE_CABIN_POSITION, position);
					/**
					 * 记录点击哪位乘机人的位置
					 */
					intent.putExtra(KEY_TYPE_PASSENGER_CERT_POSITION, view_position);
					setResult(RESULT_OK, intent);
					finish();
				}
			});
		}
		/**
		 * 如果是机票配送方式
		 */
		if (KEY_TYPE_TICKET_DISTRIBUTE.equals(type)) {
			TextView textView_select_title = (TextView)findViewById(R.id.tv_select_title);
			textView_select_title.setText(getString(R.string.title_select_ticket_distribute));
			
			int position_cabin = getIntent().getExtras().getInt(KEY_TYPE_CABIN_POSITION, 0);
			final SelectAdapter adapter = new SelectAdapter(this,
					R.layout.item_select, getResources().getStringArray(R.array.TypeTicketDestribute));
			ListView listView = (ListView) findViewById(R.id.listView_select);
			adapter.setSelectedPosition(position_cabin);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					adapter.setSelectedPosition(position);
					adapter.notifyDataSetChanged();
					Intent intent = new Intent();
					intent.putExtra(KEY_TYPE_TICKET_DISTRIBUTE, adapter.getItem(position));
					intent.putExtra(KEY_TYPE_CABIN_POSITION, position);
					setResult(RESULT_OK, intent);
					finish();
				}
			});
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(0, 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
        switch (v.getId()) {
		case R.id.linearLayout_select_blank:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub

	}

}
