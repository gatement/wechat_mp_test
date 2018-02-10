package net.johnsonlau.wechatmptest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@RequestMapping(path="/wechat", method=RequestMethod.GET)
	public String wechat() {
		return "hello world2";
	}

}
