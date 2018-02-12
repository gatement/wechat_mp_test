package net.johnsonlau.wechatmptest.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.johnsonlau.wechatmptest.dto.WechatPageAccessToken;
import net.johnsonlau.wechatmptest.dto.WechatUserInfo;

@RestController
@Slf4j
@RequestMapping("/pages")
public class PagesController {

	@GetMapping("/no_openid")
	public String noOpenId() {
		return "no_openid page";
	}

	@GetMapping("/snsapi_base")
	public String snsapiBase(@RequestParam String state, @RequestParam String code,
			@Value("${app.app-id}") String appId, @Value("${app.app-secret}") String appSecret,
			@Value("${app.wechat.url.get-page-access-token}") String url) {
		WechatPageAccessToken pageAccessToken = null;
		try {
			pageAccessToken = getPageAccessToken(code, appId, appSecret, url);
		} catch (Exception e) {
			log.error(e.toString());
			return "snsapi_base page: get page access_token error.";
		}
		log.info("/pages/snsapi_base: pageAccessToken={}", pageAccessToken);
		return "snsapi_base page: openid=" + pageAccessToken.getOpenid();
	}

	@GetMapping("/snsapi_userinfo")
	public String snsapiUserinfo(@RequestParam String state, @RequestParam String code,
			@Value("${app.app-id}") String appId, @Value("${app.app-secret}") String appSecret,
			@Value("${app.wechat.url.get-page-access-token}") String getPageAccessTokenUrl,
			@Value("${app.wechat.url.get-user-info}") String getUserInfoUrl) {

		WechatPageAccessToken pageAccessToken = null;
		try {
			pageAccessToken = getPageAccessToken(code, appId, appSecret, getPageAccessTokenUrl);
		} catch (Exception e) {
			log.error(e.toString());
			return "snsapi_userinfo page: get page access_token error.";
		}

		log.info("/pages/snsapi_userinfo: pageAccessToken={}", pageAccessToken);

		WechatUserInfo userInfo = null;
		try {
			String openid = pageAccessToken.getOpenid();
			String accessToken = pageAccessToken.getAccessToken();
			userInfo = getUserInfo(accessToken, openid, getUserInfoUrl);
		} catch (Exception e) {
			log.error(e.toString());
			return "snsapi_userinfo page: get userInfo error.";
		}
		log.info("/pages/snsapi_userinfo: userInfo={}", userInfo);
		return "snsapi_userinfo page: nickname=" + userInfo.getNickname();
	}

	private WechatPageAccessToken getPageAccessToken(String code, String appId, String appSecret, String url)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("appid", appId);
		urlVariables.put("secret", appSecret);
		urlVariables.put("code", code);
		String resultStr = new RestTemplate().getForObject(url, String.class, urlVariables);
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(resultStr, WechatPageAccessToken.class);
	}

	private WechatUserInfo getUserInfo(String accessToken, String openid, String url)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("access_token", accessToken);
		urlVariables.put("openid", openid);
		String resultStr = new RestTemplate().getForObject(url, String.class, urlVariables);
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(resultStr, WechatUserInfo.class);
	}
}