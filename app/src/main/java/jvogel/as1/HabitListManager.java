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
