package com.example.se_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.dto.CalendarDTO;
import com.example.se_app.dto.NoticeDTO;
import com.example.se_app.dto.ResponseUtilDTO;
import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);
    private String select_date = "";

    /* 화면 시작 시 실행 함수 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //SharedPreferences에서 토큰 가져오기
        String token = getToken();

        //공지사항 조회
        TextView tv_notice = findViewById(R.id.tv_notice);
        getNotice(token, tv_notice);

        //캘린더에서 날짜를 선택하지 않을 시, 오늘 날짜 출력
        TextView tv_day = findViewById(R.id.tv_day);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일");
        Date date = new Date();
        tv_day.setText(formatter.format(date));

        /* 캘린더에서 날짜 선택 시 실행 구문 */
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                //선택한 날짜 저장
                select_date = year + "-" + (month + 1) + "-" + day;

                //선택한 날짜 출력
                tv_day.setText(year + "년 " + (month + 1) + "월 " + day + "일");

                //목표시간 조회
                TextView tv_goal = findViewById(R.id.tv_goal);
                getGoalTime(token, (month + 1), tv_goal);

                //출석 기록 조회
                TextView tv_time = findViewById(R.id.tv_time);
                getDayTime(token, tv_time);
            }
        });

        //하단 홈 버튼 클릭 시 실행
        clickBtnHome();

        //하단 마이페이지 버튼 클릭 시 실행
        clickBtnMypage();

    }

    /* SharedPreferences에서 토큰을 가져오는 함수 */
    String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Log.d("TAG", "토큰 리턴 성공");
        return token;
    }

    /* 시간(초)를 시간(시:분:초)로 변환하는 함수 */
    String secondToTime(int time) {
        int hour = time / 3600;
        int minute = (time % 3600) / 60;
        int second = (time % 3600) % 60;

        return String.valueOf(hour) + ":" + String.valueOf(minute) + ":" + String.valueOf(second);
    }

    /* 공지사항을 조회하는 함수 */
    void getNotice(String token, TextView tv_notice) {
        Call<NoticeDTO.NoticeResponse> call = service.notice("Bearer " + token);
        call.enqueue(new Callback<NoticeDTO.NoticeResponse>() {
            //서버와 통신 성공
            @Override
            public void onResponse(Call<NoticeDTO.NoticeResponse> call, Response<NoticeDTO.NoticeResponse> response) {
                //응답 성공(200): 공지사항이 있는 경우
                if (response.isSuccessful()) {
                    //공지사항 출력
                    tv_notice.setText(response.body().getNoticeContent().toString());

                    Log.d("TAG", "공지사항 조회 성공");
                }
                //응답 실패(404): 공지사항이 없는 경우
                else if (response.code() == 404) {
                    //에러 메세지를 토스트 메세지로 출력
                    String errorMessage = "";
                    if (response.errorBody() != null) {
                        try {
                            // 에러 응답을 DTO로 변환
                            ResponseUtilDTO.MessageResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ResponseUtilDTO.MessageResponse.class);
                            errorMessage = errorResponse.getMessage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //공지사항 칸에 메세지 출력
                    tv_notice.setText(errorMessage);

                    Log.d("TAG", "공지사항 조회 실패: 공지사항 없음");
                }
            }

            //서버와 통신 실패
            @Override
            public void onFailure(Call<NoticeDTO.NoticeResponse> call, Throwable t) {
                Toast.makeText(CalendarActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    /* 캘린더에서 선택한 달의 목표시간을 조회하는 함수 */
    void getGoalTime(String token, int month, TextView tv_goal) {
        Call<CalendarDTO.GoalResponse> call = service.goal("Bearer " + token, String.valueOf(month));
        call.enqueue(new Callback<CalendarDTO.GoalResponse>() {
            //서버와 통신 성공
            @Override
            public void onResponse(Call<CalendarDTO.GoalResponse> call, Response<CalendarDTO.GoalResponse> response) {
                //응답 성공(200): 정상적으로 접근한 경우
                if (response.isSuccessful()) {
                    //시간 변환(초 -> 시:분:초)
                    String time = secondToTime(response.body().getStudyGoal());
                    //목표시간 설정
                    tv_goal.setText("목표시간 : " + time);

                    Log.d("TAG", "목표시간 조회 성공");
                }
            }

            //서버와 통신 실패
            @Override
            public void onFailure(Call<CalendarDTO.GoalResponse> call, Throwable t) {
                Toast.makeText(CalendarActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    /* 캘린더에서 선택한 날짜의 출석 기록을 조회하는 함수 */
    void getDayTime(String token, TextView tv_time) {
        Call<CalendarDTO.TimeResponse> call = service.time("Bearer " + token, select_date);
        call.enqueue(new Callback<CalendarDTO.TimeResponse>() {
            //서버와 통신 성공
            @Override
            public void onResponse(Call<CalendarDTO.TimeResponse> call, Response<CalendarDTO.TimeResponse> response) {
                //응답 성공(200): 정상적으로 접근한 경우
                if (response.isSuccessful()) {
                    //시간 변환(초 -> 시:분:초)
                    String time = secondToTime(response.body().getRecordTime());
                    //출석 기록 설정
                    tv_time.setText(time);

                    Log.d("TAG", "출석 기록 조회 성공");
                }
                //응답 실패(404): 해당 날짜에 기록이 없는 경우
                else if (response.body() == null) {
                    //출석 기록 설정
                    tv_time.setText("00:00:00");

                    Log.d("TAG", "출석 기록 조회 실패: 출석 기록 없음");
                }
            }

            //서버와 통신 실패
            @Override
            public void onFailure(Call<CalendarDTO.TimeResponse> call, Throwable t) {
                Toast.makeText(CalendarActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    /* 하단 홈 버튼 클릭 시 실행 함수 */
    void clickBtnHome() {
        Button btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MainActivity로 이동
                Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /* 하단 마이페이지 버튼 클릭 시 실행 함수 */
    void clickBtnMypage() {
        Button btn_mypage = findViewById(R.id.btn_mypage);
        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MypageActivity로 이동
                Intent intent = new Intent(CalendarActivity.this, MypageActivity.class);
                startActivity(intent);
            }
        });
    }
}