package com.fotic.webproject.business.entity;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fotic.webproject.jpadata.domain.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 订单实体
 * @author dgq
 *
 */
@Table(name="t_order")
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Order extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="订单编号", example="dd000001")
	private String orderNum;
	
	@ApiModelProperty(value="订单金额", example="30")
	private Integer orderAmount;
	
	/*@ApiModelProperty(value="账户ID", example="1001L")
	private Long accountId;*/

	@ManyToOne
	@JoinColumn(name="account_id", foreignKey=@ForeignKey(name="none", value=ConstraintMode.NO_CONSTRAINT))
	private Account account;
	
	public Order(Integer orderAmount, Account account) {
		super();
		this.orderAmount = orderAmount;
		this.account = account;
	}
	
	
}
