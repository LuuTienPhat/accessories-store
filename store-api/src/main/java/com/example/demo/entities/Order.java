package com.example.demo.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Base{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	@Column(name = "customer_id")
//	private String customerId;

//	@Column(name = "order_total")
//	private float orderTotal;

	@Column(name = "ship_date")
	private LocalDateTime shipDate;

	@Column(name = "ship_name")
	private String shipName;

	@Column(name = "ship_phone")
	private String shipPhone;

	@Column(name = "ship_address")
	private String shipAddress;

	@Column(name = "ship_note")
	private String shipNote;

	@Column(name = "state")
	private Integer state;

//	@JsonManagedReference(value = "order_order_detail")
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@JsonGetter
	private Long getUserId() {
		return this.user != null ? user.getId() : null;
	}

	/*
	 * public float getOrderTotal() { return orderTotal; }
	 * 
	 * public void setOrderTotal(float orderTotal) { this.orderTotal = orderTotal; }
	 */

	// GET TOTAL PRICE OF ORDER
	public static float getTotalPrice(Order order) {
		float totalPrice = 0;
		for (OrderDetail orderDetail : order.getOrderDetails()) {
			totalPrice += (orderDetail.getProduct().getPrice() * orderDetail.getQuantity());
		}
		return totalPrice;
	}

	// GET TOTAL QUANTITY OF ORDER
//	public int getTotalQuantity() {
//		int totalQuantity = 0;
//		for (OrderDetail orderDetail : orderDetails) {
//			totalQuantity += orderDetail.getQuantity();
//		}
//		return totalQuantity;
//	}

	// GET QUANTITY OF CHOSEN PRODUCT IN ORDER
//	public int getQuantity(String productId) {
//		for (OrderDetail orderDetail : orderDetails) {
//			if (orderDetail.getProduct().getId().equals(productId))
//				return orderDetail.getQuantity();
//		}
//		return 0;
//	}

}
