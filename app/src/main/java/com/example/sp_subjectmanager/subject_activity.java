package com.example.sp_subjectmanager;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class subject_activity extends AppCompatActivity {

    CustomAdapter adapter = null;
    Session currentSession = null;
    Button startStopButton = null;
    private Handler handler = new Handler();
    private long timeInMillis = 0;

    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                timeInMillis++;
                updateTimeText();
                handler.postDelayed(this, 1000); // Обновление каждую секунду
            }
        }
    };



    private boolean isRunning = false;

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subject);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ((Button)findViewById(R.id.subject_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((Button)findViewById(R.id.add_new_lession)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_lession_name = ((EditText)findViewById(R.id.new_lession_name)).getText().toString();

                List<Session> all_subject_sessions = Data.Sessions.stream().filter(x->x.Subject == Data.SelectedSubject).toList();
                Session last_session = all_subject_sessions.get(all_subject_sessions.size() -1 );

                Lession new_lession = new Lession(999, last_session, new_lession_name, false);

                Data.Lessions.add(new_lession);

                updateLessionList();
            }
        });

        startStopButton = ((Button)findViewById(R.id.session_timer));
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    stopTimer();
                } else {
                    startTimer();
                }
            }
        });

        ((TextView)findViewById(R.id.label_selected_subject)).setText(Data.SelectedSubject.Name);

        ListView lession_list = findViewById(R.id.lession_list);

        Session[] sessions = Data.Sessions.stream().filter(x->x.Subject == Data.SelectedSubject).toArray(Session[]::new);
        Lession[] scoped_lessions = Data.Lessions.stream().filter(x -> Arrays.asList(sessions).contains(x.Session)).toArray(Lession[]::new);

        adapter = new CustomAdapter(this, Arrays.stream(scoped_lessions).toList());


        ///остановился на том, что в предмете отображаются все уроки из всех сессий. даольше сделать добавление уроков в предмет.
        lession_list.setAdapter(adapter);

        //session
        currentSession = Data.SelectedSubject.getLastSession();

        updateSessionLabel();


        ((TextView)findViewById(R.id.currentDate_1)).setText("%s %s".formatted(Data.getCurrentFormatedDate(), Data.getCurrentDayOfWeek() ));

    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private void updateSessionLabel() {
        if(currentSession == null)
            Data.Sessions.add(new Session(0, Data.SelectedSubject, 0,Data.getCurrentFormatedDate()));
        currentSession = Data.SelectedSubject.getLastSession();
        ((TextView)findViewById(R.id.session_number)).setText("Сессия %d".formatted(currentSession.getAssocialIndexOfSession()));
    }

    private void startTimer() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            updateSessionLabel();
        }

        startStopButton.setText("Стоп");
        isRunning = true;
        handler.post(updateTimeRunnable); // Запуск обновления времени
    }

    private void stopTimer() {
        Data.Sessions.add(new Session(1,  Data.SelectedSubject, Math.toIntExact(timeInMillis), Data.getCurrentFormatedDate()));

        timeInMillis = 0;

        startStopButton.setText("Старт");
        isRunning = false;
        handler.removeCallbacks(updateTimeRunnable); // Остановка обновления времени
    }

    private void updateTimeText() {
        int minutes = (int) (timeInMillis / 60);
        int seconds = (int) (timeInMillis % 60);
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        ((TextView)findViewById(R.id.session_ticker)).setText(timeFormatted);
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private void updateLessionList() {
        Session[] sessions = Data.Sessions.stream().filter(x->x.Subject == Data.SelectedSubject).toArray(Session[]::new);
        List<Lession> scoped_lessions = Data.Lessions.stream().filter(x -> Arrays.asList(sessions).contains(x.Session)).toList();

        adapter.updateLessions(scoped_lessions);
    }
}