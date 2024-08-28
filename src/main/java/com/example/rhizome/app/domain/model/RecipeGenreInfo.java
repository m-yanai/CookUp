package com.example.rhizome.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * レシピとジャンルを表現するモデルクラス
 */
@Getter
@AllArgsConstructor
public class RecipeGenreInfo {
	
		/*
		 * レシピ
		 */
		private final Recipe recipe;

		
		/**
		 * ジャンル
		 */
		private final Genre genre;
}
