/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.wallet.system.service;

import com.wallet.system.mapper.UserAppMapper;
import com.wallet.system.vo.InvestmentVO;
import com.wallet.system.vo.TokenPaidDetailVO;
import com.wallet.system.vo.UserInfoVO;
import com.wallet.system.vo.WalletWithdrawalVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAppService {
    @Autowired
    UserAppMapper userAppMapper;

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
    
    
    
    

}

