package net.johnsonlau.wechatmptest.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WechatEvent {
	@XmlElement(name = "ToUserName")
	private String toUserName;
	@XmlElement(name = "FromUserName")
	private String fromUserName;
	@XmlElement(name = "CreateTime")
	private Long createTime;
	@XmlElement(name = "MsgType")
	private String msgType;
	@XmlElement(name = "Event")
	private String event;
	@XmlElement(name = "EventKey")
	private String eventKey;
}
