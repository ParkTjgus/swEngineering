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

public class DayActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<String> data = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Button btn_time;
    private Button btn_day;
    Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);

    //시간별 랭킹 화면으로 이동
    void clickTimeRank() {
        btn_time = findViewById(R.id.btn_time);
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DayActivity.this, TimeActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        btn_day = findViewById(R.id.btn_day);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        clickTimeRank();

        ListView list = findViewById(R.id.list);

        //배열 연결과정
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter);

        String token = getToken();
        getData(token);

    }
    String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        return token;
    }

    void getData(String token) {
        Call<RankDTO.RankResponse> call = service.dayrank();
        call.enqueue(new Callback<RankDTO.RankResponse>() {
            @Override
            public void onResponse(Call<RankDTO.RankResponse> call, Response<RankDTO.RankResponse> response) {
                if (response.isSuccessful()) {
                    List<RankDTO.RankResponse> rankList = (List<RankDTO.RankResponse>) response.body();
                    for (RankDTO.RankResponse rankItem : rankList) {
                        String memberName = rankItem.getMemberName().toString();
                        String memberId = rankItem.getMemberId().toString();
                        String recordTime = String.valueOf(rankItem.getRecordTime()).toString();

                        String dataItem = "Name: " + memberName + ", ID: " + memberId + ", Time: " + recordTime;
                        data.add(dataItem);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle unsuccessful response
                    String message = "Error: " + response.code();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RankDTO.RankResponse> call, Throwable t) {
                Toast.makeText(DayActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }
}