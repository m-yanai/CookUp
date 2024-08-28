package com.example.rhizome.app.domain.service;

import java.util.List;

import com.example.rhizome.app.domain.model.Genre;

public interface SearchGenre {
	List<Genre> selectAll();
}
