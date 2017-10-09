package com.sachith.parkwatch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReportHistory extends AppCompatActivity {

    private TextView tableEntries;
    ArrayList<Vehicle> vehicleList;
    ListView reportHistoryListView;
    Vehicle vehicle;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_history);

        //tableEntries = (TextView) findViewById(R.id.textView24);

        //Created an instance of a database
        myDb = new DatabaseHelper(this);

        vehicleList = new ArrayList<>();
        Cursor data = myDb.getAllData();

        int numRows = data.getCount();

        if (numRows == 0){
            Toast.makeText(ReportHistory.this, "No Entries found", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()){
                vehicle = new Vehicle(data.getString(9),data.getString(1),data.getString(2),data.getString(3),data.getString(4), data.getString(5),data.getString(8));
                vehicleList.add(vehicle);
            }
            ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this, R.layout.adapter_view_layout,vehicleList);
            reportHistoryListView = (ListView) findViewById(R.id.reportHistoryListView);
            reportHistoryListView.setAdapter(adapter);

        }

        //Declaring the bottom navigation bar elements
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //Bottom Navigation bar declarations
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        //Creating a switch statement to determine where to point the user when interacting with
        // the bottom navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.report_history:
                        Toast.makeText(ReportHistory.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.carSpaces:
                        Toast.makeText(ReportHistory.this, "Car Spaces", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(ReportHistory.this, CapSpaces.class);
                        startActivity(intent1);
                        break;

                    case R.id.gateOverview:
                        Toast.makeText(ReportHistory.this, "Gate Overview", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(ReportHistory.this, Map.class);
                        startActivity(intent2);
                        break;

                    case R.id.report_vehicle:
                        Toast.makeText(ReportHistory.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(ReportHistory.this, ReportVehicle.class);
                        startActivity(intent3);
                        break;
                }

                return true;
            }
        });

        Cursor res = myDb.getAllData();
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("ID :             " + res.getString(0) + "\n");
            buffer.append("Registration :   " + res.getString(1) + "\n");
            buffer.append("Make :           " + res.getString(2) + "\n");
            buffer.append("Model :          " + res.getString(3) + "\n");
            buffer.append("Colour :         " + res.getString(4) + "\n");
            buffer.append("Type :           " + res.getString(5) + "\n");
            buffer.append("Longitude :      " + res.getString(6) + "\n");
            buffer.append("Latitude :       " + res.getString(7) + "\n");
            buffer.append("Car Space :      " + res.getString(8) + "\n");
            buffer.append("Time Stamp :     " + res.getString(9) + "\n");
            buffer.append("Image URI :      " + res.getString(10) + "\n\n");

        }
        tableEntries.setText(buffer);
    }
}
