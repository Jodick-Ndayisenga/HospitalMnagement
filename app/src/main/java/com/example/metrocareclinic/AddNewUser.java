package com.example.metrocareclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddNewUser extends AppCompatActivity {
    TextInputEditText firstName, lastName, username, dob, email, residence, password, confirmPassword, specialities, isAdmin, role;

    MaterialButton add;
    TextView errors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        firstName = findViewById(R.id.firstNameInput);
        lastName = findViewById(R.id.lastNameInput);
        username = findViewById(R.id.usernameInput);
        dob = findViewById(R.id.dobInput);
        email = findViewById(R.id.emailInput);
        residence = findViewById(R.id.residenceInput);
        password = findViewById(R.id.passwordInput);
        confirmPassword = findViewById(R.id.confirmPasswordInput);
        specialities = findViewById(R.id.specialitiesInput);
        isAdmin = findViewById(R.id.adminInput);
        role = findViewById(R.id.roleInput);
        errors = findViewById(R.id.showErrors);

        add = findViewById(R.id.AddUser);
        FirebaseFirestore db = FirebaseFirestore.getInstance();




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = password.getText().toString();
                String cpwd = confirmPassword.getText().toString();
                String usern = username.getText().toString().toLowerCase();
                String eml = email.getText().toString();

                db.collection("users")
                        .whereEqualTo("email", eml)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // Email already exists
                                    errors.setText("Email already exists!!");
                                }else{
                                    db.collection("users")
                                            .whereEqualTo("username", usern)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                    if (!queryDocumentSnapshots.isEmpty()) {
                                                        // Email already exists
                                                        errors.setText("Username already exists!!");
                                                    }else{

                                                        if(!pwd.equals(cpwd)){
                                                            errors.setText("Passwords don't match !!");
                                                        }else {

                                                            Map<String, Object> user = new HashMap<>();
                                                            user.put("firstName", Objects.requireNonNull(firstName.getText()).toString());
                                                            user.put("lastName", Objects.requireNonNull(lastName.getText()).toString());
                                                            user.put("email", Objects.requireNonNull(email.getText()).toString().toLowerCase());
                                                            user.put("username", Objects.requireNonNull(username.getText()).toString().toLowerCase());
                                                            user.put("dob", Objects.requireNonNull(dob.getText()).toString());
                                                            user.put("password", Objects.requireNonNull(pwd));
                                                            user.put("confirmPassword", Objects.requireNonNull(cpwd));
                                                            user.put("specialities", Objects.requireNonNull("null"));
                                                            user.put("isAdmin", Objects.requireNonNull("false"));
                                                            user.put("residence", Objects.requireNonNull(residence.getText()).toString());
                                                            user.put("role", Objects.requireNonNull("patient"));

                                                            db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference) {
                                                                    Toast.makeText(AddNewUser.this, "User Added Successfully", Toast.LENGTH_SHORT).show();
                                                                    finish();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(AddNewUser.this, "There was an error while adding user", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }

                                                    }
                                                }
                                            });
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                errors.setText("Unexpected Error");
                            }
                        }
                        );
            }
        });


    }
}