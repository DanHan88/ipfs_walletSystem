package com.wallet.system.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wallet.system.mapper.InvestmentMapper;
import com.wallet.system.mapper.UserAppMapper;
import com.wallet.system.service.InvestmentService;
import com.wallet.system.service.UserAppService;
import com.wallet.system.vo.EventsAnnouncementVO;
import com.wallet.system.vo.InvestmentCategoryVO;
import com.wallet.system.vo.InvestmentVO;
import com.wallet.system.vo.LoginVO;
import com.wallet.system.vo.TokenPaidDetailVO;
import com.wallet.system.vo.UserInfoVO;
import com.wallet.system.vo.WalletWithdrawalVO;

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
	 @Value("${upload.directory}")
	 private String uploadDirectory;
	 
	@GetMapping(value={"/UserApp"})
    public ModelAndView login(@ModelAttribute("loginError") String loginError , @ModelAttribute LoginVO loginVO, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("loginError", loginError);
        mav.setViewName("views/userAppLogin");
        return mav;
    }
	
	@GetMapping(value={"/logoutUser_user"})
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/UserApp";
    }
	
	
	@GetMapping(value={"/register"})
    public ModelAndView register(@ModelAttribute("loginError") String loginError , @ModelAttribute LoginVO loginVO, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("loginError", loginError);
        mav.setViewName("views/register");
        return mav;
    }
	
	
	 @PostMapping(value={"/UserApplogin.do"})
	    private String doLogin(LoginVO loginVO, BindingResult result, RedirectAttributes redirect, HttpServletRequest request, HttpServletResponse response) throws Exception {
	        LoginVO lvo = new LoginVO();
	        UserInfoVO userInfoVO = new UserInfoVO();     
	        userInfoVO.setUser_email(loginVO.getId());
	        userInfoVO = investmentMapper.verifyUserInfoVO(userInfoVO);
	        if (userInfoVO == null) {
	        	redirect.addFlashAttribute("loginError", "아이디를 확인해주세요");
	            return "redirect:/UserApp";
	        }
	        userInfoVO = userAppService.selectDetailUserInfoByUserId(userInfoVO.getUser_id());
	        if ("중지".equals(userInfoVO.getUser_status()) || "가입거절".equals(userInfoVO.getUser_status()) || "가입대기".equals(userInfoVO.getUser_status())) {
	        	redirect.addFlashAttribute("loginError", "유효하지 않은 아이디입니다.");
	            return "redirect:/UserApp";
	        }
	        HttpSession session = request.getSession();
	        String rawPw = "";
	        String encodePw = "";
	        if (userInfoVO != null) {
	        	lvo.setId(userInfoVO.getUser_name());
	            lvo.setUserInfoVO(userInfoVO);
	            lvo.setAdmin(false);
	        	rawPw = loginVO.getPassword();
	            String value = pwEncoder.encode(rawPw);
	            if (this.pwEncoder.matches((CharSequence)rawPw, encodePw = investmentMapper.getUserPassword(loginVO.getId()))) {
	                lvo.setPassword("");
	                session.setAttribute("user", (Object)lvo);
	                return "redirect:/UserAppMain";
	            }
	        }
	        redirect.addFlashAttribute("loginError", "비밀번호를 확인해주세요");
	        return "redirect:/UserApp";
	    }
	 
	   @GetMapping(value={"/userAppPayOut"})
	    public ModelAndView userAppPayOut(HttpServletRequest request,String sb) {
		   
	        ModelAndView mav = new ModelAndView();
	        HttpSession session = request.getSession(); 
	        if(!investmentService.checkSession(request,false)) {
	        	mav.setViewName("redirect:/UserApp");
	            return mav;
	        }
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        
	        List<TokenPaidDetailVO> tokenPaidDetailList = userAppService.getTokenPaidDetailListByUser(loginVO.getUserInfoVO());
	        
	        
	        mav.addObject("sb", sb);
	        mav.addObject("tokenPaidDetailList", tokenPaidDetailList);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/userApp_payout");
	        return mav;
	    }
	   @GetMapping(value={"/userAppInvestment"})
	    public ModelAndView userAppInvestment(HttpServletRequest request,String sb) {
	        ModelAndView mav = new ModelAndView();
	        HttpSession session = request.getSession();
	        if(!investmentService.checkSession(request,false)) {
	        	mav.setViewName("redirect:/UserApp");
	            return mav;
	        }
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        List<InvestmentVO> listInvestment = userAppService.getInvestmentListByUser(loginVO.getUserInfoVO());
	        
	        mav.addObject("listInvestment" , listInvestment);
	        mav.addObject("sb", sb);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/userApp_investment");
	        return mav;
	    }
	   @GetMapping(value={"/userAppRequestFund"})
	    public ModelAndView userAppRequestFund(HttpServletRequest request,String sb) {
	        ModelAndView mav = new ModelAndView();
	        HttpSession session = request.getSession();
	        if(!investmentService.checkSession(request,false)) {
	        	mav.setViewName("redirect:/UserApp");
	            return mav;
	        }
	        LoginVO loginVO = (LoginVO)session.getAttribute("user"); 
	        List<WalletWithdrawalVO> walletWithdrawalList = userAppService.selectWalletWithdrawal(loginVO.getUserInfoVO());
	        
	        mav.addObject("walletWithdrawalList", walletWithdrawalList);
	        mav.addObject("sb", sb);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/userApp_fundRequest");
	        return mav;
	    }
	   @GetMapping(value={"/userAppUserInfo"})
	    public ModelAndView userAppUserInfo(HttpServletRequest request,String sb) {
	        ModelAndView mav = new ModelAndView();
	        HttpSession session = request.getSession();
	        if(!investmentService.checkSession(request,false)) {
	        	mav.setViewName("redirect:/UserApp");
	            return mav;
	        }
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        mav.addObject("sb", sb);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/userApp_userInfo");
	        return mav;
	    }
	   @GetMapping(value={"/UserAppMain"})
	    public ModelAndView userAppMain(HttpServletRequest request,String sb) {
	        ModelAndView mav = new ModelAndView();
	        HttpSession session = request.getSession();
	        if(!investmentService.checkSession(request,false)) {
	        	mav.setViewName("redirect:/UserApp");
	            return mav;
	        }
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        List<EventsAnnouncementVO> announcementEventList = userAppService.selectAnnouncementEventByUser(loginVO.getUserInfoVO().getUser_id());
	        mav.addObject("announcementEventList", announcementEventList);
	        mav.addObject("sb", sb);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/userAppMain");
	        return mav;
	    }
	   @GetMapping(value={"/userAppRequestPayout"})
	    public ModelAndView userAppRequestPayout(HttpServletRequest request,String sb) {
	        ModelAndView mav = new ModelAndView();
	        HttpSession session = request.getSession();
	        if(!investmentService.checkSession(request,false)) {
	        	mav.setViewName("redirect:/UserApp");
	            return mav;
	        }
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        UserInfoVO userInfoVO = userAppService.selectDetailUserInfoByUserId(loginVO.getUserInfoVO().getUser_id());
	        
	        mav.addObject("userInfoVO",userInfoVO);
	        mav.addObject("sb", sb);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/userApp_requestPayout");
	        return mav;
	    }
	   
	   
	   @ResponseBody
	    @PostMapping(value={"/insertUser"})
	    public String insertUser(@RequestBody UserInfoVO userInfoVO,  HttpServletRequest request) {
		   String rawPw = "";
	       rawPw = userInfoVO.getPassword();
	       String value = pwEncoder.encode(rawPw);
	       userInfoVO.setPassword(value);
		   
	        return userAppService.insertUser(userInfoVO);
	    }
	   
	   
	   @ResponseBody
	    @PostMapping(value={"/checkUserEmail"})
	    public boolean checkUserEmail(@RequestBody UserInfoVO userInfoVO,  HttpServletRequest request) {
		   
		   
		   
	        return userAppService.selectUserEmail(userInfoVO);
	    }
	   
	   @ResponseBody
	    @PostMapping(value={"/addWalletWithdrawal"})
	    public String addWalletWithdrawal(@RequestBody WalletWithdrawalVO walletWithdrawalVO,  HttpServletRequest request) {
		   if(!investmentService.checkSession(request,false)) {
	    		return "failed:session_closed";
	    	}
		   userAppService.addWalletWithdrawal(walletWithdrawalVO);
	        return "success";
	    }
	   
	   @ResponseBody
	    @PostMapping(value = { "/updateUserProfileImg" })
	    public String updateUserProfileImg(MultipartHttpServletRequest request) {
		   if(!investmentService.checkSession(request,false)) {
	    		return "failed:session_closed";
	    	}
	        int user_id = Integer.parseInt(request.getParameter("user_id"));
	        MultipartFile file = request.getFile("file");
	        String filePathString ="";
	        if (file != null && !file.isEmpty()) {
	        	try {
	                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	                java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDirectory);
	                if (!java.nio.file.Files.exists(uploadPath)) {
	                    java.nio.file.Files.createDirectories(uploadPath);
	                }
	                java.nio.file.Path filePath = uploadPath.resolve(user_id+fileName);
	                file.transferTo(filePath.toFile());
	                filePathString = "/profile/"+user_id+fileName;
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }else {
	        	filePathString="/profile/default_profile.jpg";
	        }
	        UserInfoVO userInfoVO = new UserInfoVO();
	        userInfoVO.setUser_id(user_id);
	        userInfoVO.setProfile_picture_url(filePathString);
	        userAppService.updateUserProfileImg(userInfoVO, request);
	        userInfoVO = userAppService.selectDetailUserInfoByUserId(userInfoVO.getUser_id());
	        HttpSession session = request.getSession();  
	        LoginVO lvo = new LoginVO();
	        	lvo.setId(userInfoVO.getUser_name());
	            lvo.setUserInfoVO(userInfoVO);
	            lvo.setAdmin(false);
	            lvo.setPassword("");
	            session.setAttribute("user", (Object)lvo);
	        return "success";       
	    }
	   @ResponseBody
	    @PostMapping(value = { "/updateUserPassword" })
	    public String updateUserPassword(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request) {
		   if(!investmentService.checkSession(request,false)) {
	    		return "failed:session_closed";
	    	}
	        int user_id = userInfoVO.getUser_id();     		
	        if(!this.pwEncoder.matches((CharSequence)userInfoVO.getOriginal_password(), userAppService.selectUserPassword(user_id))) {
	        	return "failed:wrong_password";
	        }
	        userInfoVO.setPassword(pwEncoder.encode(userInfoVO.getPassword()));
	        userInfoVO.setUser_id(user_id);
	        return userAppService.updateUserPassword(userInfoVO, request);
	    }
	   
	
}
