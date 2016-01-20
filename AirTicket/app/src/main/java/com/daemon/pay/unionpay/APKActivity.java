package com.daemon.pay.unionpay;

import android.os.Bundle;
import android.widget.TextView;

public class APKActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //doStartUnionPayPlugin(this);
    }



    public void updateTextView(TextView tv) {
        String txt = "接入指南：\n1:拷贝sdk目录下的UPPayAssistEx.jar到libs目录下\n2:获取tn后通过UPPayAssistEx.startPay(...)方法调用控件";
        tv.setText(txt);
    }


}
