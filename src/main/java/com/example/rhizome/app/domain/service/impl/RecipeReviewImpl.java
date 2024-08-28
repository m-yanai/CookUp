package com.example.rhizome.app.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rhizome.app.domain.model.Review;
import com.example.rhizome.app.domain.service.RecipeReview;
import com.example.rhizome.app.infra.entity.ReviewEntity;
import com.example.rhizome.app.infra.repository.ReviewRepository;

@Service
@Transactional

public class RecipeReviewImpl implements RecipeReview{
	ReviewRepository reviewRepository;

	@Autowired
	public RecipeReviewImpl(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	/**
	 * recipe の情報を元に recipe table のレコードを更新する
	 *
	 * @param beforeRecipe 更新前のデータ
	 * @param updateRecipe 更新後のデータ
	 */
	public void insertRecipe(Review review) {

		reviewRepository.save(ReviewEntity.convertFrom(review));

	}
	
	
	public List<Review> selectAll() {
		return  ReviewEntity.createDepartmentList(reviewRepository.findAll());
	}
	
	public List<Review> searchByIdRecipe(Integer idRecipe) {
//		ReviewEntity reviewEntityResult =reviewEntity
//				.orElseThrow(() -> new IllegalArgumentException("ReviewEntity is not present"));
		return ReviewEntity.createReviewList(reviewRepository.findByIdRecipe(idRecipe));

	}
	
	


}
