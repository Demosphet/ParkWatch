package com.sachith.parkwatch;

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
                        break;

                    case R.id.gateOverview:
                        Toast.makeText(Map.this, "Gate Overview", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.report:
                        Toast.makeText(Map.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });

    }
}
