package com.example.rhizome.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Calendar {
	
	//日付
	private final String calDate;
	
	//ユーザーID
	private final Integer idUser;
	
	//レシピID
	private final Integer idRecipe;

}
