package com.example.rhizome.app.domain.service;

import java.util.List;

import com.example.rhizome.app.domain.model.Review;

public interface RecipeReview {

	void insertRecipe(Review review);
	
	List<Review> selectAll();
	
	List<Review>  searchByIdRecipe(Integer idRecipe);
}
