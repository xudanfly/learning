var ue;
$(function() {
	//实例化编辑器 
	ue = UE.getEditor('container');
	//自定义参数
	ue.ready(function() {
	    ue.execCommand('serverparam', {
	        'fkTable': 'tb_financial_product',
	        'fkIdName': 'f_id',
	        'fkId': !$("#financialProductId").val()?"":$("#financialProductId").val()
	    });
	});
	if($("#deleteStatus").attr("value")){$("#deleteStatus").val($("#deleteStatus").attr("value"));}
	//自动生成摘要
	$("#summary").on("focus",function(){
		if(!$(this).val()){
			var txt = $.trim(ue.getContentTxt());
			if(txt){
				$(this).val(txt.length>290?(txt.substr(0,290)+"..."):txt);//数据库字段长度300
			}
		}
	})
    validateFinancialProductForm();
});

/**
 * 表单验证
 */
function validateFinancialProductForm()
{    
    $('#financialProductForm').validate({
        errorElement : 'div',
        errorClass : 'help-block',
        focusInvalid : false,
        ignore : "",
        rules : {
        	title : {
                required : true,
                maxlength:100
            },
            subtitle : {
                maxlength:100
            },
            summary : {
                required : true,
                maxlength:300
            }
        },
        messages : {
        	title : {
                required : "请填写标题",
                maxlength:"标题不能大于100个字符"
            },
            subtitle : {
                maxlength:"副标题不能大于100个字符"
            },
            summary : {
                required : "请填写摘要",
                maxlength:"摘要不能大于300个字符"
            }
        },
        highlight : function(e) {
            $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
        },
        success : function(e) {
            $(e).closest('.form-group').removeClass('has-error').addClass('has-success');
            $(e).remove();
        },
        errorPlacement : function(error, element) {
           if(element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                var controls = element.closest('div[class*="col-"]');
                if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
                else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
            }
            else if(element.is('.select2')) {
                error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
            }
            else if(element.is('.chosen-select')) {
                error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
            }
            else error.insertAfter(element.parent());
        },
        submitHandler : function(form) {
            var financialProductId = $("#financialProductId").val();
            var url = "";
            getAttachmenturl();//设置文件参数
            if(financialProductId != undefined)
            {
                url = '/financialProduct/edit.html';
            }else
            {
                url = '/financialProduct/add.html';
            }
            webside.common.commit('financialProductForm', url, '/financialProduct/listUI.html');
        }
    });
}

function getAttachmenturl(){
	//获取文章编辑内容，获取上传附件
	var attachmenturls = "";
	$("#htmlTemp").html(ue.getContent());
	$("#htmlTemp").find("img").each(function(){
		attachmenturls += $(this).attr("src")+",";
	})
	$("#htmlTemp").find("a").each(function(){
		attachmenturls += $(this).attr("href")+",";
	})
	$("#htmlTemp").find("video").each(function(){
		attachmenturls += $(this).attr("src")+",";
	})
	$("#attachmenturls").val(attachmenturls);
}