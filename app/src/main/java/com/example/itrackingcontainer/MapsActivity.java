package com.example.itrackingcontainer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private static final String TAG = "MainActivity";
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 20000; /* 20 sec */

    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;
    //-----------------doi tuong-------------------
    ListView lvContainer;
    List<Container> containerList;
    AdapterContainer adapterContainer;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        lvContainer=findViewById(R.id.lvResultSearchCont);
        containerList= new ArrayList<>();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor=sharedPreferences.edit();
        Intent intent=getIntent();
        Container container=(Container) intent.getSerializableExtra("objCont");
        containerList.add(container);
        adapterContainer= new AdapterContainer(containerList);
        lvContainer.setAdapter(adapterContainer);
        // mac dinh cua he thong
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        //--------------------------------------------------------
        if (requestSinglePermission()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            //it was pre written
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            mLatitudeTextView = (TextView) findViewById((R.id.latitude_textview));
            mLongitudeTextView = (TextView) findViewById((R.id.longitude_textview));

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            checkLocation(); //check whether location service is enable or not in your  phone
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //----------------------------------------------------------------
        if (latLng != null) {
            Intent intent=getIntent();
            Container container=(Container) intent.getSerializableExtra("objCont");
            mMap.addMarker(new MarkerOptions().position(latLng).title("Chỉ đường: " + container.getCntrNo()+"/"+container.getLocalSZPT()+"/"+container.getOprID()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //them thong tin zoom den vị tri chinh xac
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
            mMap.animateCamera(cameraUpdate);

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, R.string.str_vitrikhongxacdinh, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, R.string.str_tamdung+"");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, R.string.str_ketnoithatbai+"" + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        Double lat=20.848867,lon=106.751770;
        String cBlock;
        Intent intent=getIntent();
        Container containerObj=(Container) intent.getSerializableExtra("objCont");
        cBlock=containerObj.getcBlock();
        //xac dinh vi tri tren Bai theo kinh do, vi do
        switch (cBlock)
        {
            case "A0":
            {
                lat=20.846557;
                lon= 106.747047;
                break;
            }
            case "A1":
            {
                lat= 20.846381;
                lon= 106.747334;
                break;
            }
            case "A2":
            {

                lat=20.846273;
                lon= 106.747597;
                break;
            }
            case "A3":
            {

                lat=20.846141;
                lon=106.747745;
                break;
            }
            case "A4":
            {

                lat=20.845893;
                lon= 106.747822;
                break;
            }
            case "A5":
            {

                lat=20.845724;
                lon= 106.747953;
                break;
            }
            case "A6":
            {

                lat=20.845564;
                lon= 106.748115;
                break;
            }
            case "A7":
            {

                lat=20.845332;
                lon=106.747966;
                break;
            }
            case "B0":
            {

                lat=20.847730;
                lon= 106.748378;
                break;
            }
            case "B1":
            {

                lat=20.847532;
                lon=106.748521;
                break;
            }
            case "B2":
            {
                lat=20.847307;
                lon= 106.748656;
                break;
            }
            case "B3":
            {
                lat=20.847113;
                lon= 106.748796;
                break;
            }
            case "B4":
            {
                lat=20.847035;
                lon= 106.749075;
                break;
            }
            case "B5":
            {
                lat=20.846721;
                lon= 106.748992;
                break;
            }
            case "B6":
            {
                lat=20.846384;
                lon= 106.748893;
                break;
            }
            case "B7":
            {
                lat=20.846197;
                lon= 106.749111;
                break;
            }
            //--------------------Khu C--------------------------
            case "C0":
            {

                lat=20.848378;
                lon= 106.749281;
                break;
            }
            case "C1":
            {

                lat=20.848069;
                lon=106.749236;
                break;
            }
            case "C2":
            {
                lat=20.847832;
                lon=106.749420;
                break;
            }
            case "C3":
            {
                lat=20.847772;
                lon= 106.749765;
                break;
            }
            case "C4":
            {
                lat=20.847475;
                lon= 106.749676;
                break;
            }
            case "C5":
            {
                lat=20.847474;
                lon=106.750079;
                break;
            }
            case "C6":
            {
                lat=20.847183;
                lon= 106.750007;
                break;
            }
            case "C7":
            {
                lat=20.846940;
                lon= 106.750122;
                break;
            }
            //-------------Xác dinh toa do khu D-------------------------------------
            case "D0":
            {

                lat=20.848909;
                lon= 106.750220;
                break;
            }
            case "D1":
            {

                lat=20.848713;
                lon= 106.750175;
                break;
            }
            case "D2":
            {
                lat=20.848689;
                lon=106.750559;
                break;
            }
            case "D3":
            {
                lat=20.848480;
                lon= 106.750674;
                break;
            }
            case "D4":
            {
                lat=20.848242;
                lon= 106.750770;
                break;
            }
            case "D5":
            {
                lat=20.848039;
                lon=106.750917;
                break;
            }
            case "D6":
            {
                lat=20.847877;
                lon= 106.751077;
                break;
            }
            case "D7":
            {
                lat=20.847662;
                lon= 106.751211;
                break;
            }
                //------------------Xac dinh toa do khu E--------------------------------------------------
            case "E3":
            {
                lat=20.849341;
                lon= 106.751727;
                break;
            }
            case "E4":
            {
                lat=20.849128;
                lon= 106.752070;
                break;
            }
            case "E5":
            {
                lat=20.848790;
                lon= 106.751984;
                break;
            }
            case "E6":
            {
                lat=20.848673;
                lon= 106.752176;
                break;
            }
            case "E7":
            {
                lat=20.848691;
                lon= 106.752660;
                break;
            }

            default:
            {
                lat=20.846346;
                lon= 106.749299;
                break;
            }
        }



        String msg = "Vị trí hiện tại của bạn: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
        mLongitudeTextView.setText(String.valueOf(location.getLongitude()));
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        //20.848867, 106.751770
        //latLng = new LatLng(location.getLatitude(), location.getLongitude());
        latLng = new LatLng(lat, lon);
        //them thong tin-------------------------------------------------------------------
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        mMap.animateCamera(cameraUpdate);
        mMap.setMapType(mMap.MAP_TYPE_SATELLITE);
        //them thong tin
        //locationManager.removeUpdates(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //it was pre written
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private boolean requestSinglePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        //Single Permission is granted
                        Toast.makeText(MapsActivity.this, R.string.str_singlepermission, Toast.LENGTH_SHORT).show();
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            isPermission = false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        return isPermission;

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.str_kichhoat)
                .setMessage(R.string.str_batvitri)
                .setPositiveButton(R.string.str_setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }
}
