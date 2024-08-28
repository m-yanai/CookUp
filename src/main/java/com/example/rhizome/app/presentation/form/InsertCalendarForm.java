package com.example.rhizome.app.presentation.form;

import java.util.List;

import com.example.rhizome.app.domain.model.Calendar;
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
public class InsertCalendarForm {
	/**
	 * ユーザーID
	 */
	private Integer idUser;
	/**
	 * レシピID
	 */
	private Integer idRecipe;
	/**
	 * レシピ名
	 */
	private String nmRecipe;
	/**
	 * 年・月・日
	 */
	private String updateYear;
	
	private String updateMonth;
	
	private String updateDay;
	
	 
	/**
	 * business 層の Recipe クラスから画面用（presentation 層）の RecipeDetailUpdateForm に
	 * 変換します。
	 *
	 * @param recipe business 層の Recipe インスタンス
	 * @return 画面用（presentation 層）の RecipeDetailUpdateForm インスタンス
	 */
	public static InsertCalendarForm convertFrom(Recipe recipe) {
		return new InsertCalendarForm(
				recipe.getIdUser(),
				recipe.getIdRecipe(),
				recipe.getNmRecipe(),
				null,
				null,
				null
				);
				
	}

	/**
	 * business 層の Recipe クラスから画面用（presentation 層）の RecipeDetailUpdateForm に
	 * 変換します。（リスト版）
	 *
	 * @param recipeList business 層の Recipe インスタンスのリスト
	 * @return 画面用（presentation 層）の RecipeDetailUpdateForm インスタンスのリスト
	 */
	public static List<InsertCalendarForm> convertFrom(List<Recipe> recipeList) {
		return recipeList.stream().map(InsertCalendarForm::convertFrom).toList();
	}

	/**
	 * 画面用（presentation 層）の RecipeDetailUpdateForm クラスから business 層の Recipe クラスに
	 * 変換します。
	 *
	 * @param recipeDetailUpdateForm 画面用（presentation 層）の RecipeDetailUpdateForm インスタンス
	 * @return business 層の Recipe インスタンス
	 */
	public static Calendar convertTo(InsertCalendarForm InsertCalendarForm) {
		return new Calendar(
				null,
				InsertCalendarForm.getIdUser(),
				InsertCalendarForm.getIdRecipe()	
				);

	}
	
	//MyRecipeSearchForm から　InsertCalendarForm　に変換
	public static InsertCalendarForm convertFromMyRecipe(MyRecipeSearchForm myRecipeSearchForm) {
		return new InsertCalendarForm(
			
				myRecipeSearchForm.getIdUser(),
				myRecipeSearchForm.getIdRecipe(),
				myRecipeSearchForm.getNmRecipe(),
				null,
				null,
				null
				);
	}
	
	//MyRecipeSearchForm から　InsertCalendarForm　に変換
		public static MyRecipeSearchForm convertToMyRecipe(InsertCalendarForm InsertCalendarForm) {
			return new MyRecipeSearchForm(
					InsertCalendarForm.getIdUser(),
					InsertCalendarForm.getIdRecipe(),
					null,
					InsertCalendarForm.getNmRecipe(),
					null,
					null,
					null,
					null,
					null,
					null,
					null
					);
		}

	/**
	 * 画面から渡ってきたパラメータのチェック
	 *
	 * @param recipeDetailUpdateForm 画面から渡ってきたパラメータ
	 * @return true: エラー無し　false:エラーあり（必須を満たしていない）
	 
	public static boolean hasErrorParameter(InsertCalendarForm InsertCalendarForm) {

		boolean errorFlag = !isNullOrBlank(InsertCalendarForm.getNmRecipe());

		if (!isNullOrBlank(InsertCalendarForm.getPicRecipe())) {
			errorFlag = true;
		}
		if (!isNullOrBlank(InsertCalendarForm.getIngRecipe())) {
			errorFlag = true;
		}
		if (!isNullOrBlank(InsertCalendarForm.getHowCook())) {
			errorFlag = true;
		}

		return errorFlag;
	}
*/

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
