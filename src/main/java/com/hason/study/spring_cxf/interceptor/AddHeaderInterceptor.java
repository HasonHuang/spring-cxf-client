package com.hason.study.spring_cxf.interceptor;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * 拦截器：在XML片段头部添加 用户名和密码
 * 
 * @author hason
 *
 */
public class AddHeaderInterceptor extends AbstractPhaseInterceptor<SoapMessage>{
	
	private String name;
	private String password;

	public AddHeaderInterceptor(String name, String password) {
		super(Phase.PREPARE_SEND);  // 准备发送SOAP消息时，启动拦截器
		this.name = name;
		this.password = password;
	}

	/**
	 * 在SOAP消息添加认证信息， XML文档片段：
	 * <authHeader>
	 *   <userId>xx</userId>
	 *   <userPassword>xx</userPassword>
	 * </authHeader>
	 */
	public void handleMessage(SoapMessage message) throws Fault {
		// 创建document对象
		Document document = DOMUtils.createDocument();
		// 创建xml头部
		Element element = document.createElement("authHeader");
		// 此处创建的元素、顺序，应该按照服务器的要求
		Element userId = document.createElement("userId");
		Element userPassword = document.createElement("userPassword");
		userId.setTextContent(name);
		userPassword.setTextContent(password);
		
		element.appendChild(userId);
		element.appendChild(userPassword);
		
		// 把element包装成header，并添加到SOAP消息的Header列表 
		List<Header> headerList = message.getHeaders();
		headerList.add(new Header(new QName("Q"), element));
	}

}
