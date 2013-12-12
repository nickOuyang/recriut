package com.janan.util;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;



import android.util.Log;
import android.util.Xml;

public class MobileService {
	private static final String TAG = "MobileService";
	//����inputStream��mobile(�û�������ֻ���)�����ļ�mobileaddress.xml��mobile�����滻
	public static String readSoapFile(InputStream inputStream, String mobile) throws Exception {
		byte[] data = StreamTool.readInputStream(inputStream);
		Log.i(TAG, "datalength: " + data.length);
		String soapxml = new String(data);
		Log.i(TAG, "soapxml: " + soapxml);
		Map<String, String> params = new HashMap<String, String>();
		params.put("mobile", mobile);
		return replace(soapxml, params);
	
	}
	
	//����滻�ĺ���
	private static String replace(String xml, Map<String, String> params) throws Exception {
		String result = xml;
		if(params != null && !params.isEmpty()) {
			for(Map.Entry<String, String> entry: params.entrySet()) {
				String name = "\\{1}quot; "+ entry.getKey();
				Pattern pattern = Pattern.compile(name);
				Matcher matcher = pattern.matcher(result);
			    if(matcher.find()) {
			    	result = matcher.replaceAll(entry.getValue());
			    }
			}
		}
		return result;
	}
	
	public static String getMobileAddress(InputStream inStream, String mobile) throws Exception {
	    String soap = readSoapFile(inStream, mobile);
	    byte[] data = soap.getBytes();
		URL url = new URL("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setDoOutput(true);  //�����������
		conn.setConnectTimeout(5 * 1000); //���ó�ʱʱ��Ϊ5��
		conn.setRequestMethod("POST");  
		conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));  //���ó���
		
		OutputStream outputStream = conn.getOutputStream();
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
		
		if(conn.getResponseCode() == 200) {
			return parseResponseXML(conn.getInputStream());  //�����������˷��ص�����
		}
		
		return null;
	}
	
	//�������ص�xml���ݣ�������ֻ��Ź�����ַ
	private static String parseResponseXML(InputStream inputStream) throws XmlPullParserException, IOException {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();  //������һ���¼�
		while(eventType != XmlPullParser.END_DOCUMENT) {  //ֻҪ�����ĵ������¼�
			switch(eventType) {
			case XmlPullParser.START_TAG: 
				String name = parser.getName(); //��ȡ��������ǰָ���Ԫ�ص�����
				if("getMobileCodeInfoResult".equals(name)) {
					return parser.nextText();
				}
				break;
			}
			eventType = parser.next();
		}
		return null;
	}
	
}