package com.ants.web.boss.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**  
 * @ClassName: ManagerIndiController  
 * @Description: TODO(管理员个人信息管理)  
 * @author 马高伟
 * @date 2018年4月11日  
 *    
 */
@Controller
public class ManagerIndiController {
	
	@RequestMapping("skipIndiInfo")
	public String skipIndiInfo(){
		return "manager/idviInfo/index";
	}
	
	@RequestMapping("refreshCache")
	public void refreshCache(){
		
	}
}
