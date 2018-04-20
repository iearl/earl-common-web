package com.ants.web.boss.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**  
 * @ClassName: ManagerMemberInfoController  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author 马高伟
 * @date 2018年4月11日  
 *    
 */
@Controller
public class ManagerMemberInfoController {
	
	@RequestMapping("skipResInfo")
	public String skipResInfo(){
		return "manager/menberInfo/index";
	}

	@RequestMapping("skipDeliverInfo")
	public String skipDeliverInfo(){
		return "manager/menberInfo/index";
	}
	//skipPositionInfo skipCoInfo
	@RequestMapping("skipPositionInfo")
	public String skipPositionInfo(){
		return "manager/menberInfo/index";
	}
	@RequestMapping("skipCoInfo")
	public String skipCoInfo(){
		return "manager/menberInfo/index";
	}
}
