package com.ants.web.boss.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**  
 * @ClassName: ManagerHelpAndFeed  
 * @Description: TODO(管理员帮助反馈类)  
 * @author 马高伟
 * @date 2018年4月11日  
 *    
 */
@Controller
public class ManagerHelpAndFeed {
	@RequestMapping("skipFeedback")
	public String skipFeedback(){
		return "manager/help/index";
	}
	@RequestMapping("skipHelp")
	public String skipHelp(){
		return "manager/help/index ";
	}
}
