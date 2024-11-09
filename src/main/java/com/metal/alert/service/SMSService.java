package com.metal.alert.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;

@Service
public class SMSService {

	@Value("${twilio.account.sid}")
	private String accountSid;

	@Value("${twilio.auth.token}")
	private String authToken;

	@Value("${twilio.phone.number}")
	private String fromNumber;

	@Value("${twilio.whatsapp.phone.number}")
	private String fromWhatsappNumber;

	@PostConstruct
	public void init() {
		Twilio.init(accountSid, authToken);
	}

	public void sendSMSMessage(String toNumber, String message) {
		Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber), message).create();
	}

	public void sendWhatsappMessage(String toNumber, String message) {
		Message.creator(new PhoneNumber("whatsapp:+966540568223"), new PhoneNumber(fromWhatsappNumber), message)
				.create();
	}

}
