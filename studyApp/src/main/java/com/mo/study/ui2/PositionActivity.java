package com.mo.study.ui2;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.study.R;

import java.util.List;

/**
 * # 通过TelephonyManager 获取lac:mcc:mnc:cell-id
 * # MCC，Mobile Country Code，移动国家代码（中国的为460）；
 * # MNC，Mobile Network Code，移动网络号码（中国移动为0，中国联通为1，中国电信为2）；
 * # LAC，Location Area Code，位置区域码；
 * # CID，Cell Identity，基站编号；
 * # BSSS，Base station signal strength，基站信号强度。
 */
public class PositionActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "PositionActivity";

    private LocationManager mLocationManager;
    private TelephonyManager mTelephonyManager;
    private WifiManager mWifiManager;

    private ProgressBar progressBar;

    private TextView tvGpsInfo;
    private TextView tvCellInfo;
    private TextView tvWifiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        findViewById(R.id.btn_gps_pos).setOnClickListener(this);
        findViewById(R.id.btn_cell_info).setOnClickListener(this);
        findViewById(R.id.btn_wifi_info).setOnClickListener(this);
        findViewById(R.id.btn_gps_last).setOnClickListener(this);
        findViewById(R.id.btn_net_pos).setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progress_gps);

        tvGpsInfo = (TextView) findViewById(R.id.tv_gps_info);
        tvCellInfo = (TextView) findViewById(R.id.tv_gps_cell);
        tvWifiInfo = (TextView) findViewById(R.id.tv_wifi_info);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationManager.removeUpdates(locationGpsListener);
        mLocationManager.removeUpdates(mNetLocationListener);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_gps_pos:
                requestGpsPosition();
                break;
            case R.id.btn_cell_info:
                getCellTrackInfo();
                break;
            case R.id.btn_wifi_info:
                getWiFiPosition();
                break;
            case R.id.btn_gps_last:
                getLastPosition();
                break;
            case R.id.btn_net_pos:
                requestNetPosition();
                break;
        }
    }


    private void requestNetPosition(){
        //网络定位是否可用
        if (!mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(this, "请开启定位...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            return;
        }

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 0f, mNetLocationListener);

    }

    private LocationListener mNetLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "mNetLocationListener ----> onLocationChanged");

            //只定位一次
//            if (location != null){
//                mLocationManager.removeUpdates(mNetLocationListener);
//            }
            tvGpsInfo.setText("定位成功");

            StringBuilder sb = new StringBuilder();
            sb.append("latitude: ").append(location.getLatitude()).append("\n")
                    .append("longitude: ").append(location.getLongitude()).append("\n")
                    .append("方向: ").append(location.getBearing()).append("\n")
                    .append("高度: ").append(location.getAltitude()).append("\n")
                    .append("速度: ").append(location.getSpeed()).append("\n")
                    .append("精度: ").append(location.getAccuracy());
            Log.d(TAG, sb.toString());
            tvGpsInfo.setText(tvGpsInfo.getText().toString() + "\n" + sb.toString());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    /**
     * 获取GPS定位
     */
    private void requestGpsPosition() {
        // 判断GPS是否启动
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            return;
        }

        Toast.makeText(this, "GPS定位", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        // 10秒一次，N米距离
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000L, 1f, locationGpsListener);
//        mManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationGpsListener, null);
        //监听GPS状态，可以获取卫星信息,包括 卫星的高度角、方位角、信噪比、和伪随机号（及卫星编号）
//        mLocationManager.addGpsStatusListener(gpsStatusListener);
    }


    private LocationListener locationGpsListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Log.d(TAG, "onLocationChanged");
            //只定位一次
//            if (location != null){
//                mLocationManager.removeUpdates(locationGpsListener);
//            }
            tvGpsInfo.setText("定位成功");
            progressBar.setVisibility(View.GONE);

            StringBuilder sb = new StringBuilder();
            sb.append("latitude: ").append(location.getLatitude()).append("\n")
                    .append("longitude: ").append(location.getLongitude()).append("\n")
                    .append("方向: ").append(location.getBearing()).append("\n")
                    .append("高度: ").append(location.getAltitude()).append("\n")
                    .append("速度: ").append(location.getSpeed()).append("\n")
                    .append("精度: ").append(location.getAccuracy());
