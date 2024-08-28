package com.example.rhizome.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipeUserInfo {
	
    /**
     * レシピ
     */
    private final Recipe recipe;
    /**
     * ユーザー情報
     */
    private final User user;
    
}

