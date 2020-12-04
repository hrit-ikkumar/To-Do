package com.example.to_do.Core;


import com.example.to_do.Model.Player;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivityPresenter implements MainActivityContractor.Presenter, MainActivityContractor.onOperationListener {

    private MainActivityContractor.View mView;
    private MainActivityInteractor mInteractor;

    public MainActivityPresenter(MainActivityContractor.View mView) {
        this.mView = mView;
        mInteractor = new MainActivityInteractor(this);
    }

    @Override
    public void createNewPlayer(DatabaseReference reference, Player player) {
        mInteractor.performCreatePlayer(reference,player);
    }

    @Override
    public void readPlayers(DatabaseReference reference) {
        mInteractor.performReadPlayers(reference);
    }

    @Override
    public void updatePlayer(DatabaseReference reference, Player player) {
        mInteractor.performUpdatePlayer(reference,player);
    }

    @Override
    public void deletePlayer(DatabaseReference reference, Player player) {
        mInteractor.performDeletePlayer(reference,player);
    }

    @Override
    public void onSuccess() {
        mView.onCreatePlayerSuccessful();
    }

    @Override
    public void onFailure() {
        mView.onCreatePlayerFailure();
    }

    @Override
    public void onStart() {
        mView.onProcessStart();
    }

    @Override
    public void onEnd() {
        mView.onProcessEnd();
    }

    @Override
    public void onRead(ArrayList<Player> players) {
        mView.onPlayerRead(players);
    }

    @Override
    public void onUpdate(Player player) {
        mView.onPlayerUpdate(player);
    }

    @Override
    public void onDelete(Player player) {
        mView.onPlayerDelete(player);
    }
}
