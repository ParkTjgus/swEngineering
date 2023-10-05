package com.example.se_app.service;

import com.example.se_app.dto.LoginDTO;
import com.example.se_app.dto.MypageDTO;
import com.example.se_app.dto.RegisterDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface Service {

    //로그인
    @POST("/user/login")
    Call<LoginDTO.LoginResponse> login(@Body LoginDTO.LoginRequest loginRequest);

    //회원가입
    @POST("/user/signup")
    Call<RegisterDTO.RegisterResponse> register(@Header("Authorization") String token, @Body RegisterDTO.RegisterRequest registerRequest);

    //마이페이지 정보 조회
    @GET("/user/mypage")
    Call<MypageDTO.MypageResponse> mypage(@Header("Authorization") String token);




}
