package com.example.rhizome.app.domain.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rhizome.app.domain.model.Recipe;
import com.example.rhizome.app.domain.service.RegisterRecipe;
import com.example.rhizome.app.infra.entity.RecipeEntity;
import com.example.rhizome.app.infra.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterRecipeImpl implements RegisterRecipe{

	private final RecipeRepository recipeRepository;
	
	public void registerRecipe(Recipe recipe) {
		System.out.println(recipe.getIdUser());
		System.out.println(recipe.getNmRecipe());
		System.out.println(recipe.getPicRecipe());
		System.out.println(recipe.getIdGenre());
		System.out.println(recipe.getLevelRecipe());
		System.out.println(recipe.getIngRecipe());
		System.out.println(recipe.getHowCook());
		System.out.println(recipe.getCookTime());
		recipeRepository.save(RecipeEntity.convertFrom(recipe));
    }
}
