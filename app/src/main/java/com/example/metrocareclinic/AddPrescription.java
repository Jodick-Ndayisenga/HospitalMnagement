package com.example.metrocareclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddPrescription extends AppCompatActivity {
    TextView patientN, errors, pressSuccess;
    TextInputEditText PatientPrescription;
    MaterialButton submitPrescription;
    FirebaseFirestore db;
    String myPatient, doctor, docFieldNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        patientN = findViewById(R.id.patientName);
        errors = findViewById(R.id.showPresErrors);
        PatientPrescription = findViewById(R.id.prescription);
        submitPrescription = findViewById(R.id.UpdatePrescriptionBtn);
        pressSuccess = findViewById(R.id.showPresSuccess);
        Intent intent = getIntent();

        //patientN.setText(App.user.getFirstName() + " " + App.user.getLastName());
        patientN.setText(App.user.getFullName());
        PatientPrescription.setText(App.appointment.getMedicines());
        myPatient = App.user.getUsername();
        doctor = App.appointment.getDoc();
        docFieldNow = App.appointment.getField();
        db = FirebaseFirestore.getInstance();


        submitPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> appointmentData = new HashMap<>();
                appointmentData.put("doctor", App.appointment.getDocFullName());
                appointmentData.put("specialist", App.appointment.getField());
                appointmentData.put("time", App.appointment.getTime());
                appointmentData.put("patientUserName", App.user.getUsername());
                appointmentData.put("patientName", App.user.getFullName());
                appointmentData.put("patientEmail", App.user.getEmail());
                appointmentData.put("medicines", PatientPrescription.getText().toString());

                db.collection("prescriptions").add(appointmentData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        db.collection("appointments")
                                .whereEqualTo("doc", App.appointment.getDoc())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            QuerySnapshot querySnapshot = task.getResult();
                                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                                for (QueryDocumentSnapshot document : querySnapshot) {
                                                    Appointment appointment = document.toObject(Appointment.class);
                                                    appointment.setId(document.getId());

                                                    // Split the patients string by "|" character
                                                    String[] patients = appointment.getPatients().split("\\s+");
                                                    String remainingPatients = "";


                                                    // For each patient, create a new appointment instance
                                                    for (String patient : patients) {
                                                        if(patient.equals(App.user.getUsername())){
                                                            remainingPatients += "";
                                                        }
                                                        else{
                                                            remainingPatients += (patient + " ");
                                                        }
                                                    }

                                                    Map<String, Object> appointmentData = new HashMap<>();
                                                    appointmentData.put("doc", appointment.getDoc());
                                                    appointmentData.put("docFullName", appointment.getDocFullName());
                                                    appointmentData.put("field", appointment.getField());
                                                    appointmentData.put("time", appointment.getTime());
                                                    appointmentData.put("patients", remainingPatients);

                                                    db.collection("appointments").document(appointment.getId()).set(appointmentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(AddPrescription.this, "You have no appointments left", Toast.LENGTH_SHORT).show();
                                                            //startActivity(new Intent(AddPrescription.this, MainActivity.class));
                                                            finish();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            errors.setText("Could not remove user!");
                                                        }
                                                    });

                                                }
                                            } else {
                                                errors.setText("Bad doctor !!");
                                            }
                                        } else {
                                            errors.setText("Error finding doctor !!");
                                        }

                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        errors.setText("Couldn't submit medicines");
                    }
                });

            }
        });

    }
}