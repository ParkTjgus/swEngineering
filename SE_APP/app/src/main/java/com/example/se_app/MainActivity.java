package com.example.se_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se_app.dto.RecordDTO;
import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private SharedPreferences sharedPreferences;    //SharedPreferences에서 토큰 가져오기
    private TextView tv_time;
    private int recordTime;
    private Button btn_start;
    private Button btn_stop;

    Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);

    /* SharedPreferences에서 토큰을 가져오는 함수 */
    String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        return token;
    }

    /*위도, 경도 구하기*/
    Location getLocation() {
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
            return location;
        }
        return null;
    }

    private final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude(); // 위도
            double latitude = location.getLatitude(); // 경도
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    //당일 기록 가져오기
    void getTodayRecord() {
        Log.d(TAG, "당일기록가져오기");
        String token = getToken();
        Call<RecordDTO.TodayRecord> call = service.todayrecord("Bearer " + token);
        call.enqueue(new Callback<RecordDTO.TodayRecord>() {
            //서버 통신 성공
            @Override
            public void onResponse(Call<RecordDTO.TodayRecord> call, Response<RecordDTO.TodayRecord> response) {
                //응답 성공(200)
                if (response.isSuccessful()) {
                    //당일 기록 저장
                    recordTime = response.body().setRecordTimeToday();
                }
            }
            //서버 통신 실패
            @Override
            public void onFailure(Call<RecordDTO.TodayRecord> call, Throwable t) {
                Toast.makeText(MainActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    //기록하기(출석하기)
    void startRecord() {
        String token = getToken();
        double setUserLatitude = getLocation().getLongitude();
        double setUserLongitude = getLocation().getLatitude();
        RecordDTO.StartRecord startRecord = new RecordDTO.StartRecord(setUserLatitude, setUserLongitude);
        Call<RecordDTO.StartRecord> call = service.startrecord("Bearer " + token, startRecord);
        call.enqueue(new Callback<RecordDTO.StartRecord>() {
            @Override
            public void onResponse(Call<RecordDTO.StartRecord> record, Response<RecordDTO.StartRecord> response) {
                if (response.isSuccessful()) {
                    // 응답 성공(200)
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                } else {
                    // 응답 실패(403)
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecordDTO.StartRecord> record, Throwable t) {
                // 서버와 통신이 실패
                Toast.makeText(MainActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    //기록중단
    void stopRecord() {
        String token = getToken();
        double userLatitude = getLocation().getLongitude();
        double userLongitude = getLocation().getLatitude();

        RecordDTO.StopRecord stopRecord = new RecordDTO.StopRecord(recordTime, userLatitude, userLongitude);

        Call<RecordDTO.StopRecord> call = service.stoprecord("Bearer " + token, stopRecord);
        call.enqueue(new Callback<RecordDTO.StopRecord>() {
            @Override
            public void onResponse(Call<RecordDTO.StopRecord> record, Response<RecordDTO.StopRecord> response) {
                if (response.isSuccessful()) {
                    // 응답 성공(200)
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                } else {
                    // 응답 실패(403)
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RecordDTO.StopRecord> record, Throwable t) {
                // 서버와 통신이 실패
                Toast.makeText(MainActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    //위치 보내기
    void Location() {
        Log.d(TAG, "위치보내기");
        String token = getToken();
        double memberLatitude = getLocation().getLatitude(); //경도
        double memberLongitude = getLocation().getLongitude(); //위도
        RecordDTO.Location location = new RecordDTO.Location(recordTime, memberLatitude, memberLongitude);

        Call<RecordDTO.Location> call = service.location("Bearer " + token, location);
        call.enqueue(new Callback<RecordDTO.Location>() {
            //서버 통신 성공
            @Override
            public void onResponse(Call<RecordDTO.Location> call, Response<RecordDTO.Location> response) {
                if (response.isSuccessful()) {
                    // 응답 성공(200)
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                } else {
                    // 응답 실패(403)
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                }
            }
            //서버 통신 실패
            @Override
            public void onFailure(Call<RecordDTO.Location> call, Throwable t) {
                Toast.makeText(MainActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    // 타이머 구현
    private boolean isRunning = false;
    private Thread timeThread = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        tv_time = findViewById(R.id.tv_time);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        btn_start.setVisibility(View.VISIBLE);
        btn_stop.setVisibility(View.INVISIBLE);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "기록시작");
                if (!isRunning) {
                    isRunning = true;
                    btn_start.setVisibility(View.INVISIBLE);
                    btn_stop.setVisibility(View.VISIBLE);
                    getTodayRecord();
                    Location();
                    startRecord();
                    timeThread = new Thread(new timeThread());
                    timeThread.start();
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "기록중지");
                if (isRunning) {
                    isRunning = false;
                    btn_stop.setVisibility(View.INVISIBLE);
                    btn_start.setVisibility(View.VISIBLE);
                    Location();
                    stopRecord();
                    //record가 없ㅂㅂㅂ으면 setText("")
                    tv_time.setText("");
                    timeThread.interrupt();
                }
            }
        });
        Button btn_mypage = findViewById(R.id.btn_mypage);
        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //마이페이지 화면으로 이동
                Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                startActivity(intent);
            }
        });

        Button btn_calendar = findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //캘린더 화면으로 이동
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        Button btn_rank = findViewById(R.id.btn_rank);
        btn_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //랭킹 화면으로 이동
                Intent intent = new Intent(MainActivity.this, RankActivity.class);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 / 100) / 360;
            recordTime = msg.arg1;

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d", hour, min, sec);
            tv_time.setText(result);
        }
    };
    public class timeThread implements Runnable {
        @Override
        public void run() {
            getTodayRecord();
            int i = recordTime;
            while (!Thread.currentThread().isInterrupted()) {
                Message msg = new Message();
                msg.arg1 = i++;
                handler.sendMessage(msg);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}