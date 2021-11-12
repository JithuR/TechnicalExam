package com.decenternet.technicalexam;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.decenternet.technicalexam.adapter.TaskListAdapter;
import com.decenternet.technicalexam.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskActivity extends AppCompatActivity implements OnItemClickInterface {


    FloatingActionButton fab;

    RecyclerView recyclerView;

    ArrayList<Task> taskList;
    TaskListAdapter mTaskAdapter;
    SharedPreferences preferences;
    OnItemClickInterface mOnItemClickInterface = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        preferences = getSharedPreferences("TechnicalExam", 0);


        fab = (FloatingActionButton) findViewById(R.id.addTask);
        recyclerView = (RecyclerView) findViewById(R.id.taskList);

        loadTask();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mTaskAdapter = new TaskListAdapter(taskList,mOnItemClickInterface);
        recyclerView.setAdapter(mTaskAdapter);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddTaskDialog();
            }
        });


    }

    private void loadTask() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task", null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        taskList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (taskList == null) {
            // if the array list is empty
            // creating a new array list.
            taskList = new ArrayList<>();
        }
    }


    private void openAddTaskDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_addtask);

        final EditText text = (EditText) dialog.findViewById(R.id.descriptionEdt);


        Button dialogButton = (Button) dialog.findViewById(R.id.addTaskBtn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task task = new Task();
                task.setDescription(text.getText().toString());
                task.setId(taskList.size()+1);
                taskList.add(task);
                // notifying adapter when new data added.
                mTaskAdapter.notifyItemInserted(taskList.size());
                saveTask();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void saveTask() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json = gson.toJson(taskList);

        editor.putString("task", json);

        editor.apply();
    }



    @Override
    public void onDelete(int Id, int position) {
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog_delete_popup);
        d.show();
        Button tvCancel = d.findViewById(R.id.tvCancel);
        Button tvRemove = d.findViewById(R.id.tvRemove);
        tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Task> list = new ArrayList();
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("task", null);
                Type type = new TypeToken<ArrayList<Task>>() {}.getType();
                list = gson.fromJson(json, type);
                if (list!=null){
                    for(int i =0; i<list.size();i++){
                        if(list.get(i).getId()==Id){
                            list.remove(i);
                            taskList.remove(position);
                            mTaskAdapter.notifyDataSetChanged();

                        }
                    }
                    sharedPreferences.edit().putString("task", gson.toJson(list)).apply();
                }

                d.dismiss();

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }

    @Override
    public void onUpdate(int Id,int position) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_addtask);

        final EditText text = (EditText) dialog.findViewById(R.id.descriptionEdt);


        Button dialogButton = (Button) dialog.findViewById(R.id.addTaskBtn);


        text.setText(taskList.get(position).getDescription());
        text.setSelection(text.getText().length());

        dialogButton.setText("Update");
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Task> list = new ArrayList();
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("task", null);
                Type type = new TypeToken<ArrayList<Task>>() {}.getType();
                list = gson.fromJson(json, type);
                if (list!=null){
                    for(int i =0; i<list.size();i++){
                        if(list.get(i).getId()==Id){
                            list.get(i).setDescription(text.getText().toString());
                            taskList.get(position).setDescription(text.getText().toString());
                            mTaskAdapter.notifyDataSetChanged();

                        }
                    }
                    sharedPreferences.edit().putString("task", gson.toJson(list)).apply();
                }

                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
