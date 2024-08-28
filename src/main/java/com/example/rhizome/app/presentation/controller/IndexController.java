package com.example.rhizome.app.presentation.controller;

import static com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.rhizome.app.domain.service.impl.LoginImpl;
import com.example.rhizome.app.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.rhizome.app.presentation.form.LoginUserForm;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    HttpSession httpSession;

    @Autowired
    public IndexController(HttpSession httpSession, LoginImpl loginImpl) {
        this.httpSession = httpSession;
    }

    @ModelAttribute
    public LoginUserForm loginUserForm() {
        return new LoginUserForm();
    }

    /**
     * ログイン画面表示処理
     *
     * @param model ビューにデータを渡すためのコンテナ
     * @return 遷移先の画面
     */
    @RequestMapping(value = {INDEX_BLANK, INDEX_SLASH, INDEX_LOGIN})
    public String index(Model model) {
        // セッション情報を破棄
        if (httpSession != null) {
            httpSession.invalidate();
        }

        // 空のFormをSessionに用意
        model.addAttribute(PageReturnAttributeKeyword.LOGIN_INFO_FORM, loginUserForm());

        // ログイン画面に遷移
        return LOGIN_HTML;
    }
}
