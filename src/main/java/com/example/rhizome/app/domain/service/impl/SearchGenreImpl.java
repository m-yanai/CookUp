package com.example.rhizome.app.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.rhizome.app.domain.model.Genre;
import com.example.rhizome.app.domain.service.SearchGenre;
import com.example.rhizome.app.infra.entity.GenreEntity;
import com.example.rhizome.app.infra.repository.GenreRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchGenreImpl implements SearchGenre {
	private final GenreRepository genreRepository;
	/**
     * 部署情報を全件取得する
     *
     * @return 取得した部署情報レコード全件
     */
    public List<Genre> selectAll() {
        return GenreEntity.createGenreList(genreRepository.findAll());
    }
}
