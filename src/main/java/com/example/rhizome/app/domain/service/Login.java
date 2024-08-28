package com.example.rhizome.app.domain.service;

import java.util.List;

import com.example.rhizome.app.domain.model.LoginInfo;
import com.example.rhizome.app.domain.model.LoginUser;
import com.example.rhizome.app.domain.model.User;

public interface Login {
    LoginUser doLogin(LoginInfo loginInfo);

    boolean canLogin(LoginInfo loginInfo);
    
    List<User> selectAll();
}
