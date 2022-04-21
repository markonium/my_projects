package Back;

import java.util.ArrayList;

public class OrderTaker extends Account {
	ArrayList<Order> orders;

	public OrderTaker(String userName, String password, String email, int lvl) {
		super(userName, password, email, lvl);
	}

	public void addOrderToDB(Order order) {
		// add orders to the OrderTakerDB
	}

	/**
	 * sets the state of order in database to In progress
	 * @param order
	 */
	public void markInProgress(Order order) {
		order.setState("In progress");
		dbot.set_state_of_order(order.getId(), "In progress");
	}
	
	/**
	 * sets the state of order in database to Completed
	 * @param order
	 */
	public void markDone(Order order) {
		order.setState("Completed");
		dbot.set_state_of_order(order.getId(),"Completed");
		set_ordertaker_queue();
	}
	
	public ArrayList<Order> getQueue() {
		return orders;
	}
	
	public void set_ordertaker_queue() {
		this.orders = dbot.getqueue();
	}
	
	

}

