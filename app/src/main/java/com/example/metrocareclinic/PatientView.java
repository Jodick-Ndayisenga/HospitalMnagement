package com.example.metrocareclinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class PatientView extends AppCompatActivity {
    TextView userPerson;
    CardView services, updates, message, myAppoint, cardBlog, medecines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view);
        userPerson = findViewById(R.id.titleHeader);
        userPerson.setText(App.user.getFirstName());

        services = findViewById(R.id.cardServices);
        updates = findViewById(R.id.cardUpdates);
        message = findViewById(R.id.cardChat);
        myAppoint = findViewById(R.id.cardAppoint);
        cardBlog = findViewById(R.id.cardLabel);
        medecines = findViewById(R.id.cardMedicine);

        medecines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientView.this, Medecines.class));
            }
        });
        cardBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientView.this, Nutrition.class));
            }
        });

        MaterialButton lgt = findViewById(R.id.logoutBtnCard);
        lgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientView.this, MainActivity.class));
            }
        });
        myAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientView.this, UserAppointments.class));
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientView.this, Message.class));
            }
        });
        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientView.this, AllDoctors.class));
            }
        });
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientView.this, Services.class);
                startActivity(intent);
            }
        });
    }
}