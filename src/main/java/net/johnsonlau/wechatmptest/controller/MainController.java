package net.johnsonlau.wechatmptest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.johnsonlau.wechatmptest.SignUtil;
import net.johnsonlau.wechatmptest.dto.WechatEvent;

@RestController
@Slf4j
public class MainController {

	@GetMapping("/home")
	public String home() {
		return "Hello world";
	}

	@GetMapping("/wechat")
	public String wechatGet(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		log.info("signature: {}, timestamp: {}, nonce: {}, echostr: {}", signature, timestamp, nonce, echostr);
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		} else {
			return "";
		}
	}

	@PostMapping("/wechat")
	public String wechatPost(@RequestBody WechatEvent wechatEvent) {
		log.info("post: {}", wechatEvent);
		return "";
	}

}
