package net.johnsonlau.wechatmptest.controller;

import org.springframework.beans.factory.annotation.Value;
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
public class CallbackController {

	@GetMapping("/wechat")
	public String wechatGet(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr,
			@Value("${app.token}") String token) {
		log.info("get /wechat, signature: {}, timestamp: {}, nonce: {}, echostr: {}", signature, timestamp, nonce,
				echostr);
		if (SignUtil.checkSignature(signature, timestamp, nonce, token)) {
			log.info("get /wechat succeeded");
			return echostr;
		} else {
			log.info("get /wechat failed");
			return "";
		}
	}

	@PostMapping("/wechat")
	public String wechatPost(@RequestBody WechatEvent wechatEvent) {
		log.info("post: {}", wechatEvent);
		return "";
	}
}
