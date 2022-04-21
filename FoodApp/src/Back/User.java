package Back;

import java.util.*;
import java.util.Map.Entry;

import Database.DBMenuOrder;

public class User extends Account {
	private ArrayList<String> Addresses,creditCards;
	private ArrayList<Order>  orderHistory;
	private Cart cart;
	private String phone;
	
	public User(String userName, String password, String email, int lvl, String phone) {
		super(userName, password, email, lvl);
		this.phone = phone;
		cart = new Cart(userName);
	}
	/**
	 * Load user's cart
	 * @param cart
	 */
	public void loadCart(Cart cart) {
		this.cart = cart;
	}
	/**
	 * Getter for the user's cart
	 * @return User's cart
	 */
	public Cart getCart() {
		return this.cart;
	}
	/**
	 * Load user's addresses
	 * @param addresses
	 */
	public void loadAddresses(ArrayList<String> addresses) {
		Addresses = addresses;
	}
	/**
	 * Load user's orders history
	 * @param orders The orders in order history
	 */
	public void loadOrderHistory(ArrayList<Order> orders) {
		this.orderHistory = orders;
	}
	/**
	 * Add new address to current user
	 * @param address the new address
	 * @return true if added successfully, false if address already exists
	 */
	public boolean addAddress(String address) {
		if(Addresses.contains(address)) {
			return false;
		}
		db.addAddress(address, this.getUsername());
		Addresses.add(address);
		return true;
	}
	/**
	 * Getter for user's addresses
	 * @return user's addresses
	 */
	public ArrayList<String> getAddresses(){
		return Addresses;
	}
	/**
	 * Delete an address
	 * @param address the address to be deleted
	 * @return true if deleted, false if no such address
	 */
	public void deleteAddress(String address) {
		//Note that the address will be sent from the front-end so it is guaranteed to exist
		db.deleteAddress(address, this.getUsername());
		Addresses.remove(address);
	}
	/**
	 * Load users credit cards
	 * @param creditcards
	 */
	public void loadCreditCards(ArrayList<String> creditcards) {
		creditCards = creditcards;
	}
	/**
	 * Add a card for the user
	 * @param card the card to be added
	 * @return true if added, false if already exists
	 */
	public boolean addCreditCard(String card) {
		if(creditCards.contains(card)) {
			return false;
		}		
		db.addCard(card, this.getUsername());
		creditCards.add(card);
		return true;
	}
	/**
	 * Getter for credit cards
	 * @return the credit cards list
	 */
	public ArrayList<String> getCreditCards(){
		return creditCards;
	}
	/**
	 * Delete a card for user
	 * @param card the card to be deleted
	 */
	public void deleteCreditCard(String card) {
		//Same as delete address, guaranteed to exist
		db.deleteCard(card, this.getUsername());
		creditCards.remove(card);
	}
	/**
	 * Remove an item from cart 
	 * @param item_id The id of item to be removed
	 */
	public void removeFromCart(Integer item_id) {
		cart.removeFromCart(item_id);
	}
	/**
	 * Add an item to the cart
	 * @param item_id Id of item to be added to the cart
	 */
	public void addToCart(Integer item_id) {
		cart.addItem(item_id);
	}
	/**
	 * Clear the user's cart
	 */
	public void emptyCart() {
		cart.emptyCart();
	}
	/**
	 * Place a user order, order consists of items in the cart
	 * @param address The address to deliver the order to
	 * @return True if placed successfully, false if any error encountered
	 */
	public boolean placeOrder(String address) {
		db.updateCart(this);
		HashMap<Integer, Integer> map = cart.getMap();
		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<Integer> items_no = new ArrayList<Integer>();
		double cost = 0.0;
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			int id = entry.getKey();
			items_no.add(entry.getValue());
			double price;
			String name, type, description;
			ArrayList<String> details = db.getItemDetails(entry.getKey());
			name = details.get(0);
			type = details.get(1);
			price = Double.parseDouble(details.get(2));
			
			cost += price * (double)entry.getValue();
			
			description = details.get(3);
			items.add(new Item(id, name, type, description, price));
		}String time = new Date().toString();
		Order order = new Order(0, this.getUsername(), address, items, items_no, time, cost);
		
		this.emptyCart();
			
		int order_id = dbmo.addOrder(order);
		order.setId(order_id);
		
		this.orderHistory.add(order);
		if(order_id == 0) {
			return false;
		}return true;
	}
	/**
	 * Returns the order history of the current user
	 * @return Order history of the current user
	 */
	public ArrayList<Order> getOrderHistory(){
		DBMenuOrder dbmo = new DBMenuOrder();
		loadOrderHistory(dbmo.getOrder(this.getUsername()));
		return this.orderHistory;
	}
	/**
	 * Update user's phone
	 * @param phone New phone number
	 */
	public void updatePhone(String phone) {
		this.phone = phone;
		db.updatePhone(phone, this.getUsername());
	}
	/**
	 * Getter for user's phone number
	 * @return User's phone number
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * gets the flag form database to know whether the menu is being modefied or not
	 * @return
	 */
	public int get_flag_from_database() {
		return dbmo.get_menu_flag();
	}
	public double getCartPrice() {
		return cart.getCartPrice();
	}
}

