package com.example.rhizome.app.presentation.form;

import java.util.List;

import com.example.rhizome.app.domain.model.RecipeGenreUserInfo;
import com.example.rhizome.app.infra.entity.RecipeGenreUserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeGenreUserForm {
	
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
	
	
	//RecipeGenreUserInfoをRecipeGenreUserForm
	public static RecipeGenreUserForm convertFrom(RecipeGenreUserInfo recipeGenreUserInfo) {
		return new RecipeGenreUserForm(
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
	
	//RecipeGenreUserInfoをRecipeGenreUserForm
	//リスト版
	 public static List<RecipeGenreUserForm> convertFrom(List<RecipeGenreUserInfo> recipeGenreUserInfoList) {
	        return recipeGenreUserInfoList.stream().map(RecipeGenreUserForm::convertFrom).toList();
	 }
	 
	 //RecipeGenreUserEntityをRecipeGenreUserForm
	 public static RecipeGenreUserForm convertEtoFFrom(RecipeGenreUserEntity recipeGenreUserEntity) {
		 return new RecipeGenreUserForm(
				 recipeGenreUserEntity.getIdRecipe(),
					recipeGenreUserEntity.getIdUser(),
					recipeGenreUserEntity.getIdGenre(),
					recipeGenreUserEntity.getNmRecipe(),
					recipeGenreUserEntity.getPicRecipe(),
					recipeGenreUserEntity.getIngRecipe(),
					recipeGenreUserEntity.getLevelRecipe(),
					recipeGenreUserEntity.getHowCook(),
					recipeGenreUserEntity.getCookTime(),
					recipeGenreUserEntity.getUpdateDate(),
					recipeGenreUserEntity.getDelFlg(),
					recipeGenreUserEntity.getGenre().getNmGenre(),
					recipeGenreUserEntity.getUser().getIconUser(),
					recipeGenreUserEntity.getUser().getNmUser(),
					recipeGenreUserEntity.getUser().getMailAddress(),
					recipeGenreUserEntity.getUser().getPassword(),
					recipeGenreUserEntity.getUser().getDelFlg()
				 );
	 }
	 
	//RecipeGenreUserEntityをRecipeGenreUserForm
	//リスト版
	 public static List<RecipeGenreUserForm> convertEtoFFrom(List<RecipeGenreUserEntity> recipeGenreUserEntity) {
	        return recipeGenreUserEntity.stream().map(RecipeGenreUserForm::convertEtoFFrom).toList();
	 }

	




}
