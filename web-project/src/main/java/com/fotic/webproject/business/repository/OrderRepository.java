package com.fotic.webproject.business.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.fotic.webproject.business.entity.Order;
import com.fotic.webproject.business.vo.OrderVo;
import com.fotic.webproject.jpadata.repository.BaseRepository;

/**
 * 订单repository
 * @author dgq
 *
 */
public interface OrderRepository extends BaseRepository<Order, Long>{
	
	/**
	 * 根据Id查询订单，关联查询账户信息, 直接返回实体
	 * @param id
	 * @return  vo对象
	 */
	@Query("select new com.fotic.webproject.business.vo.OrderVo(o.id, o.orderNum, o.orderAmount, a.accountName, a.balance) from Order o left join Account a on o.account.id = a.id where o.id =?1")
	OrderVo queryOrderById(Long id);

	/**
	 * 根据订单编号分页查询订单，关联查询账户信息, 直接返回实体
	 * @param id
	 * @return  vo对象
	 */
	@Query(value="select new com.fotic.webproject.business.vo.OrderVo(o.id, o.orderNum, o.orderAmount, a.accountName, a.balance) from Order o left join Account a on o.account.id = a.id where o.orderNum =?1",
			countQuery="select count(*) from Order o left join Account a on o.account.id = a.id where o.orderNum =?1")
	Page<OrderVo> queryOrderByOrderNum (String orderNum, Pageable pageable);
	
	@Query(value="select new com.fotic.webproject.business.vo.OrderVo(o.id, o.orderNum, o.orderAmount, a.accountName, a.balance) from Order o left join Account a on o.account.id = a.id where o.orderNum =?1 and a.accountName = ?2",
			countQuery="select count(*) from Order o left join Account a on o.account.id = a.id where o.orderNum =?1 and a.accountName = ?2")
	Page<OrderVo> queryOrderByOrderNumAndAcountName(String orderNum, String accountName, Pageable pageable);
}
