package com.fotic.webproject.business.service.impl;

import org.springframework.stereotype.Service;

import com.fotic.webproject.business.entity.Account;
import com.fotic.webproject.business.repository.AccountRepository;
import com.fotic.webproject.business.service.AccountService;
import com.fotic.webproject.jpadata.service.AbstractBaseService;

@Service
public class AccountServiceImpl extends AbstractBaseService<Account, AccountRepository> implements AccountService{

}
