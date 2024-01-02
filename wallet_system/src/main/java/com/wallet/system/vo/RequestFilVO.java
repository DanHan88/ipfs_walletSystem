/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Repository
 */
package com.wallet.system.vo;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.stereotype.Repository;

@Repository(value="requestFilVO")
public class RequestFilVO{
    
	private String request_date;
	private String user_email;
	private String user_name;
	private String fil_address;
	private BigDecimal request_amount;
	private BigDecimal gas_fee;
	private BigDecimal total_amount;
	private String status;
	private String memo;
	
	public String getRequest_date() {
		return request_date;
	}
	public void setRequest_date(String request_date) {
		this.request_date = request_date;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getFil_address() {
		return fil_address;
	}
	public void setFil_address(String fil_address) {
		this.fil_address = fil_address;
	}
	public BigDecimal getRequest_amount() {
		return request_amount;
	}
	public void setRequest_amount(BigDecimal request_amount) {
		this.request_amount = request_amount;
	}
	public BigDecimal getGas_fee() {
		return gas_fee;
	}
	public void setGas_fee(BigDecimal gas_fee) {
		this.gas_fee = gas_fee;
	}
	public BigDecimal getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}

