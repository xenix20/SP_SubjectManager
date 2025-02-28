package com.example.sp_subjectmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.res.AssetManager;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "db.db"; // имя вашей БД
    private static final String DB_PATH = "/data/data/com. example. sp_subjectmanager/databases/"; // путь для хранения на устройстве
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Здесь создавайте таблицы или выполняйте другие действия, если база данных пуста
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Если нужно обновить структуру БД, то делайте это здесь
    }

    // Метод для копирования базы данных из assets в нужную директорию
    public void copyDatabase() throws IOException {
        AssetManager assetManager = context.getAssets();
        File dbFile = new File(context.getApplicationInfo().dataDir + "/databases" + DB_NAME);

            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            InputStream inputStream = assetManager.open(DB_NAME);
            OutputStream outputStream = new FileOutputStream(dbFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
    }

    // Метод для получения доступа к базе данных
    public SQLiteDatabase openDatabase() throws IOException {
        File dbFile = new File(context.getApplicationInfo().dataDir + "/databases" + DB_NAME);
            copyDatabase();
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }
}
