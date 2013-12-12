//package com.janan.util;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//import com.janan.data.bean.personal.CoverLetter;
//import com.janan.data.bean.personal.Resume;
//import com.janan.recruit.BaseActivity;
//import com.janan.recruit.R;
//
//public class ResumeManager {
//	
//	private Resume mResume;
//	private LinearLayout mLayout;
//	private BaseActivity mActivity;
//	
//	public ResumeManager(BaseActivity activity){
//		mActivity = activity;
//		checkResume();
//	}
//	
//	private void checkResume(){
//		if(mResume == null){
//			mResume = new Resume(mActivity);
//			mResume.setmBirthday("1985-5-5");
//			mResume.setmEducationString("ѧʿѧλ");
//			mResume.setmHeightString("179");
//			mResume.setmIntentionJob("��Ӫ����");
//			mResume.setmIntentionPlaceString("�Ϻ�");
//			mResume.setmNameString("����");
//			mResume.setmPage(1);
//			mResume.setmPhoneNum("13876499876");
//			mResume.setmQiwangyuexin("10000-15000");
//			mResume.setmResidence("�Ϻ�");
//			mResume.setmSexString("��");
//			mResume.setmUserId("123");
//			CoverLetter _Letter = new CoverLetter();
//			_Letter.setmTitleString("�ҵļ���");
//			_Letter.setmContentString("lelelelelelelele");
//			_Letter.setmUerId(mResume.getmUserId());
//			mResume.setmCoverLetter(_Letter);
//		}
//	}
//	public LinearLayout getFirstPageView(){
//		checkResume();
//		LinearLayout _Layout = null;
//		if(mResume.getmPage()==1){
//			_Layout = 
//					(LinearLayout)mActivity.getLayoutInflater().inflate(R.layout.firstresumeview, null);
//			EditText _NameEditText = (EditText)_Layout.findViewById(R.id.nameedit);
//			_NameEditText.setText(mResume.getmNameString());
//			RadioGroup _Group = (RadioGroup)_Layout.findViewById(R.id.ataawGroup);
//			_Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {    
//	            @Override 
//	            public void onCheckedChanged(RadioGroup group, int checkedId) { 
//	                //doing actions    
//	            	
//	            	if(checkedId == R.id.radioButton1){
//	            		group.getChildAt(0).setClickable(true);
//	            		mResume.setmSexString("��");
//	            	}
//	            	if(checkedId == R.id.radioButton2){
//	            		group.getChildAt(1).setClickable(true);
//	            		mResume.setmSexString("Ů");
//	            	}
//	            } 
//			});
//			
//			RadioButton _MaleCheckBox = (RadioButton)_Layout.findViewById(R.id.radioButton1);
//			RadioButton _FemaleCheckBox = (RadioButton)_Layout.findViewById(R.id.radioButton2);
//			if(mResume.getmSexString().equals("��")){
//				
//				_MaleCheckBox.setChecked(true);
//			}else{
//				_FemaleCheckBox.setChecked(true);
//			}
//			
//			EditText _PhoneEditText = (EditText)_Layout.findViewById(R.id.phoneedit);
//			_PhoneEditText.setText(mResume.getmPhoneNum());
//			TextView _BirthTextView = (TextView)_Layout.findViewById(R.id.birthdayedit);
//			_BirthTextView.setText(mResume.getmBirthday());
//			TextView _ResidenceTextView = (TextView)_Layout.findViewById(R.id.residenceedit);
//			_ResidenceTextView.setText(mResume.getmResidence());
//			TextView _IntentionPlaceTextView = (TextView)_Layout.findViewById(R.id.intentionplaceedit);
//			_IntentionPlaceTextView.setText(mResume.getmIntentionPlaceString());
//			TextView _IntentionJobView = (TextView)_Layout.findViewById(R.id.intentionjobedit);
//			_IntentionJobView.setText(mResume.getmIntentionJob());
//			Button _SaveButton = (Button)_Layout.findViewById(R.id.save);
//			_SaveButton.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					HttpPost httpRequest =new HttpPost("http://www.dyteam.com/api/index.php?module=user&method=register");  
//				    //Post�������ͱ���������NameValuePair[]���д���  
//				    //������ ����˻�ȡ�ķ���Ϊrequest.getParameter("name")  
//				    List <NameValuePair> params=new ArrayList<NameValuePair>();  
//				    params.add(new BasicNameValuePair("username","wantyou365")); 
//				    params.add(new BasicNameValuePair("email","wantyou365@163.com"));
//				    params.add(new BasicNameValuePair("password","shangdongf"));
//				    params.add(new BasicNameValuePair("user_type","2"));
//				    try{  
//				       
//				       
//				     //����HTTP request  
//				     httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));  
//				     //ȡ��HTTP response  
//				     HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);  
//				       
//				     //��״̬��Ϊ200 ok   
//				     if(httpResponse.getStatusLine().getStatusCode()==200){  
//				      //ȡ����Ӧ�ִ�  
//				      String strResult=EntityUtils.toString(httpResponse.getEntity());  
//				      Log.i("janan", strResult) ;
//				     }else{  
//				    	 String strResult=EntityUtils.toString(httpResponse.getEntity());
//				    	 Log.i("janan", "����"+strResult) ;
//				     }  
//				    }catch(Exception e){  
//				     e.printStackTrace();
//				    }  
//				}
//			});
//		}
//		
//				
//		return _Layout;
//	}
//	
//}
