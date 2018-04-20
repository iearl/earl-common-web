package com.ants.web.boss.controller.co.recruit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ants.common.web.utils.ResponseUtil;
import com.ants.facade.user.entity.TJobInfo;
import com.ants.facade.user.entity.TRecruitInfo;
import com.ants.facade.user.entity.TUserInfo;
import com.ants.facade.user.service.TCompanyInfoFacade;
import com.ants.facade.user.service.TRecruitInfoFacade;
import com.ants.web.boss.base.BaseController;

/**  
 * @ClassName: RecruitController  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author 马高伟
 * @date 2018年4月16日  
 *    
 */
@Controller
public class RecruitController extends BaseController {
	@Autowired
	private TRecruitInfoFacade tRecruitInfoFacade;
	
	@Autowired
	private TCompanyInfoFacade tCompanyInfoFacade;
	
	@RequestMapping("showTRecruitInfoByUId")
	public void showTRecruitInfoByUId(
		@RequestParam(value = "page", required = false) String page,
		@RequestParam(value = "rows", required = false) String rows,
		HttpServletResponse response, HttpServletRequest request) {
	Map<String, Integer> testMap = new HashMap<>();
	if (StringUtils.isNotBlank(rows) && StringUtils.isNotBlank(page)) {
		Integer intPage = Integer.parseInt(page);
		Integer intRows = Integer.parseInt(rows);
		testMap.put("startRowNum", (intPage - 1) * intRows);
		testMap.put("endRowNum", intRows);
	}
	testMap.put("uId", getLoginedUser(request).getuId());
	JSONObject result = new JSONObject();
	List<TRecruitInfo> jobList = tRecruitInfoFacade.selectByMap(testMap);

	Long total = tRecruitInfoFacade.getTotal(testMap);
	JSONArray jsonArray = JSONArray.fromObject(jobList);
	result.put("rows", jsonArray);
	result.put("total", total);
	try {
		ResponseUtil.write(response, result);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return;
}
	

	@RequestMapping("insertTRecruitInfo")
	public void insertTRecruitInfo(HttpServletRequest request,HttpServletResponse response,
			String jobType2,String recName,String perNumBom,String perNumTop,String city,String recExper,String depart,
			String recType,String recSalaryBom,String recSalaryTop,String recDegree,String recDesc,String addPlus,String recRequest,String recEmail
			) throws Exception{
		TUserInfo user = getLoginedUser(request);
		TRecruitInfo info = new TRecruitInfo();
		info.setuId(user.getuId());
		info.setCoId(tCompanyInfoFacade.selectTComInfoByUId(user.getuId()).getCoId());
		info.setJobCode(jobType2);
		info.setRecAccEmail(recEmail);
		info.setRecAddr(city);
		info.setRecType(recType);
		info.setRecSalaryTop(recSalaryTop);
		info.setRecSalaryBom(recSalaryBom);
		info.setRecDegree(recDegree);
		info.setRecPerTop(perNumTop);
		info.setRecPerBom(perNumBom);
		info.setRecPositon(depart);
		info.setRecPluses(addPlus);
		info.setRecName(recName);
		info.setRecExper(recExper);
		info.setRecDesc(recDesc);
		info.setRecRequest(recRequest);
		info.setRecDegree(recDegree);
		JSONObject result = new JSONObject();
		long insert = tRecruitInfoFacade.insert(info);
		if(insert==1){
			result.put("success", true);
		}
		ResponseUtil.write(response, result);
		
	}
}
