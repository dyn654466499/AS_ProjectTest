package com.daemon.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;

import com.daemon.airticket.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Administrator on 2016/1/15.
 */
public class ErrorCodeUtil {
    /**
     *  根据errorCode获取相应的error信息，绑定本地的error_code.xml文件
     * @param mContext
     * @param errorCode
     * @return
     */
    public static String getErrorMessage(Context mContext,String errorCode){
        if(TextUtils.isEmpty(errorCode))return errorCode;
        try{
        /**
         * 对比error_code
         */
        XmlResourceParser xmlResourceParser = mContext.getResources().getXml(R.xml.error_code);
        int event = xmlResourceParser.getEventType();//产生第一个事件
        while(event!= XmlPullParser.END_DOCUMENT){
            switch(event){
                case XmlPullParser.START_DOCUMENT://判断当前事件是否是文档开始事件
                    break;
                case XmlPullParser.START_TAG://判断当前事件是否是标签元素开始事件
                    if("Error".equals(xmlResourceParser.getName())){//判断开始标签元素是否是book
                        if(errorCode.equals(xmlResourceParser.getAttributeValue(0))){
                            return xmlResourceParser.getAttributeValue(2);
                        }
                    }

                    break;
                case XmlPullParser.END_TAG://判断当前事件是否是标签元素结束事件
                    if("Error".equals(xmlResourceParser.getName())){//判断结束标签元素是否是book

                    }
                    break;
            }
            event = xmlResourceParser.next();//进入下一个元素并触发相应事件
        }//end while
    } catch (XmlPullParserException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }catch (Exception e) {
            e.printStackTrace();
        }
        return errorCode;
    }
}
