package com.example.rhizome.app.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rhizome.app.domain.model.RecipeGenreUserInfo;
import com.example.rhizome.app.domain.model.SelectConditionRecipe;
import com.example.rhizome.app.domain.service.SelectRecipeGenreUser;
import com.example.rhizome.app.infra.entity.RecipeGenreUserEntity;
import com.example.rhizome.app.infra.repository.RecipeGenreUserRepository;

@Service
@Transactional
public class SelectRecipeGenreUserImpl implements SelectRecipeGenreUser {

	RecipeGenreUserRepository recipeGenreUserRepository;
	
	@Autowired
    public SelectRecipeGenreUserImpl(RecipeGenreUserRepository recipeGenreUserRepository) {
        this.recipeGenreUserRepository = recipeGenreUserRepository;
    }

	@Override
	public List<RecipeGenreUserInfo> selectAll() {
		return RecipeGenreUserEntity.createRecipeGenreUserInfoList(recipeGenreUserRepository.findAll());
	}

	@Override
	public List<RecipeGenreUserInfo> searchBy(SelectConditionRecipe selectConditionRecipe) {
		return RecipeGenreUserEntity
				.createRecipeGenreUserInfoList(
						recipeGenreUserRepository.findAll(Specification
								.where(RecipeGenreUserRepository.nmRecipeEqual(selectConditionRecipe.getNmRecipe()))
								.and(RecipeGenreUserRepository.ingRecipeEqual(selectConditionRecipe.getIngRecipe()))
								.and(RecipeGenreUserRepository.idGenreEqual(selectConditionRecipe.getIdGenre()))
								.and(RecipeGenreUserRepository.nmUserEqual(selectConditionRecipe.getNmUser()))
								.and(RecipeGenreUserRepository.DeleteFlgIsOne())
								)
						);
	}
}
