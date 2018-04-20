package com.ants.web.boss.controller.usercontroller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ants.common.utils.DateUtil;
import com.ants.common.utils.RandomStrUtil;
import com.ants.common.utils.StringUtil;
import com.ants.common.web.constants.RoleInfoConstants;
import com.ants.common.web.constants.SessionConstant;
import com.ants.common.web.utils.ResponseUtil;
import com.ants.facade.user.entity.TBaseInfo;
import com.ants.facade.user.entity.TUserInfo;
import com.ants.facade.user.service.EmailCheckFacade;
import com.ants.facade.user.service.PhoneCheckFacade;
import com.ants.facade.user.service.TBaseInfoFacade;
import com.ants.facade.user.service.TUserInfoFacade;
import com.ants.web.boss.base.BaseController;

/**
 * 
 * @描述: 用户登录  .
 * @作者: 马高伟 .
 * @创建时间: 2018-3-16 .
 * @版本号: V1.0 .
 */
@Controller
public class UserLoginController extends BaseController {
	private static final Log log = LogFactory.getLog(UserLoginController.class);
	
	@Autowired
	private TUserInfoFacade tUserInfoFacade;
	
	@Autowired
	private EmailCheckFacade emailCheckFacade;
	
	@Autowired
	private PhoneCheckFacade phoneCheckFacade;
	
	@Autowired
	private  HttpSession session;
	
