package com.example.metrocareclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    TextView newUser, loginErr;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        newUser = findViewById(R.id.goToRegister);
        loginButton = findViewById(R.id.buttonLogin);
        loginErr = findViewById(R.id.loginErrors);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn = username.getText().toString().toLowerCase().trim();
                String myPwd = password.getText().toString().trim();
                db.collection("users")
                        .whereEqualTo("username", usn)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.isEmpty()){
                                    loginErr.setText("Username does not exist");
                                }else{
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    User logged_in = new User();
                                    logged_in.setFirstName(document.getString("firstName"));
                                    logged_in.setLastName(document.getString("lastName"));
                                    logged_in.setEmail(document.getString("email"));
                                    logged_in.setUsername(document.getString("username"));

                                    App.user = logged_in;
                                    String isAdmin = document.getString("isAdmin");
                                    String role = document.getString("role");
                                    String gottenPassword= document.getString("password");
                                    if(gottenPassword.equals(myPwd) & isAdmin.equals("true")){
                                        startActivity(new Intent(MainActivity.this, AdminView.class));
                                    }
                                    else if(gottenPassword.equals(myPwd) & role.equals("patient")){
                                        Intent intent = new Intent(MainActivity.this, PatientView.class);
                                       //intent.putExtra("username", usn);
                                       // intent.putExtra("email", email);
                                        intent.putExtra("myName", document.getString("firstName"));
                                        startActivity(intent);
                                    }else if(gottenPassword.equals(myPwd) & role.equals("doctor")){
                                        Intent intent = new Intent(MainActivity.this, Doctors.class);
                                        intent.putExtra("username", usn);
                                        intent.putExtra("myName", document.getString("firstName"));
                                        startActivity(intent);
                                    }else{loginErr.setText("Incorrect Password !");}
                                }}

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loginErr.setText("Login Error");
                            }
                        });
            }
        });
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

}