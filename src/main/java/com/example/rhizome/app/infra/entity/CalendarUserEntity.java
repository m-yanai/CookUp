package com.example.rhizome.app.infra.entity;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.example.rhizome.app.domain.model.Calendar;
import com.example.rhizome.app.domain.model.CalendarUserInfo;
import com.example.rhizome.app.domain.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Calendar + User の Join 情報を保持するEntity
 */
@Entity
@Table(name = "calendar_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
		name = "calendar_with_user",
		includeAllAttributes = true
)
public class CalendarUserEntity {

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
	
	@ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private UserEntity user;
	
	
	// CalendarUserEntity を CalendarUserInfo に変換
	public static CalendarUserInfo createCalendarUserInfo(CalendarUserEntity calendarUserEntity) {
		return new CalendarUserInfo(
				new Calendar(
						calendarUserEntity.getCalDate(),
						calendarUserEntity.getIdUser() == null ? null : calendarUserEntity.getUser().getIdUser(),
						calendarUserEntity.getIdRecipe()
				),
				new User(
						calendarUserEntity.getUser() == null ? null : calendarUserEntity.getUser().getIdUser(),
						calendarUserEntity.getUser() == null ? null : calendarUserEntity.getUser().getIconUser(),
						calendarUserEntity.getUser() == null ? null : calendarUserEntity.getUser().getNmUser(),
						calendarUserEntity.getUser() == null ? null : calendarUserEntity.getUser().getMailAddress(),
						calendarUserEntity.getUser() == null ? null : calendarUserEntity.getUser().getPassword(),
						calendarUserEntity.getUser() == null ? null : calendarUserEntity.getUser().getDelFlg()
				)
		);
		
	}
	
	
	// CalendarUserEntity を CalendarUserInfo に変換(リスト版)
	public static List<CalendarUserInfo> createCalendarUserInfo(List<CalendarUserEntity> calendarUserEntityList){
		return calendarUserEntityList.stream().map(CalendarUserEntity::createCalendarUserInfo).toList();
	}

}
