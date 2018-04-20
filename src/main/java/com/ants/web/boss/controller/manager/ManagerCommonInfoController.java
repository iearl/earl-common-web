package com.ants.web.boss.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ants.web.boss.base.BaseController;

/**  
 * @ClassName: ManagerCommonInfoController  
 * @Description: TODO(管理员公共信息管理)  
 * @author 马高伟
 * @date 2018年4月11日  
 *    
 */
@Controller
public class ManagerCommonInfoController extends BaseController {
	
	@RequestMapping("skipAreaInfo")
	public String skipAreaInfo(){
		return "manager/comInfo/index";
	}
	@RequestMapping("skipSubInfo")
	public String skipSubInfo(){
		return "manager/comInfo/index";
	}
	@RequestMapping("skipJobInfo")
	public String skipJobInfo(){
		return "manager/comInfo/index";
	}
}
