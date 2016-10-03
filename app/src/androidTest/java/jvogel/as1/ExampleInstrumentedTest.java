package jvogel.as1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import jvogel.as1.Habit;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private boolean updated = false;

    /*@Test
        public void useAppContext() throws Exception {
            // Context of the app under test.
            Context appContext = InstrumentationRegistry.getTargetContext();

            assertEquals("jvogel.as1", appContext.getPackageName());
        }*/
    @Test
    public void testHabit() {
        String habitName = "John";
        Habit habit = new Habit(habitName);
        assertTrue("Student name is equal", habitName.equals(habit.getName()));
    }

    @Test
    public void testEmptyHabitList() {
        HabitList habitList = new HabitList();
        assertTrue("Empty List", habitList.size() == 0);
    }

    @Test
    public void testAddHabitList() {
        HabitList habitList = new HabitList();
        String habitName = "Habit";
        Habit testHabit = new Habit(habitName);
        habitList.addHabit(testHabit);
        Collection<Habit> habits = habitList.getHabit();
        assertTrue("Habit List of 1", habits.size() == 1);
        assertTrue("Test habit not contained", habits.contains(testHabit));
    }

    @Test
    public void testGetHabitList() {
        HabitList habitList = new HabitList();
        String habitName = "Habit";
        Habit testHabit = new Habit(habitName);
        habitList.addHabit(testHabit);
        assertTrue("Habit List of 1", habitList.size() == 1);
        assertTrue("Test habit not contained", habitList.contains(testHabit));
    }

    @Test
    public void testRemoveHabit() {
        HabitList habitList = new HabitList();
        String habitName = "Habit";
        Habit testHabit = new Habit(habitName);
        habitList.addHabit(testHabit);
        assertTrue("Habit list size too small", habitList.size() == 1);
        assertTrue("", habitList.contains(testHabit));
        habitList.removeHabit(testHabit);
        assertTrue("Habit List too large", habitList.size() == 0);
        assertFalse("test student still contained", habitList.contains(testHabit));
    }

    @Test
    public void testNotifyListeners(){
        HabitList habitList = new HabitList();
        updated = false;
        Integer i = new Integer(1);
        Listener l = new Listener(){
            public void update(){
                ExampleInstrumentedTest.this.updated = true;
            }
        };
        habitList.addListener(l);
        Habit testHabit = new Habit("LIL STANK");
        habitList.addHabit(testHabit);
        assertTrue("No update happened", this.updated);
        updated = false;
        habitList.removeHabit(testHabit);
        assertTrue("No Removal Happened", this.updated);
    }

    @Test
    public void testRemoveListeners(){
        HabitList habitList = new HabitList();
        updated = false;
        Integer i = new Integer(1);
        Listener l = new Listener(){
            public void update(){
                ExampleInstrumentedTest.this.updated = true;
            }
        };
        habitList.removeListener(l);
        habitList.addHabit(new Habit("New Habit"));
        assertFalse("No update happened", this.updated);
    }
}
