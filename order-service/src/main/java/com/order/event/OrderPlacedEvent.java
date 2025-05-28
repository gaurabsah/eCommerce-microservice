package com.order.event;

public class OrderPlacedEvent {

	private Long orderId;

	public OrderPlacedEvent() {

	}

	public OrderPlacedEvent(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderPlacedEvent [orderId=" + orderId + "]";
	}

}
