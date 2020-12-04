package com.example.to_do.Activities;
import android.os.Bundle;
import com.example.to_do.Core.MainActivityContractor;
import com.example.to_do.Core.MainActivityPresenter;
import com.example.to_do.Model.Player;
import com.example.to_do.R;
import com.example.to_do.Utils.Config;
import com.example.to_do.Utils.CreatePlayerDialog;
import com.example.to_do.Utils.RecylerViewAdapter;
import com.example.to_do.Utils.UpdatePlayerDialog;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.Task;
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
    public ArrayList<Player> mPlayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void savePlayer(Player player) {

        String key = mReference.push().getKey();
        Player newPlayer = new Player(player.getName(),player.getAge(),player.getPosition(),key);
        mPresenter.createNewPlayer(mReference,newPlayer);

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
    public void onPlayerRead(ArrayList<Player> players) {

        this.mPlayerList = players;
        recyclerViewAdapter = new RecylerViewAdapter(players,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onPlayerUpdate(Player player) {
        int index = getIndex(player);
        mPlayerList.set(index,player);
        recyclerViewAdapter.notifyItemChanged(index);
    }

    @Override
    public void onPlayerDelete(Player player) {
        int index = getIndex(player);
        mPlayerList.remove(index);
        recyclerViewAdapter.notifyItemRemoved(index);

        Toast.makeText(TasksActivity.this,"Deleted Player "+player.getName(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updatePlayer(Player player) {
        mPresenter.updatePlayer(mReference,player);
    }

    @Override
    public void onPlayerUpdateClick(int position) {
        Player player = mPlayerList.get(position);
        UpdatePlayerDialog updatePlayerDialog = new UpdatePlayerDialog(player);
        updatePlayerDialog.show(getSupportFragmentManager(),"update dialog");
    }

    @Override
    public void onPlayerDeleteClick(int position) {
        Player player = mPlayerList.get(position);
        mPresenter.deletePlayer(mReference, player);
    }

    public int getIndex(Player player)
    {
        int index = 0;

        for (Player countPlayer: mPlayerList)
        {
            if(countPlayer.getKey().trim().equals(player.getKey()))
            {
                break;
            }
            index++;
        }

        return index;
    }

}
