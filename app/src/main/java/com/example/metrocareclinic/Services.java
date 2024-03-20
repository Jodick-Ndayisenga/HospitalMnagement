package com.example.metrocareclinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class Services extends AppCompatActivity {

    CardView appOi, physic, cardio, nutrition, surgery, dentis;
    MaterialButton lgout;
    TextView ti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        appOi = findViewById(R.id.cardInfo);
        lgout = findViewById(R.id.logoutBtnServices);
        ti = findViewById(R.id.titleHeaderServices);
        ti.setText(App.user.getFirstName());
        cardio = findViewById(R.id.cardCardio);
        physic = findViewById(R.id.cardPhysiciansServices);
        nutrition = findViewById(R.id.cardNutrition);
        surgery = findViewById(R.id.cardSurgeron);
        dentis = findViewById(R.id.cardDentist);


        setOnclick(cardio, Cardio.class);
        setOnclick(physic, Physicians.class);
        setOnclick(nutrition, Nutrition.class);
        setOnclick(dentis, Dentist.class);
        setOnclick(surgery, Surgeron.class);

        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, MainActivity.class));
            }
        });

        appOi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Services.this, UserAppointments.class));
            }
        });
    }
    public void setOnclick(CardView card, Class<?> myActivity){
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this, myActivity);
                startActivity(intent);
            }
        });
    }
}