package com.example.pet_care;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dashboard extends AppCompatActivity {

    Button Dfoodbtn, Dnutbtn , Dequbtn, Dedubtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        Dfoodbtn = (Button) findViewById(R.id.foodbtn);
        Dnutbtn = (Button) findViewById(R.id.nutbtn);
        Dequbtn = (Button) findViewById(R.id.equbtn);
        Dedubtn = (Button) findViewById(R.id.edubtn);

        Dfoodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Foods.class);
                startActivity(intent);
            }
        });
        Dnutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Nutrition.class);
                startActivity(intent);
            }
        });

        Dequbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Equipment.class);
                startActivity(intent);
            }
        });
        Dedubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Education.class);
                startActivity(intent);
            }
        });


    }
}