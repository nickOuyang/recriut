package com.janan.net;

import java.util.ArrayList;

import org.apache.http.NameValuePair;


/**
 * ��������ӿ�
 * */
public interface EngineUtilInter {

	
	
	/**
	 * ������������
	 * */
	public void requestToNet(String url,ArrayList<NameValuePair> params);

	

	/**
	 * �������緵������
	 * */
	public void parseResponse(String result);

	/**
	 * ��֤������Ϣ
	 * */
	public boolean isConnected();
}
