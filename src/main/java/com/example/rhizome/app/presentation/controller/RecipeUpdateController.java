package com.example.rhizome.app.presentation.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rhizome.app.domain.model.Recipe;
import com.example.rhizome.app.domain.service.Login;
import com.example.rhizome.app.domain.service.RecipeDetailFromMyRecipe;
import com.example.rhizome.app.domain.service.RecipeReview;
import com.example.rhizome.app.domain.service.RecipeUpdate;
import com.example.rhizome.app.domain.service.SearchGenre;
import com.example.rhizome.app.infra.entity.RecipeEntity;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.GenreForm;
import com.example.rhizome.app.presentation.form.LoginUserForm;
import com.example.rhizome.app.presentation.form.MessageForm;
import com.example.rhizome.app.presentation.form.MyRecipeSearchForm;
import com.example.rhizome.app.presentation.form.RecipeDetailUpdateForm;
import com.example.rhizome.app.presentation.form.RecipeReviewForm;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RecipeUpdateController {
	private final HttpSession httpSession;
	private final SearchGenre searchGenre;
	private final RecipeUpdate recipeUpdate;
	private final RecipeDetailFromMyRecipe recipeDetailFromMyRecipe;
	@Autowired
	Login login;

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	RecipeReview recipeReview;

	@ModelAttribute
	public MyRecipeSearchForm myRecipeSearchForm() {
		return new MyRecipeSearchForm();
	}

	@ModelAttribute
	public RecipeDetailUpdateForm recipeDetailUpdateForm() {
		return new RecipeDetailUpdateForm();
	}

	@RequestMapping(value = TransitionTargetPageNameKeyword.RECIPE_UPDATE, method = RequestMethod.POST)
	public String insertUpdate(MyRecipeSearchForm myRecipeSearchForm, HttpSession session,
			RecipeDetailUpdateForm recipeDetailUpdateForm,
			Model model,
			@RequestParam("previousScreen") String previousScreen) {

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
		recipeDetailUpdateForm = RecipeDetailUpdateForm.changeForm(myRecipeSearchForm);
		model.addAttribute("genreFormList",
				GenreForm.convertFrom(searchGenre.selectAll()));
		model.addAttribute(PageReturnAttributeKeyword.RECIPE_DETAIL_UPDATE_FORM, recipeDetailUpdateForm);
		model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, myRecipeSearchForm);
		model.addAttribute("previousScreen", previousScreen);
		//更新画面に遷移
		return TransitionTargetPageNameKeyword.RECIPE_UPDATE_HTML;

	}

	////更新DB処理
	@RequestMapping(value = TransitionTargetPageNameKeyword.RECIPE_UPDATE_RESULT, method = RequestMethod.POST)
	public String recipeDetailFromMyRecipe(RecipeReviewForm recipeReviewForm, HttpSession session,
			MyRecipeSearchForm myRecipeSearchForm,
			RecipeDetailUpdateForm recipeDetailUpdateForm, Model model) {

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
		// recipeDetailUpdateForm から idRecipe を取得する
		Integer idRecipe = recipeDetailUpdateForm.getIdRecipe();
		System.out.println("idRecipe:" + idRecipe + " を更新します");

		// recipeDetailFromMyRecipe を使って Recipe オブジェクトを取得し、RecipeEntity に変換する
		Optional<RecipeEntity> recipeEntity = recipeDetailFromMyRecipe.searchByIdRecipe(idRecipe);

		// RecipeEntity が存在しない場合は例外をスローする
		RecipeEntity recipeEntityResult = recipeEntity
				.orElseThrow(() -> new IllegalArgumentException("RecipeEntity is not present"));

		//Recipe型へ変換
		Recipe beforeRecipe = RecipeEntity.createRecipe(recipeEntityResult);

		if (recipeDetailUpdateForm.getNmPic().isBlank()) {
			recipeDetailUpdateForm.setNmPic(recipeDetailUpdateForm.getPicRecipe());
		}

		//更新DB処理
		recipeUpdate.updateRecipe(beforeRecipe, RecipeDetailUpdateForm.convertTo(recipeDetailUpdateForm));

		//画像登録処理
		String base64Image = recipeDetailUpdateForm.getPicRecipe(); // Base64画像データ
		// Base64データ部分の抽出
		String[] parts = base64Image.split(",");
		String base64Data = parts[1];
		// ファイル名を指定してデコードと保存を行うメソッドの呼び出し
		saveImage(base64Data, "src/main/resources/static/img/Recipe/" + recipeDetailUpdateForm.getNmPic());

		recipeDetailUpdateForm.setPicRecipe("img/Recipe/" + recipeDetailUpdateForm.getNmPic());

		System.out.println(recipeDetailUpdateForm.getIdUser());
		System.out.println(recipeDetailUpdateForm.getNmRecipe());
		System.out.println(recipeDetailUpdateForm.getPicRecipe());
		System.out.println(recipeDetailUpdateForm.getIdGenre());
		System.out.println(recipeDetailUpdateForm.getLevelRecipe());
		System.out.println(recipeDetailUpdateForm.getIngRecipe());
		System.out.println(recipeDetailUpdateForm.getHowCook());
		System.out.println(recipeDetailUpdateForm.getCookTime());
		System.out.println(recipeDetailUpdateForm.getNmPic());

		//ジャンル取得
		model.addAttribute(
				"genreFormList",
				GenreForm.convertFrom(
						searchGenre.selectAll()));

		//レビュー取得
		model.addAttribute(
				"reviewFormList",
				RecipeReviewForm.convertFrom(
						//recipeReview.selectAll()));

						recipeReview.searchByIdRecipe(idRecipe)));

		//ユーザー情報取得
		model.addAttribute(
				"userFormList",
				LoginUserForm.convertFrom(
						login.selectAll()));

		model.addAttribute("ingRecipeList", myRecipeSearchForm.getIngRecipe().split(","));
		model.addAttribute("howCookList", myRecipeSearchForm.getHowCook().split(","));

		//詳細へ値セット
		myRecipeSearchForm = MyRecipeSearchForm
				.convertFrom(recipeDetailFromMyRecipe.searchByIdRecipe(idRecipe));
		model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, myRecipeSearchForm);
		final String UPDATE_COMPLETE = "更新が完了しました。";
		model.addAttribute("messageInfo", new MessageForm(UPDATE_COMPLETE));

		//レシピ詳細に遷移
		return TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_MY_RECIPE＿HTML;

	}

	//レシピ詳細に戻る処理
	@RequestMapping(value = TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_MY_RECIPE, method = RequestMethod.POST)
	public String recipeDetailFromMyRecipe(MyRecipeSearchForm myRecipeSearchForm, Model model, HttpSession session) {

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
		model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, myRecipeSearchForm);

		//レシピ詳細に遷移
		return TransitionTargetPageNameKeyword.RECIPE_DETAIL_FROM_MY_RECIPE＿HTML;

	}

	public static void saveImage(String base64Data, String outputFile) {
		try {
			// Base64文字列をバイト配列にデコード
			byte[] bytes = Base64.getDecoder().decode(base64Data);

			// フォルダが存在しない場合は作成
			Path path = Paths.get(outputFile);
			Files.createDirectories(path.getParent());

			// バイト配列をファイルに書き込み
			FileOutputStream fos = new FileOutputStream(outputFile);
			fos.write(bytes);
			fos.close();

			System.out.println("画像を保存しました: " + outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
