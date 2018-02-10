package net.johnsonlau.wechatmptest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@RequestMapping("/hello")
	public String hello() {
		return "hello world2";
	}

}
