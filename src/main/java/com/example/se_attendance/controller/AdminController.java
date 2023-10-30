package com.example.se_attendance.controller;

import com.example.se_attendance.Admin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class AdminController {

    @GetMapping("/admin/login")
    public Object loginAdmin(@RequestBody HashMap<String, String> loginInput) {

        return "message : "
                + ((Admin.isAdmin(loginInput.get("adminID"),
                loginInput.get("adminPassword"))) ?
                "로그인에 성공하였습니다." : "아이디 또는 비밀번호가 일치하지 않습니다." );

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("message", (Admin.isAdmin(loginInput.get("adminID"),
//                loginInput.get("adminPassword"))) ?
//                "로그인에 성공하였습니다." : "아이디 또는 비밀번호가 일치하지 않습니다." );

//        return jsonObject;

    }
}
