package com.daemon.pay.icbcpay;
import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ICBCPay {

	static long Timess=new Date().getTime();  //获取时间戳

	public static  String  timeul= Timess+"";//转换时间戳
	public static  String Times =timeul.substring(0, 10);//截取前十位

	static SimpleDateFormat wtime = new SimpleDateFormat("yyyyMMddHHmmss");
	static SimpleDateFormat wtimes = new SimpleDateFormat("yyyyMMdd");
	public static  String windtime=wtime.format(new Date());

	public static  String PackageIDs= "FCK123" + Times;//指令编码
	public static  String SendTimes = windtime;//发送时间
	public static  String CISs="390190001501002";//集团ID
	public static  String BankCodes="102";//银行编码
	public static  String IDs="tykj.y.3901";//ID
	public static  String TranDates=wtimes.format(new Date());//日期
	public static String TranTimes=""+Times;//时间戳
	public static String fSeqnos=PackageIDs;


	//服务器地址
	public static String urls="http://122.227.164.126:448/servlet/ICBCCMPAPIReqServlet?userID=tykj.y.3901&PackageID="+PackageIDs+"&SendTime="+SendTimes;

	/*
	 * 下面这块很重要实现功能的必须！XML银行的提供使用
	 * -----------------------------------------------------
	 */
	public static String QrySerialNo="";//要查询的交易数据
	public static String QryfSeqno="W2016012104024160509";//要查询的交易数据
	//XML DATA
	//public static String QEPAYSUB="<?xml version='1.0' encoding='GBK'?><CMS><eb><pub><TransCode>QEPAYSUB</TransCode><CIS>"+CISs+"</CIS><BankCode>"+BankCodes+"</BankCode><ID>"+IDs+"</ID><TranDate>"+TranDates+"</TranDate><TranTime>"+TranTimes+"</TranTime><fSeqno>"+fSeqnos+"</fSeqno></pub><in><QrySerialNo>"+QrySerialNo+"</QrySerialNo><QryfSeqno>"+QryfSeqno+"</QryfSeqno></in></eb></CMS>";
	public static String QEPAYSUB="<?xml version='1.0' encoding='GBK'?><CMS><eb><pub><TransCode>QEPAYSUB</TransCode><CIS>"+CISs+"</CIS><BankCode>"+BankCodes+"</BankCode><ID>"+IDs+"</ID><TranDate>"+TranDates+"</TranDate><TranTime>"+TranTimes+"</TranTime><fSeqno>"+fSeqnos+"</fSeqno></pub><in><OrderNo>W2016012104024160509</OrderNo></in></eb></CMS>";


	/*
	 * -------------------------------------------------------
	 */

	//发送数据组合
	public static String postdata="Version=0.0.1.0&TransCode=QEPAYSUB&BankCode=102&GroupCIS="+CISs+"&ID="+IDs+"&PackageID="+PackageIDs+"&Cert=&reqData="+QEPAYSUB;

	/**
	 * 开始发送请求，并返回支付数据。
	 */
	public static void startPostForPay(){
		//发送 POST 请求
		String sr;
		sr = cnpaypost.sendPost(urls, postdata);
		String xmlstr=getFromBASE64(sr);
		System.out.println("获取银行返回XML数据:"+xmlstr);
		System.out.println("获取银行返回的BS64码:"+sr);
		System.out.println("-------------------------------------------------------------------");
		System.out.println("请求的连接："+urls);
		System.out.println("POST发送的数据："+postdata);
		System.out.println("POSTXML："+QEPAYSUB);
	}

	// 将 BASE64 编码的字符串 s 进行解码 
	public static String getFromBASE64(String s)
	{

		String s1 = s.replaceFirst("reqData=","");//去除reqData=

		if (s == null)
			return null;
		//BASE64Decoder decoder = new BASE64Decoder();

		try
		{
			//byte[] b = decoder.decodeBuffer(s1);
			byte[] b = Base64.decode(s1,Base64.DEFAULT);
			return new String(b,"gb2312");
		} catch (Exception e)
		{
			return null;
		}
	}

}


