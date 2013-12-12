package com.janan.recruit;

import android.app.TabActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.gazecloud.dyteam.R;
import com.janan.data.bean.TabData;
import com.janan.recruit.companyactivity.CompanyActivity;
import com.janan.recruit.personalactivity.PersonalAcitivty;
import com.janan.recruit.searchactivity.SearchActivity;
import com.janan.recruit.subscribeactivity.JobSubscribeActivity;
import com.janan.resume.workdynactivity.WorkDynamicsActivity;

public class MainTabActivity extends TabActivity {
	/**
	 * Called when the activity is first created.
	 * ������ڣ�����������ӱ�ǩ���ֱ�Ϊ��ְλ���ģ��������������ģ���ҵ���ģ�ְ����̬
	 * */
	private TabHost mTabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost);
		addTabs();
	}

	/**
	 * �������ӱ�ǩ����Ĭ����ʾ����ҳ��
	 * */
	private void addTabs() {
		mTabHost = getTabHost();
		TabData _subscribe = new TabData(JobSubscribeActivity.class, // ����ҳ��
				R.string.tab_tag_subscribe, R.string.tab_subscribe, this);
		addTab(_subscribe);
		TabData _search = new TabData(SearchActivity.class, // ����ҳ��
				R.string.tab_tag_search, R.string.tab_search, this);
		addTab(_search);
		TabData _personal = new TabData(PersonalAcitivty.class, // ��������ҳ��
				R.string.tab_tag_personal, R.string.tab_personal, this);
		addTab(_personal);
		TabData _business = new TabData(CompanyActivity.class, // ��ҵ����ҳ��
				R.string.tab_tag_business, R.string.tab_business, this);
		addTab(_business);
		TabData _dynamics = new TabData(WorkDynamicsActivity.class, // ְ����̬ҳ��
				R.string.tab_tag_dynamics, R.string.tab_dynamics, this);
		addTab(_dynamics);
		mTabHost.setCurrentTabByTag(_search.getmTagId());// Ĭ��Ϊ����ҳ��
	}

	/**
	 * ���tab��ǩ
	 * */

	private void addTab(TabData tab) {
		Intent intent = new Intent();
		intent.setClass(MainTabActivity.this, tab.getmClass());
		TabSpec spec = mTabHost.newTabSpec(tab.getmTagId());
		spec.setIndicator(tab.getDislayView());
		spec.setContent(intent);
		mTabHost.addTab(spec);
	}

}