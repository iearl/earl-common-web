package com.ants.web.boss.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ants.web.boss.base.BaseController;

/**  
 * @ClassName: ManagerController  
 * @Description: TODO(管理员功能菜单管理)  
 * @author 马高伟
 * @date 2018年4月11日  
 *    
 */
@Controller
public class ManagerMenuFunController extends BaseController {
	
	@RequestMapping("skipMenuFun")
	public String skipMenuFun(){
		return "manager/menuFun/index";
	}
}
