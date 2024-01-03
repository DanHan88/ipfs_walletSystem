/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.wallet.system.service;

import com.wallet.system.mapper.InvestmentMapper;
import com.wallet.system.vo.EventsAnnouncementVO;
import com.wallet.system.vo.InvestmentCategoryVO;
import com.wallet.system.vo.InvestmentVO;
import com.wallet.system.vo.LoginVO;
import com.wallet.system.vo.MemoVO;
import com.wallet.system.vo.WalletWithdrawalVO;
import com.wallet.system.vo.TokenPaidDetailVO;
import com.wallet.system.vo.TokenPaidVO;
import com.wallet.system.vo.UserInfoVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class InvestmentService {
    @Autowired
    InvestmentMapper investmentMapper;
    
    @Value("${admin.wallet.address}")
    private String admin_walletAddress;

    public List<InvestmentVO> getInvestmentList() {
        return this.investmentMapper.selectInvestmentList();
    }
    public List<TokenPaidVO> selectTokenPaidVOList() {
        return investmentMapper.selectTokenPaidVOList();
    }
    
    public List<TokenPaidDetailVO> selectTokenDetailList(TokenPaidVO tokenPaidVO) {
        return investmentMapper.selectTokenDetailList(tokenPaidVO);
    }
    public List<WalletWithdrawalVO> getRequestFilList() {
        return this.investmentMapper.selectRequestFilList();
    }

    public List<UserInfoVO> selectUserInfoList() {
        return this.investmentMapper.selectUserInfoList();
    }
    
    public List<InvestmentVO> getInvestmentListByCategory(int category_index) {
        return this.investmentMapper.getInvestmentListByCategory(category_index);
    }

    public List<InvestmentCategoryVO> getInvestmentCategoryList() {
        return this.investmentMapper.selectInvestmentCategoryList();
    }

    public List<String> getIDList() {
        return this.investmentMapper.selectIDList();
    }

    public LoginVO findAdminByAdminId(String id) {
        return this.investmentMapper.findAdminByAdminId(id);
    }

    public boolean checkSession(HttpServletRequest request,boolean isAdmin) {
    	HttpSession session = request.getSession();
    	LoginVO loginVO = (LoginVO)session.getAttribute("user");
        if (session.getAttribute("user") == null || loginVO.getId() == "") {
	        return false;
	    }
        if(isAdmin!=loginVO.isAdmin()) {
        	return false;
        }
	    return true;
    }
    public boolean debuggingCheckSession(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	LoginVO loginVO = (LoginVO)session.getAttribute("user");
        if (session.getAttribute("user") == null || loginVO.getId() == "") {
	        return false;
	    }
	    return false;
    }
    
    public InvestmentCategoryVO addNewProductCategory(InvestmentCategoryVO investmentCategoryVO) {
    	InvestmentCategoryVO checked = investmentMapper.verifyProductCategory(investmentCategoryVO.getInvestment_category_name());
    	if(checked!=null) {
    		checked.setResponse("error1");
    		return checked;
    	}
    	investmentMapper.addNewProductCategory(investmentCategoryVO);
    	investmentCategoryVO = investmentMapper.verifyProductCategory(investmentCategoryVO.getInvestment_category_name());
    	investmentCategoryVO.setResponse("success");
    	return investmentCategoryVO;
    }
    public String addNewUser(UserInfoVO userInfoVO, HttpServletRequest request) {
    	UserInfoVO overlappingUserInfoVO = investmentMapper.verifyUserInfoVO(userInfoVO);
    	if(overlappingUserInfoVO!=null) {
    		return "error1";
    	}
    	userInfoVO.setProfile_picture_url("/profile/default_profile.jpg");
    	investmentMapper.addNewUser(userInfoVO);
    	UserInfoVO newUserInfoVO = investmentMapper.verifyUserInfoVO(userInfoVO);
    	MemoVO memoVO = new MemoVO();
    	memoVO.setUser_id(newUserInfoVO.getUser_id());
    	memoVO.setMemo(newUserInfoVO.memoCreate("유저등록"));
    	regMemo(memoVO, request);
    	return "success";
    }
    public String addNewInvestment(InvestmentVO investmentVO, HttpServletRequest request) {
    	investmentMapper.addNewInvestment(investmentVO);
    	MemoVO memoVO = new MemoVO();
    	memoVO.setUser_id(investmentVO.getUser_id());
    	memoVO.setMemo(investmentVO.memoCreate("투자등록"));
    	regMemo(memoVO, request);
    	return "success";
    }
    public String modifyInvestment(InvestmentVO investmentVO, HttpServletRequest request) {
    	MemoVO memoVO = new MemoVO();
    	memoVO.setUser_id(investmentVO.getUser_id());
    	memoVO.setMemo(investmentVO.memoCreate("투자수정"));
    	regMemo(memoVO, request);
    	investmentMapper.modifyInvestment(investmentVO);
    	return "success";
    }
    public String deleteInvestment(InvestmentVO investmentVO, HttpServletRequest request) {
    	MemoVO memoVO = new MemoVO();
    	memoVO.setUser_id(investmentVO.getUser_id());
    	memoVO.setMemo(investmentVO.memoCreate("투자삭제"));
    	regMemo(memoVO, request);
    	investmentMapper.deleteInvestment(investmentVO);
    	return "success";
    }
    public List<MemoVO> selectUserMemo(UserInfoVO userInfoVO) {
    	
    	return investmentMapper.selectUserMemo(userInfoVO);
    }
    public String regMemo(MemoVO regMemo, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	LoginVO loginVO = (LoginVO)session.getAttribute("user");
    	regMemo.setAdmin_id(loginVO.getId());
    	investmentMapper.regMemo(regMemo);
    	return "success";
    }
    public String updateUser(UserInfoVO userInfoVO, HttpServletRequest request) {
    	UserInfoVO overlappingUserInfoVO = investmentMapper.verifyUserInfoVO(userInfoVO);
    	if(overlappingUserInfoVO!=null && overlappingUserInfoVO.getUser_id()!=userInfoVO.getUser_id()) {
    		if(overlappingUserInfoVO.getUser_id()!=userInfoVO.getUser_id()||overlappingUserInfoVO.getUser_status().equals(userInfoVO.getUser_status())) {
    		return "error1";
    		}
    	}
    	MemoVO memoVO = new MemoVO();
    	memoVO.setUser_id(userInfoVO.getUser_id());
    	memoVO.setMemo(userInfoVO.memoCreate("유저수정"));
    	regMemo(memoVO, request);
    	investmentMapper.updateUser(userInfoVO);
    	return "success";
    }
    public InvestmentCategoryVO updateProductCategory(InvestmentCategoryVO investmentCategory) {
    	InvestmentCategoryVO investmentCategoryVO = investmentMapper.verifyProductCategory(investmentCategory.getInvestment_category_name());
    	if(investmentCategoryVO!=null&&investmentCategoryVO.getInvestment_category_index()!=investmentCategory.getInvestment_category_index()) {
    		if(investmentCategoryVO.getInvestment_category_index()!=investmentCategory.getInvestment_category_index()||investmentCategoryVO.getStatus().equals(investmentCategory.getStatus())) {
    			investmentCategoryVO.setResponse("error1");
        		return investmentCategoryVO;
    		}
    	}
    	investmentMapper.updateProductCategory(investmentCategory);
    	investmentCategoryVO = investmentMapper.selectInvestmentCategoryByIndex(investmentCategory.getInvestment_category_index());
    	investmentCategoryVO.setResponse("success");
    	return investmentCategoryVO;
    }
    
    public List<InvestmentVO> selectInvestmentListForUser(int user_id){
    	return investmentMapper.selectInvestmentListForUser(user_id);
    }
    
    
    public List<EventsAnnouncementVO> selectEvents(){
    	return investmentMapper.selectEvents();
    }
	public List<EventsAnnouncementVO> selectAnnouncements(){
		return investmentMapper.selectAnnouncements();
	}
    
    public String addNewTokenPaidInfo(List<InvestmentVO> listInvestment, HttpServletRequest request) {
    	TokenPaidVO tokenPaidVO = new TokenPaidVO();
    	tokenPaidVO.setFil_paid_per_tb(listInvestment.get(0).getFil_paid_per_tb());
    	HttpSession session = request.getSession();
    	LoginVO loginVO = (LoginVO)session.getAttribute("user");
    	tokenPaidVO.setAdmin_id(loginVO.getId());
    	investmentMapper.addNewTokenPaidInfo(tokenPaidVO);
    	double token_paid_id = investmentMapper.selectLastTokenPaidInfo();
    	for(int i=0;i<listInvestment.size();i++) {
    		if(listInvestment.get(i).isIs_getting_paid()) {
	    		TokenPaidDetailVO tokenPaidDetailVO = new TokenPaidDetailVO();
	    		tokenPaidDetailVO.setToken_paid_id(token_paid_id);
	    		tokenPaidDetailVO.setUser_id(listInvestment.get(i).getUser_id());
	    		tokenPaidDetailVO.setPaid_fil(listInvestment.get(i).getPayout_fil());
	    		tokenPaidDetailVO.setInvestment_category_index(listInvestment.get(i).getInvestment_category_id());
	    		investmentMapper.addNewTokenPaidDetailInfo(tokenPaidDetailVO);
    		}
    	}
    	return "success";
    }
 public String updateTokenPaid(TokenPaidVO tokenPaidVO) {
    	
	 
    	investmentMapper.updateTokenPaidInfo(tokenPaidVO);
    	
    	List<TokenPaidDetailVO> tokenPaidDetailList = investmentMapper.selectTokenDetailList(tokenPaidVO);
    	if(tokenPaidDetailList!=null && tokenPaidDetailList.size()==1) {
    		if(tokenPaidDetailList.get(0).getInvestment_category_name().equals("개인전송") ||tokenPaidDetailList.get(0).getInvestment_category_name().equals("개인차감")) {
    			investmentMapper.updatePersonalTokenPaidDetailInfo(tokenPaidVO);
        		return "success";
    			
    		}
    	}
    	investmentMapper.updateTokenPaidDetailInfo(tokenPaidVO);
    	return "success";
    }
 

public String approveFundRequest(WalletWithdrawalVO walletWithdrawalVO) {
	
	BigDecimal walletCheck = walletCheck();
	if(walletCheck.equals(BigDecimal.valueOf(-1))) {
    	return "에러: 로터스 노드 연결실패:"+ walletCheck;
    }
    System.out.println("테스트 조회 : " + walletCheck);
    BigDecimal filAmount = walletWithdrawalVO.getFil_amount();
    BigDecimal one = BigDecimal.ONE;
    if (filAmount.add(one).compareTo(walletCheck) > 0) {
        return "에러: 관리자 지갑 잔고 부족:" + walletCheck;
    }
    String returnHash = lotusSend(walletWithdrawalVO.getWallet_address(),walletWithdrawalVO.getFil_amount());
    walletWithdrawalVO.setMessage(returnHash);
	investmentMapper.updateStatus(walletWithdrawalVO);
	System.out.println("send 결과 : " + returnHash);
    return "success";  
}


public String declineFundRequest(WalletWithdrawalVO walletWithdrawalVO) {
	// TODO Auto-generated method stub
	investmentMapper.updateStatus(walletWithdrawalVO);
    // 조회된 행이 존재하고 상태가 '신청'인 경우에만 업데이트
	return "success";	
}

public BigDecimal walletCheck() {
    String cmd = "echo $(lotus wallet balance "+ admin_walletAddress +") | awk '{print $1}'";
    System.out.println(cmd);
    String[] command = {"/bin/sh", "-c", cmd};
    BigDecimal result = new BigDecimal(0); 
    try {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                result = new BigDecimal(line);
            } catch (NumberFormatException e) {
                result = new BigDecimal(-1);
            }
        }
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            result = new BigDecimal(-1); 
        }
    } catch (Exception e) {
        result = new BigDecimal(-1); 
    }
    return result;
}
public String lotusSend(String user_address, BigDecimal fil_amount) {
    String cmd = "lotus send --from " +  admin_walletAddress +  " " + user_address + " " + fil_amount;
    System.out.println(cmd);
    
    String[] command = {"/bin/sh", "-c", cmd};
    String result = "";

    try {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            result += line + "\n";
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            result = "Command failed";
        }
    } catch (Exception e) {
        result = "ERROR";
    }

    return result.trim();
}

public String insert_Events_or_Announcement(EventsAnnouncementVO eventsAnnouncementVO) {
	// TODO Auto-generated method stub
	investmentMapper.insertAnnouncement(eventsAnnouncementVO);
    // 조회된 행이 존재하고 상태가 '신청'인 경우에만 업데이트
	return "success";	
}

public String update_Events_or_Announcement(EventsAnnouncementVO eventsAnnouncementVO) {
	// TODO Auto-generated method stub
	investmentMapper.updateAnnouncement(eventsAnnouncementVO);
    // 議고쉶�맂 �뻾�씠 議댁옱�븯怨� �긽�깭媛� '�떊泥�'�씤 寃쎌슦�뿉留� �뾽�뜲�씠�듃
	return "success";	
}

public String delete_Events_or_Announcement(EventsAnnouncementVO eventsAnnouncementVO) {
	// TODO Auto-generated method stub
	investmentMapper.deleteAnnouncement(eventsAnnouncementVO);
    // 議고쉶�맂 �뻾�씠 議댁옱�븯怨� �긽�깭媛� '�떊泥�'�씤 寃쎌슦�뿉留� �뾽�뜲�씠�듃
	return "success";	
}

}

