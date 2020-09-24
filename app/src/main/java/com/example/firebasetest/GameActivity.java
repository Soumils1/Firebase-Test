package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {

//    Button button;


    TextView textViewP1,textViewP2;

    Button [][] buttons = new Button[3][3];
    Button deleteRoomButton;

    String [][]buttonText = new String[3][3];
    String player1Name="",player2Name="";
    String playerName="", roomName="",role="",message="",sign="X",turn = "host",winner = "";

    int roundCount,player1Points,player2Points;



    FirebaseDatabase database;
    DatabaseReference messageRef,roundCountRef,button_00Ref,hostTurnRef,button_01Ref,button_02Ref,button_10Ref,button_11Ref,button_12Ref,button_20Ref,button_21Ref,button_22Ref,winnerRef,player1PointsRef,player2PointsRef,player1NameRef,player2NameRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        button = findViewById(R.id.poke_button);
//        button.setEnabled(false);

        textViewP1 = findViewById(R.id.text_view_p1);
        textViewP2 = findViewById(R.id.text_view_p2);

        deleteRoomButton = findViewById(R.id.button_reset);

        buttons[0][0] = findViewById(R.id.button_00);
        buttons[0][1] = findViewById(R.id.button_01);
        buttons[0][2] = findViewById(R.id.button_02);
        buttons[1][0] = findViewById(R.id.button_10);
        buttons[1][1] = findViewById(R.id.button_11);
        buttons[1][2] = findViewById(R.id.button_12);
        buttons[2][0] = findViewById(R.id.button_20);
        buttons[2][1] = findViewById(R.id.button_21);
        buttons[2][2] = findViewById(R.id.button_22);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttonText[i][j] = buttons[i][j].getText().toString();
            }
        }


        database = FirebaseDatabase.getInstance();

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");




        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            roomName = extras.getString("roomName");
            if(roomName.equals(playerName)){
                role = "host";
                deleteRoomButton.setVisibility(View.VISIBLE);

                player1Name = playerName;
                player1Points = 0;
                player2Points = 0;
                player1NameRef = database.getReference().child("rooms").child(roomName).child("player1Name");
                textViewP1.setText(player1Name + ":" + player1Points);
//                player2NameRef = database.getReference().child("rooms").child(roomName).child("player2Name");
//                textViewP2.setText(player2Name + ":" + player2Points);
//                player2NameRef.setValue(player2Name);
                player1NameRef.setValue(player1Name);


            }
            else {
                role = "guest";
                player2Name = playerName;
                player2Points = 0;
                player1Points = 0;
                player2NameRef = database.getReference().child("rooms").child(roomName).child("player2Name");
                textViewP2.setText(player2Name + ":" + player2Points);
//                player1NameRef = database.getReference().child("rooms").child(roomName).child("player1Name");
//                textViewP1.setText(player1Name + ":" + player1Points);
//                player1NameRef.setValue(player1Name);
                player2NameRef.setValue(player2Name);

            }
            player1NameRef = database.getReference().child("rooms").child(roomName).child("player1Name");
            player2NameRef = database.getReference().child("rooms").child(roomName).child("player2Name");
        }



        buttons[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buttons[0][0].getText().toString().equals("")){return;}
                message = role+":entered!";
                messageRef.setValue(message);

                if(turn.equals("host")) {
                    buttonText[0][0] = "X";
                    if(checkForWin()){
                        hostWins();
                        player1Points++;
                        textViewP1.setText(player1Name + ":" + player1Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "guest";
                    buttonsForHostDisabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player1PointsRef.setValue(player1Points);
                    roundCountRef.setValue(roundCount);
                    button_00Ref.setValue(buttonText[0][0]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }



                }
                else {
                    buttonText[0][0] = "O";
                    if(checkForWin()){
                        guestWins();
                        player2Points++;
                        textViewP2.setText(player2Name + ":" + player2Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "host";
                    buttonsForHostEnabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player2PointsRef.setValue(player2Points);
                    roundCountRef.setValue(roundCount);
                    button_00Ref.setValue(buttonText[0][0]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });



        buttons[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buttons[1][0].getText().toString().equals("")){return;}
                message = role+":entered!";
                messageRef.setValue(message);

                if(turn.equals("host")) {
                    buttonText[1][0] = "X";
                    if(checkForWin()){
                        hostWins();
                        player1Points++;
                        textViewP1.setText(player1Name + ":" + player1Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "guest";
                    buttonsForHostDisabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player1PointsRef.setValue(player1Points);
                    roundCountRef.setValue(roundCount);
                    button_10Ref.setValue(buttonText[1][0]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }



                }
                else {
                    buttonText[1][0] = "O";
                    if(checkForWin()){
                        guestWins();
                        player2Points++;
                        textViewP2.setText(player2Name + ":" + player2Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "host";
                    buttonsForHostEnabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player2PointsRef.setValue(player2Points);
                    roundCountRef.setValue(roundCount);
                    button_10Ref.setValue(buttonText[1][0]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


        buttons[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buttons[0][1].getText().toString().equals("")){return;}
                message = role+":entered!";
                messageRef.setValue(message);

                if(turn.equals("host")) {
                    buttonText[0][1] = "X";
                    if(checkForWin()){
                        hostWins();
                        player1Points++;
                        textViewP1.setText(player1Name + ":" + player1Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "guest";
                    buttonsForHostDisabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player1PointsRef.setValue(player1Points);
                    roundCountRef.setValue(roundCount);
                    button_01Ref.setValue(buttonText[0][1]);


                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    buttonText[0][1] = "O";
                    if(checkForWin()){
                        guestWins();
                        player2Points++;
                        textViewP2.setText(player2Name + ":" + player2Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "host";
                    buttonsForHostEnabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player2PointsRef.setValue(player2Points);
                    roundCountRef.setValue(roundCount);
                    button_01Ref.setValue(buttonText[0][1]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


        buttons[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buttons[0][2].getText().toString().equals("")){return;}
                message = role+":entered!";
                messageRef.setValue(message);

                if(turn.equals("host")) {
                    buttonText[0][2] = "X";
                    if(checkForWin()){
                        hostWins();
                        player1Points++;
                        textViewP1.setText(player1Name + ":" + player1Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "guest";
                    buttonsForHostDisabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player1PointsRef.setValue(player1Points);
                    roundCountRef.setValue(roundCount);
                    button_02Ref.setValue(buttonText[0][2]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    buttonText[0][2] = "O";
                    if(checkForWin()){
                        guestWins();
                        player2Points++;
                        textViewP2.setText(player2Name + ":" + player2Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "host";
                    buttonsForHostEnabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player2PointsRef.setValue(player2Points);
                    roundCountRef.setValue(roundCount);
                    button_02Ref.setValue(buttonText[0][2]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


        buttons[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buttons[1][1].getText().toString().equals("")){return;}
                message = role+":entered!";
                messageRef.setValue(message);

                if(turn.equals("host")) {
                    buttonText[1][1] = "X";
                    if(checkForWin()){
                        hostWins();
                        player1Points++;
                        textViewP1.setText(player1Name + ":" + player1Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "guest";
                    buttonsForHostDisabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player1PointsRef.setValue(player1Points);
                    roundCountRef.setValue(roundCount);
                    button_11Ref.setValue(buttonText[1][1]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }



                }
                else {
                    buttonText[1][1] = "O";
                    if(checkForWin()){
                        guestWins();
                        player2Points++;
                        textViewP2.setText(player2Name + ":" + player2Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "host";
                    buttonsForHostEnabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player2PointsRef.setValue(player2Points);
                    roundCountRef.setValue(roundCount);
                    button_11Ref.setValue(buttonText[1][1]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


        buttons[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buttons[1][2].getText().toString().equals("")){return;}
                message = role+":entered!";
                messageRef.setValue(message);

                if(turn.equals("host")) {
                    buttonText[1][2] = "X";
                    if(checkForWin()){
                        hostWins();
                        player1Points++;
                        textViewP1.setText(player1Name + ":" + player1Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "guest";
                    buttonsForHostDisabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player1PointsRef.setValue(player1Points);
                    roundCountRef.setValue(roundCount);
                    button_12Ref.setValue(buttonText[1][2]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    buttonText[1][2] = "O";
                    if(checkForWin()){
                        guestWins();
                        player2Points++;
                        textViewP2.setText(player2Name + ":" + player2Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "host";
                    buttonsForHostEnabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player2PointsRef.setValue(player2Points);
                    roundCountRef.setValue(roundCount);
                    button_12Ref.setValue(buttonText[1][2]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


        buttons[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buttons[2][0].getText().toString().equals("")){return;}
                message = role+":entered!";
                messageRef.setValue(message);

                if(turn.equals("host")) {
                    buttonText[2][0] = "X";
                    if(checkForWin()){
                        hostWins();
                        player1Points++;
                        textViewP1.setText(player1Name + ":" + player1Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "guest";
                    buttonsForHostDisabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player1PointsRef.setValue(player1Points);
                    roundCountRef.setValue(roundCount);
                    button_20Ref.setValue(buttonText[2][0]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    buttonText[2][0] = "O";
                    if(checkForWin()){
                        guestWins();
                        player2Points++;
                        textViewP2.setText(player2Name + ":" + player2Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "host";
                    buttonsForHostEnabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player2PointsRef.setValue(player2Points);
                    roundCountRef.setValue(roundCount);
                    button_20Ref.setValue(buttonText[2][0]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


        buttons[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buttons[2][1].getText().toString().equals("")){return;}
                message = role+":entered!";
                messageRef.setValue(message);

                if(turn.equals("host")) {
                    buttonText[2][1] = "X";
                    if(checkForWin()){
                        hostWins();
                        player1Points++;
                        textViewP1.setText(player1Name + ":" + player1Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "guest";
                    buttonsForHostDisabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player1PointsRef.setValue(player1Points);
                    roundCountRef.setValue(roundCount);
                    button_21Ref.setValue(buttonText[2][1]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }




                }
                else {
                    buttonText[2][1] = "O";
                    if(checkForWin()){
                        guestWins();
                        player2Points++;
                        textViewP2.setText(player2Name + ":" + player2Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }
                    turn = "host";
                    buttonsForHostEnabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player2PointsRef.setValue(player2Points);
                    roundCountRef.setValue(roundCount);
                    button_21Ref.setValue(buttonText[2][1]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });



        buttons[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buttons[2][2].getText().toString().equals("")){return;}
                message = role+":entered!";
                messageRef.setValue(message);

                if(turn.equals("host")) {
                    buttonText[2][2] = "X";
                    if(checkForWin()){
                        hostWins();
                        player1Points++;
                        textViewP1.setText(player1Name + ":" + player1Points);
                        initValues();
                    }
                    else if(roundCount==8){
                        Draw();
initValues();
                    }


                    turn = "guest";
                    buttonsForHostDisabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player1PointsRef.setValue(player1Points);
                    roundCountRef.setValue(roundCount);
                    button_22Ref.setValue(buttonText[2][2]);

                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }


                   




                }
                else {
                    buttonText[2][2] = "O";
                    if(checkForWin()){
                        guestWins();
                        player2Points++;
                        textViewP2.setText(player2Name + ":" + player2Points);
                        initValues();
                    }
                    else if(roundCount==8){
                       Draw();
initValues();
                    }


                    turn = "host";
                    buttonsForHostEnabled();
                    roundCount++;
                    hostTurnRef.setValue(turn);
                    player2PointsRef.setValue(player2Points);
                    roundCountRef.setValue(roundCount);
                    button_22Ref.setValue(buttonText[2][2]);


                    if(!winner.equals("")){
                        Toast.makeText(GameActivity.this, winner +" wins", Toast.LENGTH_SHORT).show();
                    }





                }
            }
        });




//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                button.setEnabled(false);
//                message = role+":poked!";
//                messageRef.setValue(message);
//
//            }
//        });

        messageRef = database.getReference().child("rooms").child(roomName).child("message");
        message = role+":entered!";
        messageRef.setValue(message);

        winnerRef = database.getReference().child("rooms").child(roomName).child("winner");
        winner = "";
        winnerRef.setValue(winner);

        player1PointsRef = database.getReference().child("rooms").child(roomName).child("player1" + " Points");
        player1Points = 0;
        player1PointsRef.setValue(player1Points);

        player2PointsRef = database.getReference().child("rooms").child(roomName).child("player2" + " Points");
        player2Points = 0;
        player2PointsRef.setValue(player2Points);

        roundCountRef = database.getReference().child("rooms").child(roomName).child("rounds");
        roundCount = 0;
        roundCountRef.setValue(roundCount);

        hostTurnRef = database.getReference().child("rooms").child(roomName).child("turn");
        turn = "host";
        hostTurnRef.setValue(turn);

        button_00Ref = database.getReference().child("rooms").child(roomName).child("button_00");
        buttonText[0][0] = buttons[0][0].getText().toString();
        button_00Ref.setValue(buttonText[0][0]);

        button_01Ref = database.getReference().child("rooms").child(roomName).child("button_01");
        buttonText[0][1] = buttons[0][1].getText().toString();
        button_01Ref.setValue(buttonText[0][1]);

        button_02Ref = database.getReference().child("rooms").child(roomName).child("button_02");
        buttonText[0][2] = buttons[0][2].getText().toString();
        button_02Ref.setValue(buttonText[0][2]);

        button_10Ref = database.getReference().child("rooms").child(roomName).child("button_10");
        buttonText[1][0] = buttons[1][0].getText().toString();
        button_10Ref.setValue(buttonText[1][0]);

        button_11Ref = database.getReference().child("rooms").child(roomName).child("button_11");
        buttonText[1][1] = buttons[1][1].getText().toString();
        button_11Ref.setValue(buttonText[1][1]);

        button_12Ref = database.getReference().child("rooms").child(roomName).child("button_12");
        buttonText[1][2] = buttons[1][2].getText().toString();
        button_12Ref.setValue(buttonText[1][2]);

        button_20Ref = database.getReference().child("rooms").child(roomName).child("button_20");
        buttonText[2][0] = buttons[2][0].getText().toString();
        button_20Ref.setValue(buttonText[2][0]);

        button_21Ref = database.getReference().child("rooms").child(roomName).child("button_21");
        buttonText[2][1] = buttons[2][1].getText().toString();
        button_21Ref.setValue(buttonText[2][1]);

        button_22Ref = database.getReference().child("rooms").child(roomName).child("button_22");
        buttonText[2][2] = buttons[2][2].getText().toString();
        button_22Ref.setValue(buttonText[2][2]);

        addRoomEventListener();

    }

    private void initValues()
    {

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttonText[i][j] = buttons[i][j].getText().toString();
            }
        }


        winnerRef = database.getReference().child("rooms").child(roomName).child("winner");
        winner = "";
        winnerRef.setValue(winner);



        roundCountRef = database.getReference().child("rooms").child(roomName).child("rounds");
        roundCount = 0;
        roundCountRef.setValue(roundCount);

        hostTurnRef = database.getReference().child("rooms").child(roomName).child("turn");
        turn = "host";
        hostTurnRef.setValue(turn);

        button_00Ref = database.getReference().child("rooms").child(roomName).child("button_00");
        buttonText[0][0] = buttons[0][0].getText().toString();
        button_00Ref.setValue(buttonText[0][0]);

        button_01Ref = database.getReference().child("rooms").child(roomName).child("button_01");
        buttonText[0][1] = buttons[0][1].getText().toString();
        button_01Ref.setValue(buttonText[0][1]);

        button_02Ref = database.getReference().child("rooms").child(roomName).child("button_02");
        buttonText[0][2] = buttons[0][2].getText().toString();
        button_02Ref.setValue(buttonText[0][2]);

        button_10Ref = database.getReference().child("rooms").child(roomName).child("button_10");
        buttonText[1][0] = buttons[1][0].getText().toString();
        button_10Ref.setValue(buttonText[1][0]);

        button_11Ref = database.getReference().child("rooms").child(roomName).child("button_11");
        buttonText[1][1] = buttons[1][1].getText().toString();
        button_11Ref.setValue(buttonText[1][1]);

        button_12Ref = database.getReference().child("rooms").child(roomName).child("button_12");
        buttonText[1][2] = buttons[1][2].getText().toString();
        button_12Ref.setValue(buttonText[1][2]);

        button_20Ref = database.getReference().child("rooms").child(roomName).child("button_20");
        buttonText[2][0] = buttons[2][0].getText().toString();
        button_20Ref.setValue(buttonText[2][0]);

        button_21Ref = database.getReference().child("rooms").child(roomName).child("button_21");
        buttonText[2][1] = buttons[2][1].getText().toString();
        button_21Ref.setValue(buttonText[2][1]);

        button_22Ref = database.getReference().child("rooms").child(roomName).child("button_22");
        buttonText[2][2] = buttons[2][2].getText().toString();
        button_22Ref.setValue(buttonText[2][2]);

        addRoomEventListener();

    }

    private void guestWins()
    {
            winner = player2Name;
        Toast.makeText(this, winner+" wins", Toast.LENGTH_SHORT).show();
        winnerRef.setValue(winner);

    }

    private void hostWins()
    {
        winner = player1Name;
        Toast.makeText(this, winner+" wins", Toast.LENGTH_SHORT).show();

        winnerRef.setValue(winner);
    }


    private void Draw()
    {
        winner = "Draw";
        Toast.makeText(this, winner + "!", Toast.LENGTH_SHORT).show();

        winnerRef.setValue(winner);
    }
    private void PlayGame(String sign, int i, int j) {

        buttons[i][j].setText(sign);


    }

    private void addRoomEventListener()
    {
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(role.equals("host"))
                {
//                    if(dataSnapshot.getValue(String.class).contains("guest:")){
//                        button.setEnabled(true);
//                        Toast.makeText(GameActivity.this, ""+dataSnapshot.getValue(String.class).replace("guest:", ""), Toast.LENGTH_SHORT).show();
//                    }
                }
            else
                {
//                    if(dataSnapshot.getValue(String.class).contains("host:")){
//                        button.setEnabled(true);
//                        Toast.makeText(GameActivity.this, ""+dataSnapshot.getValue(String.class).replace("host:", ""), Toast.LENGTH_SHORT).show();
//                    }
                }
            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//messageRef.setValue(message);
            }
        });



        winnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                winner = dataSnapshot.getValue(String.class);

                if(!winner.equals("")){

                    if(winner.equals("Draw")){
                        Toast.makeText(GameActivity.this, winner +"!", Toast.LENGTH_SHORT).show();
                        initValues();
                    }

                    else {
                        Toast.makeText(GameActivity.this, winner + " wins", Toast.LENGTH_SHORT).show();
                        initValues();
                    }



                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//messageRef.setValue(message);
            }
        });



        player1NameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player1Name = dataSnapshot.getValue(String.class);

                textViewP1.setText(player1Name + ":" + player1Points);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//messageRef.setValue(message);
            }
        });


        player2NameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player2Name = dataSnapshot.getValue(String.class);

                textViewP2.setText(player2Name + ":" + player2Points);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//messageRef.setValue(message);
            }
        });




        player2PointsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player2Points = dataSnapshot.getValue(Integer.class);

                textViewP2.setText(player2Name + ":" + player2Points);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//messageRef.setValue(message);
            }
        });



        player1PointsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player1Points = dataSnapshot.getValue(Integer.class);

                textViewP1.setText(player1Name + ":" + player1Points);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//messageRef.setValue(message);
            }
        });



        roundCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              roundCount = dataSnapshot.getValue(Integer.class);


              if(roundCount==9){
                  Toast.makeText(GameActivity.this, "Draw!", Toast.LENGTH_SHORT).show();
                  initValues();
              }
            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//messageRef.setValue(message);
            }
        });



        hostTurnRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              turn = dataSnapshot.getValue(String.class);
            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//messageRef.setValue(message);
            }
        });


        button_00Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!turn.equals("host")) {sign = "X";
                    buttonsForHostDisabled();}
                else {sign = "O";
                    buttonsForHostEnabled();}

                if(!dataSnapshot.getValue(String.class).equals("")) {
                    buttonText[0][0] = sign;
                    if (role.equals("host")) {
                        

                        PlayGame(sign, 0, 0);

                    } else {

                        PlayGame(sign, 0, 0);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        button_01Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!turn.equals("host")) {sign = "X";
                    buttonsForHostDisabled();}
                else {sign = "O";
                    buttonsForHostEnabled();}

                if(!dataSnapshot.getValue(String.class).equals("")) {
                    buttonText[0][1] = sign;
                    if (role.equals("host")) {

                        PlayGame(sign, 0, 1);

                    } else {

                        PlayGame(sign, 0, 1);

                    }
                }
                
                

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        button_02Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!turn.equals("host")) {sign = "X";
                    buttonsForHostDisabled();}
                else {sign = "O";
                    buttonsForHostEnabled();}

                if(!dataSnapshot.getValue(String.class).equals("")) {
                    buttonText[0][2] = sign;
                    if (role.equals("host")) {

                        PlayGame(sign, 0, 2);

                    } else {

                        PlayGame(sign, 0, 2);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        button_10Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!turn.equals("host")) {sign = "X";
                    buttonsForHostDisabled();}
                else {sign = "O";
                    buttonsForHostEnabled();}

                if(!dataSnapshot.getValue(String.class).equals("")) {
                    buttonText[1][0] = sign;
                    if (role.equals("host")) {

                        PlayGame(sign, 1, 0);

                    } else {

                        PlayGame(sign, 1, 0);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        button_11Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!turn.equals("host")) {sign = "X";
                    buttonsForHostDisabled();}
                else {sign = "O";
                    buttonsForHostEnabled();}

                if(!dataSnapshot.getValue(String.class).equals("")) {
                    buttonText[1][1] = sign;
                    if (role.equals("host")) {

                        PlayGame(sign, 1, 1);

                    } else {

                        PlayGame(sign, 1, 1);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        button_12Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!turn.equals("host")) {sign = "X";
                    buttonsForHostDisabled();}
                else {sign = "O";
                    buttonsForHostEnabled();}

                if(!dataSnapshot.getValue(String.class).equals("")) {
                    buttonText[1][2] = sign;
                    if (role.equals("host")) {

                        PlayGame(sign, 1, 2);

                    } else {

                        PlayGame(sign, 1, 2);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        button_20Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!turn.equals("host")) {sign = "X";
                    buttonsForHostDisabled();}
                else {sign = "O";
                    buttonsForHostEnabled();}

                if(!dataSnapshot.getValue(String.class).equals("")) {
                    buttonText[2][0] = sign;
                    if (role.equals("host")) {

                        PlayGame(sign, 2, 0);

                    } else {

                        PlayGame(sign, 2, 0);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        button_21Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!turn.equals("host")) {sign = "X";
                    buttonsForHostDisabled();}
                else {sign = "O";
                    buttonsForHostEnabled();}

                if(!dataSnapshot.getValue(String.class).equals("")) {
                    buttonText[2][1] = sign;
                    if (role.equals("host")) {

                        PlayGame(sign, 2, 1);

                    } else {

                        PlayGame(sign, 2, 1);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        button_22Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!turn.equals("host")) {sign = "X";
                    buttonsForHostDisabled();}
                else {sign = "O";
                    buttonsForHostEnabled();}

                if(!dataSnapshot.getValue(String.class).equals("")) {
                    buttonText[2][2] = sign;
                    if (role.equals("host")) {

                        PlayGame(sign, 2, 2);

                    } else {

                        PlayGame(sign, 2, 2);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void buttonsForHostEnabled()
    {
        if(role.equals("host")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setEnabled(true);
                }
            }
        }

        else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setEnabled(false);
                }
            }
        }

    }

    private void buttonsForHostDisabled()
    {
        if(role.equals("host")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setEnabled(false);
                }
            }
        }

        else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setEnabled(true);
                }
            }
        }
    }

    private boolean checkForWin(){
        

        for(int i=0;i<3;i++){
            if(buttonText[i][0]==buttonText[i][1]
                    && buttonText[i][0]==buttonText[i][2]
                    && !buttonText[i][0].equals("")){
                return true;
            }
        }
        for(int i=0;i<3;i++){
            if(buttonText[0][i]==buttonText[1][i]
                    && buttonText[0][i]==buttonText[2][i]
                    && !buttonText[0][i].equals("")){
                return true;
            }
        }
        for(int i=0;i<3;i++){
            if(buttonText[0][0]==buttonText[1][1]
                    && buttonText[0][0]==buttonText[2][2]
                    && !buttonText[0][0].equals("")){
                return true;
            }
        }
        for(int i=0;i<3;i++){
            if(buttonText[0][2]==buttonText[1][1]
                    && buttonText[0][2]==buttonText[2][0]
                    && !buttonText[0][2].equals("")){
                return true;
            }
        }
        return false;
    }


}

