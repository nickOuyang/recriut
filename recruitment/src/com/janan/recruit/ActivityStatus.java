package com.janan.recruit;

import android.os.Message;

public interface ActivityStatus {
	
//	public static final String TAG = "title";
	public static final String STRING_TITLE = "titlestring";
	public static final String TAG_SEARCHKEY = "searchkey";
	public static final String TAG_SEARCHCONDITIONKEY = "searchcondition";
	public static final String TAG_VALUE = "value";
	public static final int REQUEST_SEARCHCHEK = 9;
	public static final String TAG_BUNDLE = "bundle";
	public static final String TAG_HTTP = "http";
	public static final int VALUE_RESULT_OK = 2;
	

	public static final String TAG_PROVINCE = "provincename";
	public static final String TAG_PROVINCEID = "provinceid";
	public static final String TAG_CITY = "cityname";
	public static final String TAG_CITYID = "cityid";
	public static final String TAG_AREA = "area";
	public static final String TAG_AREAID = "areaid";
	public static final String TAG_HASCITY = "hascity";
	public static final String TAG_HASAREA = "hasarea";
	
	public static final String TAG_JOBDEPT = "jobdeptname";
	public static final String TAG_JOBDEPTID = "jobdeptid";
	public static final String TAG_JOBNAME = "jobname";
	public static final String TAG_JOBNAMEID = "jobnameid";
	public static final String TAG_HASJOBNAME = "hasjobname";
	/**
	 * ��Ӷ���������
	 * */
	public void addBar();

	/**
	 * ���½���ui
	 * */
	public void updateUI();

	/**
	 * ����ҳ����Ϣ
	 * */
	public void saveData();

	/**
	 * ҳ������
	 * */
	public void finishSelf();
	
	/**
	 * ��ʾҳ��
	 * */
	public void showSelf();
	
	/**
	 * ��ʼ��ui
	 * */
	public void initView();
	// public void addSearchLay();
	
	public void callBack(int callbackstatus,String info);
	
	public void showToast(Message message) ;

}
