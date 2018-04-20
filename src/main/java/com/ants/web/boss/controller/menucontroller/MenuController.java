package com.ants.web.boss.controller.menucontroller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ants.common.web.utils.ResponseUtil;
import com.ants.facade.user.entity.TMenuInfo;
import com.ants.facade.user.service.TMenuInfoFacade;
import com.ants.web.boss.controller.usercontroller.UserLoginController;

/**  
 * @ClassName: MenuController  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author 马高伟
 * @date 2018年4月11日  
 *    
 */
@Controller
public class MenuController {
	private static final Log log = LogFactory.getLog(UserLoginController.class);
	
	@Autowired
	private TMenuInfoFacade tMenuInfoFacade;
	
	@RequestMapping("selectListTMenuInfo")
	public String selectListTMenuInfo(HttpServletResponse response) throws Exception{
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		List<TMenuInfo> selectListTMenuInfo = tMenuInfoFacade.selectListTMenuInfo();
//		JSONArray.fromObject(selectListTMenuInfo,jsonConfig).toString();
		JSONObject result=new JSONObject();
		String jsonArray = JSONArray.fromObject(selectListTMenuInfo,jsonConfig).toString();
		jsonArray = jsonArray.replace("\"menus\":[],", "");
		//result.put("menus", jsonArray);
		ResponseUtil.write(response, jsonArray);
		return null;
	}
}
