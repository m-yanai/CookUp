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
public class RecipeDetailUpdateConfirmForm {
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
	 * business 層の Recipe クラスから画面用（presentation 層）の RecipeDetailUpdateConfirmForm に
	 * 変換します。
	 *
	 * @param recipe business 層の Recipe インスタンス
	 * @return 画面用（presentation 層）の RecipeDetailUpdateConfirmForm インスタンス
	 */
	public static RecipeDetailUpdateConfirmForm convertFrom(Recipe recipe) {
		return new RecipeDetailUpdateConfirmForm(
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
	 * business 層の Recipe クラスから画面用（presentation 層）の RecipeDetailUpdateConfirmForm に
	 * 変換します。（リスト版）
	 *
	 * @param recipeList business 層の Recipe インスタンスのリスト
	 * @return 画面用（presentation 層）の RecipeDetailUpdateConfirmForm インスタンスのリスト
	 */
	public static List<RecipeDetailUpdateConfirmForm> convertFrom(List<Recipe> recipeList) {
		return recipeList.stream().map(RecipeDetailUpdateConfirmForm::convertFrom).toList();
	}

	/**
	 * 画面用（presentation 層）の RecipeDetailUpdateConfirmForm クラスから business 層の Recipe クラスに
	 * 変換します。
	 *
	 * @param recipeDetailUpdateConfirmForm 画面用（presentation 層）の RecipeDetailUpdateConfirmForm インスタンス
	 * @return business 層の Recipe インスタンス
	 */
	public static Recipe convertTo(RecipeDetailUpdateConfirmForm recipeDetailUpdateConfirmForm) {
		return new Recipe(
				recipeDetailUpdateConfirmForm.getIdUser(),
				recipeDetailUpdateConfirmForm.getIdRecipe(),
				recipeDetailUpdateConfirmForm.getIdGenre(),
				recipeDetailUpdateConfirmForm.getNmRecipe(),
				recipeDetailUpdateConfirmForm.getPicRecipe(),
				recipeDetailUpdateConfirmForm.getIngRecipe(),
				recipeDetailUpdateConfirmForm.getLevelRecipe(),
				recipeDetailUpdateConfirmForm.getHowCook(),
				recipeDetailUpdateConfirmForm.getCookTime(),
				recipeDetailUpdateConfirmForm.getUpdateDate(),
				recipeDetailUpdateConfirmForm.getDelFlg());

	}

	/**
	 * 画面から渡ってきたパラメータのチェック
	 *
	 * @param recipeDetailUpdateConfirmForm 画面から渡ってきたパラメータ
	 * @return true: エラー無し　false:エラーあり（必須を満たしていない）
	 */
	public static boolean hasErrorParameter(RecipeDetailUpdateConfirmForm recipeDetailUpdateConfirmForm) {

		boolean errorFlag = !isNullOrBlank(recipeDetailUpdateConfirmForm.getNmRecipe());

		if (!isNullOrBlank(recipeDetailUpdateConfirmForm.getPicRecipe())) {
			errorFlag = true;
		}
		if (!isNullOrBlank(recipeDetailUpdateConfirmForm.getIngRecipe())) {
			errorFlag = true;
		}
		if (!isNullOrBlank(recipeDetailUpdateConfirmForm.getHowCook())) {
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
