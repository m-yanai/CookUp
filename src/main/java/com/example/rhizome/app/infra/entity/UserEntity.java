
package com.example.rhizome.app.infra.entity;


import java.util.List;

import com.example.rhizome.app.domain.model.LoginUser;
import com.example.rhizome.app.domain.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * review_table テーブルの Entity
 *
 * @author IT-College
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
public class UserEntity {

	/**
	 * ユーザーID
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user", nullable = false)
	private Integer idUser;
	/**
	 * ユーザーアイコン
	 */
	@Column(name = "icon_user", nullable = false)
	private String iconUser;
	/**
	 * ユーザーネーム
	 */
	@Column(name = "nm_user", nullable = false)
	private String nmUser;
	/**
	 * メールアドレス
	 */
	@Column(name = "mail_address", nullable = false)
	private String mailAddress;
	/**
	 * パスワード
	 */
	@Column(name = "password", nullable = false)
	private String password;
	/**
	 * 削除フラグ
	 */
	@Column(name = "del_flg", nullable = false)
	private Integer delFlg;


	/**
	 * User（BusinessLogic 依存）を UserEntity（DataAccess層依存）に変換します。
	 *
	 * @param user User Table の中身
	 * @return UserEntity のインスタンス（１レコード分）
	 */
	public static UserEntity convertFrom(User user) {
		// 値を既に持ったインスタンスの生成に、コンストラクタを使用するとこうなります。
		return new UserEntity(
				user.getIdUser(),
				user.getIconUser(),
				user.getNmUser(),
				user.getMailAddress(),
				user.getPassword(),
				user.getDelFlg()
				);
	}

	/**
	 * UserEntity（DataAccess層依存）から User (BusinessLogic層依存型）に変換します。
	 *
	 * @param userEntity User Table のインスタンス
	 * @return user インスタンス
	 */
	public static User createUser(UserEntity userEntity) {
		// 値を既に持ったインスタンスの生成に、コンストラクタを使用するとこうなります。
		return new User(
				userEntity.getIdUser(),
				userEntity.getIconUser(),
				userEntity.getNmUser(),
				userEntity.getMailAddress(),
				userEntity.getPassword(),
				userEntity.getDelFlg()
				);
	}

	/**
	 * UserEntity から User に変換します。（リスト版）
	 */
	public static List<User> createUserList(List<UserEntity> userEntityList) {

		return userEntityList.stream().map(UserEntity::createUser).toList();
	}

	/**
	 * UserEntity から LoginUser (BusinessLogic層依存型）情報を作成します。
	 *
	 * @return loginUser （ログイン者の情報） インスタンス
	 */
	public LoginUser createLoginUser() {
		// 値を既に持ったインスタンスの生成に、コンストラクタを使用するとこうなります。
		return new LoginUser(
				getIdUser(),
				getIconUser(),
				getNmUser(),
				getMailAddress(),
				getPassword(),
				getDelFlg()
				);
	}

}
