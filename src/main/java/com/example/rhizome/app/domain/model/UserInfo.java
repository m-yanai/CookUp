package com.example.rhizome.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfo {
		
	/**
     * ユーザー情報
     */
    private final User user;
    /**
     * レシピ
     */
    private final Recipe recipe;
    /**
     * レビュー
     */
    private final Review review;
    /**
     * カレンダー
     */
    private final Calendar calendar;
    /**
     * ジャンル
     */
    private final Genre genre;
}

