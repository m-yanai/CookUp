package com.example.rhizome.app.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.rhizome.app.presentation.form.InsertCalendarForm;
import com.example.rhizome.app.presentation.form.LoginUserForm;
import com.example.rhizome.app.presentation.form.MyRecipeSearchForm;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class InsertCalendarController {

	@Autowired
	HttpSession httpSession;
	@Autowired
	LoginRepository loginRepository;

	@ModelAttribute
	public InsertCalendarForm insertCalendarForm() {
		return new InsertCalendarForm();
	}

	@RequestMapping(value = TransitionTargetPageNameKeyword.INSERT_CALENDAR, method = RequestMethod.POST)
	public String insertCalendar(@ModelAttribute MyRecipeSearchForm myRecipeSearchForm, Model model,
			HttpSession session,
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

		InsertCalendarForm calendarList = InsertCalendarForm.convertFromMyRecipe(myRecipeSearchForm);
		model.addAttribute(PageReturnAttributeKeyword.INSERT_CALENDAR_FORM, calendarList);
		System.out.println(calendarList.getNmRecipe());
		model.addAttribute("previousScreen", previousScreen);
		model.addAttribute(PageReturnAttributeKeyword.MY_RECIPE_SEARCH_FORM, myRecipeSearchForm);
		//更新画面に遷移
		//return TransitionTargetPageNameKeyword.CALENDAR_HTML;
		return TransitionTargetPageNameKeyword.INSERT_CALENDAR_HTML;
	}
}
