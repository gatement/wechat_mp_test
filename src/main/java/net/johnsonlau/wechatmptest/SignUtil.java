package net.johnsonlau.wechatmptest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignUtil {
	public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
		// 将token、timestamp、nonce三个参数进行字典序排序
		String[] arr = new String[] { token, timestamp, nonce };
		String content = Arrays.stream(arr).sorted().collect(Collectors.joining());

		MessageDigest md = null;
		String digestStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.getBytes("utf-8"));
			digestStr = byteToStr(digest);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return digestStr != null ? digestStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			result.append(byteToHexStr(byteArray[i]));
		}
		return result.toString();
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param input
	 * @return
	 */
	private static String byteToHexStr(byte input) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(input >>> 4) & 0X0F];
		tempArr[1] = Digit[input & 0X0F];

		return new String(tempArr);
	}
}
