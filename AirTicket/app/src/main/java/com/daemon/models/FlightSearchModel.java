package com.daemon.models;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class FlightSearchModel extends BaseModel{
    private Context mContext;
	public FlightSearchModel(Handler handler, Context context) {
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

		}
}
