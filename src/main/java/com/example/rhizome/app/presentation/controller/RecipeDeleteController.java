package com.example.rhizome.app.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.rhizome.app.domain.service.RecipeDelete;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.LoginUserForm;
import com.example.rhizome.app.presentation.form.MessageForm;
import com.example.rhizome.app.presentation.form.MyRecipeSearchForm;

import jakarta.servlet.http.HttpSession;

@Controller
public class RecipeDeleteController {

	RecipeDelete recipeDelete;
	SearchController searchController;
	HttpSession httpSession;

	@Autowired
	public RecipeDeleteController(RecipeDelete recipeDelete, SearchController searchController, HttpSession httpSession) {
		this.recipeDelete = recipeDelete;
		this.searchController = searchController;
		this.httpSession = httpSession;
	}

	@Autowired
	LoginRepository loginRepository;

	@ModelAttribute
	public MyRecipeSearchForm myRecipeSearchForm() {
		return new MyRecipeSearchForm();
	}

	@RequestMapping(value = TransitionTargetPageNameKeyword.RECIPE_DELETE, method = RequestMethod.POST)
	public String insertUpdate(MyRecipeSearchForm myRecipeSearchForm, Model model, HttpSession session) {
		final String DELETE_COMPLETE = "削除が完了しました。";

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

		recipeDelete.deleteRecipe(MyRecipeSearchForm.convertTo(myRecipeSearchForm));

		model.addAttribute("messageInfo", new MessageForm(DELETE_COMPLETE));
		
		searchController.Myrecipe(myRecipeSearchForm, model, session, null);
		
		//Myレシピに画面に遷移
		return TransitionTargetPageNameKeyword.MY_RECIPE;

	}
}