//            Log.d(TAG, sb.toString());
            tvGpsInfo.setText(tvGpsInfo.getText().toString() + "\n" + sb.toString());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled  " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled  " + provider);
        }
    };


    /**
     * 获取上次位置信息
     */
    private void getLastPosition(){
        // 判断GPS是否启动
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            return;
        }

        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("latitude: ").append(location.getLatitude()).append("\n")
                    .append("longitude: ").append(location.getLongitude()).append("\n")
                    .append("方向: ").append(location.getBearing()).append("\n")
                    .append("高度: ").append(location.getAltitude()).append("\n")
                    .append("速度: ").append(location.getSpeed()).append("\n")
                    .append("精度: ").append(location.getAccuracy());
            Log.d("debug", sb.toString());
            tvGpsInfo.setText(sb.toString());
            Intent data = new Intent();
            data.putExtra("location", location);
            setResult(RESULT_OK, data);
        }
    }

    /**
     * 基站信息
     */
    private void getCellTrackInfo() {
        // 返回值MCC + MNC
        String operator = mTelephonyManager.getNetworkOperator();
        int mcc = Integer.parseInt(operator.substring(0, 3));
        int mnc = Integer.parseInt(operator.substring(3));
        int lac;
        int cid;

        //电信是cdma
        if(mTelephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA){
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation)mTelephonyManager.getCellLocation();
            cid = cdmaCellLocation.getBaseStationId(); //获取cdma基站识别标号 BID
            lac = cdmaCellLocation.getNetworkId(); //获取cdma网络编号NID
            int sid = cdmaCellLocation.getSystemId(); //用谷歌API的话cdma网络的mnc要用这个getSystemId()取得→SID
            Log.d("debug", "longitude: " + cdmaCellLocation.getBaseStationLongitude() + " latitude: " + cdmaCellLocation.getBaseStationLatitude());
        }else{
            // 中国移动和中国联通获取LAC、CID的方式
            GsmCellLocation gsmCellLocation = (GsmCellLocation) mTelephonyManager.getCellLocation();
            cid = gsmCellLocation.getCid(); //获取gsm基站识别标号
            lac = gsmCellLocation.getLac(); //获取gsm网络编号
        }

        String logMsg = " MCC = " + mcc + "\n MNC = " + mnc + "\n LAC = " + lac + "\n CID = " + cid ;
        Log.d(TAG, logMsg);

        // 获取邻区基站信息
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            List<CellInfo> infoLists = mTelephonyManager.getAllCellInfo();
//            for (CellInfo info : infoLists) {
//                if (info instanceof CellInfoLte) {
//                    CellInfoLte cellInfoLte = (CellInfoLte) info;
//                    CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();
//                    CellSignalStrengthLte cellSignalStrengthLte = cellInfoLte.getCellSignalStrength();
//                    int Dbm = cellSignalStrengthLte.getDbm();
//                    int id = cellIdentityLte.getCi();
//                    // 处理 Dbm(信号强度)和id数据
//                    Log.d(TAG,"信号强度: " + Dbm + " Ci: " + id);
//                }
//
//            }
//        }else{
//            List<NeighboringCellInfo> infos = mTelephonyManager.getNeighboringCellInfo();
//            StringBuffer sb = new StringBuffer("总数 : " + infos.size() + "\n");
//            for (NeighboringCellInfo info1 : infos) { // 根据邻区总数进行循环
//                sb.append(" LAC : " + info1.getLac()); // 取出当前邻区的LAC
//                sb.append(" CID : " + info1.getCid()); // 取出当前邻区的CID
//                sb.append(" BSSS : " + (-113 + 2 * info1.getRssi()) + "\n"); // 获取邻区基站信号强度
//            }
//
//            Log.d(TAG, " 获取邻区基站信息:" + sb.toString());
//        }

        tvCellInfo.setText(logMsg);
    }


    private void getWiFiPosition(){

        WifiInfo info = mWifiManager.getConnectionInfo();
        if (info != null) {
            String bssid = info.getBSSID();
            String ssid = info.getSSID();
            int rssi = info.getRssi();
            rssi = WifiManager.calculateSignalLevel(rssi, 4);
            tvWifiInfo.setText("bssid: " + bssid + "\n  ssid: " + ssid + " rssi:" + rssi + "\n networkID: " + info.getNetworkId());
        }

        List<ScanResult> results = mWifiManager.getScanResults();
        for (ScanResult wifi : results) {
            Log.d(TAG, "BSSID: " + wifi.BSSID);
        }
    }
}
