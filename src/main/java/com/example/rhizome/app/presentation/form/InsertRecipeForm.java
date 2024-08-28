package com.example.rhizome.app.presentation.form;

import java.util.List;

import com.example.rhizome.app.domain.model.Recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertRecipeForm {
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
	 * レシピ画像
	 */
	private String nmPic;
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
	 * business 層の Recipe クラスから画面用（presentation 層）の RecipeDetailUpdateForm に
	 * 変換します。
	 *
	 * @param recipe business 層の Recipe インスタンス
	 * @return 画面用（presentation 層）の RecipeDetailUpdateForm インスタンス
	 */
	public static InsertRecipeForm convertFrom(Recipe recipe) {
		return new InsertRecipeForm (
				recipe.getIdUser(),
				recipe.getIdGenre(),
				recipe.getNmRecipe(),
				null,
				recipe.getPicRecipe(),
				recipe.getIngRecipe(),
				recipe.getLevelRecipe(),
				recipe.getHowCook(),
				recipe.getCookTime(),
				recipe.getUpdateDate()
				);
	}
	


	/**
	 * business 層の Recipe クラスから画面用（presentation 層）の RecipeDetailUpdateForm に
	 * 変換します。（リスト版）
	 *
	 * @param recipeList business 層の Recipe インスタンスのリスト
	 * @return 画面用（presentation 層）の RecipeDetailUpdateForm インスタンスのリスト
	 */
	public static List<InsertRecipeForm> convertFrom(List<Recipe> recipeList) {
		return recipeList.stream().map(InsertRecipeForm::convertFrom).toList();
	}

	/**
	 * 画面用（presentation 層）の RecipeDetailUpdateForm クラスから business 層の Recipe クラスに
	 * 変換します。
	 *
	 * @param recipeDetailUpdateForm 画面用（presentation 層）の RecipeDetailUpdateForm インスタンス
	 * @return business 層の Recipe インスタンス
	 */
	public static Recipe convertTo(InsertRecipeForm insertRecipeForm) {
		return new Recipe(
				null,
				insertRecipeForm.getIdUser(),
				insertRecipeForm.getIdGenre(),
				insertRecipeForm.getNmRecipe(),
				insertRecipeForm.getNmPic(),
				insertRecipeForm.getIngRecipe(),
				insertRecipeForm.getLevelRecipe(),
				insertRecipeForm.getHowCook(),
				insertRecipeForm.getCookTime(),
				insertRecipeForm.getUpdateDate(),
				1);

	}

	/**
	 * 画面から渡ってきたパラメータのチェック
	 *
	 * @param insertRecipeForm 画面から渡ってきたパラメータ
	 * @return true: エラー無し　false:エラーあり（必須を満たしていない）
	 */
	public static boolean hasErrorParameter(InsertRecipeForm insertRecipeForm) {

		boolean errorFlag = !isNullOrBlank(insertRecipeForm.getNmRecipe());

		if (!isNullOrBlank(insertRecipeForm.getPicRecipe())) {
			errorFlag = true;
		}
		if (!isNullOrBlank(insertRecipeForm.getIngRecipe())) {
			errorFlag = true;
		}
		if (!isNullOrBlank(insertRecipeForm.getHowCook())) {
			errorFlag = true;
		}

		return errorFlag;
	}

	/**
	 * 仮引数を null or 空 判定する
	 *
	 * @param str 判定対象の文字列
	 * @return true:null or 空 false:null or 空 では無い
	 */
	private static boolean isNullOrBlank(String str) {
		if (null == str) {
			return true;
		}
		return str.isBlank();
	}
}
