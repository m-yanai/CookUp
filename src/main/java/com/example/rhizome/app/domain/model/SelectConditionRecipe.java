package com.example.rhizome.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**
 * レシピの検索条件という概念を表現するモデルクラスです
 */
public class SelectConditionRecipe {
	/**
     * レシピ名
     */
    private final String nmRecipe;
    /**
     * レシピ材料
     */
    private final String ingRecipe;
    /**
     * ジャンルID
     */
    private final Integer idGenre;
    /**
     * レシピ作成者(ユーザーネーム）
     */
    private final String nmUser;
}
