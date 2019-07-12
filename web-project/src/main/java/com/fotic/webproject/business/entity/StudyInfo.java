package com.fotic.webproject.business.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fotic.webproject.jpadata.domain.BaseEntity;
import com.fotic.webproject.jpadata.domain.supports.QueryField;
import com.fotic.webproject.jpadata.domain.supports.QueryType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "T_HR_STUDY_INFO")
//@Proxy(lazy=false)
public class StudyInfo extends BaseEntity{
	private static final long serialVersionUID = 7690708809600530166L;
	
	@NotNull(message = "外派人员id不能为空")
	@ApiModelProperty(value="申请选学人员登录名,可以多选，以逗号分隔", example="zhangsan,lisi")
	private String allUserCode;

	@NotNull(message = "外派人员名称不能为空")
	@ApiModelProperty(value="申请选学人员姓名,可以多选，以逗号分隔", example="张三,李四")
	private String allUserName;

	private Long deptId;

	@NotNull(message = "计划内培训不能为空")
	@ApiModelProperty(value="是否计划内选学, 0:否，1:是", required=true)
	private Integer isPlan;

	@NotNull(message = "培训编号不能为空")
	@ApiModelProperty(value="培训编号", example="10001")
	private String studyCode;

	@NotNull(message = "培训名称不能为空")
	@ApiModelProperty(value="培训名称", example="业务培训", required=true)
	private String studyName ;

	@NotNull(message = "培训机构不能为空")
	@ApiModelProperty(value="培训机构", example="信息技术部", required=true)
	private String studyOrg;

	//不参与实际计算，用double即可
	@NotNull(message = "人均培训费用不能为空")
	@ApiModelProperty(value="人均培训费用", example="300.00", required=true)
	private Double perStudentCost;
	
	//不参与实际计算，用double即可
	@NotNull(message = "培训费用总计不能为空")
	@ApiModelProperty(value="培训费用", example="150.00", required=true)
	private Double allStudentCost;

	@QueryField(type=QueryType.LEFT_LIKE)
	@NotNull(message = "培训地点不能为空")
	@ApiModelProperty(value="培训地址", example="凯晨世贸中心6层会议室", required=true)
	private String studyAddr ;

	@NotNull(message = "签订培训协议不能为空")
	@ApiModelProperty(value="是否签署协议, 0:否，1:是", required=true)
	private Integer isSignProtocol ;

	@NotNull(message = "选学原因不能为空")
	@ApiModelProperty(value="培训原因", example="描述培训原因", required=true)
	private String studyReason ;

	@ApiModelProperty(value="培训总天数", example="3")
	private String totalTimes;

	@ApiModelProperty(value="审批状态", hidden=true)
	private Integer bpsStatus ;

	@QueryField(type=QueryType.EQUAL)
	@ApiModelProperty(value="创建人", hidden=true)
	private String createUser ;
	
	@ApiModelProperty(value="培训人数", example="2")
	private Integer studentCount;
	
	@ApiModelProperty(value="培训时间描述", example="从2019年1月1日至2019年1月3日假期进行三天培训")
	private String studyDateDesc;

    @ApiModelProperty(value="申请选学人员和创建者登录名,以逗号分隔", example="zhangsan,lisi")
	private String studyAndCreateUserCode;
	
}
