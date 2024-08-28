package com.example.rhizome.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ユーザーを表現するモデルクラスです
 */
@Getter
@AllArgsConstructor
public  class User {
	/**
	 * ユーザーID
	 */
	private final Integer idUser;
	/**
	 * ユーザーアイコン
	 */
	private final String iconUser;
	/**
	 * ユーザーネーム
	 */
	private final String nmUser;
	/**
	 * メールアドレス
	 */
	private final String mailAddress;
	/**
	 * パスワード
	 */
	private final String password;
	/**
	 * 削除フラグ
	 */
	private final Integer delFlg;

}
