package com.ants.web.boss.base;

import javax.servlet.http.HttpServletRequest;

import com.ants.facade.user.entity.TUserInfo;



public interface UserLoginedAware {

	/**
	 * 取得登录的用户
	 * 
	 * @return
	 */
	public TUserInfo getLoginedUser(HttpServletRequest request);
}
