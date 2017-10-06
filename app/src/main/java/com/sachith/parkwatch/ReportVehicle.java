package com.sachith.parkwatch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    EditText vehicleMakeEditText;
    EditText vehicleModelEditText;
    EditText vehicleColourEditText;
    EditText vehicleTypeEditText;
    Spinner carSpacesSpinner;
    Button addDataButton;
    Button viewDataButton;

    Double longitude;
    Double latitude;
    private ImageView capturedImage;

    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_vehicle);

        registrationNumberEditText = (EditText) findViewById(R.id.registrationNumberEditText);
        vehicleMakeEditText = (EditText) findViewById(R.id.vehicleMakeEditText);
        vehicleModelEditText = (EditText) findViewById(R.id.vehicleModelEditText);
        vehicleColourEditText = (EditText) findViewById(R.id.vehicleColourEditText);
        vehicleTypeEditText = (EditText) findViewById(R.id.vehicleTypeEditText);

        carSpacesSpinner = (Spinner) findViewById(R.id.carSpacesSpinner);   //Use this later on

        addDataButton = (Button) findViewById(R.id.addDataButton);
        viewDataButton = (Button) findViewById(R.id.viewDataButton);

        gpsPositionButton = (Button) findViewById(R.id.gpsPositionButton);
        gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        capturedImage = (ImageView) findViewById(R.id.capturedImage);

        configureButton();
        addDataFunction();
        viewDataFunction();


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
        Uri photoURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".my.package.name.provider", imageFile);
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
                boolean isInserted = myDb.insertData(registrationNumberEditText.getText().toString(),
                        vehicleMakeEditText.getText().toString(),
                        vehicleModelEditText.getText().toString(),
                        vehicleColourEditText.getText().toString(),
                        vehicleTypeEditText.getText().toString(),
                        longitude.toString(),
                        latitude.toString());

                if(isInserted == true){
                    Toast.makeText(ReportVehicle.this, "Data Inserted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ReportVehicle.this, "Data Failed to Insert", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Show the content of the database function
    public void viewDataFunction(){
        viewDataButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            showMessageFunction("Error", "No data found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID :" + res.getString(0) + "\n");
                            buffer.append("Registration :" + res.getString(1) + "\n");
                            buffer.append("Make :" + res.getString(2) + "\n");
                            buffer.append("Model :" + res.getString(3) + "\n");
                            buffer.append("Colour :" + res.getString(4) + "\n");
                            buffer.append("Type :" + res.getString(5) + "\n");
                            buffer.append("Longitude :" + res.getString(6) + "\n");
                            buffer.append("Latitude :" + res.getString(7) + "\n\n");

                        }

                        showMessageFunction("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessageFunction(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
