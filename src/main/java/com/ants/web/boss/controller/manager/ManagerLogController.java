package com.ants.web.boss.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**  
 * @ClassName: ManagerLogController  
 * @Description: TODO(系统日志管理)  
 * @author 马高伟
 * @date 2018年4月11日  
 *    
 */
@Controller
public class ManagerLogController {
	
	@RequestMapping("skipSysLog")
	public String skipSysLog(){
		return "manager/logInfo/index";
	}
}
