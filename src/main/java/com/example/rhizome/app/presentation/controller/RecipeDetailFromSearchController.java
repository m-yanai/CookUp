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
import com.example.rhizome.app.domain.service.SelectRecipe;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.GenreForm;
import com.example.rhizome.app.presentation.form.InsertCalendarForm;
import com.example.rhizome.app.presentation.form.InsertRecipeForm;
import com.example.rhizome.app.presentation.form.LoginUserForm;
import com.example.rhizome.app.presentation.form.MyRecipeSearchForm;
import com.example.rhizome.app.presentation.form.RecipeReviewForm;
import com.example.rhizome.app.presentation.form.SelectRecipeForm;

import jakarta.servlet.http.HttpSession;

@Controller
public class RecipeDetailFromSearchController {

	@Autowired
	HttpSession httpSession;

	@Autowired
	RecipeDetailFromMyRecipe recipeDetailFromMyRecipe;

	@Autowired
	SearchGenre searchGenre;

	@Autowired
	RecipeReview recipeReview;

	@Autowired
	Login login;

	@Autowired
	SelectRecipe selectRecipe;

	@Autowired
	public RecipeDetailFromSearchController(RecipeDetailFromMyRecipe recipeDetailFromMyRecipe) {
		this.recipeDetailFromMyRecipe = recipeDetailFromMyRecipe;
	}

	@Autowired
	LoginRepository loginRepository;

	@ModelAttribute
	public MyRecipeSearchForm myRecipeSearchForm() {
		return new MyRecipeSearchForm();
	}

	@ModelAttribute
	public InsertCalendarForm insertCalendarForm() {
		return new InsertCalendarForm();
	}

	@ModelAttribute
	public InsertRecipeForm insertRecipeForm() {
		return new InsertRecipeForm();
	}

	@ModelAttribute
	public GenreForm GenreForm() {
		return new GenreForm();
	}

	@ModelAttribute
	public RecipeReviewForm RecipeReviewForm() {
		return new RecipeReviewForm();
	}

	@ModelAttribute
	public LoginUserForm LoginUserForm() {
		return new LoginUserForm();
	}

	@RequestMapping(value = TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_SEARCH_HTML, method = RequestMethod.GET)
	public String home(MyRecipeSearchForm myRecipeSearchForm, Model model,HttpSession session,
			@RequestParam("idRecipeValue") Integer idRecipeValue) {
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
		//レシピ取得    ーーーーーー 改善必須

		myRecipeSearchForm = MyRecipeSearchForm.convertFrom(recipeDetailFromMyRecipe.searchByIdRecipe(idRecipeValue));

		model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, myRecipeSearchForm);
		//ジャンル取得
		model.addAttribute(
				"genreFormList",
				GenreForm.convertFrom(
						searchGenre.selectAll()));

		//レビュー取得 ーーーーーー 改善必須
		model.addAttribute(
				"reviewFormList",
				RecipeReviewForm.convertFrom(
						//recipeReview.selectAll()));

						recipeReview.searchByIdRecipe(myRecipeSearchForm.getIdRecipe())));

		//ユーザー情報取得
		model.addAttribute(
				"userFormList",
				LoginUserForm.convertFrom(
						login.selectAll()));

		model.addAttribute("ingRecipeList", myRecipeSearchForm.getIngRecipe().split(","));
		model.addAttribute("howCookList", myRecipeSearchForm.getHowCook().split(","));

		//レシピ詳細に遷移
		return TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_SEARCH_HTML;
	}

	@RequestMapping(value = TransitionTargetPageNameKeyword.BACK_TO_SELECT_FROM_DETAIL, method = RequestMethod.POST)
	public String insertUpdate(SelectRecipeForm selectRecipeForm, Model model) {

		//更新画面に遷移
		return TransitionTargetPageNameKeyword.SELECT_RECIPE_HTML;

	}

}
