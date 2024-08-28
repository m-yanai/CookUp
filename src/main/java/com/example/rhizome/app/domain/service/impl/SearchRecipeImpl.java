package com.example.rhizome.app.domain.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rhizome.app.domain.service.SearchRecipe;
import com.example.rhizome.app.infra.entity.RecipeEntity;
import com.example.rhizome.app.infra.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchRecipeImpl implements SearchRecipe {

	private final RecipeRepository recipeRepository;

	@Override
	public Optional<RecipeEntity> searchRecipeByIdRecipe(Integer idRecipe) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println(idRecipe);
		return recipeRepository.findByIdRecipe(idRecipe);
	}

}
