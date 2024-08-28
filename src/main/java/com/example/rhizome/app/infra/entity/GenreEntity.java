
package com.example.rhizome.app.infra.entity;


import java.util.List;

import com.example.rhizome.app.domain.model.Genre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * genre テーブルの Entity
 *
 * @author IT-College
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genre_table")
public class GenreEntity {

	/**
	 * ジャンルID
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.IDGENRE)
	@Column(name = "id_genre", nullable = false)
	private Integer idGenre;
	/*
	 * ジャンル名
	 */
	@Column(name = "nm_genre", nullable = false)
	private String nmGenre;

	/**
	 * GenreEntity（DataAccess層依存）から Department（BusinessLogic層依存型）に変換します。
	 *
	 * @param genreEntityList genre Table のリスト
	 * @return List<Department> リスト
	 */
	public static List<Genre> createGenreList(List<GenreEntity> genreEntityList) {
		
		// Stream API を利用するとこうなります。
		return genreEntityList
				.stream()
				.map(genreEntity -> new Genre(
						genreEntity.getIdGenre(),
						genreEntity.getNmGenre()
						)).toList();
	}

	/**
	 * Genre（BusinessLogic層依存型） から GenreEntity（DataAccess層依存）に変換します。
	 *
	 * @param genre genre モデルの値
	 * @return GenreEntity に変換したインスタンス
	 */
	public static GenreEntity convertFrom(Genre genre) {
		return new GenreEntity(
				genre.getIdGenre(),
				genre.getNmGenre()
				);
	}
	public static Genre createGenre(GenreEntity genreEntity) {
		return new Genre(
				genreEntity.getIdGenre(),
				genreEntity.getNmGenre()
				);
	}
	
//	public Genre genre() {
//		return new Genre(
//				getIdGenre(),
//				getNmGenre()
//				);
//		
//	}
	

}
