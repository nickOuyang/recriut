package com.janan.net;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.janan.data.data.JobDept;
import com.janan.data.data.Province;
import com.janan.recruit.BaseActivity;

public class JobDeptEngine extends BaseEngine{
	private ArrayList<JobDept> mList = null;
	public JobDeptEngine(BaseActivity activity, boolean isperson) {
		super(activity, isperson);
		// TODO Auto-generated constructor stub
	}
	public void start(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				requestToNet(mGetJobDept, null);
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
				mList = new ArrayList<JobDept>();
				JSONArray _Array= _ResultObject.optJSONArray("jobdept");
				for(int i=0;i<_Array.length();i++){
					JSONObject proObject = _Array.optJSONObject(i);
					JobDept _JobDept = new JobDept();
					_JobDept.setmId(proObject.optString("JobDeptID"));
					_JobDept.setmName(proObject.optString("JobDept"));
					mList.add(_JobDept);
				}
				mActivity.callBack(mActivity.CallbackSuccess,null);
			}
		} catch (Exception ex) {
			// �쳣�������
			mActivity.callBack(mActivity.CallbackError,MSGHANDLERESULTERROR_STRING);
			ex.printStackTrace();
		}
	}

	public ArrayList<JobDept> getmList() {
		
		return mList;
	}

	public void setmList(ArrayList<JobDept> mList) {
		this.mList = mList;
	}

}
