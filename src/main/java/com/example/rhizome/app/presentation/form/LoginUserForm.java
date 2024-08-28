package com.example.rhizome.app.presentation.form;


import java.util.List;

import com.example.rhizome.app.domain.model.LoginUser;
import com.example.rhizome.app.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserForm {
    /**
     * ユーザーID
     */
    private Integer idUser;
    /**
     **ユーザーアイコン
     */
    private String iconUser;
    /**
     * ユーザーネーム
     */
    private String nmUser;
    /**
     * メールアドレス
     */
    private String mailAddress;
    /**
     * パスワード
     */
    private String password;
    /**
     *削除フラグ 
     */    
    private Integer del_flg;


    /**
     * business 層の LoginUser クラスから画面用（presentation 層）の LoginUserForm に
     * 変換します。
     *
     * @param loginUser business 層の LoginUser インスタンス
     * @return クラスから画面用（presentation 層）の LoginUserForm
     */
    public static LoginUserForm convertFrom(LoginUser loginUser) {
        return new LoginUserForm(
                loginUser.getIdUser(),
                loginUser.getIconUser(),
                loginUser.getNmUser(),
                loginUser.getMailAddress(),
                loginUser.getPassword(),
                loginUser.getDel_flg()
        );
    }
    
    public static LoginUserForm convertFrom(User user) {
        return new LoginUserForm(
        		user.getIdUser(),
        		user.getIconUser(),
        		user.getNmUser(),
        		user.getMailAddress(),
        		user.getPassword(),
        		user.getDelFlg()
        		);
    }
    
    public static List<LoginUserForm> convertFrom(List<User> userList) {
        return userList.stream().map(LoginUserForm::convertFrom).toList();
    }

    /**
     * 画面用（presentation 層）の LoginUserForm クラスから business 層の LoginUser クラスに
     * 変換します。
     *
     * @param loginUserForm 画面用（presentation 層）の LoginUserForm インスタンス
     * @return business 層の LoginUser インスタンス
     */
    public static LoginUser convertTo(LoginUserForm loginUserForm) {
        return new LoginUser(
                loginUserForm.getIdUser(),
                loginUserForm.getIconUser(),
                loginUserForm.getNmUser(),
                loginUserForm.getMailAddress(),
                loginUserForm.getPassword(),
                loginUserForm.getDel_flg()
        );
    }

    /**
     * 画面から渡ってきた必須の値が仕様を満たしているか確認
     *
     * @param loginInfoForm 画面から渡ってきた mailAddress, password を格納しているインスタンス
     * @return true:仕様を満たしている , false:仕様を満たしていない
     */
    public static boolean checkParameter(LoginInfoForm loginInfoForm) {
        if (null == loginInfoForm.getMailAddress()) {
            return false;
        }

        if (loginInfoForm.getMailAddress().isBlank()) {
            return false;
        }

        if (null == loginInfoForm.getPassword()) {
            return false;
        }

        return !loginInfoForm.getPassword().isBlank();

    }

}
