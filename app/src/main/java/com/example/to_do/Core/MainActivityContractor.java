package com.example.to_do.Core;


import com.example.to_do.Model.ToDoTask;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface MainActivityContractor {

    interface View{
        void onCreatePlayerSuccessful();
        void onCreatePlayerFailure();
        void onProcessStart();
        void onProcessEnd();
        void onPlayerRead(ArrayList<ToDoTask> toDoTasks);
        void onPlayerUpdate(ToDoTask toDoTask);
        void onPlayerDelete(ToDoTask toDoTask);
    }

    interface Presenter{
        void createNewPlayer(DatabaseReference reference, ToDoTask toDoTask);
        void readPlayers(DatabaseReference reference);
        void updatePlayer(DatabaseReference reference, ToDoTask toDoTask);
        void deletePlayer(DatabaseReference reference, ToDoTask toDoTask);
    }

    interface Ineractor{
        void performCreatePlayer(DatabaseReference reference, ToDoTask toDoTask);
        void performReadPlayers(DatabaseReference reference);
        void performUpdatePlayer(DatabaseReference reference, ToDoTask toDoTask);
        void performDeletePlayer(DatabaseReference reference, ToDoTask toDoTask);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onStart();
        void onEnd();
        void onRead(ArrayList<ToDoTask> toDoTasks);
        void onUpdate(ToDoTask toDoTask);
        void onDelete(ToDoTask toDoTask);
    }
}
