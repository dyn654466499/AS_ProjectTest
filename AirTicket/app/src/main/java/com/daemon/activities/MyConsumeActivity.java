package com.daemon.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daemon.airticket.R;
import com.daemon.fragments.OrderTicketFragment;

import java.util.LinkedList;
import java.util.List;

public class MyConsumeActivity extends BaseActivity {

    Button btn_my_consume_shopping,btn_my_consume_catering,
            btn_my_consume_localCity,btn_my_consume_hotel,
            btn_my_consume_airTicket;

    List<Button> buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_consume);
        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.title_my_consume));

        Button btn_back = (Button)findViewById(R.id.btn_title_back);
        btn_back.setOnClickListener(this);

        btn_my_consume_shopping = (Button)findViewById(R.id.btn_my_consume_shopping);
        btn_my_consume_shopping.setOnClickListener(this);

        btn_my_consume_catering = (Button)findViewById(R.id.btn_my_consume_catering);
        btn_my_consume_catering.setOnClickListener(this);

        btn_my_consume_localCity = (Button)findViewById(R.id.btn_my_consume_localCity);
        btn_my_consume_localCity.setOnClickListener(this);

        btn_my_consume_hotel = (Button)findViewById(R.id.btn_my_consume_hotel);
        btn_my_consume_hotel.setOnClickListener(this);

        btn_my_consume_airTicket = (Button)findViewById(R.id.btn_my_consume_airTicket);
        btn_my_consume_airTicket.setOnClickListener(this);

        buttonList = new LinkedList<Button>();
        buttonList.add(btn_my_consume_shopping);
        buttonList.add(btn_my_consume_catering);
        buttonList.add(btn_my_consume_localCity);
        buttonList.add(btn_my_consume_hotel);
        buttonList.add(btn_my_consume_airTicket);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        OrderTicketFragment order_ticket = new OrderTicketFragment();
        transaction.replace(R.id.relativeLayout_fragment_content, order_ticket);
        transaction.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_title_back:
                finish();
                break;

            case R.id.btn_my_consume_shopping:

                break;

            case R.id.btn_my_consume_catering:

                break;

            case R.id.btn_my_consume_localCity:

                break;

            case R.id.btn_my_consume_hotel:

                break;

            case R.id.btn_my_consume_airTicket:

                break;
        }
    }

    @Override
    public void onViewChange(Message msg) {

    }
}
