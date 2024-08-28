
package com.example.rhizome.app.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.rhizome.app.domain.service.SelectRecipe;
import com.example.rhizome.app.infra.entity.RecipeGenreEntity;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.infra.repository.MyRecipeRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.form.LoginUserForm;

import jakarta.servlet.http.HttpSession;

@Controller
public class RecoController {

	//	    @ModelAttribute
	//	    public MessageForm messageForm() {
	//	        return new MessageForm();
	//	    }

	/**
	 * メニュー画面に遷移
	 */
	@Autowired
	SelectRecipe selectRecipe;

	@Autowired
	MyRecipeRepository myrecipeRepository;

	@Autowired
	LoginRepository loginRepository;

	@GetMapping("/recommend")
	public String homeRecommend(HttpSession session, Model model) {
		int recommendCount = 4; // おすすめするレシピの数
		List<RecipeGenreEntity> recommendedRecipes = selectRecipe.getRecommendedRecipes(recommendCount);

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

		model.addAttribute("recommendedRecipes", recommendedRecipes);
		System.out.println("Recommended Recipes:");
		for (RecipeGenreEntity recipegenre : recommendedRecipes) {
			System.out.println(
					recipegenre.getNmRecipe() + ":" + recipegenre.getPicRecipe() + ":" + recipegenre.getCookTime() + ":"
							+ recipegenre.getLevelRecipe() + ":" + recipegenre.getGenre().getNmGenre()); // RecipeのtoString()が適切に実装されている場合
		}
		return "recommend";
	}
}
