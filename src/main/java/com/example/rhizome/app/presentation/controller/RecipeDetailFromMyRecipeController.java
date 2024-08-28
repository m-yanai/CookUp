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
import com.example.rhizome.app.domain.service.SearchRecipe;
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
import com.example.rhizome.app.presentation.form.RecipeDetailUpdateForm;
import com.example.rhizome.app.presentation.form.RecipeReviewForm;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class RecipeDetailFromMyRecipeController {

	@Autowired
	HttpSession httpSession;

	@Autowired
	RecipeDetailFromMyRecipe recipeDetailFromMyRecipe;

	@Autowired
	SearchGenre searchGenre;

	@Autowired
	RecipeReview recipeReview;

	@Autowired
	SearchRecipe searchRecipe;

	@Autowired
	Login login;

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

	@Autowired
	public RecipeDetailFromMyRecipeController(RecipeDetailFromMyRecipe recipeDetailFromMyRecipe) {
		this.recipeDetailFromMyRecipe = recipeDetailFromMyRecipe;
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


	@RequestMapping(value = TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_MY_RECIPE_HTML, method = RequestMethod.GET)
	public String myRecipeHome(MyRecipeSearchForm myRecipeSearchForm, RecipeDetailUpdateForm recipeDetailUpdateForm,HttpSession session,
			Model model,@RequestParam("idRecipeValue")Integer idRecipeValue) {



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
		//ジャンル取得
		model.addAttribute(
				"genreFormList",
				GenreForm.convertFrom(
						searchGenre.selectAll()));

		//レビュー取得
		model.addAttribute(
				"reviewFormList",
				RecipeReviewForm.convertFrom(
						
						recipeReview.searchByIdRecipe(idRecipeValue)));

		//ユーザー情報取得
		model.addAttribute(
				"userFormList",
				LoginUserForm.convertFrom(
						login.selectAll()));

		myRecipeSearchForm = MyRecipeSearchForm.convertFrom(recipeDetailFromMyRecipe.searchByIdRecipe(idRecipeValue));

		model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, myRecipeSearchForm);
		
		model.addAttribute("ingRecipeList",myRecipeSearchForm.getIngRecipe().split(","));
		model.addAttribute("howCookList",myRecipeSearchForm.getHowCook().split(","));

		//レシピ詳細に遷移
		return TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_MY_RECIPE_HTML;

	}

	@RequestMapping(value = TransitionTargetPageNameKeyword.BACK_TO_DETAIL_FROM_MY_RECIPE, method = RequestMethod.POST)
	public String insertUpdate(MyRecipeSearchForm myRecipeSearchForm, Model model) {

		//更新画面に遷移
		return TransitionTargetPageNameKeyword.MY_RECIPE_HTML;

	}

	@RequestMapping(value = TransitionTargetPageNameKeyword.BACK_TO_DETAIL, method = RequestMethod.POST)
	public String backToDetail(MyRecipeSearchForm myRecipeSearchForm, Model model,
			@RequestParam("previousScreen") String previousScreen) {

		//ジャンル取得

				model.addAttribute(
						"genreFormList",
						GenreForm.convertFrom(
								searchGenre.selectAll()));

				//レビュー取得
				model.addAttribute(
						"reviewFormList",
						RecipeReviewForm.convertFrom(
								recipeReview.selectAll()));

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

	@RequestMapping(value = "backToDetailFromCalendar", method = RequestMethod.POST)
	public String backToDetail(InsertCalendarForm insertCalendarForm, Model model,
			@RequestParam("previousScreen") String previousScreen) {
		MyRecipeSearchForm form = MyRecipeSearchForm
				.convertFrom(searchRecipe.searchRecipeByIdRecipe(insertCalendarForm.getIdRecipe()));
		//ジャンル取得
		model.addAttribute(
				"genreFormList",
				GenreForm.convertFrom(
						searchGenre.selectAll()));

		//レビュー取得
		model.addAttribute(
				"reviewFormList",
				RecipeReviewForm.convertFrom(
						recipeReview.selectAll()));

		//ユーザー情報取得
		model.addAttribute(
				"userFormList",
				LoginUserForm.convertFrom(
						login.selectAll()));

		model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, form);
		
		model.addAttribute("ingRecipeList",form.getIngRecipe().split(","));
		model.addAttribute("howCookList",form.getHowCook().split(","));


		if (previousScreen.equals(TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_SEARCH_HTML)) {
			return TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_SEARCH_HTML;
		} else {
			return TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_MY_RECIPE_HTML;
		}

	}

}
