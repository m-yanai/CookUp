package com.example.rhizome.app.presentation.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rhizome.app.domain.service.SelectGenre;
import com.example.rhizome.app.domain.service.SelectRecipeGenreUser;
import com.example.rhizome.app.infra.entity.RecipeGenreUserEntity;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.infra.repository.MyRecipeRepository;
import com.example.rhizome.app.infra.repository.RecipeGenreUserRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.GenreForm;
import com.example.rhizome.app.presentation.form.LoginUserForm;
import com.example.rhizome.app.presentation.form.RecipeGenreUserForm;
import com.example.rhizome.app.presentation.form.SelectRecipeForm;

import jakarta.servlet.http.HttpSession;

/**
 * 検索画面表示用コントローラ
 */
@Controller
public class SelectRecipeController {
	// ログ出力用インスタンス
	private final static Logger LOGGER = Logger.getLogger(SelectRecipeController.class.getName());
	// エラーメッセージ用の定数フィールド
	private final static String SEVERE_ERROR_MESSAGE = "エラーが発生しました！";

	HttpSession httpSession;
	SelectGenre selectGenre;
	SelectRecipeGenreUser selectRecipeGenreUser;
	MyRecipeRepository myrecipeRepository;
	RecipeGenreUserRepository recipeGenreUserRepository;
	RecipeGenreUserEntity recipeGenreUserEntity;

	@Autowired
	public SelectRecipeController(
		HttpSession httpSession,
		SelectGenre selectGenre,
		SelectRecipeGenreUser selectRecipeGenreUser,
		MyRecipeRepository myrecipeRepository,
		RecipeGenreUserRepository recipeGenreUserRepository
		) {

		this.httpSession = httpSession;
		this.selectGenre = selectGenre;
		this.selectRecipeGenreUser = selectRecipeGenreUser;
		this.myrecipeRepository = myrecipeRepository;
		this.recipeGenreUserRepository = recipeGenreUserRepository;
	}
	@Autowired
	LoginRepository loginRepository;


	@ModelAttribute
	public SelectRecipeForm selectRecipeForm() {
		return new SelectRecipeForm();
	}

	/**
	 * レシピ検索画面表示処理コントローラ
	 *
	 * @param selectRecipeForm 検索画面のフォーム
	 * @param model            ビューにデータを渡すためのコンテナ
	 * @return 遷移先の画面
	 */
	@RequestMapping(value = TransitionTargetPageNameKeyword.SELECT_RECIPE, method = RequestMethod.POST)
	public String select(SelectRecipeForm selectRecipeForm, Model model,HttpSession session,
			@RequestParam(name = "sortOption", required = false) String sortOption) {
		try {

			// ログイン中のユーザーのアイコン情報を取得
					LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);
					Integer userId = loginUser.getIdUser();
					// ユーザーIDを使ってユーザーアイコン情報を取得
					UserEntity loggedInUser = loginRepository.findByIdUser(userId).orElse(null);
					if (loggedInUser != null) {
						String userIcon = loggedInUser.getIconUser();

						model.addAttribute("userIcon", userIcon); // HTMLに渡すアイコン情報
						System.out.println(userIcon);
					}
			// ジャンル一覧を取得し、画面に返す用のクラスに詰め替える
			// 作成したレシピ一覧をセット
			model.addAttribute(
					PageReturnAttributeKeyword.GENRE_FORM_LIST,
					GenreForm.convertFrom(selectGenre.selectAll()));
			
			int delFlg = 1;

			// RecipeGenreUserEntityの条件検索
			List<RecipeGenreUserForm> kensakuList = RecipeGenreUserForm
					.convertFrom(selectRecipeGenreUser.searchBy(SelectRecipeForm.convertTo(selectRecipeForm)));

			//ここでFormをEntityに変換
			List<RecipeGenreUserEntity> sortList = RecipeGenreUserEntity.convertFrom(kensakuList);
			

			if (sortOption == null) {
			} else {
				switch (sortOption) {
				case "01":
					sortList = recipeGenreUserRepository.findByDelFlgOrderByCookTimeAsc(delFlg);
					httpSession.setAttribute("sortOption",sortOption);
					break;
				case "02":
					sortList = recipeGenreUserRepository.findByDelFlgOrderByCookTimeDesc(delFlg);
					httpSession.setAttribute("sortOption",sortOption);
					break;
				case "03":
					sortList = recipeGenreUserRepository.findByDelFlgOrderByLevelRecipeAsc(delFlg);
					httpSession.setAttribute("sortOption",sortOption);
					break;
				case "04":
					sortList = recipeGenreUserRepository.findByDelFlgOrderByLevelRecipeDesc(delFlg);
					httpSession.setAttribute("sortOption",sortOption);
					break;
				default:
					httpSession.setAttribute("sortOption",sortOption);
					break;
				}
				
			}
			
			//sortList(List<Entity>)をList<Form>に変換
			kensakuList = RecipeGenreUserForm.convertEtoFFrom(sortList);
			
			
			// 画像ファイル名に img/recipe/ を追加
			for (RecipeGenreUserForm data : kensakuList) {

				data.setPicRecipe("img/recipe/" + data.getPicRecipe());

			}
			
			model.addAttribute(PageReturnAttributeKeyword.RECIPE_INFO_FORM, kensakuList);

			// 検索ページに遷移
			return TransitionTargetPageNameKeyword.SELECT_RECIPE_HTML;

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
