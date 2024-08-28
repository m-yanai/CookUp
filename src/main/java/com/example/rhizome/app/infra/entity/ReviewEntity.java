package com.example.rhizome.app.infra.entity;

import java.util.List;

import com.example.rhizome.app.domain.model.Review;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//レビューテーブルのEntity

@Entity
@Table(name = "review_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
	
	//レビューID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_review", nullable = false)
	private Integer idReview;
	
	//ユーザーID
	@Column(name = "id_user", nullable = false)
	private Integer idUser;
	
	//レシピID
	@Column(name = "id_recipe", nullable = false)
	private Integer idRecipe;
	
	//レビュー
	@Column(name = "review", nullable = false)
	private String review;
	
	//投稿日
	@Column(name = "postdate", nullable = false)
	private String postDate;
	
	
	public static List<Review> createDepartmentList(List<ReviewEntity> reviewEntityList) {

        // Stream API を利用するとこうなります。
        return reviewEntityList
                .stream()
                .map(reviewEntity -> new Review(
                		reviewEntity.getIdReview(),
                		reviewEntity.getIdUser(),
                		reviewEntity.getIdRecipe(),
                		reviewEntity.getReview(),
                		reviewEntity.getPostDate()
                )).toList();
    }
	
	
	//Review から ReviewEntity に変換
	public static ReviewEntity convertFrom(Review review) {
		return new ReviewEntity(
				review.getIdReview(),
				review.getIdUser(),
				review.getIdRecipe(),
				review.getReview(),
				review.getPostDate()
				);
	}
	
	//ReviewEntity から Review に変換
	public static Review createReview(ReviewEntity reviewEntity) {
		return new Review(
				reviewEntity.getIdReview(),
				reviewEntity.getIdUser(),
				reviewEntity.getIdRecipe(),
				reviewEntity.getReview(),
				reviewEntity.getPostDate()
				);
	}
	
	//リスト版
	public static List<Review> createReviewList(List<ReviewEntity> reviewEntityList){
		return reviewEntityList.stream().map(ReviewEntity::createReview).toList();
	}

}
