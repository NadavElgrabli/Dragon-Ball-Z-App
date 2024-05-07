package com.example.mydragonballzapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private CardView charactersCardView;
    private CardView planetsCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        setOnClicks();
    }

    public void findViewsById() {
        charactersCardView = findViewById(R.id.card_view_characters);
        planetsCardView = findViewById(R.id.card_view_planets);
    }

    public void setOnClicks(){
        charactersCardView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CharactersActivity.class);
            startActivity(intent);
        });

        planetsCardView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PlanetsActivity.class);
            startActivity(intent);
        });
    }


}