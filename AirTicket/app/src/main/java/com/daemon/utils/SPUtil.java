package com.daemon.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.daemon.airticket.R;
import com.daemon.consts.Constants;

import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import static com.daemon.consts.Constants.KEY_SP_CABIN;

/**
 * 针对需求实现的SharePreferences
 * Created by 邓耀宁 on 2016/1/19.
 */
public class SPUtil {
    public static SharedPreferences getThreeWord(Context context){
        /**
         * 城市名和三字码键值对，城市名为key，用于航班搜索请求
         */
        SharedPreferences sp_three_word = context.getSharedPreferences(Constants.KEY_SP_THREE_WORD, Context.MODE_PRIVATE);
        if(!sp_three_word.getBoolean("hasEdited",false)){
            SharedPreferences.Editor editor_three_word = sp_three_word.edit();
            InputStream is=null;
            try {
                is=context.getResources().openRawResource(R.raw.three_word);
                Workbook wb=Workbook.getWorkbook(is);
                Sheet sheet=wb.getSheet(0);
                int row=sheet.getRows();
                for(int i=0;i<row;++i) {
                    Cell cellCity = sheet.getCell(0, i);
                    Cell cellWord = sheet.getCell(1, i);
                    editor_three_word.putString(cellCity.getContents().trim(), cellWord.getContents().trim());
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return sp_three_word;
            }
            editor_three_word.putBoolean("hasEdited", true);
            editor_three_word.commit();
        }
        return sp_three_word;
    }

    public static SharedPreferences getAirPort(Context context){
        /**
         * 机场名和三字码键值对，三字码为key，用于显示三字码相应的机场名
         */
        SharedPreferences sp_air_port = context.getSharedPreferences(Constants.KEY_SP_AIR_PORT, Context.MODE_PRIVATE);
        if(!sp_air_port.getBoolean("hasEdited",false)){
            SharedPreferences.Editor editor_air_port = sp_air_port.edit();
            InputStream is=null;
            try {
                is=context.getResources().openRawResource(R.raw.three_word);
                Workbook wb=Workbook.getWorkbook(is);
                Sheet sheet=wb.getSheet(0);
                int row=sheet.getRows();
                for(int i=0;i<row;++i) {
                    Cell cellWord = sheet.getCell(1, i);
                    Cell cellAirPort = sheet.getCell(2, i);
                    editor_air_port.putString(cellWord.getContents().trim(), cellAirPort.getContents().trim());
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return sp_air_port;
            }
            editor_air_port.putBoolean("hasEdited", true);
            editor_air_port.commit();
        }
        return sp_air_port;
    }


    public static SharedPreferences getAirLine(Context context) {
        /**
         * 航空公司和其缩写键值对存储
         */
        SharedPreferences sp_air_line = context.getSharedPreferences(Constants.KEY_SP_AIR_LINE, Context.MODE_PRIVATE);
        if (!sp_air_line.getBoolean("hasEdited", false)) {
            SharedPreferences.Editor editor_air_line = sp_air_line.edit();
            InputStream is = null;
            try {
                is = context.getResources().openRawResource(R.raw.air_line);
                Workbook wb = Workbook.getWorkbook(is);
                Sheet sheet = wb.getSheet(0);
                int row = sheet.getRows();
                for (int i = 0; i < row; ++i) {
                    Cell cellName = sheet.getCell(0, i);
                    Cell cellWord = sheet.getCell(1, i);
                    editor_air_line.putString(cellWord.getContents().trim(), cellName.getContents().trim());
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return sp_air_line;
            }
            editor_air_line.putBoolean("hasEdited", true);
            editor_air_line.commit();
        }
        return sp_air_line;
    }

    public static SharedPreferences getCabin(Context context) {
        /**
         * 舱位类型和其缩写键值对存储
         */
        SharedPreferences sp_cabin = context.getSharedPreferences(KEY_SP_CABIN, Context.MODE_PRIVATE);
        if (!sp_cabin.getBoolean("hasEdited", false)) {
            SharedPreferences.Editor editor_cabin = sp_cabin.edit();
            String[] cabins = context.getResources().getStringArray(R.array.TypeCabin);
            editor_cabin.putString("A", cabins[0]);
            editor_cabin.putString(cabins[0], "A");

            editor_cabin.putString("Y", cabins[1]);
            editor_cabin.putString(cabins[1], "Y");

            editor_cabin.putString("C", cabins[2]);
            editor_cabin.putString(cabins[2], "C");

            editor_cabin.putString("F", cabins[3]);
            editor_cabin.putString(cabins[3], "F");

            editor_cabin.putBoolean("hasEdited", true);
            editor_cabin.commit();
        }
        return sp_cabin;
    }
}
