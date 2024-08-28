package com.example.rhizome.app.presentation.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ログインを試みる人を表現するモデルクラスです
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoForm {
    /**
     * メールアドレス
     */
    private String mailAddress;
    /**
     * パスワード
     */
    private String password;
}
