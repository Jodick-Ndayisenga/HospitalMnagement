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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddAppointments extends AppCompatActivity {
    TextInputEditText doctorFullName, queuedPatients, myUserName, doctorField, appointmentTime;

    MaterialButton add, delete, update;
    TextView errors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointments);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        doctorFullName = findViewById(R.id.DocFullNameInput);
        queuedPatients = findViewById(R.id.QueuedPatientsInput);
        myUserName = findViewById(R.id.DocUserNameInput);
        doctorField = findViewById(R.id.ThisDocField);
        appointmentTime = findViewById(R.id.AppointTimeInput);

        doctorField.setText(App.appointment.getField());
        appointmentTime.setText(App.appointment.getTime());
        doctorFullName.setText(App.appointment.getDocFullName());
        queuedPatients.setText(App.appointment.getPatients());
        myUserName.setText(App.appointment.getDoc());

        add = findViewById(R.id.AddAppointBtn);
        delete = findViewById(R.id.DeleteAppointBtn);
        update = findViewById(R.id.UpdateAppointBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddAppointments.this, AddNewAppoint.class));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("appointments").document(App.appointment.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddAppointments.this, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddAppointments.this, "Error while deleting user", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doctorName = myUserName.getText().toString().toLowerCase().trim();
                db.collection("appointments")
                        .whereEqualTo("doc", doctorName)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                        // Appointment with the same docName or docField already exists
                                        Map<String, Object> appointmentData = new HashMap<>();
                                        appointmentData.put("doc", Objects.requireNonNull(myUserName.getText().toString().toLowerCase().trim() ));
                                        appointmentData.put("docFullName", Objects.requireNonNull( doctorFullName.getText().toString().trim() ));
                                        appointmentData.put("field", Objects.requireNonNull( doctorField.getText().toString().trim()));
                                        appointmentData.put("time", Objects.requireNonNull(appointmentTime.getText().toString().trim()));
                                        appointmentData.put("patients", Objects.requireNonNull(queuedPatients.getText().toString().trim()));

                                        db.collection("appointments").document(App.appointment.getId()).set(appointmentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AddAppointments.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AddAppointments.this, "Error while saving Appointment", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }else{
                                        errors.setText("Appointment Exist man!!");

                                    }
                                }else{
                                    errors.setText("Error checking the doctor");
                                }

                            }
                        });
            }
        });
    }
}