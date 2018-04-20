package com.ants.web.boss.controller.usercontroller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ants.common.utils.DateUtil;
import com.ants.common.web.utils.ResponseUtil;
import com.ants.facade.user.entity.TAreaInfo;
import com.ants.facade.user.entity.TBaseInfo;
import com.ants.facade.user.entity.TJobInfo;
import com.ants.facade.user.entity.TUserInfo;
import com.ants.facade.user.service.TAreaInfoFacade;
import com.ants.facade.user.service.TBaseInfoFacade;
import com.ants.facade.user.service.TJobInfoFacede;
import com.ants.facade.user.service.TUserInfoFacade;
import com.ants.web.boss.base.BaseController;

/**
 * @ClassName: ComController
 * @Description: TODO(公共类，处理三种不同角色共有方法)
 * @author 马高伟
 * @date 2018年4月14日
 * 
 */
@Controller
public class ComController extends BaseController {
	private static Log log = LogFactory.getLog(UserInfoController.class);

	@Autowired
	private TAreaInfoFacade tAreaInfoFacade;
	
	@Autowired
	private TBaseInfoFacade tBaseInfoFacade;
	
	@Autowired
	private TJobInfoFacede tJobInfoFacade;
	
	@Autowired
	private TUserInfoFacade tUserInfofacade;
	
	/**
	 * 修改个人信息
	 */
	@RequestMapping("selfInfochange")
	public void selfInfochange(HttpServletResponse response, HttpServletRequest request){
		TUserInfo user = getLoginedUser(request);
	}
	/**
	 * 修改个人登录密码
	 */
	@RequestMapping("updateUInfoPwd")
	public void updateUInfoPwd(String pwd, HttpServletResponse response, HttpServletRequest request){
		TUserInfo user = getLoginedUser(request);
		user.setuPwd(Base64.encodeBase64String(pwd.getBytes()));
		int count = tUserInfofacade.updateUserInfo("updateUserInfo",user);
		JSONObject result=new JSONObject();
		if(count==1)
		result.put("success", "success");
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	@RequestMapping("serachSeleInfo")
	public void serachSeleInfo(HttpServletResponse response,HttpServletRequest request){
		TUserInfo user = getLoginedUser(request);
		JSONObject result=new JSONObject();
		result.put("lastime", DateUtil.formatDate(user.getuLastTime()));
		result.put("pwd", new String(Base64.decodeBase64(user.getuPwd())));
		result.put("name", user.getuName());
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error(e);
		}
		
	}
	
	@RequestMapping("searchAreaNameById")
	public void searchAreaNameById(String cityCode, HttpServletResponse response){
		Map<String,String> mapCon = new HashMap<>();
		mapCon.put("areaCode", cityCode);
		List<Map<String,String>> list = tAreaInfoFacade.searchAreaNameById(mapCon);
		JSONArray jsonArray = JSONArray.fromObject(list);
		try {
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			log.error(e);
		}
	}
	@RequestMapping("searchJobNameById")
	public void searchJobNameById(String jobId, HttpServletResponse response){
		Map<String,String> mapCon = new HashMap<>();
		mapCon.put("jobId", jobId);
		List<Map<String,String>> list = tJobInfoFacade.searchJobNameById(mapCon);
		JSONArray jsonArray = JSONArray.fromObject(list);
		try {
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	@RequestMapping("updateTBaseTUser")
	public void updateTBaseTUser(String buBrithday,String buName,String buNational, String nativePlace,String buSex,
			String buAddr, HttpServletRequest request,HttpServletResponse response) throws Exception{
		TBaseInfo record = new TBaseInfo();
		record.setBuBirthday(DateUtil.formatString(buBrithday));
		record.setBuCreateTime(new Date());
		record.setBuName(buName);
		record.setBuNational(buNational);
		record.setBuNative(nativePlace);
		record.setBuSex(buSex);
		record.setBuDetailAddr(buAddr);
		TUserInfo user = getLoginedUser(request);
		record.setuId(user.getuId());
		tBaseInfoFacade.updateByPrimaryKey(record);
		JSONObject result=new JSONObject();
		result.put("resultStr", "success");
		ResponseUtil.write(response, result);
	}
	
	@RequestMapping("insertTBaseTUser")
	public void insertTBaseTUser(String buBrithday,String buName,String buNational, String nativePlace,String buSex,
			String imgPath,String buAddr, HttpServletRequest request,HttpServletResponse response) throws Exception{
		TBaseInfo record = new TBaseInfo();
		record.setBuBirthday(DateUtil.formatString(buBrithday));
		record.setBuCreateTime(new Date());
		record.setBuName(buName);
		record.setBuNational(buNational);
		record.setBuNative(nativePlace);
		record.setBuPicPath(imgPath);
		record.setBuSex(buSex);
		record.setBuDetailAddr(buAddr);
		TUserInfo user = getLoginedUser(request);
		record.setuId(user.getuId());
		//执行插入操作
		tBaseInfoFacade.insert(record);
		JSONObject result=new JSONObject();
		TUserInfo userInfo = getLoginedUser(request);
		if("1".equals(userInfo.getuType())){
			result.put("resultStr", "skipCoView");//跳转到公司页面
		}else if("0".equals(userInfo.getuType())){
			result.put("resultStr", "skipjobhunterView");//跳转到公司页面
		}
		ResponseUtil.write(response, result);
	}
	@RequestMapping("selectJobTypeList")
	public void selectJobTypeList(String jobId,
			HttpServletResponse response) throws Exception{
		List<TJobInfo> jobList = tJobInfoFacade.selectTJobList(jobId);
		JSONArray jsonArr = JSONArray.fromObject(jobList);
		ResponseUtil.write(response, jsonArr);
	}
	@RequestMapping("selectAreaList")
	public void selectAreaList(String province,
			HttpServletResponse response) {
		List<TAreaInfo> list = tAreaInfoFacade.selectTAreaList(province);
		JSONArray jsonArr = JSONArray.fromObject(list);
		try {
			ResponseUtil.write(response, jsonArr);
		} catch (Exception e) {
			log.error(e);
		}
	}
}
