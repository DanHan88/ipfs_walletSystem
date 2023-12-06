/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Repository
 */
package com.wallet.system.vo;

import java.util.Date;
import org.springframework.stereotype.Repository;

@Repository(value="WalletWithdrawalVO")
public class WalletWithdrawalVO {
	
	private int user_id;
	private float fil_amount;
	private String status;
	private String message;
	private String wallet_address;
	private Date request_date;
	private Date complete_date;
	private long wallet_withdrawals_id;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public float getFil_amount() {
		return fil_amount;
	}
	public void setFil_amount(float fil_amount) {
		this.fil_amount = fil_amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getWallet_address() {
		return wallet_address;
	}
	public void setWallet_address(String wallet_address) {
		this.wallet_address = wallet_address;
	}
	public Date getRequest_date() {
		return request_date;
	}
	public void setRequest_date(Date request_date) {
		this.request_date = request_date;
	}
	public Date getComplete_date() {
		return complete_date;
	}
	public void setComplete_date(Date complete_date) {
		this.complete_date = complete_date;
	}
	public long getWallet_withdrawals_id() {
		return wallet_withdrawals_id;
	}
	public void setWallet_withdrawals_id(long wallet_withdrawals_id) {
		this.wallet_withdrawals_id = wallet_withdrawals_id;
	}
}

