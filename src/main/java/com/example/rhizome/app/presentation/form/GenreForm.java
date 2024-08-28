package com.example.rhizome.app.presentation.form;

import java.util.List;

import com.example.rhizome.app.domain.model.Genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreForm {
	/**
     * ジャンルID
     */
    private Integer idGenre;
    /**
     * ジャンル名
     */
    private String nmGenre;

   
    public static GenreForm convertFrom(Genre genre) {
        return new GenreForm(genre.getIdGenre(), genre.getNmGenre());
    }


    public static List<GenreForm> convertFrom(List<Genre> genreList) {
        return genreList.stream().map(GenreForm::convertFrom).toList();
    }

}
