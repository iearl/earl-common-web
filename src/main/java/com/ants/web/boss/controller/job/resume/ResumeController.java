package com.ants.web.boss.controller.job.resume;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.ants.common.utils.DateUtil;
import com.ants.common.web.utils.ResponseUtil;
import com.ants.facade.user.entity.TUserInfo;
import com.ants.facade.user.entity.TWorkShow;
import com.ants.facade.user.service.TWorkShowFacade;
import com.ants.web.boss.base.BaseController;

/**  
 * @ClassName: ResumeController  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author 马高伟
 * @date 2018年4月19日  
 *    
 */
@Controller
public class ResumeController extends BaseController{
	
	@Autowired
	private TWorkShowFacade tWorkShowFacade;
	
	//新增工作经验
	@RequestMapping("insertTWorkShow")
	public void insertTWorkShow(String coName,String workStart,String workEnd,String workDepart,String workContent,
			String workKpi,HttpServletRequest request,HttpServletResponse response) throws Exception{
		TUserInfo user = getLoginedUser(request);
		TWorkShow work = new TWorkShow();
		work.setCoName(coName);
		work.setCreateTime(new Date());
		work.setuId(user.getuId());
		work.setWorkStart(DateUtil.formatString(workStart));
		work.setWorkEnd(DateUtil.formatString(workEnd));
		work.setWorkDepart(workDepart);
		work.setWorkContent(workContent);
		work.setWorkKpi(workKpi);
		long insert = tWorkShowFacade.insert(work);
		JSONObject result=new JSONObject();
		if(insert==1){
			result.put("success", "success");
		}
		ResponseUtil.write(response, result);
	}
}
