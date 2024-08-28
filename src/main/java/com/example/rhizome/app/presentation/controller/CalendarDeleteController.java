package com.example.rhizome.app.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.CalendarRepository;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.infra.repository.RecipeRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CalendarDeleteController {

	private final CalendarRepository calendarRepository;
	private final RecipeRepository recipeRepository;
	private final HttpSession httpSession;

	@Autowired
	public CalendarDeleteController(CalendarRepository calendarRepository, RecipeRepository recipeRepository,
			HttpSession httpSession) {
		this.calendarRepository = calendarRepository;
		this.recipeRepository = recipeRepository;
		this.httpSession = httpSession;
	}

	@Autowired
	LoginRepository loginRepository;

	@PostMapping("/calendarDelete")
	public String deleteCalendar(Model model, @RequestParam String calDate) {
		Integer loggedInUserId = (Integer) httpSession.getAttribute("loggedInUserId");
		// ユーザーIDを使ってユーザーアイコン情報を取得
		UserEntity loggedInUser = loginRepository.findByIdUser(loggedInUserId).orElse(null);
		if (loggedInUser != null) {
			String userIcon = loggedInUser.getIconUser();

			model.addAttribute("userIcon", userIcon); // HTMLに渡すアイコン情報
			System.out.println(userIcon);
		}
		if (loggedInUserId == null) {
			return "redirect:/login"; // ログインしていない場合はログインページにリダイレクト
		}

		// カレンダーエントリをIDとユーザーIDで特定して削除
		calendarRepository.deleteById(calDate);

		return "redirect:/calendar"; // 削除後にカレンダーページにリダイレクト
	}
}
