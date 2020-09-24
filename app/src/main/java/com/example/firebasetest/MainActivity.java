package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    
    EditText editTextPlayerName;
    Button loginButton;
    
    private FirebaseDatabase database;
    private DatabaseReference RootRef;
    
    String playerName = "";
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        editTextPlayerName = findViewById(R.id.edit_text_name);
        loginButton = findViewById(R.id.login_button);
        
        database = FirebaseDatabase.getInstance();
//        RootRef = database.getReference();

        loginButton.setEnabled(false);
        editTextPlayerName.setEnabled(false);
       
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName", "");
        if(!playerName.equals("")){
            RootRef = database.getReference().child("players");
            addValueEventListener();
            RootRef.child(playerName).setValue("");
        }

       loginButton.setEnabled(true);
        editTextPlayerName.setEnabled(true);
        
        
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerName = editTextPlayerName.getText().toString();
                editTextPlayerName.setText("");
                if(!playerName.equals(""))
                {
                    loginButton.setText("Logging In");
                    loginButton.setEnabled(false);
                    RootRef = database.getReference().child("players");
                    addValueEventListener();
                    RootRef.child(playerName).setValue("");
                }
            }
        });
        
        
        
        
        
        
        
        
    }

    private void addValueEventListener()
    {
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("playerName", playerName);
                editor.apply();
                
                startRoomActivity();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loginButton.setText("Login");
                loginButton.setEnabled(true);
                Toast.makeText(MainActivity.this, "Try Another Name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startRoomActivity() {
        Intent intent = new Intent(MainActivity.this, RoomActivity.class);
        startActivity(intent);
        finish();
    }
}