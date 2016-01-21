package com.daemon.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.daemon.airticket.R;
import com.daemon.beans.Resp_OrderTicketQueryInfo;

import static com.daemon.consts.Constants.*;
public class OrderTicketDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ticket_detail);
        if(getIntent().hasExtra(KEY_PARCELABLE)){
            Resp_OrderTicketQueryInfo info = getIntent().getParcelableExtra(KEY_PARCELABLE);

        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onViewChange(Message msg) {

    }
}
