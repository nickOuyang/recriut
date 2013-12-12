package com.janan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.StaticLayout;
import android.util.Log;

public class LocationUtil {
	public static Double mLongitude = 0.0;
	public static Double mLatitude = 0.0;

	public static void setAddress(final Context context) {
		getLocation(context);
		if (!(mLongitude == 0.0 && mLatitude == 0.0)) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					String City;
					String Province;

					String url = "http://maps.google.com/maps/api/geocode/json?latlng="
							+ mLatitude
							+ ","
							+ mLongitude
							+ "&language=zh_CN&sensor=false";
					HttpClient client = new DefaultHttpClient();
					StringBuilder sb = new StringBuilder();
					try {
						HttpResponse resp = client.execute(new HttpGet(url));
						HttpEntity he = resp.getEntity();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(he.getContent()));
						String str = "";
						while ((str = br.readLine()) != null) {
							sb.append(str);
						}

					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					try {
						JSONObject jo1 = new JSONObject(sb.toString());
						String str1 = jo1.getString("results");
						JSONArray arr1 = new JSONArray(str1);
						String str2 = arr1.get(0).toString();
						JSONObject jo2 = new JSONObject(str2);
						String str3 = jo2.getString("formatted_address");
						JSONArray arr2 = jo2.getJSONArray("address_components");
						for (int i = 0; i < arr2.length(); i++) {
							JSONObject ob = arr2.optJSONObject(i);
							if (ob != null) {
								JSONArray child_ob = ob.getJSONArray("types");
								String[] strs = new String[child_ob.length()];
								for (int j = 0; j < child_ob.length(); j++) {
									strs[j] = child_ob.optString(j);
								}
								if (strs.length > 1) {
									Util.ifArrayContain("locality", strs);
									if (Util.ifArrayContain("locality", strs)
											&& Util.ifArrayContain("political",
													strs)) {
										City = ob.optString("long_name");
										SharedPreferences _pre = PreferenceManager
												.getDefaultSharedPreferences(context);
										Editor _editEditor = _pre.edit();
										_editEditor.putString("city", City);
										_editEditor.commit();
									}
									if (Util.ifArrayContain(
											"administrative_area_level_1", strs)
											&& Util.ifArrayContain("political",
													strs)) {
										Province = ob.optString("long_name");
										SharedPreferences _pre = PreferenceManager
												.getDefaultSharedPreferences(context);
										Editor _editEditor = _pre.edit();
										_editEditor.putString("province",
												Province);
										_editEditor.commit();
									}

								}

							}
						}

						// Toast.makeText(LocationService.this, str3,
						// Toast.LENGTH_LONG).show();
					} catch (JSONException e) {

					}
				}
			}).start();
		}

	}

	private static TelephonyManager telephonyManager;
	private static String IMSI;

	private static void getLocation(Context context) {
		final LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		final LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) { // ������ı�ʱ�����˺��������Provider������ͬ�����꣬���Ͳ��ᱻ����
				// log it when the location changes
				if (location != null) {
					Log.i("SuperMap",
							"Location changed : Lat: " + location.getLatitude()
									+ " Lng: " + location.getLongitude());
				}
			}

			public void onProviderDisabled(String provider) {
				// Provider��disableʱ�����˺���������GPS���ر�
			}

			public void onProviderEnabled(String provider) {
				// Provider��enableʱ�����˺���������GPS����
				Location location = locationManager
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				mLatitude = location.getLatitude(); // ����
				mLongitude = location.getLongitude(); // γ��
				double altitude = location.getAltitude(); // ����
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// Provider��ת̬�ڿ��á���ʱ�����ú��޷�������״ֱ̬���л�ʱ�����˺���
			}
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0, locationListener);
		
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		
		if (location != null) {
			mLatitude = location.getLatitude(); // ����
			mLongitude = location.getLongitude(); // γ��
		} else {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					1000, 0, locationListener);
			location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			if (location != null) {
				mLatitude = location.getLatitude(); // ����
				mLongitude = location.getLongitude(); // γ��
			} else {
				location = locationManager
						.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

				if (location != null) {
					mLatitude = location.getLatitude(); // ����
					mLongitude = location.getLongitude(); // γ��
				}
			}
		}
		
//		if(location == null){
//			String ipString = GetHostIp();
//			String url = "http://www.ip138.com/";
//			HttpClient client = new DefaultHttpClient();
//			StringBuilder sb = new StringBuilder();
//			try {
//				HttpResponse resp = client.execute(new HttpGet(url));
//				HttpEntity he = resp.getEntity();
//				BufferedReader br = new BufferedReader(
//						new InputStreamReader(he.getContent(),"gb2312"));
//				String str = "";
//				while ((str = br.readLine()) != null) {
//					sb.append(str);
//				}
//
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
		// String ProvidersName = null;
		// telephonyManager = (TelephonyManager) context
		// .getSystemService(Context.TELEPHONY_SERVICE);
		// String phoneNumString = telephonyManager.getLine1Number();
		// IMSI = telephonyManager.getSubscriberId();
		// telephonyManager.get
		// // IMSI��ǰ��3λ460�ǹ��ң������ź���2λ00 02���й��ƶ���01���й���ͨ��03���й����š�
		//
		// System.out.println(IMSI);
		//
		// if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
		//
		// ProvidersName = "�й��ƶ�";
		//
		// } else if (IMSI.startsWith("46001")) {
		//
		// ProvidersName = "�й���ͨ";
		//
		// } else if (IMSI.startsWith("46003")) {
		//
		// ProvidersName = "�й�����";
		//
		// }

		// if(location == null){
		// if(!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)){
		// android.provider.Settings.Secure.setLocationProviderEnabled(context.getContentResolver(),
		// LocationManager.GPS_PROVIDER, false);
		// }
		// }
	}

	public static String GetHostIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr
						.hasMoreElements();) {
					InetAddress inetAddress = ipAddr.nextElement();
					System.out.println("");
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
		} catch (Exception e) {
		}
		return null;
	}
}
