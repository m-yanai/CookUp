package com.example.rhizome.app.presentation.form;

import java.util.List;

import com.example.rhizome.app.domain.model.Recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetailDeleteForm {
	/**
	 * ユーザーID
	 */
	private Integer idUser;
	/**
	 * レシピID
	 */
	private Integer idRecipe;
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
	 * 調理難易度
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
	 * business 層の Recipe クラスから画面用（presentation 層）の RecipeDetailDeleteForm に
	 * 変換します。
	 *
	 * @param recipe business 層の Recipe インスタンス
	 * @return 画面用（presentation 層）の RecipeDetailDeleteForm インスタンス
	 */
	public static RecipeDetailDeleteForm convertFrom(Recipe recipe) {
		return new RecipeDetailDeleteForm(
				recipe.getIdUser(),
				recipe.getIdRecipe(),
				recipe.getIdGenre(),
				recipe.getNmRecipe(),
				recipe.getPicRecipe(),
				recipe.getIngRecipe(),
				recipe.getLevelRecipe(),
				recipe.getHowCook(),
				recipe.getCookTime(),
				recipe.getUpdateDate(),
				recipe.getDelFlg());
	}

	/**
	 * business 層の Recipe クラスから画面用（presentation 層）の RecipeDetailDeleteForm に
	 * 変換します。（リスト版）
	 *
	 * @param recipeList business 層の Recipe インスタンスのリスト
	 * @return 画面用（presentation 層）の RecipeDetailDeleteForm インスタンスのリスト
	 */
	public static List<RecipeDetailDeleteForm> convertFrom(List<Recipe> recipeList) {
		return recipeList.stream().map(RecipeDetailDeleteForm::convertFrom).toList();
	}

	/**
	 * 画面用（presentation 層）の RecipeDetailDeleteForm クラスから business 層の Recipe クラスに
	 * 変換します。
	 *
	 * @param RecipeDetailDeleteForm 画面用（presentation 層）の RecipeDetailDeleteForm インスタンス
	 * @return business 層の Recipe インスタンス
	 */
	public static Recipe convertTo(RecipeDetailDeleteForm recipeDetailDeleteForm) {
		return new Recipe(
				recipeDetailDeleteForm.getIdUser(),
				recipeDetailDeleteForm.getIdRecipe(),
				recipeDetailDeleteForm.getIdGenre(),
				recipeDetailDeleteForm.getNmRecipe(),
				recipeDetailDeleteForm.getPicRecipe(),
				recipeDetailDeleteForm.getIngRecipe(),
				recipeDetailDeleteForm.getLevelRecipe(),
				recipeDetailDeleteForm.getHowCook(),
				recipeDetailDeleteForm.getCookTime(),
				recipeDetailDeleteForm.getUpdateDate(),
				recipeDetailDeleteForm.getDelFlg());

	}

	
}
