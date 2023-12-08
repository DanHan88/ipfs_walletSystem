/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.annotations.Mapper
 */
package com.wallet.system.mapper;

import com.wallet.system.vo.InvestmentCategoryVO;
import com.wallet.system.vo.InvestmentVO;
import com.wallet.system.vo.LoginVO;
import com.wallet.system.vo.MemoVO;
import com.wallet.system.vo.RequestFilVO;
import com.wallet.system.vo.TokenPaidDetailVO;
import com.wallet.system.vo.TokenPaidVO;
import com.wallet.system.vo.UserInfoVO;
import com.wallet.system.vo.WalletWithdrawalVO;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InvestmentMapper {
    public List<InvestmentVO> selectInvestmentList();
    
    public List<TokenPaidVO> selectTokenPaidVOList();
    
    public List<TokenPaidDetailVO> selectTokenDetailList(TokenPaidVO tokenPaidVO);

    public List<InvestmentCategoryVO> selectInvestmentCategoryList();

    public List<String> selectIDList();

    public LoginVO findAdminByAdminId(String id);

    public List<InvestmentVO> getInvestmentListByCategory(int category_index);

    public InvestmentCategoryVO selectInvestmentCategoryByIndex(int category_index);
    
    public List<UserInfoVO> selectUserInfoList();
    
    public List<WalletWithdrawalVO> selectRequestFilList();
    
    public InvestmentCategoryVO verifyProductCategory(String product_name);
    
    public void addNewProductCategory(InvestmentCategoryVO investmentCategoryVO);
    
    public void updateProductCategory(InvestmentCategoryVO investmentCategoryVO);
    
    public List<InvestmentVO> selectInvestmentListForUser(int user_id);
    
    public void addNewUser(UserInfoVO userInfoVO);
    
    public void addNewInvestment(InvestmentVO investmentVO);
    
    public void modifyInvestment(InvestmentVO investmentVO);
    
    public void addNewTokenPaidInfo(TokenPaidVO tokenPaidVO);
    
    public void updateTokenPaidInfo(TokenPaidVO tokenPaidVO);
    
    public void updateTokenPaidDetailInfo(TokenPaidVO tokenPaidVO);
    
    public void addNewTokenPaidDetailInfo(TokenPaidDetailVO tokenPaidDetailVO);
    
    public double selectLastTokenPaidInfo();
     
    public void deleteInvestment(InvestmentVO investmentVO);
    
    public List<MemoVO> selectUserMemo(UserInfoVO userInfoVO);
    
    public void regMemo(MemoVO memoVO);
    
    public void updateUser(UserInfoVO userInfoVO);
    
    public UserInfoVO verifyUserInfoVO(UserInfoVO userInfoVO);
    
    public String getUserPassword(String user_email);
    
    public void updateStatus(WalletWithdrawalVO walletWithdrawalVO);
}

