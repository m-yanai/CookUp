package com.example.rhizome.app.domain.service;

import java.util.List;

import com.example.rhizome.app.domain.model.RecipeGenreUserInfo;
import com.example.rhizome.app.domain.model.SelectConditionRecipe;

public interface SelectRecipeGenreUser {
	List<RecipeGenreUserInfo> selectAll();
	
	List<RecipeGenreUserInfo> searchBy(SelectConditionRecipe selectConditionRecipe);

}
