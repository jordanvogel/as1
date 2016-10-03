package jvogel.as1;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
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

