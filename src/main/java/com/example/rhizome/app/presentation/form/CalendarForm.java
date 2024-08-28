package com.example.rhizome.app.presentation.form;

import java.util.List;

import com.example.rhizome.app.domain.model.Calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 画面の表示に使うカレンダー管理用のクラス
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarForm {
	//日付
	private String calDate;
	
	//ユーザーID
	private Integer idUser;
	
	//レシピID
	private Integer idRecipe;

    /**
     * business 層の Calendar クラスから画面用（presentation 層）の CalendarForm に
     * 変換します。
     *
     * @param calendar business 層のCalendar インスタンス
     * @return 画面用（presentation 層）の CalendarForm インスタンス
     */
    public static CalendarForm convertFrom(Calendar calendar) {
        return new CalendarForm(calendar.getCalDate(), calendar.getIdUser(),calendar.getIdRecipe());
        
    }
    public static Calendar convertto(CalendarForm calendarForm) {
    	return new Calendar(calendarForm.getCalDate(), calendarForm.getIdUser(),calendarForm.getIdRecipe());
    }
   
    /**
     * business 層の Calendar クラスから画面用（presentation 層）の CalendarForm に
     * 変換します。（リスト版）
     *
     * @param calendarList business 層の Calendar インスタンスのリスト
     * @return 画面用（presentation 層）の CalendarForm インスタンスのリスト
     */
    public static List<CalendarForm> convertFrom(List<Calendar> calendarList) {
        return calendarList.stream().map(CalendarForm::convertFrom).toList();
    }    
}
