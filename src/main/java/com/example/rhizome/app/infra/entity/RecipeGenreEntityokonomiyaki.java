//package com.example.rhizome.app.infra.entity;
//
//import java.util.List;
//
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;
//
//import com.example.rhizome.app.domain.model.Genre;
//import com.example.rhizome.app.domain.model.Recipe;
//import com.example.rhizome.app.domain.model.RecipeGenreInfo;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.NamedEntityGraph;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * Recipe + Genre の Join 情報を保持するEntity
// *
// * @author IT-College
// */
//@Entity
//@Table(name = "recipe_table")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@NamedEntityGraph(name = "recipe_with_genre", includeAllAttributes = true)
//public class RecipeGenreEntityokonomiyaki {
//
//	/**
//	 * レシピID
//	 */
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id_recipe", nullable = false)
//	private Integer idRecipe;
//
//	/**
//	 * ユーザーID
//	 */
//	@Column(name = "id_user", nullable = false)
//	private Integer idUser;
//
//	/**
//	 * ジャンルID
//	 */
//	@Column(name = "id_genre", nullable = false)
//	private Integer idGenre;
//
//	/**
//	 * レシピ名
//	 */
//	@Column(name = "nm_recipe", nullable = false)
//	private String nmRecipe;
//
//	/**
//	 * レシピ画像
//	 */
//	@Column(name = "pic_recipe", nullable = false)
//	private String picRecipe;
//
//	/**
//	 * レシピ材料
//	 */
//	@Column(name = "ing_recipe", nullable = false)
//	private String ingRecipe;
//
//	/**
//	 * レシピ難易度
//	 */
//	@Column(name = "level_recipe", nullable = false)
//	private Integer levelRecipe;
//
//	/**
//	 * 調理工程
//	 */
//	@Column(name = "how_cook", nullable = false)
//	private String howCook;
//
//	/**
//	 * 調理時間
//	 */
//	@Column(name = "cook_time", nullable = false)
//	private Integer cookTime;
//
//	/**
//	 * 更新日
//	 */
//	@Column(name = "update_date", nullable = false)
//	private String updateDate;
//
//	/**
//	 * 削除フラグ
//	 */
//	@Column(name = "del_flg", nullable = false)
//	private Integer delFlg;
//	@Column(name = "nm_genre", nullable = false)
//	private String nmGenre;
//
//	@ManyToOne
//	@JoinColumn(name = "id_genre", referencedColumnName = "id_genre", insertable = false, updatable = false)
//	@Fetch(FetchMode.JOIN)
//	private GenreEntity genre;
//
//	/**
//	 * RecipeGenreEntity（DataAccess層依存） を RecipeGenreInfo（BusinessLogic層依存） に変換します。
//	 *
//	 * @param recipeGenreEntity 　RecipeGenreEntity のインスタンス
//	 * @return RecipeGenreInfo のインスタンス
//	 */
//	public static RecipeGenreInfo createRecipeGenreInfo(RecipeGenreEntity recipeGenreEntity) {
//
//		// コンストラクタを使って書くとSetterを使用するより簡略化出来ます。
//		// 混乱してしまう人は落ち着いて EmployeeInfo のフィールドを見に行って、
//		// どうしてこの書き方が出来るのかを理解できる様になりましょう。
//		return new RecipeGenreInfo(
//				new Recipe(
//						recipeGenreEntity.getIdRecipe(),
//						recipeGenreEntity.getIdUser(),
//						recipeGenreEntity.getIdGenre() == null ? null : recipeGenreEntity.getGenre().getIdGenre(),
//						recipeGenreEntity.getNmRecipe(),
//						recipeGenreEntity.getPicRecipe(),
//						recipeGenreEntity.getIngRecipe(),
//						recipeGenreEntity.getLevelRecipe(),
//						recipeGenreEntity.getHowCook(),
//						recipeGenreEntity.getCookTime(),
//						recipeGenreEntity.getUpdateDate(),
//						recipeGenreEntity.getDelFlg()),
//				new Genre(
//						recipeGenreEntity.getGenre() == null ? null : recipeGenreEntity.getGenre().getIdGenre(),
//						recipeGenreEntity.getGenre() == null ? null : recipeGenreEntity.getGenre().getNmGenre()));
//	}
//
//	/**
//	 * RecipeGenreEntity（DataAccess層依存） を RecipeGenreInfo（BusinessLogic層依存） に変換します。
//	 * （リスト版）
//	 *
//	 * @param recipeGenreEntityList RecipeGenreEntity インスタンスのリスト
//	 * @return RecipeGenreInfo インスタンスのリスト
//	 */
//	public static List<RecipeGenreInfo> createEmployeeInfoList(
//			List<RecipeGenreEntityokonomiyaki> recipeGenreEntityList) {
//
//		// StreamAPI を使うとこうなります。
//		return recipeGenreEntityList.stream().map(RecipeGenreEntityokonomiyaki::createRecipeGenreInfo).toList();
//
//	}
//
//}
