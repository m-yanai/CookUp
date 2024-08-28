package com.example.rhizome.app.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.rhizome.app.domain.model.Calendar;
import com.example.rhizome.app.domain.service.RegisterCalendar;
import com.example.rhizome.app.infra.entity.CalendarEntity;
import com.example.rhizome.app.infra.entity.RecipeEntity;
import com.example.rhizome.app.infra.entity.UserEntity;
import com.example.rhizome.app.infra.repository.CalendarRepository;
import com.example.rhizome.app.infra.repository.LoginRepository;
import com.example.rhizome.app.infra.repository.RecipeRepository;
import com.example.rhizome.app.presentation.controller.pageproperty.SessionKeyword;
import com.example.rhizome.app.presentation.form.CalendarForm;
import com.example.rhizome.app.presentation.form.InsertCalendarForm;
import com.example.rhizome.app.presentation.form.LoginUserForm;

import jakarta.servlet.http.HttpSession;

@Controller
public class InsertCalendarResultController {
	 private final RegisterCalendar registerCalendar;
	 private final CalendarRepository calendarRepository;
	 private final RecipeRepository recipeRepository;
	 private final HttpSession httpSession;
	
	@Autowired
	public  InsertCalendarResultController(RegisterCalendar registerCalendar,CalendarRepository calendarRepository, RecipeRepository recipeRepository,
            HttpSession httpSession) {
		this.registerCalendar = registerCalendar;
		this.calendarRepository = calendarRepository;
        this.recipeRepository = recipeRepository;
        this.httpSession = httpSession;	
	}
	@Autowired
	LoginRepository loginRepository;

	 @PostMapping("/insertCalendarResult")
	 public String resultCalendar(Model model, InsertCalendarForm insertCalendarForm,HttpSession session) { 

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
		 
		 String calDate = insertCalendarForm.getUpdateYear() + "/" 
				 	+ insertCalendarForm.getUpdateMonth() +  "/" 
				 	+ insertCalendarForm.getUpdateDay();
		 
		 CalendarForm calendarForm = formResultCalendar(calDate, insertCalendarForm.getIdUser(), insertCalendarForm.getIdRecipe());
		 registerCalendar.registerCalendar(CalendarForm.convertto(calendarForm));
		 
		 Integer loggedInUserId = (Integer) httpSession.getAttribute("loggedInUserId");
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
		
		 return "calendar";
	 
	 }
	 public CalendarForm formResultCalendar(String calDate, Integer idUser, Integer idRecipe) {
		 
		 return new CalendarForm(calDate,idUser,idRecipe);
		 
	 }
}
