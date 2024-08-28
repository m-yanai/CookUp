package com.example.rhizome.app.presentation.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.rhizome.app.domain.service.RegisterRecipe;
import com.example.rhizome.app.domain.service.SearchGenre;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.GenreForm;
import com.example.rhizome.app.presentation.form.InsertRecipeForm;
import com.example.rhizome.app.presentation.form.LoginUserForm;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * recipe を投稿して結果を返す為のコントローラ
 */
@Controller
@RequiredArgsConstructor
public class InsertRecipeResultController {
	@Autowired
	LoginRepository loginRepository;

	// ログ出力用インスタンス
	private final static Logger LOGGER = Logger.getLogger(InsertRecipeConfirmController.class.getName());

	private final HttpSession httpSession;
	private final RegisterRecipe registerRecipe;
	private final SearchGenre searchGenre;

	@PostMapping("/insertRecipeResult")
	public String InsertResipeReusultController(@ModelAttribute InsertRecipeForm insertRecipeForm, Model model,HttpSession session) {

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
		// 部署一覧を取得し、画面に返す用のクラスに詰め替える
		// 作成した部署一覧をセット
		model.addAttribute(
				"genreFormList",
				GenreForm.convertFrom(
						searchGenre.selectAll()));

		// Confirm画面から渡ってきた値をチェック
		List<String> error = validateParameter(
				insertRecipeForm.getNmRecipe(),
				insertRecipeForm.getPicRecipe(),
				insertRecipeForm.getIngRecipe(),
				insertRecipeForm.getLevelRecipe(),
				insertRecipeForm.getHowCook(),
				insertRecipeForm.getCookTime(),
				insertRecipeForm.getIdGenre());

		// エラーが1件以上有る場合
		if (!error.isEmpty()) {
			// エラー内容を画面に表示
			//				model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR,
			//						new MessageForm(String.join(", ", error) + "が不正です。"));
			// 登録画面に遷移
			return TransitionTargetPageNameKeyword.INSERT_RECIPE_INPUT_HTML;
		}
		
		String base64Image = insertRecipeForm.getPicRecipe(); // Base64画像データ
		 // Base64データ部分の抽出
        String[] parts = base64Image.split(",");
        String base64Data = parts[1];
        // ファイル名を指定してデコードと保存を行うメソッドの呼び出し
        saveImage(base64Data, "src/main/resources/static/img/Recipe/" + insertRecipeForm.getNmPic());
		
		insertRecipeForm.setPicRecipe("img/Recipe/" + insertRecipeForm.getNmPic());
		
        String splitIngRecipe = insertRecipeForm.getIngRecipe().replace("\n", ","); // 改行を`,`に置換
		insertRecipeForm.setIngRecipe(splitIngRecipe);
		String splitCookList = insertRecipeForm.getHowCook().replace("\n", ","); // 改行を`,`に置換
		insertRecipeForm.setHowCook(splitCookList);
		
		// 登録処理
		registerRecipe.registerRecipe(InsertRecipeForm.convertTo(insertRecipeForm));
		// 登録結果画面に遷移
		return "insertRecipeResult";

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

	/**
	 * @param employeeName 登録対象の名前
	 * @param employeeKana 登録対象のカナ
	 * @param mailAddress  登録対象のメールアドレス
	 * @param password     登録対象のパスワード
	 * @param management   システム権限
	 * @param departmentId 部署ID
	 * @return エラーが有った場合はエラー文言、無ければ空のリストが返る
	 */
	private List<String> validateParameter(
			String nmRecipe,
			String picRecipe,
			String ingRecipe,
			Integer levelRecipe,
			String howCook,
			Integer cookTime,
			Integer idGenre) {

		final String RECIPE_NAME_NULL_BLANK = "レシピ名";
		final String RECIPE_IMAGE_NULL_BLANK = "レシピ画像";
		final String INGREDIENTS_NULL_BLANK = "レシピ材料";
		final String RECIPEL_LEVEL_ERROR = "調理難易度";
		final String HOW_COOK_NULL_BLANK = "調理手順";
		final String COOK_TIME_ERROR = "調理時間";
		final String GENRE_ID_NULL_BLANK = "ジャンル";

		List<String> error = new ArrayList<>();

		// null or 空文字判定
		if (isNullOrBlank(nmRecipe)) {
			// エラー内容を追加
			error.add(RECIPE_NAME_NULL_BLANK);
		}

		// null or 空文字判定
		if (isNullOrBlank(picRecipe)) {
			// エラー内容を追加
			error.add(RECIPE_IMAGE_NULL_BLANK);
		}

		// null or 空文字判定
		if (isNullOrBlank(ingRecipe)) {
			// エラー内容を追加
			error.add(INGREDIENTS_NULL_BLANK);
		}

		// 調理難易度の値が不正か
		if (levelRecipe == null) {
			// エラー内容を追加
			error.add(RECIPEL_LEVEL_ERROR);
		}

		// null or 空文字判定
		if (isNullOrBlank(howCook)) {
			// エラー内容を追加
			error.add(HOW_COOK_NULL_BLANK);
		}

		// 調理時間の値が不正か
		if (cookTime == null) {
			// エラー内容を追加
			error.add(COOK_TIME_ERROR);
		}

		// ジャンルの値が不正か
		if (idGenre == null) {
			// エラー内容を追加
			error.add(GENRE_ID_NULL_BLANK);
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
