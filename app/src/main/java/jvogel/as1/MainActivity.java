/*
 * (C) Copyright 2016 Jordan Vogel.
 * All rights reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Contributors: Abram Hindle - I used the Student Picker Saga as a guide
 * "MadProgrammer" - http://stackoverflow.com/questions/12575990/calendar-date-to-yyyy-mm-dd-format-in-java
 * "Raghunandan" - http://stackoverflow.com/questions/17453297/passing-arraylist-of-string-arrays-from-one-activity-to-another-in-android
 * "user2216292" - http://stackoverflow.com/questions/17453297/passing-arraylist-of-string-arrays-from-one-activity-to-another-in-android
 *
 */

package jvogel.as1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HabitListManager.initManager(this.getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.habitListView);
        //This line below for some reason is causing a crash
        //And not for lack of trying it couldn't be fixed in time.
        Collection<Habit> habits = new HabitListController().getHabitList().getHabit();
        final ArrayList<String> completeList = new ArrayList<>();
        final ArrayList<Habit> list = new ArrayList<Habit>(habits);
        final ArrayAdapter<Habit> habitAdapter = new ArrayAdapter<Habit>(this,R.layout.list_file,list );
        listView.setAdapter(habitAdapter);

        //Added a change observer
        HabitListController.getHabitList().addListener(new Listener() {
            @Override
            public void update() {
                list.clear();
                Collection<Habit> habits = new HabitListController().getHabitList().getHabit();
                list.addAll(habits);
                habitAdapter.notifyDataSetChanged();
            }
        });

        //Delete Alert Dialog box from the StudentPickerSaga
        //This one is set to onClick instead of LongClick
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setMessage("Delete habit "+list.get(position).toString()+"?");
                adb.setCancelable(true);
                final int finalPosition = position;
                adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Habit habit = list.get(finalPosition);
                        HabitListController.getHabitList().removeHabit(habit);
                    }
                });
                adb.setNegativeButton("Complete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Habit habit = list.get(finalPosition);
                        habit.addCompletion(habit.getName());
                    }
                });
                adb.show();
            }
        });
        //ON long click start a new intent with a populated list
        //that is filled with all of a habits completions

        //NOTE never got to this function because a seemingly unfixable deserialization problem
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int finalPosition = position;
                Habit habit = list.get(finalPosition);
                Intent intent = new Intent(MainActivity.this, ListHabitsActivity.class);
                intent.putStringArrayListExtra("complete list", habit.getCompletions());
                startActivity(intent);
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    public void addHabit(View v){
        CheckBox sunday;
        CheckBox monday;
        CheckBox tuesday;
        CheckBox wednesday;
        CheckBox thursday;
        CheckBox friday;
        CheckBox saturday;
        HabitListController ht = new HabitListController();
        EditText textView = (EditText) findViewById(R.id.createHabit);
        sunday = (CheckBox) findViewById(R.id.checkBox2);
        monday = (CheckBox) findViewById(R.id.checkBox3);
        tuesday = (CheckBox) findViewById(R.id.checkBox4);
        wednesday = (CheckBox) findViewById(R.id.checkBox5);
        thursday = (CheckBox) findViewById(R.id.checkBox6);
        friday = (CheckBox) findViewById(R.id.checkBox7);
        saturday = (CheckBox) findViewById(R.id.checkBox8);

        //Create the Date formatting in the yyyy-mm-dd format
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String habitName = textView.getText().toString();
        String formatDate = format1.format(cal.getTime());
        habitName += ":"+formatDate+":";

        //Boolean checks to determine which boxes were checked
        //And add the corresponding string
        if (sunday.isChecked()){
            habitName += " Sunday,";
        }
        if (monday.isChecked()){
            habitName += " Monday ";
        }
        if (tuesday.isChecked()){
            habitName += " Tuesday ";
        }
        if (wednesday.isChecked()){
            habitName += " Wednesday ";
        }
        if (thursday.isChecked()){
            habitName += " Thursday ";
        }
        if (friday.isChecked()){
            habitName += " Friday ";
        }
        if (saturday.isChecked()){
            habitName += " Saturday ";
        }
        ht.addHabit(new Habit(habitName));


    }
}
