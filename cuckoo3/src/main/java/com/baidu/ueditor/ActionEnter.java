package com.baidu.ueditor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.hunter.FileManager;
import com.baidu.ueditor.hunter.ImageHunter;
import com.baidu.ueditor.upload.Uploader;
import com.jianeye.cuckoo3.webside.attachment.model.Attachment;

@Component
@Scope("prototype")
public class ActionEnter {
	/*附件上传到文件系统的路径*/
	@Value("${local_path}")
	private String local_path;
	
	private HttpServletRequest request = null;
	
	private String rootPath = null;
	private String contextPath = null;
	//URL传递参数
	private String actionType = null;
	private String fkTable = null;
	private String fkIdName = null;
	private String fkId = null;
	
	private ConfigManager configManager = null;

	/**
	 * 原构造函数
	 * @param request
	 * @param rootPath
	 */
	public void initActionEnter( HttpServletRequest request, String rootPath ) {
		
		this.request = request;
		this.rootPath = rootPath;
		this.actionType = request.getParameter( "action" );
		this.fkTable = request.getParameter("fkTable");
		this.fkIdName = request.getParameter("fkIdName");
		this.fkId = request.getParameter("fkId");
		this.contextPath = request.getContextPath();
		this.configManager = ConfigManager.getInstance( this.rootPath, this.contextPath,this.local_path, request.getRequestURI() );
	}
	
	public String exec () {
		
		String callbackName = this.request.getParameter("callback");
		
		if ( callbackName != null ) {

			if ( !validCallbackName( callbackName ) ) {
				return new BaseState( false, AppInfo.ILLEGAL ).toJSONString();
			}
			
			return callbackName+"("+this.invoke()+");";
			
		} else {
			return this.invoke();
		}

	}
	

    synchronized public String invoke() {
		
		if ( actionType == null || !ActionMap.mapping.containsKey( actionType ) ) {
			return new BaseState( false, AppInfo.INVALID_ACTION ).toJSONString();
		}
		
		if ( this.configManager == null || !this.configManager.valid() ) {
			return new BaseState( false, AppInfo.CONFIG_ERROR ).toJSONString();
		}
		
		State state = null;
		
		int actionCode = ActionMap.getType( this.actionType );
		
		Map<String, Object> conf = null;
		
		switch ( actionCode ) {
		
			case ActionMap.CONFIG:
				return this.configManager.getAllConfig().toString();
				
			case ActionMap.UPLOAD_IMAGE:
			case ActionMap.UPLOAD_SCRAWL:
			case ActionMap.UPLOAD_VIDEO:
			case ActionMap.UPLOAD_FILE:
				conf = this.configManager.getConfig( actionCode );
				state = new Uploader( request, conf ).doExec();
				break;
				
			case ActionMap.CATCH_IMAGE:
				conf = configManager.getConfig( actionCode );
				String[] list = this.request.getParameterValues( (String)conf.get( "fieldName" ) );
				state = new ImageHunter( conf ).capture( list );
				break;
				
			case ActionMap.LIST_IMAGE:
			case ActionMap.LIST_FILE:
				conf = configManager.getConfig( actionCode );
				int start = this.getStartIndex();
				state = new FileManager( conf ).listFile( start );
				break;
				
		}
		//上传成功后将附件信息存入数据库
		if(state.isSuccess()){
			switch ( actionCode ) {
				case ActionMap.UPLOAD_IMAGE:
				case ActionMap.UPLOAD_SCRAWL:
				case ActionMap.UPLOAD_VIDEO:
				case ActionMap.UPLOAD_FILE:
				case ActionMap.CATCH_IMAGE:
					List<Attachment> allattachments = (List<Attachment>) this.request.getSession().getAttribute("allattachments");
					if(allattachments == null){  allattachments = new ArrayList<Attachment>();}
					Attachment attachment = new Attachment();
					attachment.setName(state.getInfo("original"));
					attachment.setType(state.getInfo("type"));
					attachment.setSize(Long.valueOf(state.getInfo("size")));
					attachment.setPath(state.getInfo("path"));
					attachment.setUrl(state.getInfo("url"));
					attachment.setCreatetime(new Date());
					attachment.setFkTable(fkTable);
					attachment.setFkIdName(fkIdName);
					if(!StringUtils.isEmpty(fkId)){	attachment.setFkId(Long.valueOf(fkId));}
					attachment.setStatus("0");
					allattachments.add(attachment);
					this.request.getSession().setAttribute("allattachments",allattachments );
					break;
				default:break;
			}
		}
		return state.toJSONString();
		
	}
	
	public int getStartIndex () {
		
		String start = this.request.getParameter( "start" );
		
		try {
			return Integer.parseInt( start );
		} catch ( Exception e ) {
			return 0;
		}
		
	}
	
	/**
	 * callback参数验证
	 */
	public boolean validCallbackName ( String name ) {
		
		if ( name.matches( "^[a-zA-Z_]+[\\w0-9_]*$" ) ) {
			return true;
		}
		
		return false;
		
	}
	
}