package com.zany.androidinfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class AndroidInfo extends Activity {

	private final String TAG = this.getClass().getSimpleName();
	private final int SIGNAL_STRENGTH_HEIGHT = 45;

	private Context mContext;
	
	private TelephonyManager mTelephonyMgr;
	private ZanyPhoneStateListener mPhoneStateListener;
	
	private RelativeLayout.LayoutParams mParams;
	private RelativeLayout mainLayout;
	
	private TextView mTvSignal;
	private TextView mTvInfo;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.mContext = getApplicationContext();

		BaseData.FONT_TYPE_TAHOMA = Typeface.createFromAsset(getAssets(), BaseData.FONT_TAHOMA);
		BaseData.FONT_TYPE_MALGUN = Typeface.createFromAsset(getAssets(), BaseData.FONT_MALGUN);
		BaseData.FONT_TYPE_VERDANA = Typeface.createFromAsset(getAssets(), BaseData.FONT_VERDANA);

		mPhoneStateListener = new ZanyPhoneStateListener();
		mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		mParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		mainLayout = new RelativeLayout(mContext);
		mainLayout.setLayoutParams(mParams);
		mainLayout.setBackgroundColor(Color.WHITE);
		
		mParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		mTvSignal = new TextView(mContext);
		mTvSignal.setLayoutParams(mParams);
		mTvSignal.setHeight(SIGNAL_STRENGTH_HEIGHT);
		mTvSignal.setPadding(5, 5, 0, 5);
		mTvSignal.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
		mTvSignal.setBackgroundColor(Color.rgb(51, 86, 60));
		mTvSignal.setTypeface(BaseData.FONT_TYPE_TAHOMA);
		mTvSignal.setTextSize(15.0f);
		mTvSignal.setSingleLine(true);
		mTvSignal.setTextColor(Color.WHITE);
		
		mainLayout.addView(mTvSignal);
		
		setContentView(mainLayout);
		
		drawInfo();
		
	}
	
	@Override
	protected void onResume() {
		Log.e(TAG, "------------------ onResume");
		mTelephonyMgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		Log.e(TAG, "------------------ onStop");
		mTelephonyMgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
		super.onStop();
	}
	
	@Override
	protected void onPause() {
		Log.e(TAG, "------------------ onPause");
		mTelephonyMgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
		super.onPause();
	}
	
	private String makeNetworkType(int networkType) {

		String sResult = Integer.toString(networkType);
		
		switch (networkType) {
			case TelephonyManager.NETWORK_TYPE_EDGE: sResult += " (EDGE)"; break;
			case TelephonyManager.NETWORK_TYPE_CDMA: sResult += " (CDMA)"; break;
			case TelephonyManager.NETWORK_TYPE_UMTS: sResult += " (UMTS)"; break;
			case TelephonyManager.NETWORK_TYPE_GPRS: sResult += " (GPRS)"; break;
			case TelephonyManager.NETWORK_TYPE_HSDPA: sResult += " (HSDPA)"; break;
			case TelephonyManager.NETWORK_TYPE_UNKNOWN: sResult += " (UNKNOWN)"; break;
		}
		
		return sResult;
		
	}
	
	private String makeSimState(int simState) {
		
		String sResult = Integer.toString(simState);
		
		switch (simState) {
			case TelephonyManager.SIM_STATE_ABSENT: sResult += " (ABSENT)"; break;
			case TelephonyManager.SIM_STATE_NETWORK_LOCKED: sResult += " (NETWORK_LOCKED)"; break;
			case TelephonyManager.SIM_STATE_PIN_REQUIRED: sResult += " (PIN_REQUIRED)"; break;
			case TelephonyManager.SIM_STATE_PUK_REQUIRED: sResult += " (PUK_REQUIRED)"; break;
			case TelephonyManager.SIM_STATE_READY: sResult += " (READY)"; break;
			case TelephonyManager.SIM_STATE_UNKNOWN: sResult += " (UNKNOWN)"; break;
		}
		
		return sResult;
		
	}

	private String makeCallState(int callState) {
		
		String sResult = Integer.toString(callState);
		
		switch (callState) {
			case TelephonyManager.CALL_STATE_IDLE: sResult += " (IDLE)"; break;
			case TelephonyManager.CALL_STATE_OFFHOOK: sResult += " (OFFHOOK)"; break;
			case TelephonyManager.CALL_STATE_RINGING: sResult += " (RINGING)"; break;
		}
		
		return sResult;
		
	}

	private String makeDataActivity(int dataActivity) {
		
		String sResult = Integer.toString(dataActivity);
		
		switch (dataActivity) {
			case TelephonyManager.DATA_ACTIVITY_DORMANT: sResult += " (DORMANT)"; break;
			case TelephonyManager.DATA_ACTIVITY_IN:      sResult += " (IN)"; break;
			case TelephonyManager.DATA_ACTIVITY_OUT:     sResult += " (OUT)"; break;
			case TelephonyManager.DATA_ACTIVITY_INOUT:   sResult += " (INOUT)"; break;
			case TelephonyManager.DATA_ACTIVITY_NONE:    sResult += " (NONE)"; break;
		}
		
		return sResult;
		
	}

	private String makeDataState(int dataState) {
		
		String sResult = Integer.toString(dataState);
		
		switch (dataState) {
			case TelephonyManager.DATA_CONNECTED:    sResult += " (CONNECTED)"; break;
			case TelephonyManager.DATA_CONNECTING:   sResult += " (CONNECTING)"; break;
			case TelephonyManager.DATA_DISCONNECTED: sResult += " (DISCONNECTED)"; break;
			case TelephonyManager.DATA_SUSPENDED:    sResult += " (SUSPENDED)"; break;
		}
		
		return sResult;
		
	}

	private String makePhoneType(int phoneType) {
		
		String sResult = Integer.toString(phoneType);
		
		switch (phoneType) {
			case TelephonyManager.PHONE_TYPE_CDMA: sResult += " (CDMA)"; break;
			case TelephonyManager.PHONE_TYPE_GSM: sResult += " (GSM)"; break;
			case TelephonyManager.PHONE_TYPE_NONE: sResult += " (NONE)"; break;
		}
		
		return sResult;
		
	}
	
	private void drawInfo() {

		mainLayout.removeView(mTvInfo);

		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		
		GsmCellLocation cellLocation = (GsmCellLocation) mTelephonyMgr.getCellLocation();

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
		Location location = null;
		
		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if (location == null) {
			location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		
		Log.e(TAG, "----------------- " +  (location == null));

		// MCC, MNC 를 통해 Operator, Carrier, 사용 주파수 대역 등의 정보를 알수 있다.
		// http://en.wikipedia.org/wiki/Mobile_Network_Code
				
		String sInfo = "";
		
		sInfo += "<font color='#FF5500'><b>General Info</b></font><br/>";
		sInfo += " - <b>Device ID</b> : " + mTelephonyMgr.getDeviceId() + "<br/>";
		sInfo += " - <b>Device Software Version</b> : " + mTelephonyMgr.getDeviceSoftwareVersion() + "<br/>";
		sInfo += " - <b>Call State</b> : " + makeCallState(mTelephonyMgr.getCallState()) + "<br/>";
		sInfo += " - <b>Data Activity</b> : " + makeDataActivity(mTelephonyMgr.getDataActivity()) + "<br/>";
		sInfo += " - <b>Data State</b> : " + makeDataState(mTelephonyMgr.getDataState()) + "<br/>";
		sInfo += " - <b>Phone Type</b> : " + makePhoneType(mTelephonyMgr.getPhoneType()) + "<br/>";
		sInfo += " - <b>Line1Number (MSISDN)</b> : " + mTelephonyMgr.getLine1Number() + "<br/>";
		sInfo += " - <b>Network Type</b> : " + makeNetworkType(mTelephonyMgr.getNetworkType()) + "<br/>";
		sInfo += " - <b>Network Country Iso</b> : " + mTelephonyMgr.getNetworkCountryIso() + "<br/>";
		sInfo += " - <font color='#0055FF'><b>Network Operator (MCC+MNC)</b></font> : " + mTelephonyMgr.getNetworkOperator() + "<br/>";
		sInfo += " &nbsp; &nbsp;※ MCC - Mobile Country Code<br/>";
		sInfo += " &nbsp; &nbsp;※ MNC - Mobile Network Code<br/>";
		sInfo += " &nbsp; &nbsp;※ <a href='http://en.wikipedia.org/wiki/Mobile_Network_Code'>http://en.wikipedia.org/wiki/Mobile_Network_Code</a><br/>";
		sInfo += " - <b>Network Operator Name</b> : " + mTelephonyMgr.getNetworkOperatorName() + "<br/>";
		sInfo += " - <b>Network Roaming</b> : " + mTelephonyMgr.isNetworkRoaming() + "<br/>";
		sInfo += " - <b>Sim State</b> : " + makeSimState(mTelephonyMgr.getSimState()) + "<br/>";
		sInfo += " - <b>Sim Serial No.</b> : " + mTelephonyMgr.getSimSerialNumber() + "<br/>";
		sInfo += " - <b>Sim Country Iso</b> : " + mTelephonyMgr.getSimCountryIso()+ "<br/>";
		sInfo += " - <font color='#0055FF'><b>Sim Operator (MCC+MNC)</b></font> : " + mTelephonyMgr.getSimOperator() + "<br/>";
		sInfo += " - <b>Sim Operator Name</b> : " + mTelephonyMgr.getSimOperatorName() + "<br/>";
		sInfo += " - <b>Subscriber ID (IMSI)</b> : " + mTelephonyMgr.getSubscriberId() + "<br/>";
		sInfo += " - <b>Gsm Cell ID</b> : " + cellLocation.getCid() + "<br/>";
		sInfo += " - <b>Gsm Cell Lac</b> : " + cellLocation.getLac() + "<br/>";
		
		if (networkInfo == null) {
			
			sInfo += "<font color='#FF5500'><b>Network Info</b></font><br/>";
			sInfo += " - <b>Network Information is null.</b><br/>";
			
		} else {
			
			sInfo += "<font color='#FF5500'><b>Network Info</b></font><br/>";
			sInfo += " - <b>Roaming</b> : " + networkInfo.isRoaming() + "<br/>";
			sInfo += " - <b>Available</b> : " + networkInfo.isAvailable() + "<br/>";
			sInfo += " - <b>Connected</b> : " + networkInfo.isConnected() + "<br/>";
			sInfo += " - <b>ExtraInfo</b> : " + networkInfo.getExtraInfo() + "<br/>";
			
		}
		
		if (location == null) {
			
			sInfo += "<font color='#FF5500'><b>Location</b></font><br/>";
			sInfo += " - <b>Location Information is null.</b><br/>";
			
		} else {
		
			sInfo += "<font color='#FF5500'><b>Location</b></font><br/>";
			sInfo += " - <b>Provider</b> : " + location.getProvider() + "<br/>";
			sInfo += " - <b>Accuracy</b> : ±" + location.getAccuracy() + " m<br/>";
			sInfo += " - <b>Latitude</b> : " + Location.convert(location.getLatitude(), Location.FORMAT_SECONDS) + "<br/>";
			sInfo += " - <b>Longitude</b> : " + Location.convert(location.getLongitude(), Location.FORMAT_SECONDS) + "<br/>";
			sInfo += " - <b>Altitude</b> : " + location.getAltitude() + "<br/>";
			sInfo += " - <b>Speed</b> : " + location.getSpeed() + " m/s<br/>";
			sInfo += " - <b>Bearing</b> : " + location.getBearing() + "<br/>";
			sInfo += " - <b>TimeStamp</b> : " + location.getTime() + "<br/>";
			
		}

		mParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

		mTvInfo = new TextView(mContext);
		mTvInfo.setLayoutParams(mParams);
		mTvInfo.setTypeface(BaseData.FONT_TYPE_TAHOMA);
		mTvInfo.setTextSize(15.0f);
		mTvInfo.setSingleLine(false);
		mTvInfo.setTextColor(Color.BLACK);
		mTvInfo.setText(Html.fromHtml(sInfo));
		
		mParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		mParams.setMargins(10, SIGNAL_STRENGTH_HEIGHT, 10, 10);  // L,T,R,B
		
		ScrollView svInfo = new ScrollView(mContext);
		svInfo.setLayoutParams(mParams);
		svInfo.addView(mTvInfo);
		
		mainLayout.addView(svInfo);
		
	}
	
	private class ZanyPhoneStateListener extends PhoneStateListener {
		
		@Override
		public void onSignalStrengthsChanged(SignalStrength signalStrength) {

			mTvSignal.setText(Html.fromHtml("<b>Gsm Signal Strength : " + String.valueOf(signalStrength.getGsmSignalStrength()) + "</b>"));
			
			super.onSignalStrengthsChanged(signalStrength);
			
		}
		
	}
	
}