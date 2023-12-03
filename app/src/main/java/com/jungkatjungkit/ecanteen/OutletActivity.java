package com.jungkatjungkit.ecanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OutletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("OUTLET_ID")) {
            int outletId = intent.getIntExtra("OUTLET_ID", -1);
            String outletName = intent.getStringExtra("OUTLET_NAME");

            TextView outletNameTextView = findViewById(R.id.namaOutlet);
            outletNameTextView.setText(outletName);
//            TextView outletIdTextView = findViewById(R.id.outletIdTextView);
//            outletIdTextView.setText("Outlet ID: " + outletId);

        }
    }
}