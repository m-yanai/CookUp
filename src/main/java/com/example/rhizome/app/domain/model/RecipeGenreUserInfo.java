package com.example.rhizome.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * レシピとジャンルとユーザーを表現するモデルクラス
 * 検索結果
 */
@Getter
@AllArgsConstructor
public class RecipeGenreUserInfo {
	
	/*
	 * レシピ
	 */
	private final Recipe recipe;

	
	/**
	 * ジャンル
	 */
	private final Genre genre;
	
	/*
	 * ユーザー
	 */
	private final User user;

}
