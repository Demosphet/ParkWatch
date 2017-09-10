package com.sachith.parkwatch;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class ReportVehicle extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 10;
    private Button gpsPositionButton;
    private TextView gpsCoordinates;
    private LocationManager locationManager;
    private LocationListener locationListener;

    Double longitude;
    Double latitude;
    private ImageView capturedImage;

    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_vehicle);

        gpsPositionButton = (Button) findViewById(R.id.gpsPositionButton);
        gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        configureButton();

        capturedImage = (ImageView) findViewById(R.id.capturedImage);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        myDb = new DatabaseHelper(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.report_history:
                        Toast.makeText(ReportVehicle.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(ReportVehicle.this, ReportHistory.class);
                        startActivity(intent1);
                        break;

                    case R.id.carSpaces:
                        Toast.makeText(ReportVehicle.this, "Car Spaces", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(ReportVehicle.this, CapSpaces.class);
                        startActivity(intent2);
                        break;

                    case R.id.gateOverview:
                        Toast.makeText(ReportVehicle.this, "Gate Overview", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(ReportVehicle.this, Map.class);
                        startActivity(intent3);
                        break;

                    case R.id.report_vehicle:
                        Toast.makeText(ReportVehicle.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(ReportVehicle.this, ReportVehicle.class);
                        startActivity(intent4);
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configureButton();
                }
        }
    }

    private void configureButton() {
        gpsPositionButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  createLocationRequest();
              }
          }
        );
    }

    protected void createLocationRequest () {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        FusedLocationProviderClient mFusedLocationProviderClient;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        String locationMsg = "Latitude: " + latitude + "\n " + "Longitude: " + longitude;
                        gpsCoordinates.setText(locationMsg);
                    }
                }
            });
        }
    }

    public void buttonTakePhotoClicked(View v){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                capturedImage.setImageBitmap(cameraImage);
            }
        }
    }
}
