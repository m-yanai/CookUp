package com.example.rhizome.app.presentation.form;

import java.util.List;
import java.util.Optional;

import com.example.rhizome.app.domain.model.Recipe;
import com.example.rhizome.app.infra.entity.RecipeEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyRecipeSearchForm {
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
	public static MyRecipeSearchForm convertFrom(Recipe recipe) {
		return new MyRecipeSearchForm(
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
	public static MyRecipeSearchForm convertFrom(Optional<RecipeEntity>  recipeEntity) {
		
		 RecipeEntity recipe = recipeEntity.orElseThrow(() -> new IllegalArgumentException("RecipeEntity is not present"));
		return new MyRecipeSearchForm(
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
	public static List<MyRecipeSearchForm> convertFrom(List<Recipe> recipeList) {
		return recipeList.stream().map(MyRecipeSearchForm::convertFrom).toList();
	}

	/**
	 * 画面用（presentation 層）の RecipeDetailDeleteForm クラスから business 層の Recipe クラスに
	 * 変換します。
	 *
	 * @param RecipeDetailDeleteForm 画面用（presentation 層）の RecipeDetailDeleteForm インスタンス
	 * @return business 層の Recipe インスタンス
	 */
	public static Recipe convertTo(MyRecipeSearchForm myRecipeSearchForm) {
		return new Recipe(
				myRecipeSearchForm.getIdRecipe(),
				myRecipeSearchForm.getIdUser(),
				myRecipeSearchForm.getIdGenre(),
				myRecipeSearchForm.getNmRecipe(),
				myRecipeSearchForm.getPicRecipe(),
				myRecipeSearchForm.getIngRecipe(),
				myRecipeSearchForm.getLevelRecipe(),
				myRecipeSearchForm.getHowCook(),
				myRecipeSearchForm.getCookTime(),
				myRecipeSearchForm.getUpdateDate(),
				myRecipeSearchForm.getDelFlg());

	}
}
