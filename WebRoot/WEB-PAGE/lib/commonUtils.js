var commonUtils = window.commonUtils || {};

String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, '');
};

/**
 * 扩展validatebox的校验规则
 */
$.extend($.fn.validatebox.defaults.rules, {
    equals: {
        validator: function(value, param) {
            return value == $(param[0]).val();
        }
    },
    minLength: {
        validator: function(value, param) {
            return value.trim().length >= param[0];
        },
        message: '有效字符不得少于{0}'
    },
    maxLength: {
        validator: function(value, param) {
            return value.trim().length <= param[0];
        },
        message: '有效字符不得多于{0}'
    }
});

/**
 * 扩展validatebox的编辑类型
 */
$.extend($.fn.validatebox.defaults.editors, {
    datetimebox: {
        init: function(container, options) {
            var editor = $('<input />').appendTo(container);
            options.editable = false;
            editor.datetimebox(options);
            return editor;
        },
        getValue: function(target) {
            return $(target).datetimebox('getValue');
        },
        setValue: function(target, value) {
            $(target).datetimebox('setValue', value);
        },
        resize: function(target, width) {
            $(target).datetimebox('resize', width);
        },
        destroy: function(target) {
            $(target).datetimebox('destroy');
        }
    }
});

/**
 * 扩展datagrid的字段编辑类型
 */
$.extend($.fn.datagrid.defaults.editors, {
    datetimebox: {
        init: function(container, options) {
            var editor = $('<input />').appendTo(container);
            options.editable = false;
            editor.datetimebox(options);
            return editor;
        },
        getValue: function(target) {
            return $(target).datetimebox('getValue');
        },
        setValue: function(target, value) {
            $(target).datetimebox('setValue', value);
        },
        resize: function(target, width) {
            $(target).datetimebox('resize', width);
        },
        destroy: function(target) {
            $(target).datetimebox('destroy');
        }
    },
    combocheckboxtree: {
        init: function(container, options) {
            var editor = $('<input />').appendTo(container);
            options.multiple = true;
            editor.combotree(options);
            return editor;
        },
        getValue: function(target) {
            return $(target).combotree('getValue').join(',');
        },
        setValue: function(target, value) {
            $(target).combotree('setValue', commonUtils.str2Array(value));
        },
        resize: function(target, width) {
            $(target).combotree('resize', width);
        },
        destroy: function(target) {
            $(target).combotree('destroy');
        }
    }
});

/**
 * 动态改变datagrid的字段编辑器
 */
$.extend($.fn.datagrid.methods, {
    addEditor: function(container, param) {
        if(param instanceof Array) {
            $.each(param, function(index, item) {
                var e = $(container).datagrid('getColumnOption', item.field);
                e.editor = item.editor;
            });
        } else {
            var e = $(container).datagrid('getColumnOption', param.field);
            e.editor = param.editor;
        }
    },
    removeEditor: function(container, param) {
        if(param instanceof Array) {
            $.each(param, function(index, item) {
                var e = $(container).datagrid('getColumnOption', item);
                e.editor = {};
            });
        } else {
            var e = $(container).datagrid('getColumnOption', param);
            e.editor = {};
        }
    }
});

/**
* 用于easyui加载数据出错时的操作
*/
var easyuiErrorTip = function(xhr) {
   $.messager.progress('close');
   $.messager.alert('加载错误', xhr.responseText);
};
$.fn.form.defaults.onLoadError = easyuiErrorTip;
$.fn.datagrid.defaults.onLoadError = easyuiErrorTip;
$.fn.tree.defaults.onLoadError = easyuiErrorTip;
$.fn.treegrid.defaults.onLoadError = easyuiErrorTip;
$.fn.combobox.defaults.onLoadError = easyuiErrorTip;
$.fn.combogrid.defaults.onLoadError = easyuiErrorTip;


$(function() {
    /**
     * 启动遮罩加载
     */
    if($("#loadingMask")) {
        function loading() {
            $("#loadingMask").fadeOut("normal", function() {
                $(this).remove();
            });
        }
        var pc;
        $.parser.onComplete = function() {
            if(pc) clearTimeout(pc);
            pc = setTimeout(loading, 100);
        }
    }
    
    /**
     * 捕获浏览器关闭事件
     */
    window.onunload = function() {
        $.ajax({
            url: "/user/browserCloseEvent.do",
            type: "post",
            async: false
        });
    }
    
    /**
     * 加载默认主题样式
     */
    if(!$.cookie('easyuiTheme')) {
        $.cookie('easyuiTheme', 'default', {
            expires: 7
        });
    }
    
});


/**
 * 更换EasyUI主题样式
 */
commonUtils.changeTheme = function(themeName) {
    var $easyuiTheme = $('#easyuiTheme');
    var url = $easyuiTheme.attr('href');
    var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
    $easyuiTheme.attr('href', href);
    
    var $iframe = $('iframe');
    if($iframe.length > 0) {
        for(var i=0; i<$iframe.length; i++) {
            var ifr = $iframe[i];
            $(ifr).contents().find('#easyuiTheme').attr('href', href);
        }
    }
    
    $.cookie('easyuiTheme', themeName, {
        expires : 7
    });
};


/**
 * 关闭窗口页面
 */
commonUtils.closeWindowPage = function() {
    if(navigator.userAgent.indexOf("MSIE") > 0) {
        if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
            window.opener = null;
            window.close();
        } else {
            window.open('', '_top');
            window.top.close();
        }
    } else if(navigator.userAgent.indexOf("Firefox") > 0) {
        window.location.href = 'about:blank';
    } else {
        window.opener = null;
        window.open('', '_self', '');
        window.close();
    }
};


/**
 * 判断字符串是否为空
 */
commonUtils.isEmpty = function(str) {
    if(str == null || str == "") return true;
    var regu = "^[ ]+$";
    var re = new RegExp(regu);
    return re.test(str);
};


/**
 * 序列化表单为JSON对象
 */
commonUtils.serializeForm = function(form) {
    var obj = {};
    $.each(form.serializeArray(), function(index) {
        if(obj[this['name']]) {
            obj[this['name']] = obj[this['name']] + "," + this['value'];
        } else {
            obj[this['name']] = this['value'];
        }
    });
    return obj;
};


/**
 * 将逗号序列字符串返回为数组
 */
commonUtils.str2Array = function(str) {
    if((typeof str) == 'string' && str != undefined && str != '') {
        var values = [];
        var t = str.split(',');
        for(var i=0; i<t.length; i++) {
            values.push('' + t[i]); /* 避免将ID当成数字 */
        }
        return values;
    } else {
        return [];
    }
};

/**
 * 用参数替换字符串中的{?}表达式
 */
commonUtils.formatString = function(str) {
    for(var i=0; i<arguments.length-1; i++) {
        str = str.replace("{" + i + "}", arguments[i+1]);
    }
    return str;
};
