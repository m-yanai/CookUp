package com.example.rhizome.app.presentation.controller;

import static com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.rhizome.app.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.LoginUserForm;

import jakarta.servlet.http.HttpSession;

/**
 * ログアウト処理のコントローラ
 */
@Controller
public class LogoutController {

    // ログ出力用インスタンス
    private final static Logger LOGGER = Logger.getLogger(LogoutController.class.getName());
    // エラーメッセージ用の定数フィールド
    private final static String SEVERE_ERROR_MESSAGE = "エラーが発生しました！";

    HttpSession httpSession;

    @Autowired
    public LogoutController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @ModelAttribute
    public LoginUserForm loginUserForm() {
        return new LoginUserForm();
    }

    /**
     * ログアウト処理
     *
     * @param model ビューにデータを渡すためのコンテナ
     * @return 遷移先の画面
     */
//    @PermissionCheck
//    @SessionCheck
    @RequestMapping(value = LOGOUT, method = RequestMethod.POST)
    public String doLogout(Model model) {
        try {
            // セッション情報の破棄
            httpSession.invalidate();

            // ログイン画面に遷移するための情報を設定
            model.addAttribute(PageReturnAttributeKeyword.LOGIN_INFO_FORM, loginUserForm());
            // login.html に遷移
            return TransitionTargetPageNameKeyword.LOGIN_HTML;

        } catch (Exception e) {

            // エラーログ出力
            LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE, e);
            // エラーページに遷移
            return TransitionTargetPageNameKeyword.ERROR_HTML;
        }

    }

}
