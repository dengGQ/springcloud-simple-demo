<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>客户信息管理</title>
<style type="text/css">
.form-group label{font-size:12px;font-weight:700;margin-left:0;}
.form-group input.form-control{background-color:#fff;margin:0 10px 5px;}
</style>
</head>
<body>

<script>
	$(function() {
		//证件类型转换
		var credType = getCredType($('#credType').val());
		$('#credType').val(credType);
		//还款频率
		var repayFreq = getTermsfreq($('#repayFreq').val());
		$('#repayFreq').val(repayFreq);
		//贷款状态
		var loanState = getLoadState($('#loanState').val());
		$('#loanState').val(loanState);
		//五级分类
		var fiveCalss = getFiveClass($('#fiveCalss').val());
		$('#fiveCalss').val(fiveCalss);
		
	})
</script>

	<div style="overflow: scroll;" >
		<div class="row">
			<form class="form-inline">  
				<c:forEach var="contract" items= "${list}" >
				<div class="form-group"> 
				    <table>  
					<tr>  
					    <td class="align-R"><label>合同号:</label> </td>
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.conNo}" /></td>  
					    <td class="align-R"><label>合同机构号：</label></td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.coOrgCode}"/></td>
					    <td class="align-R"><label>证件类型:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" id="credType" value="${contract.credType}" /></td> 
					    <td class="align-R"><label>证件号码:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.credCode}" /></td>
					</tr> 
					<tr>  
					    <td class="align-R"><label class="control-label">开始日期:</label> </td>
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="<fmt:formatDate value='${contract.conStartDate}' pattern='yyyy-MM-dd'/>" /></td>  
					    <td class="align-R"><label class="control-label">到期日期：</label></td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="<fmt:formatDate value='${contract.conEndDate}' pattern='yyyy-MM-dd'/>" /></td>
					    <td class="align-R"><label class="control-label">贷款期限:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.loanTerm}" /></td> 
					    <td class="align-R"><label class="control-label">贷款金额:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.loanAmt}"/></td>
					</tr>
					<tr>  
					    <td class="align-R"><label class="control-label">贷款余额:</label> </td>
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.loanBlanc}" /></td>  
					    <td class="align-R"><label class="control-label">担保方式：</label></td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${guarMode}" /></td>
					    <td class="align-R"><label class="control-label">币种:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.curry}" /></td> 
					    <td class="align-R"><label class="control-label">贷款月利率:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.loanMonthRate}" /></td>
					</tr>
					<tr>  
					    <td class="align-R"><label class="control-label">逾期利率:</label> </td>
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.ovdueRate}" /></td>  
					    <td class="align-R"><label class="control-label">贷款状态：</label></td>  
					    <td><input type="text" class="form-control"  readonly="readonly" value="${contract.loanState}" id="loanState"/></td>
					    <td class="align-R"><label class="control-label">五级分类状态:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" value="${contract.fiveCalss}" id="fiveCalss"/></td> 
					    <td class="align-R"><label class="control-label">借据号:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.iouNo}" /></td>
					</tr>
					<tr>  
					    <td class="align-R"><label class="control-label">还款频率:</label>  </td>
					    <td><input type="text" class="form-control"  readonly="readonly" value="${contract.repayFreq}" id="repayFreq"/></td>  
					    <td class="align-R"><label class="control-label">宽限类型：</label></td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.graceType}" /></td>
					    <td class="align-R"><label class="control-label">宽限期限:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.graceDay}" /></td> 
					    <td class="align-R"><label class="control-label">信托项目编号:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.projId}" /></td>
					</tr> 
					<tr>  
					    <td class="align-R"><label class="control-label">产品号:</label>  </td>
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="${contract.prodCode}" />  
					    <td class="align-R"><label class="control-label">业务日期：</label></td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="<fmt:formatDate value='${contract.etlDate}' pattern='yyyy-MM-dd'/>" /></td>
					    <td class="align-R"><label class="control-label">修改日期:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" placeholder="<fmt:formatDate value='${contract.modifDate}' pattern='yyyy-MM-dd'/>" /></td> 
					    <td class="align-R"><label class="control-label">数据来源:</label> </td>  
					    <td><input type="text" class="form-control"  readonly="readonly" value="${contract.dataSrc}"/></td>
					</tr> 
				    </table> 
		    		</div>  
				</c:forEach>  
			</form> 
		</div>
	
	<div class="container" style="height:275px">
		<div id="toolbar" class="btn-group group3">
			 <div class="btn-group">
		       <button class="btn btn-primary" id="editBtn">修改</button>
		    </div>
		</div>
		<label style="font-size:18px;">还款计划</label> 
		<table  id="details-table"></table>
	</div>
	</div>
	
	<script>
	$(function() {
		
		/**
		 * 修改实际还款信息
		 */
		$("#editBtn").click(function() {
			var rows = getRowsSeleced();
			var conNo = getIdSelections(rows);
			if (conNo == "") {
				layer.msg("请勾选要修改的数据", {
					icon : 2
				})
				return false;
			}
			var json = getParamsSeleced(rows);
			$.ajax({
				type : 'post',
				url : '${webRoot}/actualrepay/editData',
				data : { mydata : json },
				dataType : 'json',
				success : function(data) {
					if (data.success == "true") {
						parent.layer.alert("修改成功",{icon: 1});
					} else {
						parent.layer.alert(data.msg);
					}
				},
				error : function() {
					parent.layer.alert("修改失败",{icon: 2});
				},

			});
		});
		
		initDetailsTable();
		$('#details-table').bootstrapTable('hideColumn', 'ACTUALID');
	});
	
	/**
	 * 获取选中行数据
	 * @returns
	 */
	function getRowsSeleced() {
		var rows = $('#details-table').bootstrapTable('getSelections');
		return rows;
	};

	/**
	 * 封装选中行所需字段
	 * @param rows
	 * @returns json
	 */
	function getParamsSeleced(rows) {
		var paramsListA = [];
		for (var i = 0; i < rows.length; i++) {
			var paramsListB = {};
			paramsListB.actualId = rows[i].ACTUALID;//实际还款表ID
			paramsListB.actualAmt = String(rows[i].ACTUALAMT);//实际还款总额
			paramsListB.conNo = rows[i].CONNO;//合同号
			paramsListB.actualPrnpl = String(rows[i].ACTUALPRNPL);//实际还款本金
			paramsListB.actualIntes = String(rows[i].ACTUALINTES);//实际还款利息
			paramsListB.intesPnlty = String(rows[i].INTESPNLTY);//实际罚息金额
			paramsListB.actualRepayDate = "";
			var actualDate = String(rows[i].ACTUALREPAYDATE);
			if(actualDate.indexOf("-")==-1){
				var date = dateFormatUtil(actualDate);//格式化日期
				paramsListB.actualRepayDate = date;
			}else{
				paramsListB.actualRepayDate = actualDate;
			}
			paramsListA.push(paramsListB);
		}
		var json = JSON.stringify(paramsListA); 
		return json;
	};

	/**
	 * 判断是否选中
	 * @param rows
	 * @returns
	 */
	function getIdSelections(rows) {
		var conNo = "";
		for (var i = 0; i < rows.length; i++) {
			if ("" == conNo) {
				conNo = rows[i].CONNO;
			} else {
				conNo = conNo + "|" + rows[i].CONNO;
			}
		}
		return conNo;
	};
	
	/**
	 * 动态更新本月实还总额
	 * 本月实还总额=实还本金+实还利息+罚息
	 */
	function mathActualAmt(value,row){
		var actualPrnpl = row.ACTUALPRNPL;//实际还款本金
		var actualIntes = row.ACTUALINTES;//实际还款利息
		var intesPnlty = isNaN(row.INTESPNLTY) ? 0 : row.INTESPNLTY;//罚息金额
		
		if(isNaN(actualPrnpl))return;
		
		var result = math.parser().eval(actualPrnpl + "+" + actualIntes+ "+" + intesPnlty)
	      if (isNaN(result)) { 
	        return; 
	      }
	     result = Math.round(result*100)/100;//四舍五入保留两位小数 
	    return result; 
	}
	
	function initDetailsTable() {
		var url = "${webRoot}/creditinfo/queryEditInfoByConNo?conNo='${conNo}'";
		$('#details-table').bootstrapTable(
		{
			method : 'GET',
			url : url,
			pageNumber:1,
			pageSize:10,
			height: $(window).height() - 20,
			pageList:[15,25,50,100],
			clickToSelect : true, //是否启用点击选中行
			columns : [ 
				{checkbox : true}
				,{
				field: 'rowId', 
				title: '序号', 
				valign : 'left', 
				formatter: function (value, row, index) { 
					return index+1;}
				},{
				field : 'ACTUALID',
				title : 'ID',
				align : 'left',
				valign : 'middle'
				}, {
				field : 'CONNO',
				title : '合同号',
				align : 'left',
				valign : 'middle'
				},{
				field : 'REPAYDATE',
				title : '本月应还日期',
				align : 'left',
				valign : 'middle',
				formatter: function (value) {
					return dateFormatUtil(value);
				}
				}, {
				field : 'REPAYAMT',
				title : '本月应还金额',
				align : 'left',
				valign : 'middle'
				},{
				field : 'ACTUALREPAYDATE',
				title : '本月实还日期',
				align : 'left',
				valign : 'middle',
				formatter: function (value) {
					return dateFormatUtil(value);
				},
				editable : {
				type : 'date',
				mode : 'inline',
				clear: false,
				title : '本月实还日期',
				placement: 'left',
				datepicker: {
			        language: 'cn'
			    },
				validate: function (value) { 
						if (!$.trim(value)) {
								return '不能为空';
							}
						}
					}
				},{
				field : 'ACTUALAMT',
				title : '本月实还金额',
				align : 'left',
				valign : 'middle',
				formatter:function(value, row){
					return mathActualAmt(value, row);
				},
				},{
				field : 'ACTUALPRNPL',
				title : '本月实还本金',
				align : 'left',
				valign : 'middle',
				editable : {
					type : 'text',
					mode : 'inline',
					title : '本月实还本金',
					validate: function (value) { 
						if (!$.trim(value)) { 
							return '不能为空'; 
						}
						if (isNaN(value)){
							return '只允许输入数字';
						}else if(value<0){
							return '数值不能小于0';
						}
					}
				},
				formatter: function (value) {
					return !value || value==''?"":value;}
				},{
				field : 'ACTUALINTES',
				title : '本月实还利息',
				align : 'left',
				valign : 'middle',
				editable : {
				type : 'text',
				mode : 'inline',
				title : '本月实还利息',
				validate: function (value) { 
					if (!$.trim(value)) { 
						return '不能为空'; 
					}
					if (isNaN(value)){
						return '只允许输入数字';
					}else if(value<0){
						return '数值不能小于0';
					}
				  }
				},
				formatter: function (value) { 
					return !value || value==''?"":value;}
				},{
				field : 'INTESPNLTY',
				title : '本月罚息金额',
				align : 'left',
				valign : 'middle',
				editable : {
				type : 'text',
				mode : 'inline',
				title : '本月罚息金额',
				validate: function (value) { 
					if (!$.trim(value)) { 
						return '不能为空';
						}
					if (isNaN(value)){
						return '只允许输入数字';
					}else if(value<0){
						return '数值不能小于0';
					}
				  }
				},
				formatter: function (value) { 
				return !value || value==''?"":value;
				}}
			],
			onEditableSave: function (field, row, oldValue, $el) {
				  $table = $('#details-table').bootstrapTable({});
				  $table.bootstrapTable('updateRow', {index: row.rowId, row: row});
			}
		});
	}
	</script>
</body>
</html>