package com.example.rhizome.app.presentation.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rhizome.app.domain.service.SelectRecipe;
import com.example.rhizome.app.infra.entity.RecipeGenreEntity;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.infra.repository.MyRecipeRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.LoginUserForm;
import com.example.rhizome.app.presentation.form.MyRecipeSearchForm;

import jakarta.servlet.http.HttpSession;

@Controller
public class SearchController {

	// ログ出力用インスタンス
	private final static Logger LOGGER = Logger.getLogger(SearchController.class.getName());
	// エラーメッセージ用の定数フィールド
	private final static String SEVERE_ERROR_MESSAGE = "エラーが発生しました！";

	HttpSession httpSession;
	SelectRecipe selectRecipe;
	MyRecipeRepository myrecipeRepository;

	@Autowired
	public SearchController(HttpSession httpSession, SelectRecipe selectRecipe, MyRecipeRepository myrecipeRepository) {
		this.httpSession = httpSession;
		this.selectRecipe = selectRecipe;
		this.myrecipeRepository = myrecipeRepository;

	}

	@Autowired
	LoginRepository loginRepository;

	@ModelAttribute
	public MyRecipeSearchForm searchForm() {
		return new MyRecipeSearchForm();
	}

	@GetMapping(value = "/Myrecipe")
	public String Myrecipe(MyRecipeSearchForm myRecipeSearchForm, Model model, HttpSession session,
			@RequestParam(name = "sortOption", required = false) String sortOption) {
		try {
			LoginUserForm loginUser = (LoginUserForm) httpSession.getAttribute(SessionKeyword.LOGIN_USER);
			Integer userId = loginUser.getIdUser();
			int delFlg = 1; // DeleteFlgが1の条件を設定

			//sessionからuseridを持ってくる。
			List<RecipeGenreEntity> myRecipeList = selectRecipe.selectMyRecipes(userId, delFlg);

			// ユーザーIDを使ってユーザーアイコン情報を取得
			UserEntity loggedInUser = loginRepository.findByIdUser(userId).orElse(null);
			if (loggedInUser != null) {
				String userIcon = loggedInUser.getIconUser();

				model.addAttribute("userIcon", userIcon); // HTMLに渡すアイコン情報
				System.out.println(userIcon);
			}

			// ソートオプションに基づいてレシピを取得
			List<RecipeGenreEntity> sortedRecipeList;
			// sortOption の null チェックを追加する
			System.out.println(sortOption);
			if (sortOption == null) {
				// デフォルトではソートせずに元のリストを使用する
				sortedRecipeList = myrecipeRepository.findAllByIdUserAndDelFlg(userId, delFlg);
			} else {
				switch (sortOption) {
				case "01":
					sortedRecipeList = myrecipeRepository.findByIdUserAndDelFlgOrderByCookTimeAsc(userId, delFlg);
					// セッションに値を保存する
					session.setAttribute("sortOption", sortOption); // ソートオプションをセッションに保存
					session.setAttribute("sortedList", sortedRecipeList); // ソート後のリストをセッションに保存
					break;
				case "02":
					sortedRecipeList = myrecipeRepository.findByIdUserAndDelFlgOrderByCookTimeDesc(userId,
							delFlg);
					session.setAttribute("sortOption", sortOption); // ソートオプションをセッションに保存
					session.setAttribute("sortedList", sortedRecipeList); // ソート後のリストをセッションに保存

					break;
				case "03":
					sortedRecipeList = myrecipeRepository.findByIdUserAndDelFlgOrderByLevelRecipeAsc(userId,
							delFlg);
					session.setAttribute("sortOption", sortOption); // ソートオプションをセッションに保存
					session.setAttribute("sortedList", sortedRecipeList); // ソート後のリストをセッションに保存

					break;
				case "04":
					sortedRecipeList = myrecipeRepository.findByIdUserAndDelFlgOrderByLevelRecipeDesc(userId,
							delFlg);
					session.setAttribute("sortOption", sortOption); // ソートオプションをセッションに保存
					session.setAttribute("sortedList", sortedRecipeList); // ソート後のリストをセッションに保存

					break;
				default:
					// デフォルトではソートせずに元のリストを使用する
					session.setAttribute("sortOption", sortOption); // ソートオプションをセッションに保存

					sortedRecipeList = myRecipeList;
					break;

				}
			}
			// モデルにソート後のリストを追加
			model.addAttribute("myRecipeList", sortedRecipeList);
			System.out.println(sortedRecipeList);
			System.out.println(myRecipeList);
			Integer recipeCount = selectRecipe.countRecipesByIdUserAndDelFlg(userId, delFlg);
			model.addAttribute("recipeCount", recipeCount);
			System.out.println(recipeCount);
			System.out.println("Recommended Recipes:");
			for (RecipeGenreEntity recipegenre : myRecipeList) {
				System.out.println(recipegenre.getNmRecipe() + ":" + recipegenre.getPicRecipe() + ":"
						+ recipegenre.getCookTime() + ":"
						+ recipegenre.getLevelRecipe() + ":" + recipegenre.getGenre().getNmGenre()); // RecipeのtoString()が適切に実装されている場合
			}
			return "myRecipe";
		} catch (Exception e) {
			// ログイン情報の破棄
			httpSession.invalidate();

			e.printStackTrace();

			// エラーログ出力
			LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE, e);
			// エラー画面遷移
			return TransitionTargetPageNameKeyword.ERROR_HTML;
		}

	}
}
