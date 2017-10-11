package com.sachith.parkwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CapSpaces extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button reportVehicleButton;
    private ImageView carSpaceImageView;
    Spinner carSpaceSpinner;
    TextView parkedVehiclesNumberTextView;
    TextView inflowValueTextView;
    TextView outflowValueTextView;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_spaces);

        carSpaceSpinner = (Spinner) findViewById(R.id.carSpaceSpinner);
        parkedVehiclesNumberTextView = (TextView) findViewById(R.id.parkedVehiclesNumberTextView);
        inflowValueTextView = (TextView) findViewById(R.id.inflowValueTextView);
        outflowValueTextView = (TextView) findViewById(R.id.outflowValueTextView);
        carSpaceImageView = (ImageView) findViewById(R.id.carSpaceImageView);

        //Declaring the "Report Vehicle" button
        reportVehicleButton = (Button) findViewById(R.id.reportVehicleButton);

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

        //Initialising Spinner
        carSpaceSpinner.setOnItemSelectedListener(this);

        //Initialising the Spinner for Car Spaces
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CapSpaces.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.carSpacesList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSpaceSpinner.setAdapter(myAdapter);


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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int resA = myDb.getAllData_carSpaceA();
        int resB = myDb.getAllData_carSpaceB();
        int resC = myDb.getAllData_carSpaceC();

        int resInA = myDb.getIn_carSpaceA();
        int resInB = myDb.getIn_carSpaceB();
        int resInC = myDb.getIn_carSpaceC();

        int resOutA = myDb.getOut_carSpaceA();
        int resOutB = myDb.getOut_carSpaceB();
        int resOutC = myDb.getOut_carSpaceC();

        String item = adapterView.getItemAtPosition(i).toString();

        int position = carSpaceSpinner.getSelectedItemPosition();


        switch(position){
            case 0:
                parkedVehiclesNumberTextView.setText(String.valueOf(resA));
                inflowValueTextView.setText(String.valueOf(resInA));
                outflowValueTextView.setText(String.valueOf(resOutA));
                carSpaceImageView.setImageResource(R.drawable.ca1);
                break;
            case 1:
                parkedVehiclesNumberTextView.setText(String.valueOf(resB));
                inflowValueTextView.setText(String.valueOf(resInB));
                outflowValueTextView.setText(String.valueOf(resOutB));
                carSpaceImageView.setImageResource(R.drawable.cb1);
                break;
            case 2:
                parkedVehiclesNumberTextView.setText(String.valueOf(resC));
                inflowValueTextView.setText(String.valueOf(resInC));
                outflowValueTextView.setText(String.valueOf(resOutC));
                carSpaceImageView.setImageResource(R.drawable.cc1);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
