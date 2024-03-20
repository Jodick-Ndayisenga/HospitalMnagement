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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddNewAppoint extends AppCompatActivity {
    TextInputEditText docName, docField, time;

    MaterialButton add;
    TextView errors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_appoint);
        docName = findViewById(R.id.DocUsernameInput);
        docField = findViewById(R.id.DocField);
        time =  findViewById(R.id.AppointTimeInput);
        errors = findViewById(R.id.showErrors);
        add = findViewById(R.id.AddAppointBtn);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appointmentDocName = docName.getText().toString().toLowerCase().trim();
                String appointmentDocField = docField.getText().toString().trim();
                String appointmentTime = time.getText().toString().trim();

                // Check if an appointment with the same docName or docField exists
                db.collection("appointments")
                        .whereEqualTo("doc", appointmentDocName)
                        .whereEqualTo("field", appointmentDocField)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                        // Appointment with the same docName or docField already exists
                                        errors.setText("Appointment already exists");
                                        //Toast.makeText(AddNewAppoint.this, "Appointment with the same doctor already exists", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // No appointment with the same docName or docField exists
                                        // Proceed to fetch user details based on the username (docName)
                                        db.collection("users")
                                                .whereEqualTo("username", appointmentDocName)
                                                .whereEqualTo("role", "doctor")
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            QuerySnapshot userSnapshot = task.getResult();
                                                            if (userSnapshot != null && !userSnapshot.isEmpty()) {
                                                                String firstName = userSnapshot.getDocuments().get(0).getString("firstName");
                                                                String lastName = userSnapshot.getDocuments().get(0).getString("lastName");
                                                                Map<String, Object> appointmentData = new HashMap<>();
                                                                appointmentData.put("doc", appointmentDocName);
                                                                appointmentData.put("field", appointmentDocField);
                                                                appointmentData.put("time", appointmentTime);
                                                                appointmentData.put("patients", "");
                                                                appointmentData.put("medicines", "");
                                                                appointmentData.put("docFullName", firstName + " " + lastName);

                                                                db.collection("appointments")
                                                                        .add(appointmentData)
                                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                            @Override
                                                                            public void onSuccess(DocumentReference documentReference) {
                                                                                Toast.makeText(AddNewAppoint.this, "Appointment added successfully", Toast.LENGTH_SHORT).show();
                                                                                startActivity(new Intent(AddNewAppoint.this, AppointmentManagement.class));
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                errors.setText("Failed to add appointment");
                                                                            }
                                                                        });
                                                            } else {
                                                                errors.setText("We don't have that doctor right now");
                                                            }
                                                        } else {
                                                            errors.setText("Error fetching user details");

                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    errors.setText("Error checking for existing appointments");
                                    //Toast.makeText(AddNewAppoint.this, "Error checking for existing appointments", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}