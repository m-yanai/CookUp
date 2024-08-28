package com.example.rhizome.app.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.rhizome.app.domain.service.SearchGenre;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.GenreForm;
import com.example.rhizome.app.presentation.form.InsertRecipeForm;
import com.example.rhizome.app.presentation.form.LoginUserForm;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InsertRecipeInputController {
	private final HttpSession httpSession;
	private final SearchGenre searchGenre;
	@Autowired
	LoginRepository loginRepository;

	@GetMapping(TransitionTargetPageNameKeyword.INSERT_RECIPE_INPUT)
	public String InsertResipeInputController(@ModelAttribute InsertRecipeForm insertRecipeForm, Model model,
			HttpSession session) {

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

		//		try {
		// 部署コードリストを作成し、画面に返す値としてセット
		model.addAttribute("genreFormList",
				GenreForm.convertFrom(searchGenre.selectAll()));
		// パラメータをセット
		model.addAttribute(PageReturnAttributeKeyword.INSERT_RECIPE_FORM, insertRecipeForm);

		return TransitionTargetPageNameKeyword.INSERT_RECIPE_INPUT_HTML;
		//		} catch (Exception e) {
		//			// ログイン情報の破棄
		//			httpSession.invalidate();
		//
		//			e.printStackTrace();
		//
		//			// エラーログ出力
		//			LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE, e);
		//			// エラー画面遷移
		//			return TransitionTargetPageNameKeyword.ERROR_HTML;
		//		}
	}

	@PostMapping(TransitionTargetPageNameKeyword.INSERT_RECIPE_INPUT)
	public String InsertResipeInput(@ModelAttribute InsertRecipeForm insertRecipeForm, Model model,
			HttpSession session) {
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
		//		try {
		// 部署コードリストを作成し、画面に返す値としてセット
		model.addAttribute("genreFormList",
				GenreForm.convertFrom(searchGenre.selectAll()));
		// パラメータをセット
		model.addAttribute(PageReturnAttributeKeyword.INSERT_RECIPE_FORM, insertRecipeForm);

		return TransitionTargetPageNameKeyword.INSERT_RECIPE_INPUT_HTML;
		//		} catch (Exception e) {
		//			// ログイン情報の破棄
		//			httpSession.invalidate();
		//
		//			e.printStackTrace();
		//
		//			// エラーログ出力
		//			LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE, e);
		//			// エラー画面遷移
		//			return TransitionTargetPageNameKeyword.ERROR_HTML;
		//		}
	}
}
