/**
 * @param divId: 页面容器，必填
 * @param url：视图path
 * @param success: 回调
 * @param formIdOrJson: 需要提交form的Id或待提交的数据（json格式）,可选
 */
function toPage(divId, url, success, formIdOrJson){
	var divc = "content_container";
	var json = {};
	if(null!=divId&&""!=divId){
		divc = divId;
	}
	if(typeof formIdOrJson == "object"){
		json = formIdOrJson
	}else{
		if(null!=formIdOrJson&&""!=formIdOrJson){
			json = formToJsonObject(formIdOrJson);
		}
	}
	$("#"+divc).load(url,json,success);
}

/**
 * ajax提交form
 * @param url
 * @param formId
 * @param success
 * @returns
 */
function toAjaxForm(url,formId,success){
	var json = {};
	if(null!=formId&&""!=formId){
		json = $("#"+formId).serializeObject();
	}
	$.post(url,json,success,"json");
}



$(function(){
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	
	/**
	 * 正则计算一组字符串中英文字母的个数
	 * @param str
	 * @returns
	 */
	String.prototype.getWorldNum = function(){
		if(/[a-z]/i.test(this)){
	        return this.match(/[a-z]/ig).length;
	    }
	    return 0;
	};
	
	String.prototype.toHumpStyle = function(){
		return this.toLowerCase().replace(/_(\w)/g, function(all, letter){
			return letter.toUpperCase();
		})
	};
	
	Date.prototype.Format = function (fmt) { // author: meizz
	    var o = {
	        "M+": this.getMonth() + 1, // 月份
	        "d+": this.getDate(), // 日
	        "h+": this.getHours(), // 小时
	        "m+": this.getMinutes(), // 分
	        "s+": this.getSeconds(), // 秒
	        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
	        "S": this.getMilliseconds() // 毫秒
	    };
	    if (/(y+)/.test(fmt))
	        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	            return fmt;
	}
})

function JsonObjToHumpStyle(o){
	var t = {};
	Object.keys(o).forEach(function(key){
		var newkey = key.toLowerCase().replace(/_(\w)/g, function(all, letter){
			return letter.toUpperCase();
		})
		t[newkey] = o[key];
	});
	return t
}