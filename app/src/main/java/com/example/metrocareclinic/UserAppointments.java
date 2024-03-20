package com.example.metrocareclinic;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserAppointments extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    FloatingActionButton refresh;
    MaterialButton LOGOUT;

    TextView notApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointments);
        FirebaseApp.initializeApp(UserAppointments.this);
        db = FirebaseFirestore.getInstance();
        recyclerView= findViewById(R.id.myAppointments);
        LOGOUT = findViewById(R.id.logOutBtn);
        notApp = findViewById(R.id.noAppointText);

        LOGOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAppointments.this, MainActivity.class));
            }
        });

        geAllDoctors();
    }

    private void geAllDoctors() {
        db.collection("appointments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean foundAppointment = false;
                            ArrayList<Appointment> arrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Appointment appointment = document.toObject(Appointment.class);
                                String patients = appointment.getPatients();
                                if (patients != null && patients.contains(App.user.getUsername())) {
                                        foundAppointment = true;
                                        appointment.setId(document.getId());
                                        arrayList.add(appointment);
                                    }
                                    AppointAdapter adapter = new AppointAdapter(UserAppointments.this, arrayList);
                                    adapter.setOnItemClickListener(new AppointAdapter.OnItemClickListener() {
                                    @Override
                                    public void onClick(Appointment appointment) {

                                    }});

                                    recyclerView.setAdapter(adapter);
                                }
                            if(!foundAppointment){
                                notApp.setText("YOU HAVE NO APPOINTMENT !!");
                            }
                        }
                    }
                });


    }
}