package com.example.rhizome.app.domain.service;

import java.util.List;

import com.example.rhizome.app.domain.model.Recipe;
import com.example.rhizome.app.infra.entity.RecipeGenreEntity;

public interface  SelectRecipe {
	List<Recipe> selectAll();

	List<RecipeGenreEntity> getRecommendedRecipes(int recommendCount);

	List<RecipeGenreEntity> selectMyRecipes(int i,int delFlg);

	Integer countRecipesByIdUser(int userId);
	
	Integer countRecipesByIdUserAndDelFlg(int userId, int delFlg);
	
}
