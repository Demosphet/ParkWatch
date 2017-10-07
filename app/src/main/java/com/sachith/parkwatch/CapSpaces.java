package com.sachith.parkwatch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CapSpaces extends AppCompatActivity {

    private Button reportVehicleButton;
    Button viewDatabaseButton;
    Spinner carSpaceSpinner;
    TextView parkedVehiclesNumberTextView;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_spaces);

        carSpaceSpinner = (Spinner) findViewById(R.id.carSpaceSpinner);
        parkedVehiclesNumberTextView = (TextView) findViewById(R.id.parkedVehiclesNumberTextView);

        //Declaring the "Report Vehicle" button
        reportVehicleButton = (Button) findViewById(R.id.reportVehicleButton);
        viewDatabaseButton = (Button) findViewById(R.id.viewDatabaseButton);

        //Created an instance of a database
        myDb = new DatabaseHelper(this);

        //Listening to when the "Report Vehicle" button is pressed
        reportVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ReportVehicle.class);
                startActivity(startIntent);
            }
        });

        //Initialising the Spinner for Car Spaces
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CapSpaces.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.carSpacesList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSpaceSpinner.setAdapter(myAdapter);

//        carSpaceSpinnerFunction();
        viewDataTable2();

        //Declaring the bottom navigation bar elements
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //Bottom Navigation bar declarations
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        //Creating a switch statement to determine where to point the user when interacting with
        // the bottom navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.carSpaces:
                        Toast.makeText(CapSpaces.this, "Car Spaces", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.gateOverview:
                        Toast.makeText(CapSpaces.this, "Gate Overview", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(CapSpaces.this, Map.class);
                        startActivity(intent1);
                        break;

                    case R.id.report_history:
                        Toast.makeText(CapSpaces.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(CapSpaces.this, ReportHistory.class);
                        startActivity(intent2);
                        break;

                    case R.id.report_vehicle:
                        Toast.makeText(CapSpaces.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(CapSpaces.this, ReportVehicle.class);
                        startActivity(intent3);
                        break;
                }

                return true;
            }
        });
    }

    public void viewDataTable2() {
        viewDatabaseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData_carSpace();
                        if(res.getCount() == 0) {
                            showMessageFunction("Error", "No data found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID :             " + res.getString(0) + "\n");
                            buffer.append("Car Space :      " + res.getString(1) + "\n");
                            buffer.append("Number of Cars : " + res.getString(2) + "\n");
                            buffer.append("Image URL :      " + res.getString(3) + "\n\n");
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

    public void carSpaceSpinnerFunction() {
        //Initialising the Spinner for Car Spaces
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CapSpaces.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.carSpacesList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSpaceSpinner.setAdapter(myAdapter);

        carSpaceSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                carSpacesCheck();
            }
        });
    }

    public void carSpacesCheck(){
        Cursor res = myDb.getAllData_carSpace();
        StringBuffer bufferIntNoOfCarsA = new StringBuffer();
        StringBuffer bufferIntNoOfCarsB = new StringBuffer();
        StringBuffer bufferIntNoOfCarsC = new StringBuffer();
//        StringBuffer bufferID = new StringBuffer();
//        StringBuffer bufferCarSpace = new StringBuffer();
//        StringBuffer bufferNoOfCars = new StringBuffer();
//        StringBuffer bufferImageURL = new StringBuffer();


        double ID = 1;
        while (res.moveToNext()) {
            if (res.getString(2) == "A") {
                if (res.getDouble(0) >= ID) {
                    bufferIntNoOfCarsA = null;
                    bufferIntNoOfCarsA.append(res.getString(2));
                    parkedVehiclesNumberTextView.setText(bufferIntNoOfCarsA);
                }
            } else if (res.getString(2) == "B") {
                if (res.getDouble(0) >= ID) {
                    bufferIntNoOfCarsB = null;
                    bufferIntNoOfCarsB.append(res.getString(2));
                    parkedVehiclesNumberTextView.setText(bufferIntNoOfCarsB);
                }
            } else if (res.getString(2) == "A") {
                if (res.getDouble(0) >= ID) {
                    bufferIntNoOfCarsA = null;
                    bufferIntNoOfCarsA.append(res.getString(2));
                    parkedVehiclesNumberTextView.setText(bufferIntNoOfCarsC);
                }
            }
//            bufferID.append(res.getString(0) + "\n");
//            bufferCarSpace.append(res.getString(1) + "\n");
//            bufferNoOfCars.append(res.getString(2) + "\n");
//            bufferImageURL.append(res.getString(3) + "\n");
            ID++;
        }
//        parkedVehiclesNumberTextView.setText(bufferIntNoOfCarsA);
    }
}
