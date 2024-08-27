package com.example.pet_care;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        // Reference to the buttons
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        // Set onClickListener for Button 1
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 1 click
                Intent intent = new Intent(index.this, CaregiverLogin.class);
                startActivity(intent);
                showToast("WELCOME TO CAREGIVER PAGE");
            }
        });

        // Set onClickListener for Button 2
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button 2 click
                Intent intent = new Intent(index.this, LoginActivity.class);
                startActivity(intent);
                showToast("WELCOME TO OWNER LOGIN PAGE");

            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Method to be called when Button 1 is clicked (specified in XML)
    public void onButton1Click(View view) {
        showToast("Button 1 clicked!");

    }

    // Method to be called when Button 2 is clicked (specified in XML)
    public void onButton2Click(View view) {
        showToast("Button 2 clicked!");

    }
}
