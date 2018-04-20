package com.ants.web.boss.base;

import javax.servlet.http.HttpServletRequest;

import com.ants.common.web.constants.SessionConstant;
import com.ants.facade.user.entity.TUserInfo;


/**
 * 
 * @描述: Web系统权限模块基础支撑Action.
 * @作者: 马高伟.
 * @创建: 2018-3-16 .
 * @版本: V1.0
 * 
 */
public class BaseController implements UserLoginedAware {

	/**
	 * 取出当前登录用户对象
	 */
	public TUserInfo getLoginedUser(HttpServletRequest request) {
		TUserInfo user = (TUserInfo) request.getSession().getAttribute(SessionConstant.USER_SESSION_KEY);
		return user;
	}


}
