package com.example.to_do.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.to_do.Model.ToDoTask;
import com.example.to_do.R;

public class UpdatePlayerDialog extends AppCompatDialogFragment {

    public EditText mName;

    public Button mSaveBtn;
    public UpdatePlayerDialog.UpdatePlayerDialogListener mListener;
    public ToDoTask toDoTask;

    public UpdatePlayerDialog(ToDoTask toDoTask) {
        this.toDoTask = toDoTask;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        builder.setView(view);
        builder.setTitle("Update Player");
        builder.setCancelable(true);

        mName = (EditText)view.findViewById(R.id.et_name);

        mSaveBtn = (Button)view.findViewById(R.id.btn_save);

        mName.setText(toDoTask.getName());


        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mName.getText().toString();


                if(name.isEmpty())
                {
                    return;
                }
                else
                {
                    ToDoTask updateToDoTask = new ToDoTask(name);
                    mListener.updatePlayer(updateToDoTask);
                    dismiss();

                }

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UpdatePlayerDialog.UpdatePlayerDialogListener) context;
    }

    public interface UpdatePlayerDialogListener{
        void updatePlayer(ToDoTask toDoTask);
    }
}
