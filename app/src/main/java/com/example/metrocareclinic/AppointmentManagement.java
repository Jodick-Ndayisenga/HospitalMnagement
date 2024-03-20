package com.example.metrocareclinic;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class AppointmentManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_management);

        FirebaseApp.initializeApp(AppointmentManagement.this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.myAppointments);

        FloatingActionButton add = findViewById(R.id.addAppointment);
        add.setOnClickListener(view -> startActivity(new Intent(AppointmentManagement.this, AddNewAppoint.class)));

        db.collection("appointments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Appointment> arrayList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Appointment appointment = document.toObject(Appointment.class);
                        appointment.setId(document.getId());
                        arrayList.add(appointment);
                    }
                    AppointAdapter adapter = new AppointAdapter(AppointmentManagement.this, arrayList);
                    adapter.setOnItemClickListener(new AppointAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(Appointment appointment) {
                           App.appointment = appointment;
                           startActivity(new Intent(AppointmentManagement.this, AddAppointments.class));
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(AppointmentManagement.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton refresh = findViewById(R.id.refreshAppointments);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("appointments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Appointment> arrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Appointment appointment = document.toObject(Appointment.class);
                                appointment.setId(document.getId());
                                arrayList.add(appointment);
                            }
                            AppointAdapter adapter = new AppointAdapter(AppointmentManagement.this, arrayList);
                            recyclerView.setAdapter(adapter);

                            adapter.setOnItemClickListener(new AppointAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(Appointment appointment) {
                                    App.appointment = appointment;
                                    startActivity(new Intent(AppointmentManagement.this, AddAppointments.class));
                                }
                            });
                        } else {
                            Toast.makeText(AppointmentManagement.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}