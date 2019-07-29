package com.fotic.auth.service;
import com.fotic.auth.entity.LoginUser;
/**
 * 
* @Title: ILoginUserService.java 
* @Package com.fotic.auth.service 
* @Description: 登陆用户接口
* @author 王明月   
* @date 2017年7月11日 下午2:20:44
 */
public interface ILoginUserService {
	LoginUser getByUserNameAndPassword(String username,String password);
}
