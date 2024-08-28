package com.example.rhizome.app.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rhizome.app.domain.service.SearchGenre;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.GenreForm;
import com.example.rhizome.app.presentation.form.LoginUserForm;
import com.example.rhizome.app.presentation.form.MyRecipeSearchForm;
import com.example.rhizome.app.presentation.form.RecipeDetailUpdateForm;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class RecipeUpdateConfirmController {
	
	@Autowired
	LoginRepository loginRepository;

	private final SearchGenre searchGenre;
	@ModelAttribute
    public RecipeDetailUpdateForm recipeDetailUpdateForm() {
        return new RecipeDetailUpdateForm();
    }
	
	@RequestMapping(value = TransitionTargetPageNameKeyword.RECIPE_UPDATE_CONFIRM, method = RequestMethod.POST)
	public String insertUpdateConfirm(MyRecipeSearchForm myRecipeSearchForm, RecipeDetailUpdateForm recipeDetailUpdateForm, 
			Model model,HttpSession session ,@RequestParam("previousScreen") String previousScreen) {
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
		model.addAttribute("genreFormList",
				GenreForm.convertFrom(searchGenre.selectAll()));
		model.addAttribute(PageReturnAttributeKeyword.RECIPE_DETAIL_UPDATE_FORM,recipeDetailUpdateForm);
		model.addAttribute("previousScreen",previousScreen);
		model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, myRecipeSearchForm);
		//model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM,myRecipeSearchForm);
		//更新確認画面に遷移
		return TransitionTargetPageNameKeyword.RECIPE_UPDATE_CONFIRM_HTML;
		
	}
	
	@RequestMapping(value = TransitionTargetPageNameKeyword.RECIPE_UPDATE_RETURN, method = RequestMethod.POST)
	public String inputUpdate(MyRecipeSearchForm myRecipeSearchForm, Model model,HttpSession session,RecipeDetailUpdateForm recipeDetailUpdateForm) {
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
		model.addAttribute("genreFormList",
				GenreForm.convertFrom(searchGenre.selectAll()));
		model.addAttribute(PageReturnAttributeKeyword.RECIPE_DETAIL_UPDATE_FORM,recipeDetailUpdateForm);
		//model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, myRecipeSearchForm);
		//更新画面に遷移
		return TransitionTargetPageNameKeyword.RECIPE_UPDATE_HTML;
		
	}
}
