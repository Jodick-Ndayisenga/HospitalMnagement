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
import com.google.android.gms.tasks.OnSuccessListener;
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

public class Doctors extends AppCompatActivity {
    Intent intent;
    RecyclerView recyclerView;
    TextView noAppoint;
    FirebaseFirestore db;
    FloatingActionButton refresh, log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        FirebaseApp.initializeApp(Doctors.this);
        intent = getIntent();
        noAppoint = findViewById(R.id.noAppointments);
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.myPatients);


        fetchPatients();

         refresh= findViewById(R.id.refreshPatients);
         log = findViewById(R.id.logout);
         log.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Doctors.this, MainActivity.class));
             }
         });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchPatients();
            }
        });

    }

    private void fetchPatients() {
        FirebaseFirestore.getInstance().collection("appointments")
                .whereEqualTo("doc", intent.getStringExtra("username"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                ArrayList<Appointment> arrayList = new ArrayList<>();
                                ArrayList<User> arrayUser = new ArrayList<>();

                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    Appointment appointment = document.toObject(Appointment.class);
                                    appointment.setId(document.getId());

                                    // Split the patients string by "|" character
                                    String[] patients = appointment.getPatients().split("\\s+");
                                    if(appointment.getPatients().isEmpty()){
                                        noAppoint.setText("You have no Appointments !!");
                                        return;

                                    }else{

                                    // For each patient, create a new appointment instance
                                    for (String patient : patients) {
                                        FirebaseFirestore.getInstance().collection("users")
                                                .whereEqualTo("username", patient.toLowerCase().trim())
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                        if (!queryDocumentSnapshots.isEmpty()) {
                                                            for (DocumentSnapshot userSnapshot : queryDocumentSnapshots.getDocuments()) {
                                                                User user = userSnapshot.toObject(User.class);
                                                                if (user != null) {
                                                                    Appointment newAppointment = new Appointment();
                                                                    newAppointment.setTime(appointment.getTime());
                                                                    newAppointment.setDoc(appointment.getDoc());
                                                                    newAppointment.setDocFullName(appointment.getDocFullName());
                                                                    newAppointment.setField(appointment.getField());
                                                                    User newUser = new User();
                                                                    newUser.setFirstName(user.getFirstName());
                                                                    newUser.setLastName(user.getLastName());
                                                                    newUser.setResidence(user.getResidence());
                                                                    newUser.setEmail(user.getEmail());
                                                                    newUser.setUsername(user.getUsername());

                                                                    arrayList.add(newAppointment);
                                                                    arrayUser.add(newUser);
                                                                }

                                                            }
                                                            DoctorAdapter adapter = new DoctorAdapter(Doctors.this, arrayList, arrayUser);
                                                            adapter.setOnItemClickListener(new DoctorAdapter.OnItemClickListener() {
                                                                @Override
                                                                public void onClick(Appointment appointment, User user) {
                                                                    App.appointment = appointment;
                                                                    App.user = user;
                                                                    startActivity(new Intent( Doctors.this, AddPrescription.class));

                                                                }
                                                            });
                                                            recyclerView.setAdapter(adapter);
                                                        }
                                                    }
                                                });
                                    }
                                    }
                                }
                            } else {
                               noAppoint.setText("Error fetching appointments !!");
                            }
                        } else {
                            noAppoint.setText("Error finding doctor !!");
                        }
                    }
                });


    }

}