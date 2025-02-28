package com.example.sp_subjectmanager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class statistic extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout w = findViewById(R.id.statistic_list);


        for (Subject s:
             Data.Subjects) {

            StringBuilder history = new StringBuilder();

            int index = 0;

            for (Session session:
                 s.getAllSessions().toList()) {
                index++;
                history.append("сессия %d, %s: %d сек\n".formatted(index, session.Date, session.EllapsedTime));
            }
            w.addView(createStatisticCard(s.Name, history.toString(), s.CalculateAllHours()));
        }

        findViewById(R.id.statistic_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public LinearLayout createStatisticCard(String title, String statistic, int total_hours) {
        Context context = getApplicationContext();
        LinearLayout linearLayout = new LinearLayout(context);

        linearLayout.setPadding(30, 30, 30, 30);
        Drawable background = context.getResources().getDrawable(R.drawable.statistic_card);
        linearLayout.setBackground(background);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView1 = new TextView(context);
        textView1.setText(title);
        textView1.setTextSize(20);
        textView1.setTypeface(null, android.graphics.Typeface.BOLD);
        linearLayout.addView(textView1);

        TextView textView2 = new TextView(context);
        textView2.setText(statistic);
        linearLayout.addView(textView2);

        TextView textView3 = new TextView(context);
        textView3.setText("Всего: "+total_hours+" секунд");
        textView3.setTextAlignment(TextView.TEXT_ALIGNMENT_VIEW_END);
        linearLayout.addView(textView3);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        p.setMargins(0,0,0,30);
        linearLayout.setLayoutParams(p);

        return linearLayout;
    }

}