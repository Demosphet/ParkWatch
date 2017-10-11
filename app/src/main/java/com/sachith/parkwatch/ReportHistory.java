package com.sachith.parkwatch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.sachith.parkwatch.R.id.carSpaces;

public class ReportHistory extends AppCompatActivity {

    ArrayList<Vehicle> vehicleList;
    ListView reportHistoryListView;
    Vehicle vehicle;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_history);

        //Created an instance of a database
        myDb = new DatabaseHelper(this);

//        References for ListView
//        1st Tutorial Name: Adding multiple columns to your ListView (part 1/3)
//        Link: https://www.youtube.com/watch?v=8K-6gdTlGEA&t=72s
//
//        1.1st Tutorial Name: Android Beginner Tutorial #8 - Custom ListView Adapter For Displaying Multiple Columns
//        Link: https://www.youtube.com/watch?annotation_id=annotation_4035470155&feature=iv&src_vid=jpt3Md9aDIQ&v=E6vE8fqQPTE
//
//        2nd Tutorial Name: Adding multiple columns to your ListView (part 2/3)
//        Link: https://www.youtube.com/watch?v=hHQqFGpod14
//
//        3rd Tutorial Name: Adding multiple columns to your ListView (part 3/3)
//        Link: https://www.youtube.com/watch?v=jpt3Md9aDIQ&t=1s

        vehicleList = new ArrayList<>();
        Cursor data = myDb.getAllData();

        int numRows = data.getCount();

        if (numRows == 0){
            Toast.makeText(ReportHistory.this, "No Entries found", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()){
                vehicle = new Vehicle(
                        data.getString(0),
                        data.getString(1),
                        data.getString(2),
                        data.getString(3),
                        data.getString(4),
                        data.getString(5),
                        data.getString(6),
                        data.getString(7),
                        data.getString(8),
                        data.getString(9),
                        data.getString(10)
                );
                vehicleList.add(vehicle);
            }
            ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this, R.layout.adapter_view_layout,vehicleList);
            reportHistoryListView = (ListView) findViewById(R.id.reportHistoryListView);
            reportHistoryListView.setAdapter(adapter);

            reportHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Vehicle vehicleHistory = (Vehicle) adapterView.getItemAtPosition(i);
                    String vehicleID = vehicleHistory.getId();
                    Log.d("dbDebug", vehicleID);
                    Intent ListViewIntent = new Intent(ReportHistory.this, com.sachith.parkwatch.ListViewIntent.class);
                    ListViewIntent.putExtra("com.sachith.parkwatch.ID",vehicleID);
                    startActivity(ListViewIntent);
                    }
                });
        }

//        References for Bottom Navigation Bar
//        First Tutorial:
//        Android Studio Tutorial - Bottom Navigation View
//        https://www.youtube.com/watch?v=wcE7IIHKfRg&t=131s
//
//        Second Tutorial:
//        Bottom Navigation Bar with Activities - Android Advanced Tutorial #6
//        https://www.youtube.com/watch?v=xyGrdOqseuw&t=97s

        //Declaring the bottom navigation bar elements
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //Bottom Navigation bar declarations
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        //Creating a switch statement to determine where to point the user when interacting with
        //the bottom navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.report_history:
                        Toast.makeText(ReportHistory.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        break;

                    case carSpaces:
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
    }
}
