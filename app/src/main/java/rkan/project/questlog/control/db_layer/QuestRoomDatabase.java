package rkan.project.questlog.control.db_layer;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rkan.project.questlog.model.Quest;

@Database(entities = Quest.class, version = 1, exportSchema = false)
abstract class QuestRoomDatabase extends RoomDatabase {

    public abstract QuestDao questDao();

    private static volatile QuestRoomDatabase INSTANCE;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static QuestRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuestRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            QuestRoomDatabase.class,
                            "quest_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
