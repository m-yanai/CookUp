package com.example.rhizome.app.presentation.form;

import com.example.rhizome.app.domain.model.SelectConditionRecipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectRecipeForm {
	 /**
     * レシピ名
     */
    private String nmRecipe;
    /**
     * レシピ材料
     */
    private String ingRecipe;
    /**
     * ジャンルID
     */
    private Integer idGenre;
    /**
     * レシピ作成者(ユーザーネーム）
     */
    private String nmUser;
    
    /**
     * business 層の SelectConditionRecipe クラスから画面用（presentation 層）の SelectRecipeForm に
     * 変換します。
     *
     * @param selectConditionRecipe business 層の SelectConditionRecipe インスタンス
     * @return 画面用（presentation 層）の SearchConditionEmployeeForm インスタンス
     */
    public static SelectRecipeForm convertFrom(SelectConditionRecipe selectConditionRecipe) {
        return new SelectRecipeForm(
        		selectConditionRecipe.getNmRecipe(),
        		selectConditionRecipe.getIngRecipe(),
        		selectConditionRecipe.getIdGenre(),
        		selectConditionRecipe.getNmUser()
        );
    }
    /**
     * 画面用（presentation 層）の SelectRecipeForm クラスから business 層の  SelectConditionRecipee クラスに
     * 変換します。
     *
     * @param SelectRecipeForm 画面用（presentation 層）の SelectRecipeForm インスタンス
     * @return business 層の  SelectConditionRecipe インスタンス
     */
    public static SelectConditionRecipe convertTo(SelectRecipeForm selectRecipeForm) {
    	 return new SelectConditionRecipe(
    		selectRecipeForm.getNmRecipe(),
    		selectRecipeForm.getIngRecipe(),
    		selectRecipeForm.getIdGenre(),
    		selectRecipeForm.getNmUser()
        );
    }
    
    //ソートに使うよ
//    /**
//     * business 層の EmployeeInfo クラスから画面用（presentation 層）の EmployeeInfoForm に
//     * 変換します。（リスト版）
//     *
//     * @param employeeInfoList business 層の EmployeeInfo インスタンスのリスト
//     * @return クラスから画面用（presentation 層）の EmployeeInfoForm インスタンスのリスト
//     */
//    public static List<Recipe> convertFrom(List<Recipe> employeeInfoList) {
//        return employeeInfoList.stream().map(EmployeeInfoForm::convertFrom).toList();
//    }
}
