package com.ants.web.boss.controller.jobinfocontroller;

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

import com.ants.common.page.PageBean;
import com.ants.common.page.PageParam;
import com.ants.common.web.utils.ResponseUtil;
import com.ants.facade.user.entity.TJobInfo;
import com.ants.facade.user.service.TJobInfoFacede;
import com.ants.web.boss.base.BaseController;

/**
 * @ClassName: HomeInfoController
 * @Description: TODO(展示首页信息的类)
 * @author 马高伟
 * @date 2018年4月6日
 * 
 */
@Controller
public class HomeInfoController extends BaseController {

	@Autowired
	private TJobInfoFacede tJobInfoFacade;

	// 首页展示时，出现在左边的职位分类信息
	@RequestMapping("showJobType")
	public void showJobType(
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
		JSONObject result = new JSONObject();
		List<TJobInfo> jobList = tJobInfoFacade.selectByMap(testMap);
		Long total = tJobInfoFacade.getTotal(testMap);
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

}
