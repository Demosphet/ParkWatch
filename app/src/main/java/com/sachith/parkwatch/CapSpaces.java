package com.sachith.parkwatch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class CapSpaces extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_spaces);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
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
                        break;

                    case R.id.report:
                        Toast.makeText(CapSpaces.this, "Report Vehicle", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });
    }
}
