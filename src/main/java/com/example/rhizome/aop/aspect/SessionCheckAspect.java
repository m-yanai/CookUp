package com.example.rhizome.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.exception.NoSessionException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


/**
 * セッションチェック用のAOP
 */
@Aspect
@Component
public class SessionCheckAspect {

    HttpServletRequest request;

    @Autowired
    public SessionCheckAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Pointcut("@annotation(com.example.rhizome.aop.aspect.SessionCheck)")
    public void sessionCheckMethod() {
    }

    /**
     * セッションチェック
     */
    @Before("sessionCheckMethod()")
    public void checkSession() {
        // セッションの取得
        HttpSession session = request.getSession(false);

        // セッションが取得できない場合はエラー画面に遷移
        if (session == null || session.getAttribute(SessionKeyword.LOGIN_USER) == null) {
            throw new NoSessionException("不正なログインです");
        }

    }

}
