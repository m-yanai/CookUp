package com.example.rhizome.aop.Handler;

import static com.example.rhizome.app.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.LoginInfoForm;
import com.example.rhizome.app.presentation.form.MessageForm;
import com.example.rhizome.exception.NoSessionException;

/**
 * 例外処理用のコントローラ
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

    private final String SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION = "不正な遷移です。";
    private final String SEVERE_ERROR_MESSAGE_BAD_REQUEST = "不正なリクエストです。";

    /**
     * セッションが切れている場合の例外処理
     *
     * @param model ビューにデータを渡すためのコンテナ
     * @return 遷移先の画面
     */
    @ExceptionHandler(NoSessionException.class)
    public String handleNoSessionException(Model model, Exception e) {

        model.addAttribute(MESSAGE_ERROR, new MessageForm(SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION));
        model.addAttribute(LOGIN_INFO_FORM, new LoginInfoForm());

        e.printStackTrace();

        // エラーログ出力
        LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION, e);

        return TransitionTargetPageNameKeyword.LOGIN_HTML;
    }


    /**
     * リクエストメソッドが不正な場合の例外処理
     *
     * @param model ビューにデータを渡すためのコンテナ
     * @return 遷移先の画面
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleBadRequestException(Model model, Exception e) {
        // エラーメッセージやその他の情報をモデルに追加する
        model.addAttribute("error", SEVERE_ERROR_MESSAGE_BAD_REQUEST);
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());

        e.printStackTrace();

        // エラーログ出力
        LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION, e);

        // カスタムエラーページにリダイレクト
        return TransitionTargetPageNameKeyword.ERROR_HTML;
    }

    /**
     * その他の例外処理
     *
     * @param model ビューにデータを渡すためのコンテナ
     * @return 遷移先の画面
     */
    @ExceptionHandler(Exception.class)
    public String AllException(Model model, Exception e) {
        // エラーメッセージやその他の情報をモデルに追加する
        model.addAttribute("error", SEVERE_ERROR_MESSAGE_BAD_REQUEST);

        e.printStackTrace();

        LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION, e);

        // カスタムエラーページにリダイレクト
        return TransitionTargetPageNameKeyword.ERROR_HTML;
    }
}
