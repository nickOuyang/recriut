package com.janan.net;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;
import android.widget.Toast;

import com.janan.data.bean.JobInfo;
import com.janan.data.bean.search.NearSearch;
import com.janan.data.data.DataForSearchCondition;
import com.janan.recruit.BaseActivity;

public class SendResumeOutEngine extends BaseEngine {
	
	private String jobName;
	private String comName;
	private String comEmail;
	public SendResumeOutEngine(BaseActivity activity,boolean isperson){
		super(activity,isperson);
	}
	

	public void start(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<NameValuePair> _Params = new ArrayList<NameValuePair>();
				addEncodePara(comEmail, "company_email", _Params);
				addEncodePara(comName, "company_name", _Params);
				addEncodePara(jobName, "job_name", _Params);
				addEncodePara(mActivity.mUser.getmUserID(), "per_id", _Params);
				requestToNet(mSendResumeOut, _Params);
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
			String _TextString = _ResultObject.optString("result_text");
			if (_ResultId == 0) {			
				mActivity.callBack(mActivity.CallbackError,_TextString);
			} else if (_ResultId == 1) {
				mActivity.callBack(mActivity.CallbackSuccess,_TextString);
			}
		} catch (Exception ex) {
			// �쳣�������
			mActivity.callBack(mActivity.CallbackError,MSGHANDLERESULTERROR_STRING);
			ex.printStackTrace();
		}
	}


	public String getJobName() {
		return jobName;
	}


	public void setJobName(String jobName) {
		this.jobName = jobName;
	}


	public String getComName() {
		return comName;
	}


	public void setComName(String comName) {
		this.comName = comName;
	}


	public String getComEmail() {
		return comEmail;
	}


	public void setComEmail(String comEmail) {
		this.comEmail = comEmail;
	}

	
}
