package com.jianeye.cuckoo3.webside.attachment.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jianeye.cuckoo3.webside.attachment.model.Attachment;
import com.jianeye.cuckoo3.webside.base.basemapper.BaseMapper;

/*@Repository*/
public interface AttachmentMapper /*extends BaseMapper<Attachment, Long>*/{

    int insertSelective(Attachment record);
    
    Attachment findByUrl(String url);

   /**
	* fkTable 关联表
	* fkIdName 关联表ID名称
	* fkId 关联表ID值
	* attachments,删除url in attachments.url的附件；
	* fkIds,删除相关的附件
    * urls,传入urls,删除url not in urls的附件；
    * @param parameter
    * @return
    */
    int deleteBatch(Map<String, Object> parameter);
    
}