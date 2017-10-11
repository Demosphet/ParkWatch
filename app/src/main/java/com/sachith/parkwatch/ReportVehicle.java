package com.sachith.parkwatch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReportVehicle extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 10;
    private static final int CAMERA_REQUEST_CODE = 1313;
    private static final int REQUEST_CAMERA_PERMISSION = 6363;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 3939;

    private Button gpsPositionButton;
    private TextView gpsCoordinates;
    private LocationManager locationManager;
    private LocationListener locationListener;

    EditText registrationNumberEditText;
    EditText vehicleModelEditText;
    EditText vehicleColourEditText;
    Spinner carSpacesSpinner;
    Spinner carMakeSpinner;
    Spinner carTypeSpinner;
    Button addDataButton;

    Double longitude;
    Double latitude;
    public Uri photoURI;

    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_vehicle);

        registrationNumberEditText = (EditText) findViewById(R.id.registrationNumberEditText);
        vehicleModelEditText = (EditText) findViewById(R.id.vehicleModelEditText);
        vehicleColourEditText = (EditText) findViewById(R.id.vehicleColourEditText);

        carSpacesSpinner = (Spinner) findViewById(R.id.carSpacesSpinner);
        carMakeSpinner = (Spinner) findViewById(R.id.carMakeSpinner);
        carTypeSpinner = (Spinner) findViewById(R.id.carTypeSpinner);

        addDataButton = (Button) findViewById(R.id.addDataButton);

        gpsPositionButton = (Button) findViewById(R.id.gpsPositionButton);
        gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        //Initialising the Spinner for Car Spaces
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ReportVehicle.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.carSpacesList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSpacesSpinner.setAdapter(myAdapter);

        //Initialising the Spinner for Car Makes
        ArrayAdapter<String> myAdapterMake = new ArrayAdapter<String>(ReportVehicle.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.carMake));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMakeSpinner.setAdapter(myAdapterMake);

        //Initialising the Spinner for Car Makes
        ArrayAdapter<String> myAdapterType = new ArrayAdapter<String>(ReportVehicle.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.carType));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carTypeSpinner.setAdapter(myAdapterType);

        configureButton();
        addDataFunction();


        //Declaring the bottom navigation bar elements
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //Bottom Navigation bar declarations
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        //Created an instance of a database
        myDb = new DatabaseHelper(this);

        //Creating a switch statement to determine where to point the user when interacting with
        // the bottom navigation bar
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

    //Checking the Request Permission of the "configureButton" function
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configureButton();
                }
            case CAMERA_REQUEST_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    invokeCamera();
                }
        }
    }

    //Initialising the GPS button press
    private void configureButton() {
        gpsPositionButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  createLocationRequest();
              }
          }
        );
    }

    //Setting up the location services to get the GPS coordinates from the inbuilt GPS module
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

    //Creating the intent to take the user to the Camera Intent
    public void buttonTakePhotoClicked(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //Calls the camera
                invokeCamera();
            } else {
                String[] permissionRequested = {Manifest.permission.CAMERA};
                requestPermissions(permissionRequested, CAMERA_REQUEST_CODE);
            }
        }
    }


    //Invoke the Camera function
    private void invokeCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureName = getPictureName();
        File imageFile = new File(pictureDirectory, pictureName);
        photoURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".my.package.name.provider", imageFile);
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    //Picture name function
    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = sdf.format(new Date());
        return "ParkWatchImage" + timeStamp + ".jpeg";
    }


    //Capturing an image and showing a preview within the application
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
//                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
//                capturedImage.setImageBitmap(cameraImage);
            }
        }
    }

    //Add data to the database function
    public void addDataFunction(){
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item;
                if(registrationNumberEditText.getText().toString().equals("")){
                    Toast.makeText(ReportVehicle.this, "Registration Field is Empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (vehicleModelEditText.getText().toString().equals("")){
                    Toast.makeText(ReportVehicle.this, "Model Field is Empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (vehicleColourEditText.getText().toString().equals("")){
                    Toast.makeText(ReportVehicle.this, "Colour Field is Empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (longitude==null){
                    Toast.makeText(ReportVehicle.this, "GPS is not capture", Toast.LENGTH_LONG).show();
                    return;
                } else if (latitude==null){
                    Toast.makeText(ReportVehicle.this, "GPS is not capture", Toast.LENGTH_LONG).show();
                    return;
                } else if (photoURI==null){
                    Toast.makeText(ReportVehicle.this, "An Image has not been captured", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    boolean isInserted = myDb.insertData(registrationNumberEditText.getText().toString(),
                            carMakeSpinner.getSelectedItem().toString(),
                            vehicleModelEditText.getText().toString(),
                            vehicleColourEditText.getText().toString(),
                            carTypeSpinner.getSelectedItem().toString(),
                            longitude.toString(),
                            latitude.toString(),
                            carSpacesSpinner.getSelectedItem().toString(),
                            photoURI.toString());

                    item = carSpacesSpinner.getSelectedItem().toString();

                    Toast.makeText(ReportVehicle.this,item, Toast.LENGTH_LONG).show();

                    if (item.equals("A")) {
                        myDb.updateSpacesA();
                        Log.d("db-debug","Item A");
                    } else if (item.equals("B")) {
                        myDb.updateSpacesB();
                        Log.d("db-debug","Item B");
                    } else if (item.equals("C")) {
                        myDb.updateSpacesC();
                        Log.d("db-debug","Item C");
                    }

                    if(isInserted == true){
                        Toast.makeText(ReportVehicle.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        registrationNumberEditText.setText("");
                        vehicleModelEditText.setText("");
                        vehicleColourEditText.setText("");
                        gpsCoordinates.setText("");
                    } else {
                        Toast.makeText(ReportVehicle.this, "Data Failed to Insert", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
