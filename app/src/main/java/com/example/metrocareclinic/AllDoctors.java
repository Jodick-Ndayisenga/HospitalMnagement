package com.example.metrocareclinic;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class AllDoctors extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    FloatingActionButton refresh;
    MaterialButton myAppoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_doctors);
        FirebaseApp.initializeApp(AllDoctors.this);
         db = FirebaseFirestore.getInstance();
         refresh = findViewById(R.id.refreshAppointments);
         recyclerView= findViewById(R.id.myAppointments);
         myAppoint = findViewById(R.id.myAppointmentsBtn);

         myAppoint.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(AllDoctors.this, UserAppointments.class));
             }
         });

        geAllDoctors();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geAllDoctors();
            }
        });
    }

    private void geAllDoctors() {
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
                    UserAppointAdapter adapter = new UserAppointAdapter(AllDoctors.this, arrayList);
                    adapter.setOnItemClickListener(new UserAppointAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(Appointment appointment) {
                                if(!appointment.getPatients().contains(App.user.getUsername())){
                                    // Appointment with the same docName or docField already exists
                                    Map<String, Object> appointmentData = new HashMap<>();
                                    appointmentData.put("doc", appointment.getDoc());
                                    appointmentData.put("docFullName", appointment.getDocFullName());
                                    appointmentData.put("field", appointment.getField());
                                    appointmentData.put("time", appointment.getTime());
                                    appointmentData.put("patients", appointment.getPatients() + " " + App.user.getUsername());

                                    db.collection("appointments").document(appointment.getId()).set(appointmentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(AllDoctors.this, "Booked Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AllDoctors.this, "Error booking Appointment", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }else{
                                    Toast.makeText(AllDoctors.this, "You have already booked this appointment", Toast.LENGTH_SHORT).show();


                                }

                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(AllDoctors.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}