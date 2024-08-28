package com.example.rhizome.app.domain.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rhizome.app.domain.model.Recipe;
import com.example.rhizome.app.domain.service.RecipeDelete;
import com.example.rhizome.app.infra.entity.RecipeEntity;
import com.example.rhizome.app.infra.repository.RecipeRepository;


@Service
@Transactional

public class RecipeDeleteImpl implements RecipeDelete{

	RecipeRepository recipeRepository;
	
	@Autowired
    public RecipeDeleteImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
	
	/**
     * recipe の情報を元に recipe_table のレコードを更新する
     *
     * @param recipe 更新のデータ
     */
    public void deleteRecipe(Recipe recipe) {
    	Integer id = recipe.getIdRecipe();
        Optional<RecipeEntity> optionalRecipe = recipeRepository.findByIdRecipe(id);
        optionalRecipe.ifPresent(selectRecipe -> {
            selectRecipe.setDelFlg(0); // 削除フラグを設定
            recipeRepository.save(selectRecipe); // レシピの保存（削除フラグの更新）
        });
    }
	
//	/**
//     * employee の情報を元に Recipe のレコードを更新する
//     *
//     * @param beforeRecipe 更新前のデータ
//     * @param deleteRecipe 更新後のデータ
//     */
//    public void deleteRecipe(Recipe beforeRecipe, Recipe deleteRecipe) {
//
//    	recipeRepository.save(RecipeEntity.convertFrom(mergeRecipe(beforeRecipe, deleteRecipe)));
//
//    }
//    
//    /**
//     * 削除前のデータと更新対象のデータをマージする
//     *
//     * @param beforeRecipe 更新前のデータ
//     * @param deleteRecipe 更新対象のデータ
//     * @return マージしたデータ
//     */
//    private Recipe mergeRecipe(Recipe beforeRecipe, Recipe deleteRecipe) {
//        
//        return new Recipe(
//        		deleteRecipe.getIdUser(),
//        		deleteRecipe.getIdRecipe(),
//        		deleteRecipe.getIdGenre(),
//        		deleteRecipe.getNmRecipe(),
//        		deleteRecipe.getPicRecipe(),
//        		deleteRecipe.getIngRecipe(),
//        		deleteRecipe.getLevelRecipe(),
//        		deleteRecipe.getHowCook(),
//        		deleteRecipe.getCookTime(),
//        		deleteRecipe.getUpdateDate(),
//        		deleteRecipe.getDelFlg()
//        		
//        );
//
//    }
}
