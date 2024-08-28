package com.example.rhizome.app.presentation.form;

import java.util.List;

import com.example.rhizome.app.domain.model.RecipeGenreUserInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeInfoForm {
	
	 /**
     * レシピID
     */
    private Integer idRecipe;
    
	/**
     * ユーザーID
     */
    private Integer idUser;

    /**
     * ジャンルID
     */
    private Integer idGenre;

    /**
     * レシピ名
     */
    private String nmRecipe;

    /**
     * レシピ画像
     */
    private String picRecipe;

    /**
     * レシピ材料
     */
    private String ingRecipe;

    /**
     * レシピ難易度
     */
    private Integer levelRecipe;
    
    /**
     * 調理手順
     */
    private String howCook;
    
    /**
     * 調理時間
     */
    private Integer cookTime;
    
    /**
     * 更新日
     */
    private String updateDate;
    
    /**
     * 削除フラグ
     */
    private Integer delFlg;
    
    /**
     * ジャンル名
     */
    private String nmGenre;
    
    /**
	 * ユーザーアイコン
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
	 * 削除フラグ
	 */
	private Integer userDelFlg;
	
	
	//RecipeGenreUserInfoをRecipeInfoForm
	public static RecipeInfoForm convertFrom(RecipeGenreUserInfo recipeGenreUserInfo) {
		return new RecipeInfoForm(
				recipeGenreUserInfo.getRecipe().getIdRecipe(),
				recipeGenreUserInfo.getRecipe().getIdUser(),
				recipeGenreUserInfo.getRecipe().getIdGenre(),
				recipeGenreUserInfo.getRecipe().getNmRecipe(),
				recipeGenreUserInfo.getRecipe().getPicRecipe(),
				recipeGenreUserInfo.getRecipe().getIngRecipe(),
				recipeGenreUserInfo.getRecipe().getLevelRecipe(),
				recipeGenreUserInfo.getRecipe().getHowCook(),
				recipeGenreUserInfo.getRecipe().getCookTime(),
				recipeGenreUserInfo.getRecipe().getUpdateDate(),
				recipeGenreUserInfo.getRecipe().getDelFlg(),
				recipeGenreUserInfo.getGenre().getNmGenre(),
				recipeGenreUserInfo.getUser().getIconUser(),
				recipeGenreUserInfo.getUser().getNmUser(),
				recipeGenreUserInfo.getUser().getMailAddress(),
				recipeGenreUserInfo.getUser().getPassword(),
				recipeGenreUserInfo.getUser().getDelFlg()
		);
	}
	
	//RecipeGenreUserInfoをRecipeInfoForm
	//リスト版
	 public static List<RecipeInfoForm> convertFrom(List<RecipeGenreUserInfo> recipeGenreUserInfoList) {
	        return recipeGenreUserInfoList.stream().map(RecipeInfoForm::convertFrom).toList();
	 }

	




}
