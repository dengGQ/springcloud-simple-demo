<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/jsp/mytag.jsp"%>
<%@include file="/common/jsp/bootstrap.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>客户信息管理</title>
<style type="text/css">
.form-group label{font-size:12px;font-weight:700;margin-left:0;white-space:nowrap;}
.form-group input.form-control{background-color:#fff;margin:0 10px 5px;}
</style>
</head>
<body>

<script>
		$(function() {
			//证件类型转换
			var credType = getCredType($('#credType').val());
			$('#credType').val(credType);
			//客户类型转换
			var custType = getCustType($('#custType').val());
			$('#custType').val(custType);
			//配偶证件类型
			var mateCredType = getCredType($('#mateCredType').val());
			$('#mateCredType').val(mateCredType);
			//性别
			var sexs = getSex($('#sex').val());
			$('#sex').val(sexs);
			//婚姻状况
			var marryStatus = getMarriage($('#marryStatus').val());
			$('#marryStatus').val(marryStatus);
			//学历
			var hiestEdu = getHiestEdu($('#hiestEdu').val());		
			$('#hiestEdu').val(hiestEdu);
			//学位
			var highestDegre = getHighestDegre($('#highestDegre').val());
			$('#highestDegre').val(highestDegre);
			//居住状况
			var livStatus = getLivStatus($('#livStatus').val());
			$('#livStatus').val(livStatus);
			//职业
			var profes = getProfes($('#profes').val());
			$('#profes').val(profes);
			//职务
			var duty = getDuty($('#duty').val());
			$('#duty').val(duty);
			//职称
			var title = getTitle($('title').val());
			$('#title').val(title);
			//数据来源
			var dataSrc = getDataSrc($('#dataSrc').val());
			$('#dataSrc').val(dataSrc);
			//单位所属行业
			var workUnitIndOwned = getWorkUnitIndOwned($('#workUnitIndOwned').val());
			$('#workUnitIndOwned').val(workUnitIndOwned);
		})
</script>

	<div class="container-fluid">
		<div class="row base-margin" id="query">
			<form class="form-inline"> 
				<c:forEach var="custom" items= "${Customer}" > 
					<div class="form-group">
						<table> 
							<tr> 
								<td class="align-R"><label>客户姓名:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.custName}" /></td>   
								<td class="align-R"><label>证件号码:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.credCode}" /></td>   
								<td class="align-R"><label>证件类型:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="credType" value="${custom.credType}" /></td>   
								<td class="align-R"><label>客户类型:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="custType" value="${custom.custType}" /></td>   
							</tr> 
							
							<tr> 
								<td class="align-R"><label>性别:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="sex" value="${custom.sex}" /></td>   
								<td class="align-R"><label>联系方式:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.tel}" /></td>   
								<td class="align-R"><label>出生日期:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="<fmt:formatDate value='${custom.birthday}' pattern='yyyy-MM-dd'/>" /></td>   
								<td class="align-R"><label>婚姻状况:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="marryStatus" value="${custom.marryStatus}" /></td>   
							</tr> 
							
							<tr> 
								<td class="align-R"><label>最高学历:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="hiestEdu" value="${custom.hiestEdu}" /></td>   
								<td class="align-R"><label>最高学位:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="highestDegre" value="${custom.highestDegre}" /></td>   
								<td class="align-R"><label>手机号码:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.moble}" /></td>   
								<td class="align-R"><label>email:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.email}" /></td>   
							</tr>
							
							<tr> 
								<td class="align-R"><label>住宅电话:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.hiestEdu}" /></td>   
								<td class="align-R"><label>通讯邮编:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.postCode}" /></td>   
								<td class="align-R"><label>通讯地址:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.postAddr}" /></td>   
								<td class="align-R"><label>户籍归属:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.houseRegst}" /></td>   
							</tr>
							
							<tr> 
								<td class="align-R"><label>住宅邮编:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.housePostCode}" /></td>   
								<td class="align-R"><label>住宅地址:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.houseAdd}" /></td>   
								<td class="align-R"><label>居住状况:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="livStatus" value="${custom.livStatus}" /></td>   
								<td class="align-R"><label>月收入（元）:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.monthIncom}" /></td>   
							</tr>
							
							<tr> 
								<td class="align-R"><label>配偶名称:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.mateName}" /></td>   
								<td class="align-R"><label>配偶证件类型:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="mateCredType" value="${custom.mateCredType}" /></td>   
								<td class="align-R"><label>配偶证件号码:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.mateCredNo}" /></td>   
								<td class="align-R"><label>配偶工作单位:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.mateWorkUnit}" /></td>   
							</tr>
							
							<tr> 
								<td class="align-R"><label>配偶联系电话:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.mateTel}" /></td>   
								<td class="align-R"><label>职业:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="profes" value="${custom.profes}" /></td>   
								<td class="align-R"><label>工作单位名称:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.workUnitName}" /></td>   
								<td class="align-R"><label>工作单位所属行业:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="workUnitIndOwned" value="${custom.workUnitIndOwned}" /></td>   
							</tr>
							
							<tr> 
								<td class="align-R"><label>工作单位邮编:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.workUnitPostCode}" /></td>   
								<td class="align-R"><label>工作单位地址:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.workUnitAdd}" /></td>   
								<td class="align-R"><label>职务:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="duty" value="${custom.duty}" /></td>   
								<td class="align-R"><label>职称:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="title" value="${custom.title}" /></td>   
							</tr>
							
							<tr> 
								<td class="align-R"><label>工作起始年份:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.startWorkYear}" /></td>   
								<td class="align-R"><label>工作状态:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" value="${custom.workState}" /></td>   
								<td class="align-R"><label>数据来源:</label> </td>
								<td><input type="text" class="form-control"  readonly="readonly" id="dataSrc" value="${custom.dataSrc}" /></td>   
							</tr>
						</table>
					</div>
				</c:forEach>  
			</form>
		</div>
		
	</div>
</body>
</html>