package com.shop.ningbaoqi.gps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class MyGPS extends Activity {
    protected static final String TAG = null;
    private Location location;
    private LocationManager locationManager;
    private String provider;
    private GpsStatus gpsStatus;
    Iterable<GpsSatellite> allSatellites;
    float satellitedegree[][] = new float[24][3];
    float alimuth[] = new float[24];
    float elevation[] = new float[24];
    float snr[] = new float[24];
    private boolean status = false;
    protected Iterator<GpsSatellite> iterableStae;
    private float bear;
    private DisplayMetrics displayMetrics;
    panintview layout;
    Button openButton;
    Button closeButton;
    TextView latitudeView;
    TextView longitudeview;
    TextView altitudeview;
    TextView speedView;
    TextView timeView;
    TextView errorView;
    TextView bearingview;
    TextView stacountview;
    private int height;
    private int width;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout);
        findView();
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status) {
                    openGPSSettings();
                    getLocation();
                    status = true;
                }
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeGPS();
            }
        });
    }

    private void openGPSSettings() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS正常模块", Toast.LENGTH_LONG).show();
            return;
        }
        status = false;
        Toast.makeText(this, "请开启GPS", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        startActivityForResult(intent, 0);
    }

    private void closeGPS() {
        if (status == true) {
            locationManager.removeUpdates(locationListener);
            locationManager.removeGpsStatusListener(statusListener);
            errorView.setText("");
            latitudeView.setText("");
            longitudeview.setText("");
            speedView.setText("");
            timeView.setText("");
            altitudeview.setText("");
            bearingview.setText("");
            stacountview.setText("");
            status = false;
        }
    }

    private void findView() {
        openButton = findViewById(R.id.open);
        closeButton = findViewById(R.id.close);
        latitudeView = findViewById(R.id.latitudevalue);
        longitudeview = findViewById(R.id.longtudevalue);
        altitudeview = findViewById(R.id.altitudevalue);
        speedView = findViewById(R.id.speedView);
        timeView = findViewById(R.id.timevalue);
        errorView = findViewById(R.id.error);
        bearingview = findViewById(R.id.bearvalue);
        layout = findViewById(R.id.iddraw);
        stacountview = findViewById(R.id.satellitevalue);
    }

    private final LocationListener locationListener = new LocationListener() {
        @SuppressLint("MissingPermission")
        @Override
        public void onLocationChanged(Location location) {
            location = locationManager.getLastKnownLocation(provider);
            updateToNewLocation(location);
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
     * 添加监听卫星
     */
    private final GpsStatus.Listener statusListener = new GpsStatus.Listener() {

        @SuppressLint("MissingPermission")
        @Override
        public void onGpsStatusChanged(int event) {
            gpsStatus = locationManager.getGpsStatus(null);
            switch (event) {
                case GpsStatus.GPS_EVENT_STARTED:
                    break;
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    break;
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    drawMap();
                    break;
                case GpsStatus.GPS_EVENT_STOPPED:
                    break;
            }
        }
    };

    private void drawMap() {
        int i = 0;
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        allSatellites = gpsStatus.getSatellites();
        iterableStae = allSatellites.iterator();
        while (iterableStae.hasNext()) {
            GpsSatellite satellite = iterableStae.next();
            alimuth[i] = satellite.getAzimuth();
            elevation[i] = satellite.getElevation();
            snr[i] = satellite.getSnr();
            i++;
        }
        stacountview.setText("" + i);
        layout.redraw(bear, alimuth, elevation, snr, width, height, i);
        layout.invalidate();
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setSpeedRequired(true);
        provider = locationManager.getBestProvider(criteria, true);
        location = locationManager.getLastKnownLocation(provider);
        updateToNewLocation(location);
        locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);
        locationManager.addGpsStatusListener(statusListener);
    }

    private void updateToNewLocation(Location location) {
        if (location != null) {
            bear = location.getBearing();
            double latitude = location.getLatitude();
            double longtitude = location.getLongitude();
            float GpsSpeed = location.getSpeed();
            long gpsTime = location.getTime();
            Date date = new Date(gpsTime);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            double gpsAil = location.getAltitude();
            latitudeView.setText(" " + latitude);
            longitudeview.setText(" " + longtitude);
            speedView.setText("" + GpsSpeed);
            timeView.setText("" + format.format(date));
            altitudeview.setText("" + gpsAil);
            bearingview.setText("" + bear);
        } else {
            errorView.setText("无法获取地理信息");
        }
    }
}
