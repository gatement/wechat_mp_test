package net.johnsonlau.wechatmptest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MainController {
	private static String TOKEN = "123456";

	@GetMapping("/wechat")
	public String wechat(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		log.info("signature: {}, timestamp: {}, nonce: {}, echostr: {}", signature, timestamp, nonce, echostr);
		if (checkSignature(signature, timestamp, nonce)) {
			return echostr;
		} else {
			return "";
		}
	}

	private boolean checkSignature(String signature, String timestamp, String nonce) {
		boolean result = false;
		log.info("verify result: {}", result);
		return result;
	}

}
