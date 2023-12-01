package com.example.se_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.dto.EditDTO;
import com.example.se_app.dto.MypageDTO;
import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);
    private String state;

    /* 화면 시작 시 실행 함수 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        EditText et_edit_password = findViewById(R.id.et_edit_password); //비밀번호
        EditText et_edit_name = findViewById(R.id.et_edit_name); //이름
        EditText et_edit_major = findViewById(R.id.et_edit_major); //학과
        EditText et_edit_birth = findViewById(R.id.et_edit_birth); //생년월일
        Spinner sp_edit_state = findViewById(R.id.sp_edit_state); //상태(재학/휴학/졸업)

        /* 상태 리스트의 아이템 클릭 시 실행 구문 */
        sp_edit_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //아이템이 선택된 경우
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = adapterView.getItemAtPosition(i).toString();
            }

            //아무것도 선택되지 않은 경우
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //SharedPreferences에서 토큰 가져오기
        String token = getToken();

        //사용자 정보 가져오기
        getUserData(token, et_edit_name, et_edit_major, et_edit_birth, sp_edit_state);

        //확인 버튼 클릭 시 실행
        clickBtnOk(token, et_edit_password, et_edit_name, et_edit_major, et_edit_birth);

        //취소 버튼 클릭 시 실행 구문
        clickBtnNo();
    }

    /* SharedPreferences에서 토큰을 가져오는 함수 */
    String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Log.d("TAG", "토큰 리턴 성공");
        return token;
    }

    /* 사용자의 정보를 가져오는 함수 */
    void getUserData(String token, EditText et_edit_name, EditText et_edit_major, EditText et_edit_birth, Spinner sp_edit_state) {
        Call<MypageDTO.MypageResponse> call = service.mypage("Bearer " + token);
        call.enqueue(new Callback<MypageDTO.MypageResponse>() {
            //서버와 통신 성공
            @Override
            public void onResponse(Call<MypageDTO.MypageResponse> call, Response<MypageDTO.MypageResponse> response) {
                //응답 성공(200): 데이터베이스에서 회원 정보를 제대로 읽어왔을 때
                if (response.isSuccessful()) {
                    //정보 출력
                    et_edit_name.setText(response.body().getMemberName().toString());
                    et_edit_major.setText(response.body().getMemberMajor().toString());
                    et_edit_birth.setText(response.body().getMemberBirth().toString());
                    state = response.body().getMemberState().toString();
                    int stateIndex = getIndex(sp_edit_state, state);
                    sp_edit_state.setSelection(stateIndex);

                    Log.d("TAG", "정보 출력 성공");
                }
            }

            //서버와 통신 실패
            @Override
            public void onFailure(Call<MypageDTO.MypageResponse> call, Throwable t) {
                Toast.makeText(EditActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    /* 확인 버튼 클릭 시 실행 함수 */
    void clickBtnOk(String token, EditText et_edit_password, EditText et_edit_name, EditText et_edit_major, EditText et_edit_birth) {

        Button btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDTO.EditRequest editRequest = new EditDTO.EditRequest(
                        et_edit_password.getText().toString(),
                        et_edit_name.getText().toString(), et_edit_major.getText().toString(),
                        state, et_edit_birth.getText().toString());

                Call<EditDTO.EditResponse> call = service.edit("Bearer " + token, editRequest);
                call.enqueue(new Callback<EditDTO.EditResponse>() {
                    //서버와 통신 성공
                    @Override
                    public void onResponse(Call<EditDTO.EditResponse> call, Response<EditDTO.EditResponse> response) {
                        //응답 성공(200): 데이터베이스에서 회원 정보를 제대로 읽어왔을 때
                        if (response.isSuccessful()) {
                            //body의 성공 메세지를 저장
                            String message = response.body().getMessage();

                            //성공 메세지를 토스트 메세지로 띄움
                            Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();

                            //MypageActivity로 이동
                            Intent intent = new Intent(EditActivity.this, MypageActivity.class);
                            startActivity(intent);

                            Log.d("TAG", "정보 수정 성공");
                        }
                    }

                    //서버와 통신 실패
                    @Override
                    public void onFailure(Call<EditDTO.EditResponse> call, Throwable t) {
                        Toast.makeText(EditActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "서버 통신 실패: " + t.getMessage());
                    }
                });
            }
        });
    }

    /* 취소 버튼 클릭 시 실행 함수 */
    void clickBtnNo() {
        Button btn_no = findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MypageActivity로 이동(뒤로가기)
                Toast.makeText(EditActivity.this, "정보 수정을 취소합니다.", Toast.LENGTH_SHORT).show();
                EditActivity.super.onBackPressed();
            }
        });
    }

    /* state 초기 값의 번호를 찾아주는 함수 */
    private int getIndex(Spinner sp, String item) {
        for (int i = 0; i < sp.getCount(); i++) {
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(item)) {
                return i;
            }
        }
        return 0;
    }

}