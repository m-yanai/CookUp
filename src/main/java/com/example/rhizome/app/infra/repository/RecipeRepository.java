package com.example.rhizome.app.infra.repository;


import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.rhizome.app.infra.entity.RecipeEntity;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Integer>, JpaSpecificationExecutor<RecipeEntity> {

	static Specification<RecipeEntity> idRecipeEqual(Integer idRecipe) {
		return idRecipe == null ? null
				: (Specification<RecipeEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
						.equal(root.get("idRecipe"), idRecipe);
	}

	static Specification<RecipeEntity> idUserEqual(Integer idUser) {
		return idUser == null ? null
				: (Specification<RecipeEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
						.equal(root.get("idUser"), idUser);
	}

	static Specification<RecipeEntity> idGenreEqual(Integer idGenre) {
		return idGenre == null ? null
				: (Specification<RecipeEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
						.equal(root.get("idGenre"), idGenre);
	}

	static Specification<RecipeEntity> nmRecipeEqual(String nmRecipe) {
		return (nmRecipe == null || nmRecipe.isEmpty()) ? null
				: (Specification<RecipeEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
						.like(root.get("nmRecipe"), nmRecipe);
	}

	static Specification<RecipeEntity> picRecipeEqual(String picRecipe) {
		return (picRecipe == null || picRecipe.isEmpty()) ? null
				: (Specification<RecipeEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
						.like(root.get("picRecipe"), picRecipe);
	}

	static Specification<RecipeEntity> levelRecipeEqual(Integer levelRecipe) {
		return levelRecipe == null ? null
				: (Specification<RecipeEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
						.equal(root.get("levelRecipe"), levelRecipe);
	}

	static Specification<RecipeEntity> howCookEqual(String howCook) {
		return (howCook == null || howCook.isEmpty()) ? null
				: (Specification<RecipeEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
						.like(root.get("howCook"), howCook);
	}

	static Specification<RecipeEntity> cookTimeEqual(Integer cookTime) {
		return cookTime == null ? null
				: (Specification<RecipeEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
						.equal(root.get("cookTime"), cookTime);
	}

	static Specification<RecipeEntity> updateDateEqual(String updateDate) {
		return (updateDate == null || updateDate.isEmpty()) ? null
				: (Specification<RecipeEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
						.like(root.get("updateDate"), updateDate);
	}

	static Specification<RecipeEntity> delFlgEqual(Integer delFlg) {
		return delFlg == null ? null
				: (Specification<RecipeEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
						.equal(root.get("delFlg"), delFlg);
	}

	static Specification<RecipeEntity> nmRecipeLike(String nmRecipe) {
		return (nmRecipe == null || nmRecipe.isEmpty()) ? null
				: (Specification<RecipeEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
						.like(root.get("nmRecipe"), "%" + nmRecipe + "%");
	}
	
	/**
	 * IDで検索
	 **/
	Optional<RecipeEntity> findByIdRecipe(Integer idRecipe);
}
