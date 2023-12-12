package com.wallet.system.etc;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor())
        .addPathPatterns("/userAppUserInfo")
        .addPathPatterns("/userAppUserInfo/**")
        .addPathPatterns("/userAppPayOut")
        .addPathPatterns("/userAppPayOut/**")
        .addPathPatterns("/userAppInvestment")
        .addPathPatterns("/userAppInvestment/**")
        .addPathPatterns("/userAppRequestFund")
        .addPathPatterns("/userAppRequestFund/**")
        .addPathPatterns("/UserAppMain")
        .addPathPatterns("/UserAppMain/**")
        .addPathPatterns("/updateUserPassword")
        .addPathPatterns("/updateUserPassword/**")
        .addPathPatterns("/updateUserProfileImg")
        .addPathPatterns("/updateUserProfileImg/**")
        .addPathPatterns("/addWalletWithdrawal")
        .addPathPatterns("/addWalletWithdrawal/**")
        .addPathPatterns("/updateFundRequest")
        .addPathPatterns("/updateFundRequest/**")
        .addPathPatterns("/addNewProduct")
        .addPathPatterns("/addNewProduct/**")
        .addPathPatterns("/selectInvestmentListForUser")
        .addPathPatterns("/selectInvestmentListForUser/**")
        .addPathPatterns("/addNewUser")
        .addPathPatterns("/addNewUser/**")
        .addPathPatterns("/modifyInvestment")
        .addPathPatterns("/modifyInvestment/**")
        .addPathPatterns("/deleteInvestment")
        .addPathPatterns("/deleteInvestment/**")
        .addPathPatterns("/selectUserMemo")
        .addPathPatterns("/selectUserMemo/**")
        .addPathPatterns("/selectTokenDetailList")
        .addPathPatterns("/selectTokenDetailList/**")
        .addPathPatterns("/updateUser")
        .addPathPatterns("/updateUser/**")
        .addPathPatterns("/regMemo")
        .addPathPatterns("/regMemo/**")
        .addPathPatterns("/payoutConfirm")
        .addPathPatterns("/payoutConfirm/**")
        .addPathPatterns("/addNewTokenPaid")
        .addPathPatterns("/addNewTokenPaid/**")
        .addPathPatterns("/updateTokenPaid")
        .addPathPatterns("/updateTokenPaid/**")
        .addPathPatterns("/userManager")
        .addPathPatterns("/userManager/**")
        .addPathPatterns("/fundRequest")
        .addPathPatterns("/fundRequest/**")
        .addPathPatterns("/main")
        .addPathPatterns("/main/**")
        .addPathPatterns("/payoutManager")
        .addPathPatterns("/payoutManager/**")
        .excludePathPatterns().excludePathPatterns("/UserApp")
        .excludePathPatterns().excludePathPatterns("/");
    }
}