package net.johnsonlau.wechatmptest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.johnsonlau.wechatmptest.SignUtil;

@RestController
@Slf4j
public class MainController {

	@GetMapping("/wechat")
	public String wechat(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		log.info("signature: {}, timestamp: {}, nonce: {}, echostr: {}", signature, timestamp, nonce, echostr);
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		} else {
			return "";
		}
	}

}