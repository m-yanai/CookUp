package com.example.rhizome.app.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rhizome.app.domain.model.Recipe;
import com.example.rhizome.app.domain.service.RecipeUpdate;
import com.example.rhizome.app.infra.entity.RecipeEntity;
import com.example.rhizome.app.infra.repository.RecipeRepository;

@Service
@Transactional

public class RecipeUpdateImpl implements RecipeUpdate {

	RecipeRepository recipeRepository;

	@Autowired
	public RecipeUpdateImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	/**
	 * recipe の情報を元に recipe table のレコードを更新する
	 *
	 * @param beforeRecipe 更新前のデータ
	 * @param updateRecipe 更新後のデータ
	 */
	public void updateRecipe(Recipe beforeRecipe, Recipe updateRecipe) {
		recipeRepository.save(RecipeEntity.convertFrom(mergeRecipe(beforeRecipe, updateRecipe)));
	}

	/**
	 * 更新前のデータと更新対象のデータをマージする
	 *
	 * @param beforeRecipe 更新前のデータ
	 * @param updateRecipe 更新対象のデータ
	 * @return マージしたデータ
	 */
	private Recipe mergeRecipe(Recipe beforeRecipe, Recipe updateRecipe) {
		// 更新対象のデータのジャンルが null の場合は、更新前のジャンルを使用する
		// 更新対象のデータのレシピ難易度が null の場合は、更新前のレシピ難易度を使用する
		// 更新対象のデータの調理時間が null の場合は、更新前の調理時間を使用する
		return new Recipe(
				
				updateRecipe.getIdRecipe(),
				updateRecipe.getIdUser(),
				updateRecipe.getIdGenre() == null ? beforeRecipe.getIdGenre() : updateRecipe.getIdGenre(),
				updateRecipe.getNmRecipe(),
				updateRecipe.getPicRecipe(),
				updateRecipe.getIngRecipe(),
				updateRecipe.getLevelRecipe() == null ? beforeRecipe.getLevelRecipe() : updateRecipe.getLevelRecipe(),
				updateRecipe.getHowCook(),
				updateRecipe.getCookTime() == null ? beforeRecipe.getCookTime() : updateRecipe.getCookTime(),
				updateRecipe.getUpdateDate(),
				updateRecipe.getDelFlg());

	}

	

}
