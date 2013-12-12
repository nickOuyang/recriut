package com.janan.net;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.janan.data.bean.OutResumeRecord;
import com.janan.data.bean.personal.Resume;
import com.janan.recruit.BaseActivity;

public class SendResumeOutRecordsEngine extends BaseEngine {
	private ArrayList<OutResumeRecord> mList;
	public SendResumeOutRecordsEngine(BaseActivity activity,boolean isperson){
		super(activity,isperson);
	}
	

	public void start(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<NameValuePair> _Params = new ArrayList<NameValuePair>();
				addEncodePara(mActivity.mUser.getmUserID(), "per_id", _Params);
				requestToNet(mSendResumeOutRecords, _Params);
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
				int _count = _ResultObject.optInt("total_count");
				mList = new ArrayList<OutResumeRecord>();
				if(_count>0){
					JSONArray _Records = _ResultObject.optJSONArray("records");
					
					for(int i=0;i<_Records.length();i++){
						OutResumeRecord _Record = new OutResumeRecord();
						JSONObject _RecordObject = _Records.optJSONObject(i);
						_Record.setmId(_RecordObject.optString("id").trim());
						_Record.setmJianliId(_RecordObject.optString("jianliid").trim());
						_Record.setmPotime(_RecordObject.optString("potime").trim());
						_Record.setmQiyemc(_RecordObject.optString("qiyemc").trim());
						_Record.setmQiyeyx(_RecordObject.optString("qiyeyx").trim());
						_Record.setmZhiweimc(_RecordObject.optString("zhiweimc").trim());
						mList.add(_Record);
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


	public ArrayList<OutResumeRecord> getmList() {
		return mList;
	}


	public void setmList(ArrayList<OutResumeRecord> mList) {
		this.mList = mList;
	}


	
}
