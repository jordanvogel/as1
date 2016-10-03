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
