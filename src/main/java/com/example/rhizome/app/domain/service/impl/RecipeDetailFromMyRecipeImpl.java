package com.example.rhizome.app.domain.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rhizome.app.domain.service.RecipeDetailFromMyRecipe;
import com.example.rhizome.app.infra.entity.RecipeEntity;
import com.example.rhizome.app.infra.repository.RecipeRepository;

@Service
@Transactional


public class RecipeDetailFromMyRecipeImpl implements RecipeDetailFromMyRecipe{
	
	
	RecipeRepository recipeRepository;
	
	  @Autowired
	    public RecipeDetailFromMyRecipeImpl(RecipeRepository recipeRepository) {
	        this.recipeRepository = recipeRepository;
	    }
	
	 public Optional<RecipeEntity> searchByIdRecipe(Integer idRecipe){
	    	 return recipeRepository.findById(idRecipe);
	    }

}
