/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.wallet.system.service;

import com.wallet.system.mapper.InvestmentMapper;
import com.wallet.system.mapper.UserAppMapper;
import com.wallet.system.vo.EventsAnnouncementVO;
import com.wallet.system.vo.InvestmentVO;
import com.wallet.system.vo.MemoVO;
import com.wallet.system.vo.TokenPaidDetailVO;
import com.wallet.system.vo.UserInfoVO;
import com.wallet.system.vo.WalletWithdrawalVO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAppService {
    @Autowired
    UserAppMapper userAppMapper;
    @Autowired
    InvestmentMapper investmentMapper;
    @Autowired
    private InvestmentService investmentService;

    public List<InvestmentVO> getInvestmentListByUser(UserInfoVO userInfoVO) {
        return this.userAppMapper.getInvestmentListByUser(userInfoVO);
    }
    
    public List<TokenPaidDetailVO> getTokenPaidDetailListByUser(UserInfoVO userInfoVO) {
        return this.userAppMapper.getTokenPaidDetailListByUser(userInfoVO);
    }
    
    public String addWalletWithdrawal(WalletWithdrawalVO walletWithdrawalVO) {
        userAppMapper.addWalletWithdrawal(walletWithdrawalVO); 
        return "success";
    }
    
    public List<WalletWithdrawalVO> selectWalletWithdrawal(UserInfoVO userInfoVO) {
        return userAppMapper.selectWalletWithdrawalByUser(userInfoVO); 
    }
    
    public String updateUserProfileImg(UserInfoVO userInfoVO, HttpServletRequest request) {
    	MemoVO memoVO = new MemoVO();
    	memoVO.setUser_id(userInfoVO.getUser_id());
    	userAppMapper.updateUserProfileImg(userInfoVO);
    	return "success";
    }
    public String updateUserPassword(UserInfoVO userInfoVO, HttpServletRequest request) {
    	MemoVO memoVO = new MemoVO();
    	memoVO.setUser_id(userInfoVO.getUser_id());
    	userAppMapper.updateUserPassword(userInfoVO);
    	return "success";
    }
    
    public String selectUserPassword(int user_id) {
    	return userAppMapper.selectUserPassword(user_id);
    }
    
    public UserInfoVO selectDetailUserInfoByUserId(int user_id) {
    	return userAppMapper.selectDetailUserInfoByUserId(user_id);
    }
	public List<EventsAnnouncementVO> selectAnnouncementEventByUser(int user_id){
		return userAppMapper.selectAnnouncementEventByUser(user_id);
	}
    
	

}

