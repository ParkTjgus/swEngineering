package com.example.se_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        clickDayRank();
        clickTimeRank();
        clickBack();
    }

    //날짜별 랭킹 화면으로 이동
    void clickDayRank() {

        Button btn_day = findViewById(R.id.btn_day);
        btn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankActivity.this, DayActivity.class);
                startActivity(intent);
            }
        });
    }

    //시간별 랭킹 화면으로 이동
    void clickTimeRank() {

        Button btn_time = findViewById(R.id.btn_time);
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankActivity.this, TimeActivity.class);
                startActivity(intent);
            }
        });
    }
    void clickBack() {
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}