package com.janan.view.mamager;

import java.util.ArrayList;

import android.widget.LinearLayout;

import com.janan.adapter.PositionSubscribeAdapter;
import com.janan.data.bean.JobInfo;
import com.janan.recruit.BaseActivity;

/**
 * PositionSubscribeActivity ��JobsActivity�򵥹�����Ϣ
 * */
public class SubScribeJobManager {
	private ArrayList<JobInfo> mJobsList;
	private BaseActivity mActivity;
	private LinearLayout mLayout;
	private PositionSubscribeAdapter mSubscribeAdapter;

	public SubScribeJobManager(BaseActivity activity) {
		this.mActivity = activity;
	}

	public LinearLayout getSubscribeItemView(int i) {
		return mJobsList.get(i).getSubscribeItemView(mActivity);
	}

	public LinearLayout getJobsItemView(int i) {

		return mJobsList.get(i).getJobsItemView(mActivity);

	}

	public void setData(ArrayList<JobInfo> list) {
		// setmAddress(address);
		// setmCompName(compname);
		// setmDate(date);
		// setmJobName(jobname);
		// setmTimes(times);
		this.mJobsList = list;
	}

	public ArrayList<JobInfo> getJobsData() {

		for (int i = 0; i < 20; i++) {
			JobInfo _info1 = new JobInfo();
			_info1.setmZhiweimingcheng("פ�꾭��");
			_info1.setmGongzuodidian("��ɽ��");
			_info1.setmGongsimingchen("����һ����������޹�˾");
			_info1.setmFaburiqi("2013-03-14");
			_info1.setmSendClickString("56");

			mJobsList.add(_info1);
		}
		return mJobsList;
	}

	public PositionSubscribeAdapter getmSubscribeAdapter() {
		return mSubscribeAdapter;
	}

	public void setmSubscribeAdapter(PositionSubscribeAdapter mSubscribeAdapter) {
		this.mSubscribeAdapter = mSubscribeAdapter;
	}
	
	public PositionSubscribeAdapter createSubscribeAdapter(){
		if(mJobsList!=null&&mJobsList.size()>0){
			mSubscribeAdapter = new PositionSubscribeAdapter(mActivity,false);
			mSubscribeAdapter.setmList(mJobsList);
		}
		return mSubscribeAdapter;
	}
}
