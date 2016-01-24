package com.hason.study.spring_cxf.client;

import org.apache.cxf.frontend.ClientProxy;

import com.hason.study.spring_cxf.interceptor.AddHeaderInterceptor;
import com.hason.study.spring_cxf.webservice.HelloWorldWebService;
import com.hason.study.spring_cxf.webservice.impl.HelloWorldWebServiceImplService;

public class Client {

	public static void main(String[] args) {
		HelloWorldWebServiceImplService factory = new HelloWorldWebServiceImplService();
		HelloWorldWebService service = factory.getHelloWorldWebServiceImplPort();
		// 添加拦截器，通过自定义Out拦截器，发送消息时头部添加用户、密码
		org.apache.cxf.endpoint.Client client = ClientProxy.getClient(service);
		client.getOutInterceptors().add(new AddHeaderInterceptor("hason", "hason"));
		
		String result = service.sayHi("hason_Huang");
		System.out.println(result);
	}
}
