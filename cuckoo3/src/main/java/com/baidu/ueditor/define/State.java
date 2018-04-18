package com.baidu.ueditor.define;

/**
 * 处理状态接口
 * @author hancong03@baidu.com
 *
 */
public interface State {
	
	public boolean isSuccess ();
	
	public void putInfo( String name, String val );
	
	public void putInfo ( String name, long val );
	
	public String getInfo ( String name);//add by xudan;获取信息保存
	
	public String toJSONString ();

}
