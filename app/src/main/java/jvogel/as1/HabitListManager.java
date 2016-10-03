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

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
  Habit list manager based on the studentPickerSaga StudentListManager
 */

public class HabitListManager {
    static final String prefFile = "HabitList";
    static final String hlKey = "habitList";

    Context context;

    static private HabitListManager habitListManager = null;


    public static void initManager(Context context){
        if (habitListManager == null) {
            if (context == null) {
                throw new RuntimeException("missing context for habitListManager");
            }
            habitListManager = new HabitListManager(context);
        }
    }

    public static HabitListManager getManager(){
        if (habitListManager == null) {
            throw new RuntimeException("did not initialize manager");
        }
        return habitListManager;
    }

    public HabitListManager(Context context){
        this.context = context;
    }

    public HabitList loadHabitList() throws IOException, ClassNotFoundException {
        SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        String habitListData = settings.getString(hlKey,"");
        if (habitListData.equals("")){
            return new HabitList();
        } else {
            return habitListFromString(habitListData);
        }

    }

    public HabitList habitListFromString(String habitListData) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(habitListData, Base64.DEFAULT));
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (HabitList) oi.readObject();
    }

    public String habitListToString(HabitList hl) throws IOException{
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(hl);
        oo.close();
        byte bytes[] = bo.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public void saveHabitList(HabitList hl) throws IOException {
        SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(hlKey, habitListToString(hl));
        editor.commit();

    }
}
