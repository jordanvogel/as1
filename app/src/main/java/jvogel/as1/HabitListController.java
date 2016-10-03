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

import java.io.IOException;
import java.io.Serializable;

/**
 HabitListController based on the StudentListController in the student picker saga
 */

public class HabitListController implements Serializable{


    private static HabitList habitList = null;

    static public HabitList getHabitList() {
        if (habitList == null){
            try {
                habitList = HabitListManager.getManager().loadHabitList();
                HabitList.addListener(new Listener() {
                    @Override
                    public void update() {
                        saveHabitList();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not deserialize");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return habitList;
    }

    static public void saveHabitList(){
        try {
            HabitListManager.getManager().saveHabitList(getHabitList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not deserialize");
        }
    }

    public void addHabit (Habit habit) {
        getHabitList().addHabit(habit);
    }

}
