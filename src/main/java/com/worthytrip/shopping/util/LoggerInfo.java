package com.worthytrip.shopping.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 日志记录
 * @author weiyunpeng
 *
 */

public class LoggerInfo {
	
	//日志记录
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private  StringBuffer stringbuffer=new StringBuffer(500);//日志记录，静态属性用于记录日志
	
	//拼接方法查询
	public void appendLog(String msg)
	{
		stringbuffer.append(msg);
	}
	public  void printLogger()
	{
		//记录日志
		logger.info(stringbuffer.toString());
		//取得字符串的长度
		int stringbuffer_length=stringbuffer.length();
		stringbuffer.delete(0, stringbuffer_length);//删除字符串从0-stringbuffer_length处的内容
		
	}
	
	
	

}
