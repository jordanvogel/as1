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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 HabitList based off the StudentList from the studentPickerSaga
 */

public class HabitList implements Serializable{

    protected ArrayList<Habit> habitList = null;
    protected static transient ArrayList<Listener> listeners = null;

    public HabitList() {
        habitList = new ArrayList<Habit>();
        listeners = new ArrayList<Listener>();
    }

    private static ArrayList<Listener> getListeners() {
        if (listeners == null){
            listeners = new ArrayList<Listener>();
        }
        return listeners;
    }

    public Collection<Habit> getHabit() {

        return habitList;
    }

    public void addHabit(Habit testHabit) {
        habitList.add(testHabit);
        notifyListeners();
    }

    private void notifyListeners() {
        for (Listener listener: getListeners()  ) {
            listener.update();
        }
    }



    public void removeHabit(Habit testHabit) {
        habitList.remove(testHabit);
        notifyListeners();
    }


    public int size() {
        return habitList.size();
    }


    public boolean contains(Habit testHabit) {

        return habitList.contains(testHabit);
    }

    public static void addListener(Listener l) {

        getListeners().add(l);
    }

    public void removeListener(Listener l) {

        getListeners().remove(l);
    }
}

