package com.example.se_attendance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Admin {
    private static String adminID = "admin";
    private static String adminPW = "admin";
    private double latitude;
    private double longitude;

    public static boolean isAdmin(String inputID, String inputPW){
        //로그인 성공 시
        if (inputID.equals(adminID)) {
            return inputPW.equals(adminPW);
        }
        //로그인 실패 시 (id 또는 password가 틀렸을 때)
        return false;
    }

    void setLocation(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }
}

