package com.example.rhizome.app.infra.entity;




import java.util.List;

import com.example.rhizome.app.domain.model.Recipe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeEntity {

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
     * 調理手順
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
    
    
    public static List<Recipe> createGenreList(List<RecipeEntity> recipeEntityList) {

		// Stream API を利用するとこうなります。
		return recipeEntityList
				.stream()
				.map(recipeEntity -> new Recipe(
						recipeEntity.getIdRecipe(),
						recipeEntity.getIdUser(),
						recipeEntity.getIdGenre(),
						recipeEntity.getNmRecipe(),
						recipeEntity.getPicRecipe(),
						recipeEntity.getIngRecipe(),
						recipeEntity.getLevelRecipe(),
						recipeEntity.getHowCook(),
						recipeEntity.getCookTime(),
						recipeEntity.getUpdateDate(),
						recipeEntity.getDelFlg()
						)).toList();
	}
    
    
    /**
     * Recipe を RecipeEntity に変換
     */
    public static RecipeEntity convertFrom(Recipe recipe) {
    	return new RecipeEntity(
    			recipe.getIdRecipe(),
    			recipe.getIdUser(),
    			recipe.getIdGenre(),
    			recipe.getNmRecipe(),
    			recipe.getPicRecipe(),
    			recipe.getIngRecipe(),
    			recipe.getLevelRecipe(),
    			recipe.getHowCook(),
    			recipe.getCookTime(),
    			recipe.getUpdateDate(),
    			recipe.getDelFlg());
    }
    
    /**
     * RecipeEntity を Recipe に変換
     */
    public static Recipe createRecipe(RecipeEntity recipeEntity) {
    	return new Recipe(
    			recipeEntity.getIdRecipe(),
    			recipeEntity.getIdUser(),
    			recipeEntity.getIdGenre(),
    			recipeEntity.getNmRecipe(),
    			recipeEntity.getPicRecipe(),
    			recipeEntity.getIngRecipe(),
    			recipeEntity.getLevelRecipe(),
    			recipeEntity.getHowCook(),
    			recipeEntity.getCookTime(),
    			recipeEntity.getUpdateDate(),
    			recipeEntity.getDelFlg());
    }
    
    /**
     * RecipeEntity を Recipe に変換（リスト版）
     */
    public static List<Recipe> createRecipeList(List<RecipeEntity> recipeEntityList){
    	return recipeEntityList.stream().map(RecipeEntity::createRecipe).toList();
    }
    
}
