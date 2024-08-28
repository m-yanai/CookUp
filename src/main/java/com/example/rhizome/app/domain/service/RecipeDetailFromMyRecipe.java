package com.example.rhizome.app.domain.service;

import java.util.Optional;

import com.example.rhizome.app.infra.entity.RecipeEntity;

public interface RecipeDetailFromMyRecipe {

	Optional<RecipeEntity>  searchByIdRecipe(Integer idRecipe);

}
