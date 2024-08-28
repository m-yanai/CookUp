package com.example.rhizome.app.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rhizome.app.domain.model.Genre;
import com.example.rhizome.app.domain.service.SelectGenre;
import com.example.rhizome.app.infra.entity.GenreEntity;
import com.example.rhizome.app.infra.repository.GenreRepository;

/**
 * ジャンル情報検索系の機能を集約させるサービスクラスです。
 */
@Service
@Transactional
public class SelectGenreImpl implements SelectGenre{
	GenreRepository genreRepository;
	
	
	/**
     * コンストラクタ
     *
     * @param departmentRepository DI対象
     */
    @Autowired
    public SelectGenreImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    /**
     * ジャンル情報を全件取得する
     *
     * @return 取得したジャンル情報レコード全件
     */
    public List<Genre> selectAll() {
        return GenreEntity.createGenreList(genreRepository.findAll());
    }
}
