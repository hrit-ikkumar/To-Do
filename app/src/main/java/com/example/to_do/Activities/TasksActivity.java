package com.example.to_do.Activities;
import android.os.Bundle;
import com.example.to_do.Core.MainActivityContractor;
import com.example.to_do.Core.MainActivityPresenter;
import com.example.to_do.Model.ToDoTask;
import com.example.to_do.R;
import com.example.to_do.Utils.Config;
import com.example.to_do.Utils.CreatePlayerDialog;
import com.example.to_do.Utils.RecylerViewAdapter;
import com.example.to_do.Utils.UpdatePlayerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity implements CreatePlayerDialog.createPlayerDialogListener,
        MainActivityContractor.View,
        UpdatePlayerDialog.UpdatePlayerDialogListener,
        RecylerViewAdapter.onPlayerListener {
    public MainActivityPresenter mPresenter;
    public ProgressBar mProgressbar;
    public DatabaseReference mReference;
    public RecyclerView mRecyclerView;
    public RecylerViewAdapter recyclerViewAdapter;
    public ArrayList<ToDoTask> mToDoTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);

        mProgressbar = (ProgressBar)findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mReference = FirebaseDatabase.getInstance().getReference().child(Config.USER_NODE);

        mPresenter = new MainActivityPresenter(this);
        mPresenter.readPlayers(mReference);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        CreatePlayerDialog createPlayerDialog = new CreatePlayerDialog();
        createPlayerDialog.show(getSupportFragmentManager(),"create dialog");
    }

    @Override
    public void savePlayer(ToDoTask toDoTask) {

        String key = mReference.push().getKey();
        ToDoTask newToDoTask = new ToDoTask(toDoTask.getName(),key);
        mPresenter.createNewPlayer(mReference, newToDoTask);

    }

    @Override
    public void onCreatePlayerSuccessful() {
        Toast.makeText(TasksActivity.this,"New Player Created",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreatePlayerFailure() {
        Toast.makeText(TasksActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessStart() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProcessEnd() {
        mProgressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPlayerRead(ArrayList<ToDoTask> toDoTasks) {

        this.mToDoTaskList = toDoTasks;
        recyclerViewAdapter = new RecylerViewAdapter(toDoTasks,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onPlayerUpdate(ToDoTask toDoTask) {
        int index = getIndex(toDoTask);
        mToDoTaskList.set(index, toDoTask);
        recyclerViewAdapter.notifyItemChanged(index);
    }

    @Override
    public void onPlayerDelete(ToDoTask toDoTask) {
        int index = getIndex(toDoTask);
        mToDoTaskList.remove(index);
        recyclerViewAdapter.notifyItemRemoved(index);

        Toast.makeText(TasksActivity.this,"Deleted Player "+ toDoTask.getName(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updatePlayer(ToDoTask toDoTask) {
        mPresenter.updatePlayer(mReference, toDoTask);
    }

    @Override
    public void onPlayerUpdateClick(int position) {
        ToDoTask toDoTask = mToDoTaskList.get(position);
        UpdatePlayerDialog updatePlayerDialog = new UpdatePlayerDialog(toDoTask);
        updatePlayerDialog.show(getSupportFragmentManager(),"update dialog");
    }

    @Override
    public void onPlayerDeleteClick(int position) {
        ToDoTask toDoTask = mToDoTaskList.get(position);
        mPresenter.deletePlayer(mReference, toDoTask);
    }

    public int getIndex(ToDoTask toDoTask)
    {
        int index = 0;

        for (ToDoTask countToDoTask : mToDoTaskList)
        {
            if(countToDoTask.getKey().trim().equals(toDoTask.getKey()))
            {
                break;
            }
            index++;
        }

        return index;
    }

}
