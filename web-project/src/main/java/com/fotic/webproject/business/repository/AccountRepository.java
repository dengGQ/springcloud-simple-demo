package com.fotic.webproject.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.fotic.webproject.business.entity.Account;
import com.fotic.webproject.jpadata.repository.BaseRepository;

/**
 * 账户repository
 * @author Administrator
 *
 */
public interface AccountRepository extends BaseRepository<Account, Long>{

	@EntityGraph(value="Account", type=EntityGraphType.LOAD)
	List<Account> getByAccountName(String name);
	
}
