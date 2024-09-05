package com.gkemayo.library.customer;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(defaultValue = "Mail Model")
public class MailDTO {
	
	@Schema(defaultValue = "Mail sender address")
	public final String MAIL_FROM = "noreply.library.test@gmail.com";
	
	@Schema(defaultValue = "Customer receiver id")
	private Integer customerId;
	
	@Schema(defaultValue = "Email subject")
	private String emailSubject;
	
	@Schema(defaultValue = "Email content")
	private String emailContent;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	
}
