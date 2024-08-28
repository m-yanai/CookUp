package com.example.rhizome.app.domain.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rhizome.app.domain.model.Calendar;
import com.example.rhizome.app.domain.service.RegisterCalendar;
import com.example.rhizome.app.infra.entity.CalendarEntity;
import com.example.rhizome.app.infra.repository.CalendarRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor


public class RegisterCalendarImpl implements RegisterCalendar{
	private final CalendarRepository calendarRepository;
	@Override
	public void registerCalendar(Calendar calendar) {	
		// TODO 自動生成されたメソッド・スタブ
		calendarRepository.save(CalendarEntity.convertFrom(calendar));
	}
}
	

	
	

