package com.example.se_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.dto.RegisterDTO;
import com.example.se_app.dto.ResponseUtilDTO;
import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);
    private String state;

    /* 화면 시작 시 실행 함수 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText et_studentId = findViewById(R.id.et_studentId); //학번(아이디)
        EditText et_password = findViewById(R.id.et_password); //비밀번호
        EditText et_name = findViewById(R.id.et_name); //이름
        EditText et_major = findViewById(R.id.et_major); //학과
        EditText et_birth = findViewById(R.id.et_birth); //생년월일(yyMMdd)
        Spinner sp_state = findViewById(R.id.sp_state); //상태(재학/휴학/졸업)

        /* 상태 리스트의 아이템 클릭 시 실행 구문 */
        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //아이템이 선택된 경우
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = adapterView.getItemAtPosition(i).toString();
            }

            //아무것도 선택되지 않은 경우
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                state = "";
            }
        });

        //확인 버튼 클릭 시 실행
        clickBtnRegister(et_studentId, et_password, et_name, et_major, et_birth);

        //취소 버튼 클릭 시 실행
        clickBtnCancel();

    }

    /* 확인 버튼 클릭 시 실행 함수 */
    void clickBtnRegister(EditText et_studentId, EditText et_password, EditText et_name, EditText et_major, EditText et_birth) {
        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterDTO.RegisterRequest registerRequest = new RegisterDTO.RegisterRequest(
                        et_studentId.getText().toString(), et_password.getText().toString(),
                        et_name.getText().toString(), et_major.getText().toString(),
                        state, et_birth.getText().toString());

                Call<RegisterDTO.RegisterResponse> call = service.register(registerRequest);
                call.enqueue(new Callback<RegisterDTO.RegisterResponse>() {
                    //서버와 통신 성공
                    @Override
                    public void onResponse(Call<RegisterDTO.RegisterResponse> call, Response<RegisterDTO.RegisterResponse> response) {
                        //응답 성공(200): 모든 항목을 입력했을 경우
                        if (response.isSuccessful()) {
                            //body의 회원가입 성공 메세지를 저장
                            String message = response.body().getMessage().toString();

                            //회원가입 성공 메세지를 토스트 메세지로 띄움
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                            //LoginActivity로 이동
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);

                            Log.d("TAG", "회원가입 성공");
                        }
                        //응답 실패(404): 아이디가 중복일 경우
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

                            //에러 메세지를 토스트 메세지로 출력
                            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

                            Log.d("TAG", "회원가입 실패: 아이디 중복");
                        }
                    }

                    //서버와 통신 실패
                    @Override
                    public void onFailure(Call<RegisterDTO.RegisterResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "서버 통신 실패: " + t.getMessage());
                    }
                });
            }
        });
    }

    /* 취소 버튼 클릭 시 실행 함수 */
    void clickBtnCancel() {
        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 취소 메세지를 토스트 메세지로 출력
                Toast.makeText(RegisterActivity.this, "회원가입을 취소합니다.", Toast.LENGTH_SHORT).show();

                //LoginActivity로 이동(뒤로가기)
                RegisterActivity.super.onBackPressed();
            }
        });
    }

}