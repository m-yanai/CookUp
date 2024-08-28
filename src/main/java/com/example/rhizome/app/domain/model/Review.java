package com.example.rhizome.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Review {
	
	//レビューID
	private final Integer idReview;
	
	//ユーザーID
	private final Integer idUser;
	
	//レシピID
	private final Integer idRecipe;
	
	//レビュー
	private final String review;
	
	//登校日
	private final String postDate;

}
