$(function () {
	debugger
	/**
	 * 文件上传初始化
	 * @returns
	 */
	var url = webRoot+'/fileOperate/readExcelFile';
	var paramsJson = {};
	$bootstrapFileInput.load('file', url, ['xls','xlsx'], paramsJson);
	/**
	 * 上传前校验
	 */
	$bootstrapFileInput.preUpload('file', function(){
		
		if($("#jobName").val()==''){
        	layer.msg('请选择EXCEL文件类型',{icon: 2});
        	return false;
        }
		return true;
	});
})
