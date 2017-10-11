package com.sachith.parkwatch;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//import android.location.LocationListener;

public class Map extends AppCompatActivity implements OnMapReadyCallback {

    DatabaseHelper myDb;

    GoogleMap mGoogleMap;
//    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDb = new DatabaseHelper(this);

        if (googleServicesAvailable()) {
            Toast.makeText(this, "Connection with Play services established", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_map);
            initMap();
        } else {
            //No Google Maps Layout
        }

        //Declaring the bottom navigation bar elements
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //Bottom Navigation bar declarations
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        //Creating a switch statement to determine where to point the user when interacting with
        // the bottom navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.gateOverview:
                        Toast.makeText(Map.this, "Gate Overview", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.carSpaces:
                        Toast.makeText(Map.this, "Car Spaces", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(Map.this, CapSpaces.class);
                        startActivity(intent1);
                        break;

                    case R.id.report_history:
                        Toast.makeText(Map.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(Map.this, ReportHistory.class);
                        startActivity(intent2);
                        break;

                    case R.id.report_vehicle:
                        Toast.makeText(Map.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(Map.this, ReportVehicle.class);
                        startActivity(intent3);
                        break;
                }

                return true;
            }
        });

    }

    //Creating and instance of a Map View fragment
    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    //Interfacing in Google Map API
    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can not connect to Play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //Interfacing in Google Map API
    public void onMapReady(GoogleMap googleMap) {
        final int resA = myDb.getAllData_carSpaceA();
        final int resB = myDb.getAllData_carSpaceB();
        final int resC = myDb.getAllData_carSpaceC();

        mGoogleMap = googleMap;
        goToLocationZoom(-37.951503, 145.251464, 17);


        if(mGoogleMap != null) {
            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.carspace_infowindow,null);

                    TextView parkValueLayoutTextView = (TextView) v.findViewById(R.id.parkValueLayoutTextView);
                    TextView CarsLayoutValueTextView = (TextView) v.findViewById(R.id.CarsLayoutValueTextView);
                    ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

                    LatLng ll = marker.getPosition();
                    parkValueLayoutTextView.setText(marker.getTitle());

                    Log.d("db-debug","For Loop");
                    for (int i=1; i<4; i=i+1) {
                        if(marker.getTitle().toString().equals("A")){
                            Log.d("db-debug",String.valueOf(resA));
                            Log.d("db-debug","A");
                            imageView.setImageResource(R.drawable.ca1);
                            CarsLayoutValueTextView.setText(String.valueOf(resA));
                        } else if (marker.getTitle().toString().equals("B")) {
                            Log.d("db-debug",String.valueOf(resB));
                            Log.d("db-debug","B");
                            imageView.setImageResource(R.drawable.cb1);
                            CarsLayoutValueTextView.setText(String.valueOf(resB));
                        } else if (marker.getTitle().toString().equals("C")) {
                            Log.d("db-debug",String.valueOf(resC));
                            Log.d("db-debug","C");
                            imageView.setImageResource(R.drawable.cc1);
                            CarsLayoutValueTextView.setText(String.valueOf(resC));
                        }
                    }
                    return v;
                }
            });
        }
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        int resA = myDb.getAllData_carSpaceA();
        int resB = myDb.getAllData_carSpaceB();
        int resC = myDb.getAllData_carSpaceC();

        LatLng ll = new LatLng(lat, lng);
        String titleA = "A";
        String titleB = "B";
        String titleC = "C";
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);

        if (resA > 0){
            getMarkerOptionsR(titleA,-37.951725,145.252004);

        } if (resA <= 0){
            getMarkerOptionsG(titleA,-37.951725,145.252004);

        } if (resB > 0){
            getMarkerOptionsR(titleB,-37.952086,145.251044);

        } if (resB <= 0) {
            getMarkerOptionsG(titleB,-37.952086,145.251044);

        } if (resC > 0){
            getMarkerOptionsR(titleC,-37.951133,145.250077);

        } if (resC <= 0) {
            getMarkerOptionsG(titleC,-37.951133,145.250077);

        }


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    @NonNull
    private MarkerOptions getMarkerOptionsR(String titleA, double lat, double lng) {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(lat,lng))
                .title(titleA)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mGoogleMap.addMarker(marker);
        return marker;
    }

    @NonNull
    private MarkerOptions getMarkerOptionsG(String titleA, double lat, double lng) {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(lat,lng))
                .title(titleA)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mGoogleMap.addMarker(marker);
        return marker;
    }
}
