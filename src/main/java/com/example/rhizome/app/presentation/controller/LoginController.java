package com.example.rhizome.app.presentation.controller;

import static com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.rhizome.app.domain.model.LoginInfo;
import com.example.rhizome.app.domain.model.LoginUser;
import com.example.rhizome.app.domain.service.Login;
import com.example.rhizome.app.domain.service.SelectRecipe;
import com.example.rhizome.app.infra.entity.RecipeGenreEntity;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.infra.repository.MyRecipeRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.LoginInfoForm;
import com.example.rhizome.app.presentation.form.LoginUserForm;
import com.example.rhizome.app.presentation.form.MessageForm;

import jakarta.servlet.http.HttpSession;;

/**
 * ログイン関連のコントローラ
 */
@Controller
public class LoginController {

	// ログ出力用インスタンス
	private final static Logger LOGGER = Logger.getLogger(LoginController.class.getName());

	// エラーメッセージ用の定数フィールド
	private final static String SEVERE_ERROR_MESSAGE = "エラーが発生しました！";

	HttpSession httpSession;
	Login login;

	@Autowired
	public LoginController(HttpSession httpSession, Login login) {
		this.httpSession = httpSession;
		this.login = login;
	}

	@Autowired
	SelectRecipe selectRecipe;

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	MyRecipeRepository myrecipeRepository;

	@ModelAttribute
	public LoginUserForm loginUserForm() {
		return new LoginUserForm();
	}

	/**
	 * 画面から渡ってきたパラメータのチェックを行う
	 *
	 * @param loginInfoForm mailAddress, password を保持したインスタンス
	 * @param model         ビューにデータを渡すためのコンテナ
	 * @return 遷移先の画面
	 */
	@RequestMapping(value = DO_LOGIN, method = RequestMethod.POST)
	public String doLogin(LoginInfoForm loginInfoForm, Model model, HttpSession session) {

		// ログに出力する
		final String MAIL_ADDRESS_PASSWORD_ERROR = "メールアドレス、パスワードの入力内容に誤りがあります";
		// 発生しうるErrorに対するメッセージ
		final String LOGIN_ERROR = "IDまたはパスワードが間違っています";

		try {
			// mailAddress, password の入力に誤りがあるかチェック
			if (!LoginUserForm.checkParameter(loginInfoForm)) {

				// mail,passwordが仕様を満たしていない場合
				model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR,
						new MessageForm(MAIL_ADDRESS_PASSWORD_ERROR));

				// エラーだった場合に login.html に処理を戻す。mailAddress, password を login.htmlに表示するために
				// ここで addAttribute する
				model.addAttribute(PageReturnAttributeKeyword.LOGIN_INFO_FORM, loginInfoForm);
				// login.html に遷移する様に設定
				return TransitionTargetPageNameKeyword.LOGIN_HTML;
			}

			// Login者情報インスタンスを作成
			LoginInfo loginInfo = createLoginInfo(httpSession, loginInfoForm);

			// login 可能かチェック
			if (!login.canLogin(loginInfo)) {
				// ログイン不可の場合、エラーメッセージをセットして入力された値とともに login.html を再表示
				// 画面に表示するエラーメッセージをセット
				model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR, new MessageForm(LOGIN_ERROR));
				// 画面に表示する mailAddress, password をセット
				model.addAttribute(PageReturnAttributeKeyword.LOGIN_INFO_FORM, loginInfoForm);
				// login.html に遷移
				return TransitionTargetPageNameKeyword.LOGIN_HTML;
			}

			// ログイン（インスタンス作成）
			LoginUser loginUser = login.doLogin(loginInfo);

			// ログイン者の情報をセッション情報に格納
			// セッションに loginUser 情報を保持し続ける = ログイン中として扱う
			httpSession.setAttribute(SessionKeyword.LOGIN_USER, LoginUserForm.convertFrom(loginUser));

			httpSession.setAttribute("loggedInUserId", loginUser.getIdUser()); // ここでセッションに設定する

			Integer userId = loginUser.getIdUser();
			// ユーザーIDを使ってユーザーアイコン情報を取得
			UserEntity loggedInUser = loginRepository.findByIdUser(userId).orElse(null);
			if (loggedInUser != null) {
				String userIcon = loggedInUser.getIconUser();

				model.addAttribute("userIcon", userIcon); // HTMLに渡すアイコン情報
				System.out.println(userIcon);
			}
			int recommendCount = 4; // おすすめするレシピの数
			List<RecipeGenreEntity> recommendedRecipes = selectRecipe.getRecommendedRecipes(recommendCount);

			model.addAttribute("recommendedRecipes", recommendedRecipes);
			System.out.println("Recommended Recipes:");
			for (RecipeGenreEntity recipegenre : recommendedRecipes) {
				System.out.println(recipegenre.getNmRecipe() + ":" + recipegenre.getPicRecipe() + ":"
						+ recipegenre.getCookTime() + ":"
						+ recipegenre.getLevelRecipe() + ":" + recipegenre.getGenre().getNmGenre()); // RecipeのtoString()が適切に実装されている場合
			}

			// メニュー画面に遷移
			return TransitionTargetPageNameKeyword.RECOMMEND_HTML;

		} catch (

		Exception e) {
			// ログイン情報の破棄
			httpSession.invalidate();
			e.printStackTrace();
			// エラーログ出力
			LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE, e);
			// エラー画面に遷移
			return TransitionTargetPageNameKeyword.ERROR_HTML;
		}
	}

	/**
	 * ログイン者情報のインスタンスを作成
	 *
	 * @param httpSession   セッション情報
	 * @param loginInfoForm mailAddress, password を保持したインスタンス
	 */
	private static LoginInfo createLoginInfo(HttpSession httpSession, LoginInfoForm loginInfoForm) {
		// セッションからログイン者情報を取得
		LoginUserForm loginUserForm = (LoginUserForm) httpSession.getAttribute(SessionKeyword.LOGIN_USER);

		// セッションにログイン者情報があるか
		if (loginUserForm != null) {
			// セッションにログイン者情報がある場合
			return new LoginInfo(loginUserForm.getMailAddress(), loginUserForm.getPassword());
		}

		// セッションにない場合は画面からの input 値 を取得
		return new LoginInfo(
				loginInfoForm.getMailAddress(),
				loginInfoForm.getPassword());
	}
}
