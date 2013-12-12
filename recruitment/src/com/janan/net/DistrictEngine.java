package com.janan.net;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

import com.janan.data.bean.JobInfo;
import com.janan.data.data.City;
import com.janan.data.data.District;
import com.janan.data.data.Province;
import com.janan.recruit.BaseActivity;

public class DistrictEngine extends BaseEngine{
	private ArrayList<District> mList = null;
	private String pId;
	public DistrictEngine(BaseActivity activity, boolean isperson) {
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
				addEncodePara(pId, "cityid", _Params);
				requestToNet(mGetDistrict, _Params);
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

	@Override
	public void parseResponse(String result) {
		// TODO Auto-generated method stub
		try {
			JSONTokener jsonParser = new JSONTokener(result);
			// ��ʱ��δ��ȡ�κ�json�ı���ֱ�Ӷ�ȡ����һ��JSONObject����
			// �����ʱ�Ķ�ȡλ����"name" : �ˣ���ônextValue����"yuanzhifei89"��String��
			JSONObject _ResultObject = (JSONObject) jsonParser.nextValue();
			int _ResultId = _ResultObject.optInt("result");
			
			if (_ResultId == 0) {	
				String _TextString = _ResultObject.optString("result_text");
				mActivity.callBack(mActivity.CallbackError,_TextString);
			} else if (_ResultId == 1) {
				mList = new ArrayList<District>();
				JSONArray _Array= _ResultObject.optJSONArray("areas");
				for(int i=0;i<_Array.length();i++){
					JSONObject proObject = _Array.optJSONObject(i);
					District _District = new District();
					_District.setmID(proObject.optString("areaID"));
					_District.setmName(proObject.optString("area"));
					mList.add(_District);
				}
				mActivity.callBack(mActivity.CallbackSuccess,null);
			}
		} catch (Exception ex) {
			// �쳣�������
			mActivity.callBack(mActivity.CallbackError,MSGHANDLERESULTERROR_STRING);
			ex.printStackTrace();
		}
	}

	public ArrayList<District> getmList() {
		
		return mList;
	}

	public void setmList(ArrayList<District> mList) {
		this.mList = mList;
	}

}
