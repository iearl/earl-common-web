package com.ants.web.boss.Interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ants.common.utils.DateUtil;
import com.ants.common.web.constants.SessionConstant;
import com.ants.facade.user.entity.TUserInfo;
import com.ants.web.boss.controller.usercontroller.UserLoginController;

public class LoginInterceptor implements HandlerInterceptor {

	private static final Log log = LogFactory.getLog(UserLoginController.class);

	// 不进行拦截的请求
	private static final String[] IGNORE_URI = { "/userLogin",
			"/insertTUserInfo", "/agreement", "/registerReq", "/validateKey",
			"/validatePhone", "/validateEmail" };

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean flag = false;
		String servletPath = request.getServletPath();
		for (String s : IGNORE_URI) {
			if (servletPath.contains(s)) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			TUserInfo user = (TUserInfo)request.getSession().getAttribute(
					SessionConstant.USER_SESSION_KEY);
			if (user == null) {
				response.sendRedirect("/loginpage.html");
				request.setAttribute("message", "请登录51职聘");
				request.getRequestDispatcher("").forward(request, response);
				log.warn("登录拦截请求"+DateUtil.formatDate(new Date()));
			} else {
				log.info(user.getuName()+"已登录可进行下一步操作");
				flag = true;
			}
		}

		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion");
	}

}