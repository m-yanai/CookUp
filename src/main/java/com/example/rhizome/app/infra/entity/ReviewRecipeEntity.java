package com.example.rhizome.app.infra.entity;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.example.rhizome.app.domain.model.Recipe;
import com.example.rhizome.app.domain.model.Review;
import com.example.rhizome.app.domain.model.ReviewRecipeInfo;

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

/**
 * Review + Recipe の Join 情報を保持するEntity
 */
@Entity
@Table(name = "review_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "review_with_recipe", includeAllAttributes = true)
public class ReviewRecipeEntity {

	/**
	 * レビューID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_review", nullable = false)
	private Integer idReview;

	/**
	 * ユーザーID
	 */
	@Column(name = "id_user", nullable = false)
	private Integer idUser;

	/**
	 * レシピID
	 */
	@Column(name = "id_recipe", nullable = false)
	private Integer idRecipe;

	/**
	 * レビュー
	 */
	@Column(name = "review", nullable = false)
	private String review;

	/**
	 * 投稿日
	 */
	@Column(name = "postdate", nullable = false)
	private String postDate;
		
	@ManyToOne
	@JoinColumn(name = "id_recipe", referencedColumnName = "id_recipe", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private RecipeEntity recipe;

	//ReviewRecipeEntity から ReviewRecipeInfo に変換
	public static ReviewRecipeInfo createReviewRecipeInfo(ReviewRecipeEntity reviewRecipeEntity) {
		 return new ReviewRecipeInfo(
				 new Review(
						 reviewRecipeEntity.getIdReview(),
						 reviewRecipeEntity.getIdUser(),
						 reviewRecipeEntity.getIdRecipe() == null ? null : reviewRecipeEntity.getRecipe().getIdRecipe(),
						 reviewRecipeEntity.getReview(),
						 reviewRecipeEntity.getPostDate()
						 
			     ),
				 new Recipe(
						 reviewRecipeEntity.getRecipe() == null ? null : reviewRecipeEntity.getRecipe().getIdRecipe(),
						 reviewRecipeEntity.getRecipe() == null ? null : reviewRecipeEntity.getRecipe().getIdUser(),
						 reviewRecipeEntity.getRecipe() == null ? null : reviewRecipeEntity.getRecipe().getIdGenre(),
					     reviewRecipeEntity.getRecipe() == null ? null : reviewRecipeEntity.getRecipe().getNmRecipe(),
						 reviewRecipeEntity.getRecipe() == null ? null : reviewRecipeEntity.getRecipe().getPicRecipe(),
						 reviewRecipeEntity.getRecipe() == null ? null : reviewRecipeEntity.getRecipe().getIngRecipe(),	
						 reviewRecipeEntity.getRecipe() == null ? null : reviewRecipeEntity.getRecipe().getLevelRecipe(),
						 reviewRecipeEntity.getRecipe() == null ? null : reviewRecipeEntity.getRecipe().getHowCook(),
						 reviewRecipeEntity.getRecipe() == null ? null : reviewRecipeEntity.getRecipe().getCookTime(),
						 reviewRecipeEntity.getRecipe() == null ? null : reviewRecipeEntity.getRecipe().getUpdateDate(),
						 reviewRecipeEntity.getRecipe() == null ? null : reviewRecipeEntity.getRecipe().getDelFlg()
			     )
	    );
	 }
	
	//リスト版
	public static List<ReviewRecipeInfo> createReviewRecipeInfoList(List<ReviewRecipeEntity> reviewRecipeEntityList) {
		return reviewRecipeEntityList.stream().map(ReviewRecipeEntity::createReviewRecipeInfo).toList();
	}

}
