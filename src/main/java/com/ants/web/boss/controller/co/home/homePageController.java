package com.ants.web.boss.controller.co.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ants.common.web.utils.ResponseUtil;
import com.ants.facade.user.entity.TCompanyInfo;
import com.ants.facade.user.entity.TUserInfo;
import com.ants.facade.user.service.TCompanyInfoFacade;
import com.ants.web.boss.base.BaseController;
import com.ants.web.boss.controller.usercontroller.UserLoginController;

/**  
 * @ClassName: homePageController  
 * @Description: TODO(公司登录首页)  
 * @author 马高伟
 * @date 2018年4月13日  
 *    
 */
@Controller
public class homePageController extends BaseController {
	private static final Log log = LogFactory.getLog(UserLoginController.class);
	
	@Autowired 
	private TCompanyInfoFacade tCompanyInfoFacade;
	
	
	@RequestMapping("updateTCompanyInfo")
	public void updateTCompanyInfo(String coName,String coSimpName,String imagePathUrl,String coWebUrl,String addr,
			String introduce,String cityCode,String jobType2,String size,String invest,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		TCompanyInfo record = new TCompanyInfo();
		record.setCoAddr(addr);
		record.setuId(getLoginedUser(request).getuId());
		record.setCoDomain(jobType2);
		record.setCoSize(size);
		record.setCoCity(cityCode);
		record.setCoLogoPath(imagePathUrl);
		record.setCoIntroduce(introduce);
		record.setCoName(coName);
		record.setCoSimpName(coSimpName);
		record.setCoUrl(coWebUrl);
		record.setCoStage(invest);
		long i = tCompanyInfoFacade.updateByPrimaryKey(record);
		JSONObject result=new JSONObject();
		if(i==0L)
			result.put("result", "success");
		ResponseUtil.write(response, result);
	}
	
	@RequestMapping("insertTCompanyInfo")
	public void insertTCompanyInfo(String coName,String coSimpName,String imagePathUrl,String coWebUrl,String addr,
			String introduce,String cityCode,String jobType2,String size,String invest,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		TCompanyInfo record = new TCompanyInfo();
		record.setCoAddr(addr);
		record.setuId(getLoginedUser(request).getuId());
		record.setCoDomain(jobType2);
		record.setCoSize(size);
		record.setCoCity(cityCode);
		record.setCoLogoPath(imagePathUrl);
		record.setCoIntroduce(introduce);
		record.setCoName(coName);
		record.setCoSimpName(coSimpName);
		record.setCoUrl(coWebUrl);
		record.setCoStage(invest);
		long i = tCompanyInfoFacade.insert(record);
		JSONObject result=new JSONObject();
		if(i==0L)
			result.put("result", "success");
		ResponseUtil.write(response, result);
	}
	
	@RequestMapping("searchCoInfoById")
	public void searchCoInfoById(HttpServletRequest request, HttpServletResponse response) throws Exception{
		TUserInfo user = getLoginedUser(request);
		TCompanyInfo tCompanyInfo = tCompanyInfoFacade.selectTComInfoByUId(user.getuId());
		JSONObject result=new JSONObject();
		if(tCompanyInfo==null)
			result.put("noCompany", "noCompany");
		result.put("tCompanyInfo", tCompanyInfo);
		ResponseUtil.write(response, result);
		
	}
	
}
