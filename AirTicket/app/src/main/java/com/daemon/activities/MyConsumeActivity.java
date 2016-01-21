package com.daemon.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daemon.airticket.R;
import com.daemon.beans.Resp_OrderTicketQueryInfo;
import com.daemon.fragments.OrderTicketFragment;
import com.daemon.models.TicketOrderModel;
import com.daemon.utils.AutoLoadingUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.daemon.consts.Constants.MODEL_ORDER_TICKET_QUERY;
import static com.daemon.consts.Constants.VIEW_ORDER_TICKET_QUERY;

public class MyConsumeActivity extends BaseActivity {

    Button btn_my_consume_shopping,btn_my_consume_catering,
            btn_my_consume_localCity,btn_my_consume_hotel,
            btn_my_consume_airTicket;

    List<Button> buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_consume);
        setModelDelegate(new TicketOrderModel(handler, this));
        setViewChangeListener(this);

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

        btn_my_consume_airTicket.callOnClick();

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
                RelativeLayout layout = (RelativeLayout)findViewById(R.id.relativeLayout_fragment_content);
                AutoLoadingUtil.setAutoLoadingView(layout);
                HashMap<String,String> params_map = new HashMap<String,String>();
                params_map.put("UserName","wang87654321");
				params_map.put("orderno","W2016012104024160509");
                notifyModelChange(Message.obtain(handler, MODEL_ORDER_TICKET_QUERY,params_map));
                break;
        }
    }

    @Override
    public void onViewChange(Message msg) {
        switch (msg.what){
            case VIEW_ORDER_TICKET_QUERY:
                AutoLoadingUtil.cancelAutoLoadingView();
                if(msg.obj instanceof String){

                }else {
                    if(!isDestroyed) {
                        Resp_OrderTicketQueryInfo info = (Resp_OrderTicketQueryInfo) msg.obj;
                        List<Resp_OrderTicketQueryInfo> infos = new LinkedList<>();
                        infos.add(info);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        OrderTicketFragment order_ticket = new OrderTicketFragment(infos);
                        transaction.replace(R.id.relativeLayout_fragment_content, order_ticket);
                        transaction.commit();
                    }
                }break;
        }
    }
}
