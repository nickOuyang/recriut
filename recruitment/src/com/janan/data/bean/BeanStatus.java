package com.janan.data.bean;

import android.R.integer;
import android.os.Bundle;

public interface BeanStatus {
	/**
	 * ˢ��bean��ֵ
	 * */
	public void updateUI(Bundle bundle);
	
	/**
	 * ��ȡĬ��ֵ
	 * */
	public String getDefoultValue(int tag);
}
