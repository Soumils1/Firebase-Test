package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {

    ListView listViewRooms;


    private Button createRoomButton;
    private List<String> roomsList;

    String playerName = "";
    String roomName = "";


    private DatabaseReference roomRef,roomsRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        database = FirebaseDatabase.getInstance();

        createRoomButton =findViewById(R.id.button_create_room);
        listViewRooms = findViewById(R.id.list_rooms);

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");
        roomName = playerName;

        roomsList = new ArrayList<>();


        createRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRoomButton.setText("Creating Room");
                createRoomButton.setEnabled(false);

                roomName = playerName;
                roomRef = database.getReference().child("rooms").child(roomName).child("player1");
                addValueEventListener();
                roomRef.setValue(playerName);
            }
        });

        listViewRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roomName = roomsList.get(position);
                roomRef = database.getReference().child("rooms").child(roomName).child("player2");
                addValueEventListener();
                roomRef.setValue(playerName);
            }
        });

        addRoomsEventListener();


    }

    private void addRoomsEventListener() {
        roomsRef = database.getReference().child("rooms");
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomsList.clear();
                Iterable<DataSnapshot> rooms = dataSnapshot.getChildren();
                for(DataSnapshot snapshot : rooms)
                {
                    roomsList.add(snapshot.getKey());

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_list_item_1, roomsList);
                    listViewRooms.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RoomActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addValueEventListener() {
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                createRoomButton.setText("Create Room");
                createRoomButton.setEnabled(true);
                roomRef.setValue(playerName);

                Intent intent = new Intent(RoomActivity.this, GameActivity.class);
                intent.putExtra("roomName", roomName);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                createRoomButton.setText("Create Rooms");
                createRoomButton.setEnabled(true);
                Toast.makeText(RoomActivity.this, "Room Already Exists", Toast.LENGTH_SHORT).show();
            }
        });
    }
}