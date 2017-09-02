package com.sachith.parkwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class Map extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.carSpaces:
                        Toast.makeText(Map.this, "Car Spaces", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(Map.this, CapSpaces.class);
                        startActivity(intent1);
                        break;

                    case R.id.gateOverview:
                        Toast.makeText(Map.this, "Gate Overview", Toast.LENGTH_SHORT).show();
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
}
