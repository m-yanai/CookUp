package com.example.rhizome.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * レシピを表現するモデルクラス
 */
@Getter
@AllArgsConstructor
public class Recipe {

    /**
     * レシピID
     */
    private final Integer idRecipe;
    
	/**
     * ユーザーID
     */
    private final Integer idUser;

    /**
     * ジャンルID
     */
    private final Integer idGenre;

    /**
     * レシピ名
     */
    private final String nmRecipe;

    /**
     * レシピ画像
     */
    private final String picRecipe;

    /**
     * レシピ材料
     */
    private final String ingRecipe;

    /**
     * レシピ難易度
     */
    private final Integer levelRecipe;
    
    /**
     * 調理手順
     */
    private final String howCook;
    
    /**
     * 調理時間
     */
    private final Integer cookTime;
    
    /**
     * 更新日
     */
    private final String updateDate;
    
    /**
     * 削除フラグ
     */
    private final Integer delFlg;

}
