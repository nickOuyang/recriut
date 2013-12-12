package com.janan.net;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.janan.data.data.City;
import com.janan.data.data.JobName;
import com.janan.recruit.BaseActivity;

public class JobNameEngine extends BaseEngine{
	private ArrayList<JobName> mList = null;
	private String DpId;
	public JobNameEngine(BaseActivity activity, boolean isperson) {
		super(activity, isperson);
		// TODO Auto-generated constructor stub
	}
	
	public String getDpId() {
		return DpId;
	}

	public void setDpId(String dpId) {
		DpId = dpId;
	}

	public void start(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<NameValuePair> _Params = new ArrayList<NameValuePair>();
				
				addEncodePara(DpId, "jobdeptid", _Params);
				requestToNet(mGetJobName, _Params);
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
				mList = new ArrayList<JobName>();
				JSONArray _Array= _ResultObject.optJSONArray("jobnames");
				for(int i=0;i<_Array.length();i++){
					JSONObject proObject = _Array.optJSONObject(i);
					JobName _JobName = new JobName();
					_JobName.setmId(proObject.optString("Jobid"));
					_JobName.setmName(proObject.optString("Job"));
					mList.add(_JobName);
				}
				mActivity.callBack(mActivity.CallbackSuccess,null);
			}
		} catch (Exception ex) {
			// �쳣�������
			mActivity.callBack(mActivity.CallbackError,MSGHANDLERESULTERROR_STRING);
			ex.printStackTrace();
		}
	}

	public ArrayList<JobName> getmList() {
		
		return mList;
	}

	public void setmList(ArrayList<JobName> mList) {
		this.mList = mList;
	}

}
