package com.daemon.activities;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daemon.airticket.R;
import com.daemon.consts.Constants;

public class EndorseActivity extends BaseActivity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_endorse);
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText(getString(R.string.title_endorseOrBackDesc));
		
		Button btn_back = (Button)findViewById(R.id.btn_title_back);
		btn_back.setOnClickListener(this);

		String desc = "暂无";
		String filter = "&#xA";

		TextView tv_endorse_refund_desc = (TextView)findViewById(R.id.tv_endorse_refund_desc);
		if(!TextUtils.isEmpty(getIntent().getStringExtra(Constants.KEY_RETURN))){
			desc = getIntent().getStringExtra(Constants.KEY_RETURN);
			desc = desc.replace(filter,"");
		}
		tv_endorse_refund_desc.setText(desc);

		desc = "暂无";
		TextView tv_endorse_change_desc = (TextView)findViewById(R.id.tv_endorse_change_desc);
		if(!TextUtils.isEmpty(getIntent().getStringExtra(Constants.KEY_CHANGE))){
			desc = getIntent().getStringExtra(Constants.KEY_CHANGE);
			desc = desc.replace(filter,"");
		}
		tv_endorse_change_desc.setText(desc);
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
		
	}

}
