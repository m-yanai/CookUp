package com.example.rhizome.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewRecipeInfo {
	
	/**
     * レビュー
     */
    private final Review review;
    /**
     * レシピ
     */
    private final Recipe recipe;

}
