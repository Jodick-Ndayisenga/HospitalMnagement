package com.example.metrocareclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddUser extends AppCompatActivity {
    TextInputEditText firstName, lastName, username, dob, email, residence, password, confirmPassword, specialities, isAdmin, role;

    MaterialButton add, delete, update;
    TextView errors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
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


        firstName.setText(App.user.getFirstName());
        lastName.setText(App.user.getLastName());
        username.setText(App.user.getUsername());
        dob.setText(App.user.getDob());
        email.setText(App.user.getEmail());
        residence.setText(App.user.getResidence());
        password.setText(App.user.getPassword());
        confirmPassword.setText(App.user.getConfirmPassword());
        specialities.setText(App.user.getSpecialities());
        isAdmin.setText(App.user.getIsAdmin());
        role.setText(App.user.getRole());

        add = findViewById(R.id.AddUser);
        delete = findViewById(R.id.DeleteUser);
        update = findViewById(R.id.UpdateUser);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(AddUser.this, AddNewUser.class));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users").document(App.user.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddUser.this, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddUser.this, "Error while deleting user", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = password.getText().toString();
                String cpwd = confirmPassword.getText().toString();
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
                    user.put("specialities", Objects.requireNonNull(specialities.getText()).toString().toLowerCase());
                    user.put("isAdmin", Objects.requireNonNull(isAdmin.getText()).toString().toLowerCase());
                    user.put("residence", Objects.requireNonNull(residence.getText()).toString());
                    user.put("role", Objects.requireNonNull(role.getText()).toString().toLowerCase());

                    db.collection("users").document(App.user.getId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddUser.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddUser.this, "Error while saving users", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}