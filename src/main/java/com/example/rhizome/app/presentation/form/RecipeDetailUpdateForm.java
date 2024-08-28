package com.example.rhizome.app.presentation.form;

import java.util.List;

import com.example.rhizome.app.domain.model.Recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 画面から渡ってきた更新内容をまとめ、再度画面に表示する為のクラス
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetailUpdateForm {
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
	public static RecipeDetailUpdateForm convertFrom(Recipe recipe) {
		return new RecipeDetailUpdateForm (
				recipe.getIdRecipe(),
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
	public static List<RecipeDetailUpdateForm> convertFrom(List<Recipe> recipeList) {
		return recipeList.stream().map(RecipeDetailUpdateForm::convertFrom).toList();
	}

	/**
	 * 画面用（presentation 層）の RecipeDetailUpdateForm クラスから business 層の Recipe クラスに
	 * 変換します。
	 *
	 * @param recipeDetailUpdateForm 画面用（presentation 層）の RecipeDetailUpdateForm インスタンス
	 * @return business 層の Recipe インスタンス
	 */
	public static Recipe convertTo(RecipeDetailUpdateForm recipeDetailUpdateForm) {
		return new Recipe(
				recipeDetailUpdateForm.getIdRecipe(),
				recipeDetailUpdateForm.getIdUser(),
				recipeDetailUpdateForm.getIdGenre(),
				recipeDetailUpdateForm.getNmRecipe(),
				recipeDetailUpdateForm.getNmPic(),
				recipeDetailUpdateForm.getIngRecipe(),
				recipeDetailUpdateForm.getLevelRecipe(),
				recipeDetailUpdateForm.getHowCook(),
				recipeDetailUpdateForm.getCookTime(),
				recipeDetailUpdateForm.getUpdateDate(),
				1);

	}
	public static RecipeDetailUpdateForm changeForm(MyRecipeSearchForm myRecipeSearchForm) {
		return new RecipeDetailUpdateForm(
				myRecipeSearchForm.getIdRecipe(),
				myRecipeSearchForm.getIdUser(),
				myRecipeSearchForm.getIdGenre(),
				myRecipeSearchForm.getNmRecipe(),
				myRecipeSearchForm.getPicRecipe(),
				null,
				myRecipeSearchForm.getIngRecipe(),
				myRecipeSearchForm.getLevelRecipe(),
				myRecipeSearchForm.getHowCook(),
				myRecipeSearchForm.getCookTime(),
				myRecipeSearchForm.getUpdateDate()
				);

	}
	

	/**
	 * 画面から渡ってきたパラメータのチェック
	 *
	 * @param insertRecipeForm 画面から渡ってきたパラメータ
	 * @return true: エラー無し　false:エラーあり（必須を満たしていない）
	 */
	public static boolean hasErrorParameter(RecipeDetailUpdateForm recipeDetailUpdateForm) {

		boolean errorFlag = !isNullOrBlank(recipeDetailUpdateForm.getNmRecipe());

		if (!isNullOrBlank(recipeDetailUpdateForm.getPicRecipe())) {
			errorFlag = true;
		}
		if (!isNullOrBlank(recipeDetailUpdateForm.getIngRecipe())) {
			errorFlag = true;
		}
		if (!isNullOrBlank(recipeDetailUpdateForm.getHowCook())) {
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
