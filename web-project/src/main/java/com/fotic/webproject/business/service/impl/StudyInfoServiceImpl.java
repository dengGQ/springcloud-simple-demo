package com.fotic.webproject.business.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.webproject.business.entity.StudyInfo;
import com.fotic.webproject.business.repository.StudyInfoRepository;
import com.fotic.webproject.business.service.StudyInfoService;
import com.fotic.webproject.jpadata.service.AbstractBaseService;

@Service
@Transactional
public class StudyInfoServiceImpl extends AbstractBaseService<StudyInfo, StudyInfoRepository> implements StudyInfoService{

	@Autowired
	private StudyInfoRepository sir;
	
	@Override
	public void updateById(StudyInfo t) {
		sir.saveAndFlush(t);
		/*
		StudyInfo studyInfo = new StudyInfo();
		this.insert(studyInfo);*/
	}
}
