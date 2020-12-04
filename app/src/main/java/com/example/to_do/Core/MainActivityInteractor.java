package com.example.to_do.Core;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.to_do.Model.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivityInteractor implements MainActivityContractor.Ineractor {

    private MainActivityContractor.onOperationListener mListner;
    private ArrayList<Player> players = new ArrayList<>();

    public MainActivityInteractor(MainActivityContractor.onOperationListener mListner) {
        this.mListner = mListner;
    }

    @Override
    public void performCreatePlayer(DatabaseReference reference, Player player) {

        mListner.onStart();
        reference.child(player.getKey()).setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    mListner.onSuccess();
                    mListner.onEnd();
                }
                else
                {
                    mListner.onFailure();
                    mListner.onEnd();
                }
            }
        });
    }

    @Override
    public void performReadPlayers(DatabaseReference reference) {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Player player = dataSnapshot.getValue(Player.class);
                players.add(player);
                mListner.onRead(players);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Player player = dataSnapshot.getValue(Player.class);
                mListner.onUpdate(player);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Player player = dataSnapshot.getValue(Player.class);
                mListner.onDelete(player);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void performUpdatePlayer(DatabaseReference reference, Player player) {
        mListner.onStart();
        reference.child(player.getKey()).setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    mListner.onEnd();
                }
                else
                {
                    mListner.onEnd();
                }
            }
        });
    }

    @Override
    public void performDeletePlayer(DatabaseReference reference, Player player) {
        mListner.onStart();
        reference.child(player.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    mListner.onEnd();
                }
                else
                {
                    mListner.onEnd();
                }
            }
        });
    }
}
