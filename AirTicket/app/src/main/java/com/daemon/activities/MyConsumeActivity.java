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
import com.daemon.beans.Resp_OrderCateringList;
import com.daemon.beans.Resp_OrderTicketList;
import com.daemon.beans.Resp_OrderTicketQueryInfo;
import com.daemon.consts.Constants;
import com.daemon.fragments.OrderCateringFragment;
import com.daemon.fragments.OrderTicketFragment;
import com.daemon.interfaces.Commands;
import com.daemon.models.OrderModel;
import com.daemon.utils.AutoLoadingUtil;
import com.daemon.utils.DialogUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.daemon.consts.Constants.MODEL_ORDER_TICKET_QUERY;
import static com.daemon.consts.Constants.VIEW_ORDER_CATERING_QUERY;
import static com.daemon.consts.Constants.VIEW_ORDER_TICKET_QUERY;

public class MyConsumeActivity extends BaseActivity {

    Button btn_my_consume_shopping,btn_my_consume_catering,
            btn_my_consume_localCity,btn_my_consume_hotel,
            btn_my_consume_airTicket;

    List<Button> buttonList;

    List<Resp_OrderTicketQueryInfo> OrderTicketQueryInfos;
    Resp_OrderCateringList resp_orderCateringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_consume);
        setModelDelegate(new OrderModel(handler, this));
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

        btn_my_consume_catering.callOnClick();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_title_back:
                finish();
                break;

            case R.id.btn_my_consume_shopping:
                setButtonClick(v.getId());
                break;
            /**
             * 餐饮列表查询
             */
            case R.id.btn_my_consume_catering:
                setButtonClick(v.getId());
                if(resp_orderCateringList==null){
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout_fragment_content);
                    AutoLoadingUtil.setAutoLoadingView(layout);
                    HashMap<String, String> params_map = new HashMap<String, String>();
                    params_map.put("UId", "yesicity2015");
                    params_map.put("Field_YHID", "1");
                    params_map.put("Yesicity", "1");
                    params_map.put("page", "1");
                    notifyModelChange(Message.obtain(handler, Constants.MODEL_ORDER_CATERING_QUERY, params_map));
                }else{
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    OrderCateringFragment order_catering = new OrderCateringFragment(resp_orderCateringList);
                    transaction.replace(R.id.relativeLayout_fragment_content, order_catering);
                    transaction.commit();
                }
                break;

            case R.id.btn_my_consume_localCity:
                setButtonClick(v.getId());
                break;

            case R.id.btn_my_consume_hotel:
                setButtonClick(v.getId());
                break;
            /**
             * 机票列表查询
             */
            case R.id.btn_my_consume_airTicket:
                setButtonClick(v.getId());
                /**
                 * 如果为null，则请求网络数据；否则直接显示列表
                 */
                if(OrderTicketQueryInfos == null) {
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout_fragment_content);
                    AutoLoadingUtil.setAutoLoadingView(layout);
                    HashMap<String, String> params_map = new HashMap<String, String>();
                    params_map.put("UId", "yesicity2015");
                    params_map.put("Field_YHID", "1");
                    params_map.put("Yesicity", "1");
                    params_map.put("page", "1");
                    notifyModelChange(Message.obtain(handler, Constants.MODEL_ORDER_TICKET_ORDER_NO_QUERY, params_map));
                }else{
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    OrderTicketFragment order_ticket = new OrderTicketFragment(OrderTicketQueryInfos);
                    transaction.replace(R.id.relativeLayout_fragment_content, order_ticket);
                    transaction.commit();
                }

                break;
        }
    }

    private void setButtonClick(int resId){
        for (Button button:buttonList) {
            if(button.getId()==resId){
                if(button.getId() == R.id.btn_my_consume_shopping){
                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_conner_left_press));
                }else if(button.getId() == R.id.btn_my_consume_airTicket){
                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_conner_right_press));
                }else{
                    button.setBackgroundColor(getResources().getColor(R.color.ticket_white));
                }
                button.setTextColor(getResources().getColor(R.color.ticket_title_color));
            }else{
                if(button.getId() == R.id.btn_my_consume_shopping){
                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_conner_left_unpress));
                }else if(button.getId() == R.id.btn_my_consume_airTicket){
                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_conner_right_unpress));
                }else{
                    button.setBackgroundColor(getResources().getColor(R.color.ticket_title_color));
                }
                button.setTextColor(getResources().getColor(R.color.ticket_white));
            }
        }
    }


    @Override
    public void onViewChange(Message msg) {
        switch (msg.what){
            /**
             * 返回后台数据库的机票订单列表（主要是订单号），接下来去访问第三方的后台查询订单详情
             */
            case Constants.VIEW_ORDER_TICKET_ORDER_NO_QUERY:
                if(msg.obj instanceof String){
                    AutoLoadingUtil.cancelAutoLoadingView();
                    DialogUtil.showDialog(MyConsumeActivity.this, getString(R.string.title_my_consume), (String)msg.obj, new Commands() {
                        @Override
                        public void executeCommand(Message msg_params) {

                        }
                    });
                }else {
                    Resp_OrderTicketList order_info = (Resp_OrderTicketList)msg.obj;
                    notifyModelChange(Message.obtain(handler, MODEL_ORDER_TICKET_QUERY, order_info));
                }
                break;
            /**
             * 通知机票界面
             */
            case VIEW_ORDER_TICKET_QUERY:
                AutoLoadingUtil.cancelAutoLoadingView();
                if(msg.obj instanceof String){
//                        DialogUtil.showDialog(MyConsumeActivity.this, getString(R.string.title_my_consume), (String)msg.obj, new Commands() {
//                            @Override
//                            public void executeCommand(Message msg_params) {
//
//                            }
//                        });
                }else {
                    if(!isDestroyed) {
                            OrderTicketQueryInfos = (List<Resp_OrderTicketQueryInfo>) msg.obj;
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            OrderTicketFragment order_ticket = new OrderTicketFragment(OrderTicketQueryInfos);
                            transaction.replace(R.id.relativeLayout_fragment_content, order_ticket);
                            transaction.commit();
                    }
                }
                break;
            /**
             * 通知餐饮界面
             */
            case VIEW_ORDER_CATERING_QUERY:
                AutoLoadingUtil.cancelAutoLoadingView();
                if(msg.obj instanceof String){
                        DialogUtil.showDialog(MyConsumeActivity.this, getString(R.string.title_my_consume), (String)msg.obj, new Commands() {
                            @Override
                            public void executeCommand(Message msg_params) {

                            }
                        });
                }else {
                    if(!isDestroyed) {
                        resp_orderCateringList = (Resp_OrderCateringList) msg.obj;
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        OrderCateringFragment order_catering = new OrderCateringFragment(resp_orderCateringList);
                        transaction.replace(R.id.relativeLayout_fragment_content, order_catering);
                        transaction.commit();
                    }
                }
                break;
        }
    }
}
