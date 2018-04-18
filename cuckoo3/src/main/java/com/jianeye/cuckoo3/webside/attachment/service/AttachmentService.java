/**
 * 
 */
package com.jianeye.cuckoo3.webside.attachment.service;

import java.util.List;
import java.util.Map;

import com.jianeye.cuckoo3.webside.attachment.model.Attachment;

/**
 * 上传附件操作
 * @author xudan
 *
 */
public interface AttachmentService {
	
	public List<Attachment> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 自定义方法
	 * 获取所有符合条件的附件，不分页
	 * @param userId
	 * @return
	 */
	public List<Attachment> queryListAll(Map<String, Object> parameter);
	
	public int insert(Attachment attachment);
	
	public int insertBatch(List<Attachment> attachments);
	
	public Attachment findById(Long id);
	
	public Attachment findByUrl(String url);

	public int update(Attachment attachment);
    
    public int deleteBatchById(List<Long> ids);
    
    public int deleteBatchByFkId(Map<String, Object> parameter);
    
    public int deleteBatchByUrl(Map<String, Object> parameter);
    
    public int deleteBatchByUrl(List<Attachment> allattachments);
}
