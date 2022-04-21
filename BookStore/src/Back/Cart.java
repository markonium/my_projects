package Back;

import java.util.HashMap;

import Database.DBCustomer;

public class Cart {
	HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	DBCustomer db = new DBCustomer();
	public Cart(HashMap<Integer, Integer> map) {
		this.map = map;
	}
	/**
	 * Add a book to cart
	 * @param isbn
	 * @param username
	 */
	public void addToCart(Integer isbn, String username) {
		if(map.containsKey(isbn)) {
			map.put(isbn, map.get(isbn) + 1);
			db.incCart(isbn, username);
		}else {
			map.put(isbn, 1);
			db.addToCart(isbn, username);
		}
	}
	/**
	 * Remove a book from cart
	 * @param isbn
	 * @param username
	 */
	public void removeFromCart(Integer isbn, String username) {
		map.put(isbn, map.get(isbn) - 1);
		if(map.get(isbn) == 0) {
			map.remove(isbn);
			db.removeFromCart(isbn, username);
		}else {
			db.decCart(isbn, username);
		}
	}
	/**
	 * Return cart map
	 * @return cart map
	 */
	public HashMap<Integer, Integer> getMap(){
		return this.map;
	}
}
