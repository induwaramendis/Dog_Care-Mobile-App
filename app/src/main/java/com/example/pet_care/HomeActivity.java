package com.example.pet_care;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

public class HomeActivity extends AppCompatActivity {

    public CardView card1, card2, card3, card4, card5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize buttons
         card1 = findViewById(R.id.ViewProfile);
         card2 = findViewById(R.id.EditProfile);
         card3 = findViewById(R.id.AddPet);
         card4 = findViewById(R.id.ViewPets);

        card1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ViewProfileActivity.class);
                startActivity(intent);

            }
        });

        card2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Handle the click event, and redirect to ViewProfileActivity
                Intent intent = new Intent(HomeActivity.this, EditProfileActivity.class);
                startActivity(intent);


            }
        });

        card3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Handle the click event, and redirect to ViewProfileActivity
                Intent intent = new Intent(HomeActivity.this, AddPetActivity.class);
                startActivity(intent);

            }
        });

        card4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Handle the click event, and redirect to ViewProfileActivity
                Intent intent = new Intent(HomeActivity.this, EditPetActivity.class);
                startActivity(intent);

            }
        });


    }
}
