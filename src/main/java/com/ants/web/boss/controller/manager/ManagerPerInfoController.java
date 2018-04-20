package com.ants.web.boss.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ants.web.boss.base.BaseController;

/**  
 * @ClassName: ManagerPerInfoController  
 * @Description: TODO(包括求职会员和公司会员的人员信息管理)  
 * @author 马高伟
 * @date 2018年4月11日  
 *    
 */
@Controller
public class ManagerPerInfoController extends BaseController {
	

	@RequestMapping("skipJobHunter")
	public String skipJobHunter(){
		return "manager/perInfo/index";
	  //return "manager/comInfo/index";
	}
	@RequestMapping("skipCompany")
	public String skipCompany(){
		return "manager/perInfo/index";
	}
}
