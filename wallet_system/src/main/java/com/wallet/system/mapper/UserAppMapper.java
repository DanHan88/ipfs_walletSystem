/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.annotations.Mapper
 */
package com.wallet.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wallet.system.vo.InvestmentVO;
import com.wallet.system.vo.TokenPaidDetailVO;
import com.wallet.system.vo.UserInfoVO;
import com.wallet.system.vo.WalletWithdrawalVO;

@Mapper
public interface UserAppMapper {
	public List<InvestmentVO> getInvestmentListByUser(UserInfoVO userInfoVO);
	public List<TokenPaidDetailVO> getTokenPaidDetailListByUser(UserInfoVO userInfoVO);
	public void addWalletWithdrawal(WalletWithdrawalVO walletWithdrawalVO);
	
	
}

