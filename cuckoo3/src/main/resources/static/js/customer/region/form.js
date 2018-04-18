$(function() {
    initRole();
    validateRegionForm();
});

/**
 * 初始化资源类型下拉框
 */
function initRole() {
	initRoleData();//角色选择下拉框数据
    $('#roleId').select2({
        placeholder : "请选择角色...",
        language : "zh-CN",
        minimumResultsForSearch : Infinity
    });
}

/**
 * 表单验证
 */
function validateRegionForm()
{    
    $('#regionForm').validate({
        errorElement : 'div',
        errorClass : 'help-block',
        focusInvalid : false,
        ignore : "",
        rules : {
            regionName : {
                required : true,
                remote: { //更新时不验证
                  param: {
                    url: sys.rootPath + '/region/validateRegionName.html',
                    cache :false
                  },
                  depends: function() {
                    return typeof($("#regionId").val()) == "undefined";
                  }
                }
            },
            "role.id" : {
                required : true
            }
        },
        messages : {
        	regionName : {
                required : "请填写区域名称",
                remote : "该区域已注册,请使用其它区域名称"
            },
            "role.id" : "请选择角色"
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
            var regionId = $("#regionId").val();
            var url = "";
            if(regionId != undefined)
            {
                url = '/region/edit.html';
            }else
            {
                url = '/region/add.html';
            }
            // modyfy by zhangmz 2016-07-01
            // commit('regionForm', url, '/region/listUI.html');
            webside.common.commit('regionForm', url, '/region/listUI.html');
        }
    });
}

/**
 * 这句没有起到应有的作用，在实体类中使用了默认值
 * @param object
 */
function checkboxValue(object)
{
	object.value=object.checked?1:0;
}