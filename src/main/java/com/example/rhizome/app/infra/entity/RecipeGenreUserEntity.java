package com.example.rhizome.app.infra.entity;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.example.rhizome.app.domain.model.Genre;
import com.example.rhizome.app.domain.model.Recipe;
import com.example.rhizome.app.domain.model.RecipeGenreUserInfo;
import com.example.rhizome.app.domain.model.User;
import com.example.rhizome.app.presentation.form.RecipeGenreUserForm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
		name = "recipe_with_genre_with_user",
		includeAllAttributes = true
		)
public class RecipeGenreUserEntity {
	
	/**
	 * レシピID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_recipe", nullable = false)
	private Integer idRecipe;

	/**
	 * ユーザーID
	 */
	@Column(name = "id_user", nullable = false)
	private Integer idUser;

	/**
	 * ジャンルID
	 */
	@Column(name = "id_genre", nullable = false)
	private Integer idGenre;

	/**
	 * レシピ名
	 */
	@Column(name = "nm_recipe", nullable = false)
	private String nmRecipe;

	/**
	 * レシピ画像
	 */
	@Column(name = "pic_recipe", nullable = false)
	private String picRecipe;

	/**
	 * レシピ材料
	 */
	@Column(name = "ing_recipe", nullable = false)
	private String ingRecipe;

	/**
	 * レシピ難易度
	 */
	@Column(name = "level_recipe", nullable = false)
	private Integer levelRecipe;

	/**
	 * 調理工程
	 */
	@Column(name = "how_cook", nullable = false)
	private String howCook;

	/**
	 * 調理時間
	 */
	@Column(name = "cook_time", nullable = false)
	private Integer cookTime;

	/**
	 * 更新日
	 */
	@Column(name = "update_date", nullable = false)
	private String updateDate;

	/**
	 * 削除フラグ
	 */
	@Column(name = "del_flg", nullable = false)
	private Integer delFlg;
	
//	/*
//	 * ジャンル名
//	 */
//	@Column(name = "nm_genre", nullable = false)
//	private String nmGenre;
	
	@ManyToOne
	@JoinColumn(name = "id_genre", referencedColumnName = "id_genre", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private GenreEntity genre;
	
	@ManyToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private UserEntity user;

    /**
     * EmployeeDepartmentEntity（DataAccess層依存） を EmployeeInfo（BusinessLogic層依存） に変換します。
     *
     * @param employeeDepartmentEntity 　EmployeeDepartmentEntity のインスタンス
     * @return EmployeeInfo のインスタンス
     */
    public static RecipeGenreUserInfo createRecipeGenreUserInfo(RecipeGenreUserEntity recipeGenreUserEntity) {

        return new RecipeGenreUserInfo(
                new Recipe(
                		recipeGenreUserEntity.getIdRecipe(),
                		recipeGenreUserEntity.getIdUser(),
                		recipeGenreUserEntity.getIdGenre(),
                		recipeGenreUserEntity.getNmRecipe(),
                		recipeGenreUserEntity.getPicRecipe(),
                		recipeGenreUserEntity.getIngRecipe(),
                		recipeGenreUserEntity.getLevelRecipe(),
                		recipeGenreUserEntity.getHowCook(),
                		recipeGenreUserEntity.getCookTime(),
                		recipeGenreUserEntity.getUpdateDate(),
                		recipeGenreUserEntity.getDelFlg()
                ),
                new Genre(
                		recipeGenreUserEntity.genre.getIdGenre(),
                		recipeGenreUserEntity.genre.getNmGenre()
                ),
                new User(
                		recipeGenreUserEntity.user.getIdUser(),
                		recipeGenreUserEntity.user.getIconUser(),
                		recipeGenreUserEntity.user.getNmUser(),
                		recipeGenreUserEntity.user.getMailAddress(),
                		recipeGenreUserEntity.user.getPassword(),
                		recipeGenreUserEntity.user.getDelFlg()
                		)
        );

    }

    /**
     * EmployeeDepartmentEntity（DataAccess層依存） を EmployeeInfo（BusinessLogic層依存） に変換します。
     * （リスト版）
     *
     * @param employeeDepartmentEntityList EmployeeDepartmentEntity インスタンスのリスト
     * @return EmployeeInfo インスタンスのリスト
     */
    public static List<RecipeGenreUserInfo> createRecipeGenreUserInfoList(List<RecipeGenreUserEntity> recipeGenreUserEntityList) {

        return recipeGenreUserEntityList.stream().map(RecipeGenreUserEntity::createRecipeGenreUserInfo).toList();

    }
    
    
    //RecipeGenreUserForm を RecipeGenreUserEntity に変換
    public static RecipeGenreUserEntity convertFrom(RecipeGenreUserForm recipeGenreUserForm) {
    	
    	return new RecipeGenreUserEntity(
    			recipeGenreUserForm.getIdRecipe(),
    			recipeGenreUserForm.getIdUser(),
    			recipeGenreUserForm.getIdGenre(),
    			recipeGenreUserForm.getNmRecipe(),
    			recipeGenreUserForm.getPicRecipe(),
    			recipeGenreUserForm.getIngRecipe(),
    			recipeGenreUserForm.getLevelRecipe(),
    			recipeGenreUserForm.getHowCook(),
    			recipeGenreUserForm.getCookTime(),
    			recipeGenreUserForm.getUpdateDate(),
    			recipeGenreUserForm.getDelFlg(),
    		new GenreEntity(
    			recipeGenreUserForm.getIdGenre(),
    			recipeGenreUserForm.getNmGenre()
    			),
    		new UserEntity(
    			recipeGenreUserForm.getIdUser(),
    			recipeGenreUserForm.getIconUser(),
    			recipeGenreUserForm.getNmUser(),
    			recipeGenreUserForm.getMailAddress(),
    			recipeGenreUserForm.getPassword(),
    			recipeGenreUserForm.getUserDelFlg()
    			)
    				);
    }
    
  //RecipeGenreUserForm を RecipeGenreUserEntity に変換
    public static List<RecipeGenreUserEntity> convertFrom(List<RecipeGenreUserForm> recipeGenreUserForm) {
        return recipeGenreUserForm.stream().map(RecipeGenreUserEntity::convertFrom).toList();
 }


}
