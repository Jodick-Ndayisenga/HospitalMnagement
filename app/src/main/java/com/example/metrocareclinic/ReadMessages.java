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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ReadMessages extends AppCompatActivity {
    MaterialButton lougt, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_messages);

        FirebaseApp.initializeApp(ReadMessages.this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.AllMyMessages);

        lougt = findViewById(R.id.LgoutMessage);
        back = findViewById(R.id.letSgoback);

        lougt.setOnClickListener(view -> startActivity(new Intent(ReadMessages.this, MainActivity.class)));

        db.collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<UserMessage> arrayList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        UserMessage message = document.toObject(UserMessage.class);
                        message.setId(document.getId());
                        message.setUserName(document.getString("Name"));
                        message.setUserEmail(document.getString("Email"));
                        message.setUserMessage(document.getString("Message"));
                        arrayList.add(message);
                    }
                    MessageAdapter adapter = new MessageAdapter(ReadMessages.this, arrayList);
                    adapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(UserMessage appointment) {
                            //App.appointment = appointment;
                            //startActivity(new Intent(AppointmentManagement.this, AddAppointments.class));
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ReadMessages.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }
}