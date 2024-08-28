package com.example.rhizome.app.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rhizome.app.domain.service.Login;
import com.example.rhizome.app.domain.service.RecipeDetailFromMyRecipe;
import com.example.rhizome.app.domain.service.RecipeReview;
import com.example.rhizome.app.domain.service.SearchGenre;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.GenreForm;
import com.example.rhizome.app.presentation.form.LoginUserForm;
import com.example.rhizome.app.presentation.form.MessageForm;
import com.example.rhizome.app.presentation.form.MyRecipeSearchForm;
import com.example.rhizome.app.presentation.form.RecipeReviewForm;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class RecipeReviewController {

    RecipeReview recipeReview;
    HttpSession httpSession;
     SearchGenre searchGenre;
     RecipeDetailFromMyRecipe recipeDetailFromMyRecipe;
    Login login;

    @Autowired
    public RecipeReviewController(RecipeReview recipeReview, HttpSession httpSession, SearchGenre searchGenre,
                                  RecipeDetailFromMyRecipe recipeDetailFromMyRecipe, Login login) {
        this.recipeReview = recipeReview;
        this.httpSession = httpSession;
        this.searchGenre = searchGenre;
        this.recipeDetailFromMyRecipe = recipeDetailFromMyRecipe;
        this.login = login;
    }
    @Autowired
	LoginRepository loginRepository;

	@ModelAttribute
	public RecipeReviewForm recipeReviewForm() {
		return new RecipeReviewForm();
	}
	@ModelAttribute
	public MyRecipeSearchForm myRecipeSearchForm() {
		return new MyRecipeSearchForm();
	}

	@RequestMapping(value = TransitionTargetPageNameKeyword.RECIPE_REVIEW, method = RequestMethod.POST)
	public String recipeReviewInput(RecipeReviewForm recipeReviewForm, Model model,HttpSession session,
			@RequestParam("previousScreen") String previousScreen,MyRecipeSearchForm myRecipeSearchForm) {
		
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
		System.out.println(previousScreen);
		recipeReviewForm.setReview("");
		//遷移画面情報格納
		model.addAttribute("previousScreen", previousScreen);
		model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, myRecipeSearchForm);

		// 画面に返す為の登録情報をセット
		model.addAttribute(PageReturnAttributeKeyword.RECIPE_REVIEW_FORM, recipeReviewForm);

		return TransitionTargetPageNameKeyword.RECIPE_REVIEW_HTML;
	}

	@RequestMapping(value = TransitionTargetPageNameKeyword.BACK_TO_DETAIL_FROM_REVIEW, method = RequestMethod.POST)
	public String backToDetailFromReview(RecipeReviewForm recipeReviewForm,MyRecipeSearchForm myRecipeSearchForm,
			@RequestParam("previousScreen") String previousScreen, Model model) {
		
		
		
		//遷移画面情報格納
		System.out.println(previousScreen);
		myRecipeSearchForm = MyRecipeSearchForm.convertFrom(recipeDetailFromMyRecipe.searchByIdRecipe(recipeReviewForm.getIdRecipe()));
		model.addAttribute(
				"genreFormList",
				GenreForm.convertFrom(
						searchGenre.selectAll()));

		//レビュー取得
		model.addAttribute(
				"reviewFormList",
				RecipeReviewForm.convertFrom(
						recipeReview.searchByIdRecipe(myRecipeSearchForm.getIdRecipe())));

		//ユーザー情報取得
		model.addAttribute(
				"userFormList",
				LoginUserForm.convertFrom(
						login.selectAll()));
		
		model.addAttribute("ingRecipeList",myRecipeSearchForm.getIngRecipe().split(","));
		model.addAttribute("howCookList",myRecipeSearchForm.getHowCook().split(","));
		
		model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, myRecipeSearchForm);
		
		if (previousScreen.equals(TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_SEARCH_HTML)) {
			return TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_SEARCH_HTML;
		} else {
			return TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_MY_RECIPE_HTML;
		}

	}

	@RequestMapping(value = TransitionTargetPageNameKeyword.UPDATE_REVIEW, method = RequestMethod.POST)
	public String updateReview(RecipeReviewForm recipeReviewForm, @RequestParam("previousScreen") String previousScreen,
			Model model,MyRecipeSearchForm myRecipeSearchForm) {
		final String DELETE_COMPLETE = "レビューを投稿しました。";
		
		String error = validateParameter(recipeReviewForm.getReview());
		recipeReview.insertRecipe(RecipeReviewForm.convertTo(recipeReviewForm));
		myRecipeSearchForm = MyRecipeSearchForm.convertFrom(recipeDetailFromMyRecipe.searchByIdRecipe(recipeReviewForm.getIdRecipe()));
		model.addAttribute(
				"genreFormList",
				GenreForm.convertFrom(
						searchGenre.selectAll()));

		//レビュー取得
		model.addAttribute(
				"reviewFormList",
				RecipeReviewForm.convertFrom(
						recipeReview.searchByIdRecipe(myRecipeSearchForm.getIdRecipe())));

		//ユーザー情報取得
		model.addAttribute(
				"userFormList",
				LoginUserForm.convertFrom(
						login.selectAll()));
		
		model.addAttribute("ingRecipeList",myRecipeSearchForm.getIngRecipe().split(","));
		model.addAttribute("howCookList",myRecipeSearchForm.getHowCook().split(","));
		
		
		model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, myRecipeSearchForm);
		model.addAttribute("messageInfo", new MessageForm(DELETE_COMPLETE));

		// エラーが1件以上有る場合
		if (!error.isEmpty()) {
			// エラー内容を画面に表示
			//遷移画面情報格納
			model.addAttribute("previousScreen", previousScreen);
			// 画面に返す為の登録情報をセット
			model.addAttribute(PageReturnAttributeKeyword.RECIPE_REVIEW_FORM, recipeReviewForm);
			//model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR, new MessageForm(String.join(", ", error) + "が不正です。"));
			// 登録画面に遷移
			return TransitionTargetPageNameKeyword.RECIPE_REVIEW_HTML;
		}
		

		//遷移画面情報格納
		System.out.println(previousScreen);
		if (previousScreen.equals(TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_SEARCH_HTML)) {
			return TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_SEARCH_HTML;
		} else {
			return TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_MY_RECIPE_HTML;
		}

	}

	private String validateParameter(
			String review) {

		final String REVIEW_NAME_NULL_BLANK = "レビュー";

		String error ="";

		// null or 空文字判定
		if (isNullOrBlank(review)) {
			// エラー内容を追加
			error = REVIEW_NAME_NULL_BLANK;
		}

		return error;

	}

	/**
	 * 仮引数を null or 空 判定する
	 *
	 * @param str 判定対象の文字列
	 * @return true:null or 空 false:null or 空 では無い
	 */
	private static boolean isNullOrBlank(String str) {
		if (null == str) {
			return true;
		}

		return str.isBlank();
	}
}
