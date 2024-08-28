package com.example.rhizome.app.presentation.form;

import java.util.List;
import java.util.Optional;

import com.example.rhizome.app.domain.model.Review;
import com.example.rhizome.app.infra.entity.ReviewEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeReviewForm {
	/**
	 * レビューID
	 */
	private Integer idReview;
	/**
	 * ユーザーID
	 */
	private Integer idUser;
	/**
	 * レシピID
	 */
	private Integer idRecipe;
	/**
	 * レビュー本文
	 */
	private String review;
	/**
	 * 投稿日
	 */
	private String postDate;

	public static RecipeReviewForm convertFrom(Review review) {
		return new RecipeReviewForm(
				review.getIdReview(),
				review.getIdUser(),
				review.getIdRecipe(),
				review.getReview(),
				review.getPostDate());
	}
	
	public static RecipeReviewForm convertFrom(Optional<ReviewEntity>  reviewEntity) {
		
		ReviewEntity recipe = reviewEntity.orElseThrow(() -> new IllegalArgumentException("ReviewEntity is not present"));
		return new RecipeReviewForm(
				recipe.getIdReview(),
				recipe.getIdUser(),
				recipe.getIdRecipe(),
				recipe.getReview(),
				recipe.getPostDate());
	}

	/**
	 * business 層の Recipe クラスから画面用（presentation 層）の RecipeDetailUpdateConfirmForm に
	 * 変換します。（リスト版）
	 *
	 * @param recipeList business 層の Recipe インスタンスのリスト
	 * @return 画面用（presentation 層）の RecipeDetailUpdateConfirmForm インスタンスのリスト
	 */
	public static List<RecipeReviewForm> convertFrom(List<Review> reviewList) {
		return reviewList.stream().map(RecipeReviewForm::convertFrom).toList();
	}

	public static Review convertTo(RecipeReviewForm recipeReviewForm) {
		return new Review(
				recipeReviewForm.getIdReview(),
				recipeReviewForm.getIdUser(),
				recipeReviewForm.getIdRecipe(),
				recipeReviewForm.getReview(),
				recipeReviewForm.getPostDate());

	}

	public static boolean hasErrorParameter(RecipeReviewForm recipeReviewForm) {
		boolean errorFlag = !isNullOrBlank(recipeReviewForm.getReview());

		if (!isNullOrBlank(recipeReviewForm.getPostDate())) {
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
