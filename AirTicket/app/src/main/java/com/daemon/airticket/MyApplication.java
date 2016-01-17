package com.daemon.airticket;import android.app.Application;import android.content.Context;import android.content.SharedPreferences;import android.util.Log;import android.view.WindowManager;import com.daemon.consts.Constants;import java.io.InputStream;import java.util.HashMap;import jxl.Cell;import jxl.Sheet;import jxl.Workbook;public class MyApplication extends Application {	private final String TAG = MyApplication.class.getSimpleName();	private static MyApplication instance;	private static int screenWidth, screenHeight;	public static final String S_MSG_TAG = "msgHandler";	public static MyApplication getInstance() {		return instance;	}	@Override	public void onCreate() {		super.onCreate();		instance = this;		WindowManager wm = (WindowManager) this				.getSystemService(Context.WINDOW_SERVICE);		this.setScreenWidth(wm.getDefaultDisplay().getWidth());// 屏幕宽度		this.setScreenHeight(wm.getDefaultDisplay().getHeight());// 屏幕高度	}	public static int getScreenWidth() {		return screenWidth;	}	public static void setScreenWidth(int screenWidth) {		MyApplication.screenWidth = screenWidth;	}	public static int getScreenHeight() {		return screenHeight;	}	public static void setScreenHeight(int screenHeight) {		MyApplication.screenHeight = screenHeight;	}}