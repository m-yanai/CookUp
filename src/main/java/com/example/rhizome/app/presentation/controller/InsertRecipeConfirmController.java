package com.example.rhizome.app.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class InsertRecipeConfirmController {
	private final HttpSession httpSession;
    private final SearchGenre searchGenre;
//    private final SearchRecipe searchRecipe;
    @Autowired
	LoginRepository loginRepository;

	@PostMapping(TransitionTargetPageNameKeyword.INSERT_RECIPE_CONFIRM)
	public String InsertResipeConfirmController(@ModelAttribute InsertRecipeForm insertRecipeForm, Model model,HttpSession session) {


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
                    		searchGenre.selectAll()
                    ));

            // 登録用パラメータチェック
            boolean error = insertRecipeForm.hasErrorParameter(insertRecipeForm);

            // 入力内容に誤りがあるか
            if (!error) {
                // 誤りがあった場合
                // 誤りがあった箇所のエラーメッセージを表示
//                model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR, new MessageForm(String.join(", ", error) + "が不正です。"));
                // 画面に返す為の登録情報をセット
                model.addAttribute(PageReturnAttributeKeyword.INSERT_RECIPE_FORM, insertRecipeForm);
                // 登録画面に遷移
                return TransitionTargetPageNameKeyword.INSERT_RECIPE_INPUT_HTML;
            }

//            final String DUPLICATION = "既に登録済みのメールアドレスです。";

//            // 登録対象のメールアドレスを元に検索
//            List<Employee> employeeList = searchEmployee.searchEmployee(insertRecipeForm.getMailAddress());
//
//            // 検索結果があるか
//            if (!employeeList.isEmpty()) {
//                // 検索した結果0件ではない場合
//                // 重複を許さないため、エラー処理
////                model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR, new MessageForm(DUPLICATION));
//                // 画面に返す為の登録情報をセット
//                model.addAttribute(PageReturnAttributeKeyword.INSERT_RECIPE_FORM, insertRecipeForm);
//                // 登録画面に遷移
//                return TransitionTargetPageNameKeyword.INSERT_RECIPE_INPUT_HTML;
//            }

            System.out.println(insertRecipeForm.getIdUser());
            System.out.println(insertRecipeForm.getPicRecipe());
            System.out.println(insertRecipeForm.getNmPic());

            // 登録確認ページに遷移
            model.addAttribute(PageReturnAttributeKeyword.INSERT_RECIPE_FORM, insertRecipeForm);
            // 登録確認画面に遷移
            return TransitionTargetPageNameKeyword.INSERT_RECIPE_CONFIRM_HTML;


//        } catch (Exception e) {
//            // ログイン情報の破棄
//            httpSession.invalidate();
//
//            e.printStackTrace();
//
//            // エラーログ出力
//            LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE, e);
//            // エラー画面遷移
//            return TransitionTargetPageNameKeyword.ERROR_HTML;
//        }
	}
}
