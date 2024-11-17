package com.metal.alert.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.metal.alert.model.MetalAlertBean;
import com.metal.alert.service.EmailService;
import com.metal.alert.service.SMSService;

@RestController
public class MetalAlertController {

	private static final Logger logger = LogManager.getLogger(MetalAlertController.class);

	private String URL = "https://www.goldapi.io/api";
//	https://www.goldapi.io/api/XAU/USD

//	OPT - Add this into a prop file and call using @value
	private static final String API_KEY = "goldapi-219eesm2rpjjwp-io";

	private static final String FORWARD_SLASH = "/";

	private static final String EMAIL_SUBJECT = "Test Email For Metals API";

	private static final String SPACE = " ";

	private static final String PLUS = "+";

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EmailService emailService;

	SMSService smsService;

	@GetMapping("/getGoldPrice")
	public Double getGoldPrice(@RequestParam String metalType, @RequestParam String currencyIn3Letters,
			@RequestParam String toEmailAddress, @RequestParam String toMobileNumber,
			@RequestParam(name = "date", required = false) Object date) {

		logger.info("getGoldPrice method started");
		Double currentPrice = -1.0;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("x-access-token", API_KEY);
		try {

//			cleaning the mobile number field

			if (toMobileNumber.contains(SPACE)) {
				toMobileNumber = toMobileNumber.trim();
			}

			if (!metalType.isEmpty() && !currencyIn3Letters.isEmpty()) {
				URL = URL.concat(FORWARD_SLASH).concat(metalType).concat(FORWARD_SLASH).concat(currencyIn3Letters);
			}
			HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

			ResponseEntity<MetalAlertBean> response = restTemplate.exchange(URL, HttpMethod.GET, httpEntity,
					MetalAlertBean.class);

			if (response.getStatusCode().equals(HttpStatus.OK)) {
				if (response.getBody() != null) {
					currentPrice = response.getBody().getPrice_gram_24k();

					String finalMessage = "The current price for 24k gold is - " + currentPrice;

//					send Email
					emailService.sendEmail(toEmailAddress, EMAIL_SUBJECT, finalMessage);

//					send SMS
					smsService.sendSMSMessage(toMobileNumber, finalMessage);

//					send WhatappMessage
					smsService.sendWhatsappMessage(toMobileNumber, finalMessage);

				}
			}
		} catch (HttpClientErrorException e) {
			logger.info("Error" + e.getMessage());
		} catch (Exception e) {
			logger.info("Error" + e.getMessage());
		}
		logger.info("getGoldPrice method ended");
		return currentPrice;

	}

}
