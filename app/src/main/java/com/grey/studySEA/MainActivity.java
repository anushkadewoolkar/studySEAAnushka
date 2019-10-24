package com.grey.studySEA;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.grey.studySEA.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DataBase studySEAHelper;
    private ListView assignmentList;
    private ArrayAdapter<String> assignmentAdapter;
    private EditText textBox;
    private SharedPreferences studySEAPreferences;
    private String list;
    private TextView viewDate;
    private DatePickerDialog.OnDateSetListener dateListener;
    private Button study;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studySEAHelper = new DataBase(this);
        assignmentList = (ListView)findViewById(R.id.assignmentsList);
        viewDate = (TextView) findViewById(R.id.assignmentDate);
        study = (Button) findViewById(R.id.study);

        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudySubjectActivity.class);
                startActivity(intent);
            }
        });

        studySEAPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        list = studySEAPreferences.getString("listName","");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        viewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,dateListener,year,month,day);
                dialog.show();

            }
        });
        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month+1;
                String date = month + "/" + day + "/" + year;
                viewDate.setText(date);
            }
        };
        changeTextAction();
        loadAllTasks();
    }

    private void changeTextAction() {
    }

    private void loadAllTasks() {
        ArrayList<String> taskList = studySEAHelper.getAllTasks();
        if(assignmentAdapter == null) {
            assignmentAdapter = new ArrayAdapter<String>(this, R.layout.rows, R.id.assignmentName, taskList);
            assignmentList.setAdapter(assignmentAdapter);
        } else {
            assignmentAdapter.clear();
            assignmentAdapter.addAll(taskList);
            assignmentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_new_task) {
            final EditText userTaskGet = new EditText(this);
            final EditText getDateUser = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("lets add a task!")
                    .setMessage("what is the name of your assignment/task for today")
                    .setView(userTaskGet)
                    .setPositiveButton(R.string.add, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(!userTaskGet.getText().toString().matches(""))
                            {
                                String task = String.valueOf(userTaskGet.getText());
                                studySEAHelper.insertData(task);
                                loadAllTasks();
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .create();
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view) {
        View parent = (View)view.getParent();
        TextView viewAssignment = (TextView)parent.findViewById(R.id.assignmentName);
        String task = String.valueOf(viewAssignment.getText());
        studySEAHelper.deleteData(task);
        loadAllTasks();
    }


}
