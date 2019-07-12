package com.fotic.webproject.business.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.Transient;

import com.fotic.webproject.jpadata.domain.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账户实体
 * @author dgq
 *
 */
@Table(name="t_account")
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@NamedEntityGraph(name="Account", attributeNodes=@NamedAttributeNode("orders"))
public class Account extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="账户名称",example="dgqwer")
	private String accountName;
	
	@ApiModelProperty(value="账户余额", example= "3000")
	private Integer balance;
	
//	@Lazy(value=false)
//	@ForeignKey(name="none")
	
	@OneToMany(mappedBy="account", fetch=FetchType.EAGER)
	private List<Order> orders = new ArrayList<>();
}
