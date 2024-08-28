package com.example.rhizome.app.domain.service;

import com.example.rhizome.app.domain.model.Recipe;

public interface RecipeUpdate {
	void updateRecipe(Recipe beforeRecipe, Recipe updateRecipe);

}
