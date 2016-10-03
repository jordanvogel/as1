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
