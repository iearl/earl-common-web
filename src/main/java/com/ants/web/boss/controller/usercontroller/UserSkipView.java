package com.ants.web.boss.controller.usercontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ants.common.web.constants.RoleInfoConstants;

/**
 * @ClassName: UserSkipView
 * @Description: TODO(控制用户跳转页面类)
 * @author 马高伟
 * @date 2018年4月13日
 * 
 */
@Controller
public class UserSkipView {
	/**
	 * selfImproInfo：跳转到完善个人信息页面
	 */
	@RequestMapping("selfImproInfo")
	public String selfImproInfo(){
		return "selfImproInfo";
	}

	// 找回密码
	@RequestMapping("findPwd2")
	public String findPwd2() {
		return "findPwd2";
	}

	// 找回密码第一步
	@RequestMapping("findPwd")
	public String findPwd() {
		return "findPwd1";
	}
	
	/**
	 * skipCoView:跳转到管理员主页
	 */
	@RequestMapping("skipManagerView")
	public String skipManagerView(){
		return RoleInfoConstants.managerView;
	}
	
	/**
	 * skipjobhunterView:跳转到求职者主页
	 */
	@RequestMapping("skipjobhunterView")
	public String skipjobhunterView() {
		return RoleInfoConstants.jobhunterView;
	}
	
	/**
	 * skipCoView:跳转到公司主页
	 */
	@RequestMapping("skipCoView")
	public String skipCoView() {
		return RoleInfoConstants.companyView;
	}
	
	/**
	 * loginReq:跳转到登录页面
	 */
	@RequestMapping("loginReq")
	public String loginReq(){
		return "login";
	}

	/**
	 * skipHomePage:跳转到平台首页
	 */
	@RequestMapping("skipHomePage")
	public String skipHomePage() {
		return "index";
	}
}
