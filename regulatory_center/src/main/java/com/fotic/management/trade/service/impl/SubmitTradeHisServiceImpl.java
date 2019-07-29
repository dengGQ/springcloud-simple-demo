package com.fotic.management.trade.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fotic.management.trade.dao.SubmitTradeHisMapper;
import com.fotic.management.trade.entity.SubmitTradeHis;
import com.fotic.management.trade.service.SubmitTradeHisService;
@Service
public class SubmitTradeHisServiceImpl implements SubmitTradeHisService {
	@Autowired
	private SubmitTradeHisMapper submitTradeHisMapper;
	@Override
	public List<SubmitTradeHis> findAll(SubmitTradeHis entity) {
		return submitTradeHisMapper.select(entity);
	}

}
