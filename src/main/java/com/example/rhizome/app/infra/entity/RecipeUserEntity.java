package com.example.rhizome.app.infra.entity;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.example.rhizome.app.domain.model.Recipe;
import com.example.rhizome.app.domain.model.RecipeUserInfo;
import com.example.rhizome.app.domain.model.User;

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
@NamedEntityGraph(name = "recipe_with_user", includeAllAttributes = true)
public class RecipeUserEntity {

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

	@ManyToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private UserEntity user;

	/**
     * RecipeUserEntity（DataAccess層依存） を RecipeUserInfo（BusinessLogic層依存） に変換します。
     *
     * @param recipeUserEntity 　RecipeUserEntity のインスタンス
     * @return RecipeUserInfo のインスタンス
     */
    public static RecipeUserInfo createRecipeUserInfo(RecipeUserEntity recipeUserEntity) {

        return new RecipeUserInfo(
                new Recipe(
                		recipeUserEntity.getIdRecipe(),
                		recipeUserEntity.getIdUser()== null ? null : recipeUserEntity.getUser().getIdUser(),
                		recipeUserEntity.getIdGenre(),
                		recipeUserEntity.getNmRecipe(),
                		recipeUserEntity.getPicRecipe(),
                		recipeUserEntity.getIngRecipe(),
                		recipeUserEntity.getLevelRecipe(),
                		recipeUserEntity.getHowCook(),
                		recipeUserEntity.getCookTime(),
                		recipeUserEntity.getUpdateDate(),
                		recipeUserEntity.getDelFlg()
                ),
                new User(
                		recipeUserEntity.getUser() == null ? null : recipeUserEntity.getUser().getIdUser(),
                		recipeUserEntity.getUser() == null ? null : recipeUserEntity.getUser().getIconUser(),
                		recipeUserEntity.getUser() == null ? null : recipeUserEntity.getUser().getNmUser(),
                		recipeUserEntity.getUser() == null ? null : recipeUserEntity.getUser().getMailAddress(),
                		recipeUserEntity.getUser() == null ? null : recipeUserEntity.getUser().getPassword(),
                		recipeUserEntity.getUser() == null ? null : recipeUserEntity.getUser().getDelFlg()
                )
        );

    }

	/**
	 *RecipeUserEntity を RecipeUserInfo に変換します。
	 * （リスト版）
	 */
	public static List<RecipeUserInfo> createRecipeUserInfoList(List<RecipeUserEntity> recipeUserEntityList) {

		return recipeUserEntityList.stream().map(RecipeUserEntity::createRecipeUserInfo).toList();

	}

}
