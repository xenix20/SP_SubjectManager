package com.example.sp_subjectmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sp_subjectmanager.Data;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter adapter = null;
    ListView subject_list =null;


    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = null;

        try {
            db = dbHelper.openDatabase();

            Cursor subjects = db.rawQuery("SELECT * FROM Subjects", null);
            if(subjects != null) {
                while(subjects.moveToNext()) {
                    Data.Subjects.add(new Subject(subjects.getInt(0), subjects.getString(1)));
                }
            }
            subjects.close();

            Cursor sessions = db.rawQuery("SELECT * FROM Session", null);
            if(sessions != null) {
                while(sessions.moveToNext()) {
                    Subject subject = Data.Subjects.stream().filter(x->x.Id == sessions.getInt(1)).findFirst().get();
                    Data.Sessions.add(new Session(sessions.getInt(0), subject, sessions.getInt(2), sessions.getString(3)));
                }
            }
            subjects.close();


            Cursor Lessions = db.rawQuery("SELECT * FROM Lessions", null);
            if(Lessions != null) {
                while(Lessions.moveToNext()) {
                    Session session = Data.Sessions.stream().filter(x->x.Id == Lessions.getInt(1)).findFirst().get();
                    Data.Lessions.add(new Lession(Lessions.getInt(0), session, Lessions.getString(2),Lessions.getInt(3) == 1 ));
                }
            }
            Lessions.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        findViewById(R.id.add_subject_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, add_subject.class);
                startActivityForResult(intent, 1);

            }
        });

        findViewById(R.id.open_statistic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, statistic.class);
                startActivityForResult(intent, 1);

            }
        });
         subject_list = findViewById(R.id.subject_list);


        subject_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Data.SelectedSubject = Data.Subjects.get(position);

                Intent intent = new Intent(MainActivity.this, subject_activity.class);
                startActivityForResult(intent, 1);

            }
        });


         adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, Data.Subjects.stream().map(s -> String.format("%s (%d сек)", s.getName(), s.CalculateAllHours())).toArray(String[]::new));
        subject_list.setAdapter(adapter);


        ((TextView)findViewById(R.id.currentDate)).setText("%s %s".formatted(Data.getCurrentFormatedDate(), Data.getCurrentDayOfWeek() ));
    }


    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, Data.Subjects.stream().map(s -> String.format("%s (%d сек)", s.getName(), s.CalculateAllHours())).toArray(String[]::new));
        subject_list.setAdapter(adapter);

        ((TextView)findViewById(R.id.currentDate)).setText("%s %s".formatted(Data.getCurrentFormatedDate(), Data.getCurrentDayOfWeek() ));
    }
}