	@Autowired
	private TBaseInfoFacade tBaseInfoFacade;
	

	
	@RequestMapping("upPhoto")
	public void upPhoto(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Map<String, String> map = fileUpDownLoadFacade.processUploadPost(inputStream, tarPath,imgName);
		JSONObject result=new JSONObject();
		if(!file.isEmpty()){
			//根据相对路径获取绝对路径，图片上传后位于元数据中  
	        String path=request.getServletContext().getRealPath(""); 
	        String tempPath = path.substring(0,path.lastIndexOf(File.separator));
	        File filepath = new File(tempPath+File.separator+"upload");
	        if (!filepath.exists()){
	        	filepath.mkdirs();
	        }
	        String fileName = file.getOriginalFilename();
	        fileName =  generateFileName(fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()));
	        file.transferTo(new File(filepath+File.separator+fileName));
	        result.put("imgPath", "/upload/"+fileName);
	        result.put("result", "success");
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("查询人是否完善信息出错"+e);
		}
	}
	
	private String generateFileName(String suffix) {
        String filename;
        File file;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String base = format.format(date);
        filename = base + "." + suffix;
        file = new File(filename);
        int i = 1;
        while (file.exists()) {
            filename = String.format("%s_%d.%s", base, i, suffix);
            i++;
        }
        return filename;
    }
	
	/**
	 * 当页面加载完执行本方法，确保用户完善个人信息
	 */
	@RequestMapping("checkInfoOnLoad")
	public void checkInfoOnLoad(HttpServletRequest request, HttpServletResponse response){
		//从缓存拿到user对象,查找人员基本信息，当前公司会员是否完善个人信息
		TUserInfo user = (TUserInfo) request.getSession().getAttribute(SessionConstant.USER_SESSION_KEY);
		Integer id = user.getuId();
		TBaseInfo tbaseInfo = tBaseInfoFacade.selectByPrimaryKey(id);
		boolean flag = StringUtil.isEmpty(tbaseInfo);
		JSONObject result=new JSONObject();
		if(flag){
			result.put("resultStr", "N");
		}else{
			result.put("baseInfo", tbaseInfo);
			result.put("buBirthday", DateUtil.formatDate(tbaseInfo.getBuBirthday()));
			result.put("resultStr", "Y");
		}
		result.put("uName", user.getuName());
		result.put("uLastTime", DateUtil.formatDate(user.getuLastTime()));
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("查询人是否完善信息出错"+e);
		}
		
	}

	
	@RequestMapping("insertTUserInfo")
	public void insertTUserInfo( HttpServletResponse response,String uType, String uName, String uPwd, String confirmPwd, 
			String uEmail, String checkEmail, String uPhoneNo, String checkPhone, String uQuestion, String uAnswer){
		JSONObject result=new JSONObject();
		String validatePhoneCode = (String) session.getAttribute("validatePhoneCode");
		String validateEmailCode = (String) session.getAttribute("validateEmailCode");
		if(StringUtils.isNotBlank(validatePhoneCode)&&
				!StringUtils.equals(StringUtil.toUpper(validatePhoneCode), StringUtil.toUpper(checkPhone))){
			result.put("result", "errPhoneNo");//注册时，此电话号码存在
			try {
				ResponseUtil.write(response, result);
				return;
			} catch (Exception e) {
				log.error( "新增时，短信验证码输入有误"+e);
			}
		}else if(StringUtils.isNotBlank(validateEmailCode)&&
				!StringUtils.equals(StringUtil.toUpper(validateEmailCode), StringUtil.toUpper(checkEmail))){
			result.put("result", "errEmail");//注册时，此电话号码存在
			try {
				ResponseUtil.write(response, result);
				return;
			} catch (Exception e) {
				log.error( "新增时，短信验证码输入有误"+e);
			}
		}else{
			TUserInfo userInfo = new TUserInfo();
			userInfo.setuCreateTime(new Date());
			userInfo.setuEmail(uEmail);
			userInfo.setuLastTime(new Date());
			userInfo.setuName(uName);
			userInfo.setuErrCount(0);
			userInfo.setuPwd(uPwd);
			userInfo.setuPhoneNo(uPhoneNo);
			userInfo.setuType(uType);
			userInfo.setuAnswer(uAnswer);
			userInfo.setuQuestion(uQuestion);
			userInfo.setuStatue("0");
			userInfo.setuStopFlag("0");
			tUserInfoFacade.insertUserInfo(userInfo);
			result.put("result", "session");//注册时，此电话号码存在
			try {
				ResponseUtil.write(response, result);
				return;
			} catch (Exception e) {
				log.error( "新增时，短信验证码输入有误"+e);
			}
		}
		
		
	}
	

	@RequestMapping("agreement")
	public String agreement(){
		return "agreement";
	}
	@RequestMapping("registerReq")
	public String registerReq(){
		//注册请求跳转
		return "register";
	}
	//校验key是否存在
	@RequestMapping("validateKey")
	public void validateKey(HttpServletResponse response,String paramter ){
		JSONObject result=new JSONObject();
		TUserInfo tUserInfo = tUserInfoFacade.findUserByUserId("findUserByUserId", paramter);
		if(tUserInfo!=null){
			result.put("result", "exitRecord");//注册时，此电话号码存在
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error( "短信验证出错"+e);
		}
	}
	@RequestMapping("validatePhone")
	public void validatePhone(HttpServletResponse response,String uPhone){
		JSONObject result=new JSONObject();
		String code = RandomStrUtil.randomNum();
		TUserInfo tUserInfo = tUserInfoFacade.findUserByUserId("findUserByUserId", uPhone);
		if(tUserInfo!=null){
			result.put("result", "exitPhone");//注册时，此电话号码存在
			return;
		}
		boolean flag = phoneCheckFacade.registerPhone(uPhone, code);
		
		if(flag){
			result.put("result", "success");
			session.setAttribute("validatePhoneCode",code);
		}else{
			result.put("result", "error");
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error( "短信验证出错"+e);
		}
	}
	
	@RequestMapping("validateEmail")
	public void validateEmail(HttpServletResponse response,String uEmail){
		//注册时验证邮箱validateEmail
		JSONObject result=new JSONObject();
		String code = RandomStrUtil.randomStr();
		TUserInfo tUserInfo = tUserInfoFacade.findUserByUserId("findUserByUserId", uEmail);
		if(tUserInfo!=null){
			result.put("result", "exitPhone");//注册时，此电话号码存在
			return;
		}
		boolean flag = emailCheckFacade.registerMail(uEmail, code, new Date());
		
		if(flag){
			result.put("result", "success");
			session.setAttribute("validateEmailCode",code);
		}else{
			result.put("result", "error");
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error( "邮箱验证出错"+e);
		}
	}
	
	@RequestMapping("userLogin")
	public void userLogin(ModelAndView mv,String email,String password,HttpSession session,HttpServletResponse response) {
		JSONObject json=new JSONObject();
		String resultStr = "";
		try {
			TUserInfo user = tUserInfoFacade.findUserByUserId("findUserByUserId",email);
			if (user == null) {
				log.warn("== no such user");
				json.put("result", "noUser");
			}
			if (user.getuPwd().equals(Base64.encodeBase64String(password.getBytes()))) {
				session.setAttribute(SessionConstant.USER_SESSION_KEY, user);// 用户信息，包括登录信息和权限
				user.setuLastTime(new Date());// 更新登录数据
				user.setuErrCount(0); // 错误次数设为0	
				tUserInfoFacade.updateUserInfo("updateUserInfo",user);//更新用户信息
				String type = user.getuType();
				if(RoleInfoConstants.manager.equals(type)){
					resultStr="skipManagerView";//进入管理员界面
				} else if(RoleInfoConstants.company.equals(type)){
					resultStr="skipCoView";//进入公司会员界面
				}else if(RoleInfoConstants.jobhunter.equals(type)){
					resultStr="skipjobhunterView";
				}
				json.put("result", resultStr);
			} else {
				log.warn("密码错误");
				Integer pwdErrorCount = user.getuErrCount();// 错误次数加1
				if (pwdErrorCount == null) {
					pwdErrorCount = 0;
				}
				if(pwdErrorCount.intValue() >=SessionConstant.WEB_PWD_INPUT_ERROR_LIMIT){
					log.warn("==After the user has entered the wrong password three times in a row, the account is frozen.");//当用户连续输入三次错误密码后，该账号冻结
					user.setuStatue(RoleInfoConstants.freeze);//设置账号冻结
				}
				user.setuErrCount(pwdErrorCount + 1);
				resultStr = "errPwd";
				json.put("result", resultStr);
			}
		} catch (Exception e) {
			log.error("login exception:", e);
			resultStr = "errException";
			json.put("result", resultStr);
		}finally{
			try {
				ResponseUtil.write(response, json);
			} catch (Exception e1) {
				log.error("return respone exception:", e1);
			}
		}
	}

	/**
	 * 跳转到退出确认页面.
	 */
	public String logoutConfirm() {
		log.info("== logoutConfirm");
		return "logoutConfirm";
	}
	


	/**
	 * 退出登录
	 */
	public String logout(HttpSession session) throws Exception {
		if(session!=null){
			session.invalidate();
		}
		log.info("== logout");
		return "logout";
	}

	/**
	 * 跳转到登录超时确认页面
	 */
	public String timeoutConfirm(HttpSession session) throws Exception {
		if(session!=null){
			session.invalidate();
		}
		return "timeoutConfirm";
	}
	
	public static void main(String[] args) {
		String str = "123456"; // YWJj为要解密的字符串
		String base64Str = Base64.encodeBase64String(str.getBytes());
		        System.out.println("base64加密-->" + base64Str);

	}
}
