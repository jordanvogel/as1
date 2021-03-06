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
import java.util.Date;

/**
 Habit class containing all the information needed for each object list
 */

public class Habit implements Serializable{
    protected String habitName;
    private Date date;
    public ArrayList<String> completions = new ArrayList<String>();

    //Contructor
    public Habit(String name){
        this.habitName = name;
        this.date = new Date();
    }

    //Add a string of the habitname to a list of completed habits
    public void addCompletion(String string){
        this.completions.add(string);
    }

    // returns a an arraylist for completed habits
    public ArrayList<String> getCompletions(){
        return this.completions;
    }

    public String getName() {
        return this.habitName;
    }

    public boolean equals (Object compareHabit){
        if (compareHabit != null && compareHabit.getClass()==this.getClass()){
            return this.equals((Habit)compareHabit);
            } else {
            return false;
        }
    }

    public boolean equals(Habit compareHabit){
        if (compareHabit==null){
            return false;
        }
        return getName().equals(compareHabit.getName());
    }
    public int hashCode(){
        return ("Habit:"+getName()).hashCode();
    }

    public String toString(){
        return getName();
    }
}
