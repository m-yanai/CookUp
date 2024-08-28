package com.example.rhizome.app.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rhizome.app.domain.model.LoginInfo;
import com.example.rhizome.app.domain.model.LoginUser;
import com.example.rhizome.app.domain.model.User;
import com.example.rhizome.app.domain.service.Login;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;

/**
 * ログイン系の機能を集約させるサービスクラスです。
 */
@Service
@Transactional
public class LoginImpl implements Login {

    LoginRepository loginRepository;

    @Autowired
    public LoginImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }


    /**
     * メールアドレス、パスワードでログイン判定を行う
     *
     * @param loginInfo ログインを試みるアカウント
     * @return loginUser のインスタンス
     */
    public LoginUser doLogin(LoginInfo loginInfo) {
        // ログイン情報を元にログインユーザーを取得
        return loginRepository
                .findByMailAddressAndPassword(loginInfo.getMailAddress(), loginInfo.getPassword())
                .orElse(new UserEntity())
                .createLoginUser();

    }

    /**
     * メールアドレス、パスワードでログイン判定を行う
     *
     * @param loginInfo ログインを試みるアカウント
     * @return loginUser のインスタンス
     */
    public boolean canLogin(LoginInfo loginInfo) {
        // ログイン情報を元にログインユーザーを取得
        return loginRepository
                .findByMailAddressAndPassword(loginInfo.getMailAddress(), loginInfo.getPassword())
                .isPresent();

    }


	
	public List<User> selectAll() {
		return UserEntity.createUserList(loginRepository.findAll());
	}
}
