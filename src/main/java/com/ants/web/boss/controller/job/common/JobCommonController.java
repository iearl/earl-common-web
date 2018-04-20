package com.ants.web.boss.controller.job.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ants.web.boss.base.BaseController;

/**
 * @ClassName: JobCommonController
 * @Description: TODO(求职者公共类)
 * @author 马高伟
 * @date 2018年4月14日
 * 
 */
@Controller
public class JobCommonController extends BaseController {
	
	//跳转到修改密码页面
	@RequestMapping("skipJobChangePwd")
	public String skipJobChangePwd(){
		return "jobhunter/jobPwdChange";
	}
	
	//跳转到职位详情页
	@RequestMapping("skipJobDetail")
	public String skipJobDetail(){
		return "jobhunter/jobDetail";
		
	}
	
	@RequestMapping("skipAddResumePage")
	public String skipAddResumepage(){
		return "jobhunter/addResumePage";
	}
	
	/**
	 * skipJobInfoPage:跳转到求职会员页面
	 */
	@RequestMapping("skipJobInfoPage")
	public String skipJobInfoPage() {
		return "jobhunter/jobInfomanager";
	}

	/**
	 * skipDeliverRecord:跳转到投递记录页面
	 */
	@RequestMapping("skipDeliverRecord")
	public String skipDeliverRecord() {
		return "jobhunter/deliverRec";
	}
}
