package com.wallet.system.etc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.wallet.system.vo.LoginVO;

public class CustomInterceptor implements HandlerInterceptor{
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	            throws Exception {
		 LoginVO loginVO = (LoginVO)request.getSession().getAttribute("user");
		 if (loginVO==null || !checkSession(request)) { 
			  if(!loginVO.isAdmin()){
	            response.sendRedirect(request.getContextPath() + "/UserApp");
			  }else {
				  response.sendRedirect(request.getContextPath() + "/");
			  }
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	            ModelAndView modelAndView) throws Exception {
	    }

	    @Override
	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	            throws Exception {
	    }
	    public boolean checkSession(HttpServletRequest request) {
	    	HttpSession session = request.getSession();
	    	LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        if (session.getAttribute("user") == null || loginVO.getId() == "") {
		        return false;
		    }
		    return true;
	    }
}
