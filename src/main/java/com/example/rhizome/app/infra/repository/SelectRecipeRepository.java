package com.example.rhizome.app.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rhizome.app.infra.entity.RecipeGenreUserEntity;

@Repository
public interface SelectRecipeRepository extends JpaRepository<RecipeGenreUserEntity, Integer> {
	Optional<RecipeGenreUserEntity> findByNmRecipe(String nmRecipe);
	
	Optional<RecipeGenreUserEntity> findByIngRecipe(String ingRecipe);
	
	Optional<RecipeGenreUserEntity> findByIdGenre(Integer id);
	
	

}
