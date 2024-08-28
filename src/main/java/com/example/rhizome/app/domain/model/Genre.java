package com.example.rhizome.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ジャンル情報の概念を表現するモデルクラスです。
 */
@Getter
@AllArgsConstructor
public class Genre {
    /**
     * ジャンルID
     */
    private final Integer idGenre;
    /**
     * ジャンル名
     */
    private final String nmGenre;

}