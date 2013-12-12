package com.janan.net;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.janan.data.bean.JobInfo;
import com.janan.data.data.City;
import com.janan.data.data.Province;
import com.janan.recruit.BaseActivity;
import com.janan.util.Util;

public class CityEngine extends BaseEngine{
	private ArrayList<City> mList = null;
	private String pId;
	public CityEngine(BaseActivity activity, boolean isperson) {
		super(activity, isperson);
		// TODO Auto-generated constructor stub
	}
	
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public void start(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<NameValuePair> _Params = new ArrayList<NameValuePair>();
				addEncodePara(pId, "provinceid", _Params);
			
				requestToNet(mGetCities, _Params);
			}
		}).start();
		
	}

	@Override
	public void requestToNet(String url, ArrayList<NameValuePair> params) {
		// TODO Auto-generated method stub
		if (super.isConnected()) {
			super.requestToNet(url, params);
		}

	}
	private boolean canTop = false;
	@Override
	public void parseResponse(String result) {
		// TODO Auto-generated method stub
		try {
			JSONTokener jsonParser = new JSONTokener(result);
			// ��ʱ��δ��ȡ�κ�json�ı���ֱ�Ӷ�ȡ����һ��JSONObject����
			// �����ʱ�Ķ�ȡλ����"name" : �ˣ���ônextValue����"yuanzhifei89"��String��
			JSONObject _ResultObject = (JSONObject) jsonParser.nextValue();
			int _ResultId = _ResultObject.getInt("result");
			SharedPreferences _pre = mActivity.getPreferences(mActivity.MODE_APPEND);
    		Editor _editEditor = _pre.edit();
    		
    		String _city = _pre.getString("city", "");
			if (_ResultId == 0) {	
				String _TextString = _ResultObject.getString("result_text");
				mActivity.callBack(mActivity.CallbackError,_TextString);
			} else if (_ResultId == 1) {
				mList = new ArrayList<City>();
				JSONArray _Array= _ResultObject.optJSONArray("citys");
				if(_Array!=null){
					for(int i=0;i<_Array.length();i++){
						JSONObject proObject = _Array.optJSONObject(i);
						City _City = new City();
						_City.setmId(proObject.optString("cityID"));
						_City.setmName(proObject.optString("city"));
						if(Util.checkString(_city)){
							if(_City.getmName().contains(_city)){
								canTop = true;
							}
						}
						if(canTop){
							mList.add(0, _City)	;
							canTop = false;
						}else{
							mList.add(_City);
						}
						
					}
				}
				
				mActivity.callBack(mActivity.CallbackSuccess,null);
			}
		} catch (Exception ex) {
			// �쳣�������
			mActivity.callBack(mActivity.CallbackError,MSGHANDLERESULTERROR_STRING);
			ex.printStackTrace();
		}
	}

	public ArrayList<City> getmList() {
		
		return mList;
	}

	public void setmList(ArrayList<City> mList) {
		this.mList = mList;
	}

}
