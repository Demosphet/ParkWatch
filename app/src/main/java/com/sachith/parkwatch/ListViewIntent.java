package com.sachith.parkwatch;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ListViewIntent extends AppCompatActivity implements OnMapReadyCallback {

    TextView registrationValueTextView;
    TextView makeValueTextView;
    TextView modelValueTextView;
    TextView colourValueTextView;
    TextView typeValueTextView;
    TextView carSpaceValueTextView;
    TextView timeStampValueTextView;
    Button buttonDelete;
    ImageView imageView;
    DatabaseHelper myDb;
    GoogleMap mGoogleMap;
    String idGlobal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_intent);


        registrationValueTextView = (TextView) findViewById(R.id.registrationValueTextView);
        makeValueTextView = (TextView) findViewById(R.id.makeValueTextView);
        modelValueTextView = (TextView) findViewById(R.id.modelValueTextView);
        colourValueTextView = (TextView) findViewById(R.id.colourValueTextView);
        typeValueTextView = (TextView) findViewById(R.id.typeValueTextView);
        carSpaceValueTextView = (TextView) findViewById(R.id.carSpaceValueTextView);
        timeStampValueTextView = (TextView) findViewById(R.id.timeStampValueTextView);
        imageView = (ImageView) findViewById(R.id.imageView);
        myDb = new DatabaseHelper(this);

        if(googleServicesAvailable()){
            Toast.makeText(this, "Connection with Play services established", Toast.LENGTH_LONG).show();
//            setContentView(R.layout.activity_list_view_intent);
            initMap();
        } else {
            //No Google Maps Layout
        }

        if(getIntent().hasExtra("com.sachith.parkwatch.ID")){
            String id = getIntent().getExtras().getString("com.sachith.parkwatch.ID");
            idGlobal = id;
            Vehicle viewVehicle = myDb.getAllData_ListView(id);

            Uri imageUrl = Uri.parse(viewVehicle.getImageUrl());
//            textView.setText(imageUrl.toString());
//            textView.append("\n" + viewVehicle.getId());

            registrationValueTextView.setText(viewVehicle.getRegistration());
            makeValueTextView.setText(viewVehicle.getMake());
            modelValueTextView.setText(viewVehicle.getModel());
            colourValueTextView.setText(viewVehicle.getColour());
            typeValueTextView.setText(viewVehicle.getType());
            carSpaceValueTextView.setText(viewVehicle.getCarSpaces());
            timeStampValueTextView.setText(viewVehicle.getTimestamp());

            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUrl);
                imageView.setImageBitmap(imageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vehicle viewVehicle = myDb.getAllData_ListView(idGlobal);
                String carSpace = viewVehicle.getCarSpaces();
                myDb.deleteCar(idGlobal,carSpace);
                Intent backToHistory = new Intent(ListViewIntent.this, ReportHistory.class);
                startActivity(backToHistory);
                finish();
            }
        });

    }

    private void initMap(){
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);
    }

    private boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can not connect to Play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        goToLocationZoom(-37.951503, 145.251464, 17);
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        Vehicle viewVehicle = myDb.getAllData_ListView(idGlobal);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
        Log.d("dbdebug",viewVehicle.getId());
        Log.d("dbdebug",viewVehicle.getTimestamp());
        Log.d("dbdebug",viewVehicle.getRegistration());
        Log.d("dbdebug",viewVehicle.getMake());
        Log.d("dbdebug",viewVehicle.getModel());
        Log.d("dbdebug",viewVehicle.getColour());
        Log.d("dbdebug",viewVehicle.getType());
        Log.d("dbdebug",viewVehicle.getLongitude());
        Log.d("dbdebug",viewVehicle.getLatitude());
        Log.d("dbdebug",viewVehicle.getCarSpaces());
        Log.d("dbdebug",viewVehicle.getImageUrl());
        getMarkerOptions(viewVehicle.getLatitude(),viewVehicle.getLongitude(),viewVehicle.getTimestamp());
    }
    @NonNull
    private MarkerOptions getMarkerOptions(String sLat, String sLng,String title) {
        double lat = Double.parseDouble(sLat);
        double lng = Double.parseDouble(sLng);
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(lat,lng))
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mGoogleMap.addMarker(marker);
        return marker;
    }

}
