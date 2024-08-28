package com.example.rhizome.app.infra.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.rhizome.app.infra.entity.RecipeGenreUserEntity;
import com.example.rhizome.app.infra.entity.UserEntity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

@Repository
public interface RecipeGenreUserRepository extends JpaRepository<RecipeGenreUserEntity, Integer>, JpaSpecificationExecutor<RecipeGenreUserEntity> {

	static Specification<RecipeGenreUserEntity> nmRecipeEqual(String nmRecipe) {
        return (nmRecipe == null || nmRecipe.isEmpty()) ?
                null :
                (Specification<RecipeGenreUserEntity>)
                        (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("nmRecipe"), "%" + nmRecipe + "%");
    }

    static Specification<RecipeGenreUserEntity> ingRecipeEqual(String ingRecipe) {
        return (ingRecipe == null || ingRecipe.isEmpty()) ?
                null :
                (Specification<RecipeGenreUserEntity>)
                        (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("ingRecipe"), "%" + ingRecipe + "%");
    }

    static Specification<RecipeGenreUserEntity> idGenreEqual(Integer idGenre) {
        return idGenre == null ?
                null :
                (Specification<RecipeGenreUserEntity>)
                        (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("idGenre"), idGenre);
    }
    
    public static Specification<RecipeGenreUserEntity> nmUserEqual(String nmUser) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (nmUser == null || nmUser.isEmpty()) {
                return null;
            }
            
            
            Join<RecipeGenreUserEntity, UserEntity> userJoin = root.join("user", JoinType.INNER);
            return criteriaBuilder.like(userJoin.get("nmUser"), "%" + nmUser + "%");
        };
    };
        static Specification<RecipeGenreUserEntity> DeleteFlgIsOne() {
        	return (root, query, criteriaBuilder) -> criteriaBuilder.and(
        			criteriaBuilder.equal(root.get("delFlg"), 1)
        			);

    }
    
//    List<RecipeGenreUserEntity> findByOrderByCookTimeAsc();
    List<RecipeGenreUserEntity> findByDelFlgOrderByCookTimeAsc(int delFlg);
//	List<RecipeGenreUserEntity> findByOrderByCookTimeDesc();
	List<RecipeGenreUserEntity> findByDelFlgOrderByCookTimeDesc(int delFlg);
	
//	List<RecipeGenreUserEntity> findByOrderByLevelRecipeAsc();
	List<RecipeGenreUserEntity> findByDelFlgOrderByLevelRecipeAsc(int delFlg);
//	List<RecipeGenreUserEntity> findByOrderByLevelRecipeDesc();
	List<RecipeGenreUserEntity> findByDelFlgOrderByLevelRecipeDesc(int delFlg);

}
