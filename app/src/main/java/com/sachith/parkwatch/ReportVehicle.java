package com.sachith.parkwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ReportVehicle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_vehicle);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.report_history:
                        Toast.makeText(ReportVehicle.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(ReportVehicle.this, ReportHistory.class);
                        startActivity(intent1);
                        break;

                    case R.id.carSpaces:
                        Toast.makeText(ReportVehicle.this, "Car Spaces", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.gateOverview:
                        Toast.makeText(ReportVehicle.this, "Gate Overview", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(ReportVehicle.this, Map.class);
                        startActivity(intent2);
                        break;

                    case R.id.report_vehicle:
                        Toast.makeText(ReportVehicle.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(ReportVehicle.this, ReportVehicle.class);
                        startActivity(intent3);
                        break;
                }

                return true;
            }
        });
    }
}
