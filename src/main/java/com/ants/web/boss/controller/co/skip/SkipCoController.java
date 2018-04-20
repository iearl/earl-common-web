package com.ants.web.boss.controller.co.skip;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ants.web.boss.base.BaseController;

/**  
 * @ClassName: SkipCoController  
 * @Description: TODO(公司会员页面跳转)  
 * @author 马高伟
 * @date 2018年4月14日  
 *    
 */
@Controller
public class SkipCoController extends BaseController{
	//deliInfo.html
	/**
	 * 跳转到公司登录人信息修改页面
	 */
	@RequestMapping("skipCoDeliInfo")
	public String skipCoDeliInfo(){
		return "company/deliInfo";
	}
	
	/**
	 * 跳转到公司登录人信息修改页面
	 */
	@RequestMapping("selfInfoChangeSkip")
	public String selfInfochangeSkip(){
		return "company/selfInfoChange";
	}
	/**
	 * 跳转到公司登录人修改密码界面
	 */
	@RequestMapping("coPwdChange")
	public String coPwdChange(){
		return "company/coPwdChange";
	}
	/**
	 * 跳转到求职信息发布页面
	 */
	@RequestMapping("skipPubPosition")
	public String skipPubPosition(){
		return "company/pubPosition";
	}
	/**
	 *跳转到公司信息完善页面 
	 */
	@RequestMapping("skipCoImproInfo")
	public String skipCoImproInfo(){
		return "company/coImproInfo";
	}
	/**
	 * 公司会员信息管理跳转页面
	 */
	@RequestMapping("skipCompanyInfo")
	public String skipCompanyInfo(){
		return "company/coInfomanager";
	}
	/**
	 * 公司会员职位管理跳转页面
	 */
	@RequestMapping("skipCoPosition")
	public String skipCoPosition(){
		return "company/coPositionInfomanager";
	}
}
