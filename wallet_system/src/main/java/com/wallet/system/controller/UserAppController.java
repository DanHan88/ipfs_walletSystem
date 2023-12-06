package com.wallet.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wallet.system.mapper.InvestmentMapper;
import com.wallet.system.service.InvestmentService;
import com.wallet.system.service.UserAppService;
import com.wallet.system.vo.InvestmentCategoryVO;
import com.wallet.system.vo.InvestmentVO;
import com.wallet.system.vo.LoginVO;
import com.wallet.system.vo.TokenPaidDetailVO;
import com.wallet.system.vo.UserInfoVO;

@Controller
public class UserAppController {
	 @Autowired
	 private InvestmentService investmentService;
	 @Autowired
	 private UserAppService userAppService;
	 @Autowired
	 private PasswordEncoder pwEncoder;
	 @Autowired
	 private InvestmentMapper investmentMapper;

	@GetMapping(value={"/UserApp"})
    public ModelAndView login(@ModelAttribute LoginVO loginVO, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/userAppLogin");
        return mav;
    }
	
	 @PostMapping(value={"/UserApplogin.do"})
	    private String doLogin(LoginVO loginVO, BindingResult result, RedirectAttributes redirect, HttpServletRequest request, HttpServletResponse response) throws Exception {
	        LoginVO lvo = new LoginVO();;
	        UserInfoVO userInfoVO = new UserInfoVO();     
	        userInfoVO.setUser_email(loginVO.getId());
	        userInfoVO = investmentMapper.verifyUserInfoVO(userInfoVO);
	        HttpSession session = request.getSession();
	        String rawPw = "";
	        String encodePw = "";
	        if (userInfoVO != null) {
	        	lvo.setId(userInfoVO.getUser_name());
	            lvo.setUserInfoVO(userInfoVO);
	        	rawPw = loginVO.getPassword();
	            String value = pwEncoder.encode(rawPw);
	            if (this.pwEncoder.matches((CharSequence)rawPw, encodePw = investmentMapper.getUserPassword(loginVO.getId()))) {
	                lvo.setPassword("");
	                session.setAttribute("user", (Object)lvo);
	                return "redirect:/UserAppMain";
	            }
	            redirect.addFlashAttribute("result", (Object)0);
	            return "redirect:/UserApp";
	        }
	        redirect.addFlashAttribute("result", (Object)0);
	        return "redirect:/UserApp";
	    }
	 
	   @GetMapping(value={"/userAppPayOut"})
	    public ModelAndView userAppPayOut(HttpServletRequest request, String category,String sb) {
	        ModelAndView mav = new ModelAndView();
	        if(!investmentService.checkSession(request)) {
	        	mav.setViewName("redirect:/UserApp");
	            return mav;
	        }
	        HttpSession session = request.getSession();
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        
	        List<TokenPaidDetailVO> tokenPaidDetailList = userAppService.getTokenPaidDetailListByUser(loginVO.getUserInfoVO());
	        
	        
	        mav.addObject("sb", sb);
	        mav.addObject("tokenPaidDetailList", tokenPaidDetailList);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/userApp_payout");
	        return mav;
	    }
	   @GetMapping(value={"/userAppInvestment"})
	    public ModelAndView userAppInvestment(HttpServletRequest request, String category,String sb) {
	        ModelAndView mav = new ModelAndView();
	        
	        if(!investmentService.checkSession(request)) {
	        	mav.setViewName("redirect:/UserApp");
	            return mav;
	        }
	        HttpSession session = request.getSession();
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        
	        List<InvestmentVO> listInvestment = userAppService.getInvestmentListByUser(loginVO.getUserInfoVO());
	        
	        mav.addObject("listInvestment" , listInvestment);
	        mav.addObject("sb", sb);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/userApp_investment");
	        return mav;
	    }
	   @GetMapping(value={"/userAppRequestFund"})
	    public ModelAndView userAppRequestFund(HttpServletRequest request, String category,String sb) {
	        ModelAndView mav = new ModelAndView();
	        
	        if(!investmentService.checkSession(request)) {
	        	mav.setViewName("redirect:/userApp_fundRequest");
	            return mav;
	        }
	        HttpSession session = request.getSession();
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        mav.addObject("sb", sb);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/userAppMain");
	        return mav;
	    }
	   @GetMapping(value={"/userAppUserInfo"})
	    public ModelAndView userAppUserInfo(HttpServletRequest request, String category,String sb) {
	        ModelAndView mav = new ModelAndView();
	        
	        if(!investmentService.checkSession(request)) {
	        	mav.setViewName("redirect:/userApp_userInfo");
	            return mav;
	        }
	        HttpSession session = request.getSession();
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        mav.addObject("sb", sb);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/userAppMain");
	        return mav;
	    }
	   @GetMapping(value={"/UserAppMain"})
	    public ModelAndView userAppMain(HttpServletRequest request, String category,String sb) {
	        ModelAndView mav = new ModelAndView();
	        
	        if(!investmentService.checkSession(request)) {
	        	mav.setViewName("redirect:/UserApp");
	            return mav;
	        }
	        HttpSession session = request.getSession();
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        mav.addObject("sb", sb);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/userAppMain");
	        return mav;
	    }
	   
	   @ResponseBody
	    @PostMapping(value={"/addWalletWithdrawal"})
	    public InvestmentCategoryVO addWalletWithdrawal(@RequestBody InvestmentCategoryVO investmentCategoryVO,  HttpServletRequest request) {
	    	if(!investmentService.checkSession(request)) {
	    		investmentCategoryVO.setResponse("success");
	    		return investmentCategoryVO;
	    	}
	        return investmentService.addNewProductCategory(investmentCategoryVO);
	    }
	
	
}
