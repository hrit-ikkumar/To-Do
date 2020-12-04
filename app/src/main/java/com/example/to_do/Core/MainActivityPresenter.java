package com.example.to_do.Core;


import com.example.to_do.Model.ToDoTask;
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
    public void createNewPlayer(DatabaseReference reference, ToDoTask toDoTask) {
        mInteractor.performCreatePlayer(reference, toDoTask);
    }

    @Override
    public void readPlayers(DatabaseReference reference) {
        mInteractor.performReadPlayers(reference);
    }

    @Override
    public void updatePlayer(DatabaseReference reference, ToDoTask toDoTask) {
        mInteractor.performUpdatePlayer(reference, toDoTask);
    }

    @Override
    public void deletePlayer(DatabaseReference reference, ToDoTask toDoTask) {
        mInteractor.performDeletePlayer(reference, toDoTask);
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
    public void onRead(ArrayList<ToDoTask> toDoTasks) {
        mView.onPlayerRead(toDoTasks);
    }

    @Override
    public void onUpdate(ToDoTask toDoTask) {
        mView.onPlayerUpdate(toDoTask);
    }

    @Override
    public void onDelete(ToDoTask toDoTask) {
        mView.onPlayerDelete(toDoTask);
    }
}
