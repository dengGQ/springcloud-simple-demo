package com.fotic.webproject.business.service;

import com.fotic.webproject.business.entity.StudyInfo;
import com.fotic.webproject.jpadata.service.BaseService;


public interface StudyInfoService extends BaseService<StudyInfo>{
	void updateById(StudyInfo t);
}
