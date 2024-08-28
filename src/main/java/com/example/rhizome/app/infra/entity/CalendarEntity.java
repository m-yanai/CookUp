package com.example.rhizome.app.infra.entity;

import java.util.List;

import com.example.rhizome.app.domain.model.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//カレンダーテーブルのEntity

@Entity
@Table(name = "calendar_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEntity {
	
	//日付
	@Id
	@Column(name = "cal_date", nullable = false)
	private String calDate;
	
	//ユーザーID
	@Column(name = "id_user", nullable = false)
	private Integer idUser;
	
	//レシピID
	@Column(name = "id_recipe", nullable = false)
	private Integer idRecipe;
	
	
	//Calendar から CalendarEntity に変換
	public static CalendarEntity convertFrom(Calendar calendar) {
		return new CalendarEntity(
				calendar.getCalDate(),
				calendar.getIdUser(),
				calendar.getIdRecipe()
				);
	}
	
	//CalendarEntity から Calendar に変換
	public static Calendar createCalendar(CalendarEntity calendarEntity) {
		return new Calendar(
				calendarEntity.getCalDate(),
				calendarEntity.getIdUser(),
				calendarEntity.getIdRecipe()
				);
	}
	
	//リスト版
	public static List<Calendar> createCalendarList(List<CalendarEntity> calendarEntityList){
		return calendarEntityList.stream().map(CalendarEntity::createCalendar).toList();
	}

}

