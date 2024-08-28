package com.example.rhizome.app.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.rhizome.app.domain.model.Calendar;
import com.example.rhizome.app.infra.entity.CalendarEntity;
import com.example.rhizome.app.infra.entity.RecipeEntity;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.CalendarRepository;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.infra.repository.RecipeRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CalendarController {


    private final CalendarRepository calendarRepository;
    private final RecipeRepository recipeRepository;
    private final HttpSession httpSession;
    @Autowired
	LoginRepository loginRepository;

    @Autowired
    public CalendarController(CalendarRepository calendarRepository, RecipeRepository recipeRepository,
            HttpSession httpSession) {
        this.calendarRepository = calendarRepository;
        this.recipeRepository = recipeRepository;
        this.httpSession = httpSession;
    }

    @GetMapping("/calendar")
    public String showCalendarList(Model model) {
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

        // ログインユーザーの idUser を基に関連するカレンダーデータを取得
        List<CalendarEntity> calendarEntityList = calendarRepository.findByIdUser(loggedInUserId);

        // CalendarEntityから必要なデータを抽出して、Modelに追加
        List<Calendar> calendars = CalendarEntity.createCalendarList(calendarEntityList);
        model.addAttribute("calendars", calendars);

        // レシピリストを取得し、Modelに追加
        List<RecipeEntity> recipeEntityList = recipeRepository.findAll(); // RecipeEntityのリストを取得するメソッド
        model.addAttribute("RecipeList", recipeEntityList);

        return "calendar"; // 表示するThymeleafのテンプレート名を返す
    }
}
