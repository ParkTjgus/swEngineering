package com.example.se_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.dto.RankDTO;
import com.example.se_app.dto.RecordDTO;
import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class TimeActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<String> data = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);
    private Button btn_time;
    private Button btn_day;
    private Button btn_back;

    //날짜별 랭킹 화면으로 이동
    void clickDayRank() {

        btn_day = findViewById(R.id.btn_day);
        btn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimeActivity.this, DayActivity.class);
                startActivity(intent);
            }
        });
    }

    void clickBack() {
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        clickDayRank();
        clickBack();
        btn_time = findViewById(R.id.btn_time);
        ListView list = findViewById(R.id.list);

        //배열 연결과정
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter);

        getData();
    }

    void getData() {
        Call<List<RankDTO.RankResponse>> call = service.timerank();
        call.enqueue(new Callback<List<RankDTO.RankResponse>>() {
            @Override
            public void onResponse(Call<List<RankDTO.RankResponse>> call, Response<List<RankDTO.RankResponse>> response) {
                if (response.isSuccessful()) {
                    List<RankDTO.RankResponse> rankList = response.body();

                    for (RankDTO.RankResponse rankItem : rankList) {
                        String memberName = rankItem.getMemberName();
                        String memberId = rankItem.getMemberId();
                        String totalRecordTime = String.valueOf(rankItem.getTotalRecordTime());

                        String dataItem = "Name: " + memberName + ", ID: " + memberId + ", Time: " + totalRecordTime;
                        data.add(dataItem);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    String message = "Error: " + response.code();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RankDTO.RankResponse>> call, Throwable t) {
                Toast.makeText(TimeActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }


}