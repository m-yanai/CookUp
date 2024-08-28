package com.example.rhizome.app.infra.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.rhizome.app.infra.entity.RecipeGenreEntity;

@Repository
public interface MyRecipeRepository extends JpaRepository<RecipeGenreEntity, Integer>, JpaSpecificationExecutor<RecipeGenreEntity>{

//	@Query("SELECT r.nmRecipe, g.nmGenre FROM RecipeGenreEntity r JOIN GenreEntity g ON r.idGenre = g.idGenre WHERE r.idRecipe = :idRecipe")
//    Optional<Object[]> findRecipeAndGenreNamesById(Integer idRecipe);
	
	 static Specification<RecipeGenreEntity> idUserEqual(Integer idUser) {
	        return idUser == null ?
	                null :
	                	 // 戻り値の型 Specification<DepartmentEntity> にキャスト
	                    (Specification<RecipeGenreEntity>)
	                            // ラムダ式で記載しているが書いてあることは criteriaBuilder を使って Where句の後の "'idDepartment' = idDepartment(変数)"
	                            // を作成している
	                            (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("idUser"), idUser);
	        }
	
	 static Specification<RecipeGenreEntity> idRecipeEqual(Integer idRecipe) {
	        return idRecipe == null ?
	                null :
	                	 // 戻り値の型 Specification<DepartmentEntity> にキャスト
	                    (Specification<RecipeGenreEntity>)
	                            // ラムダ式で記載しているが書いてあることは criteriaBuilder を使って Where句の後の "'idDepartment' = idDepartment(変数)"
	                            // を作成している
	                            (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("idRecipe"), idRecipe);
	        }

	 // DeleteFlg が 1かつログイン中のユーザー のレコードを取得する Specification
	 static Specification<RecipeGenreEntity> userAndDeleteFlgIsOne(Integer userId) {
	        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
	                criteriaBuilder.equal(root.get("idUser"), userId),
	                criteriaBuilder.equal(root.get("delFlg"), 1)
	        );
	    }
	Integer countByIdUser(int userId);
	Integer countByIdUserAndDelFlg(int userId, int delFlg);

	List<RecipeGenreEntity> findAllByIdUserAndDelFlg(int userId,int delFlg);
	
	List<RecipeGenreEntity> findByIdUserAndDelFlgOrderByCookTimeAsc(int userId,int delFlg);
	
	List<RecipeGenreEntity> findByIdUserAndDelFlgOrderByCookTimeDesc(int userId,int delFlg);
	
	List<RecipeGenreEntity> findByIdUserAndDelFlgOrderByLevelRecipeAsc(int userId,int delFlg);
	List<RecipeGenreEntity> findByIdUserAndDelFlgOrderByLevelRecipeDesc(int userId,int delFlg);
	
	}
	
	
	
	

