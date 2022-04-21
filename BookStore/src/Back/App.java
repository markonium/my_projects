package Back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Database.*;

public class App {
	DBCustomer db = new DBCustomer();
	DBManager dbm = new DBManager();
	User user = null;
	/**
	 * Sign in a user
	 * @param username User's username
	 * @param password User's password
	 * @return true is sign in successful, false otherwise
	 */
	public boolean signIn(String username, String password) {
		Cryptography c = new Cryptography();
		user = db.signIn(username, c.hashPassword(password, username));
		if(user == null) {
			return false;
		}
		user.emptyCart();
		return true;
	}
	/**
	 * Sign up a user
	 * @param username User's username
	 * @param fname	User's first name
	 * @param lname User's last name
	 * @param email User's email
	 * @param password User's password
	 * @param phone User's phone
	 * @param address User's shipping address
	 * @return true is sign up successful, false otherwise
	 */
	public boolean signUp(String username, String fname, String lname, String email, String password, String phone, String address) {
		Cryptography c = new Cryptography();
		return db.signUp(username, fname, lname, email, c.hashPassword(password, username), phone, address);
	}
	/**
	 * Sign out the current user
	 */
	public void signOut() {
		db.emptyCart(user.getUsername());
		this.user = null;
	}
	/**
	 * Change user's username 
	 * @param username New username
	 * @return true if successful, false is username already taken
	 */
	public boolean updateUsername(String username) {
		return user.setUsername(username);
	}
	/**
	 * Change user's email 
	 * @param email New email
	 * @return true if successful, false is email already taken
	 */
	public boolean updateEmail(String email) {
		return user.setEmail(email);
	}
	/**
	 * Change user's password
	 * @param password New password
	 * @return true is password changed successfully, false if new password is the same as  old password
	 */
	public boolean updatePassword(String password) {
		Cryptography c = new Cryptography();
		return user.setPassword(c.hashPassword(password, user.getUsername()));
	}
	/**
	 * Change user's shipping address
	 * @param address New address
	 */
	public void updateAddress(String address) {
		user.setAddress(address);
	}
	/**
	 * Change user's phone
	 * @param phone New phone
	 */
	public void updatePhone(String phone) {
		user.setPhone(phone);
	}
	/**
	 * Change user's first name
	 * @param fname New first name
	 */
	public void updateFname(String fname) {
		user.setFname(fname);
	}
	/**
	 * Change user's last name
	 * @param fname New last name
	 */
	public void updateLname(String lname) {
		user.setLname(lname);
	}
	/**
	 * Add a book to cart
	 * @param isbn Book's ISBN
	 */
	public void addToCart(int isbn) {
		user.addToCart(isbn);
	}
	/**
	 * Remove a book from cart
	 * @param isbn Book's ISBN
	 */
	public void removeFromCart(int isbn) {
		user.removeFromCart(isbn);
	}
	/**
	 * Get user's cart
	 * @return user's cart
	 */
	public ArrayList<String[]> getCart(){
		ArrayList<String[]> books = new ArrayList<String[]>();
		HashMap<Integer, Integer> map = user.getCart();
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			String[] arr = new String[4];
			arr[0] = db.getBookTitle(entry.getKey());
			arr[1] = String.valueOf(db.getBookPrice(entry.getKey()));
			arr[2] = String.valueOf(entry.getValue());
			arr[3] = String.valueOf(entry.getKey());
			books.add(arr);
		}
		return books;
	}
	/**
	 * Upgrade a certain user to manager
	 * @param username User's username
	 * @return true is current user has privileges to do so, false otherwise
	 */
	public boolean upgradeUser(String username) {
		return db.checkUser(username) && user.upgradeLevel(username);
	}
	/**
	 * Downgrade a certain user to customer
	 * @param username User's username
	 * @return true is current user has privileges to do so, false otherwise
	 */
	public boolean downgradeUser(String username) {
		return db.checkUser(username) && user.downgradeLevel(username);
	}
	/**
	 * Place an order with user's cart content
	 */
	public void placeOrderUser() {
		for (Entry<Integer, Integer> entry : user.getCart().entrySet()) {
			db.decreaseStock(entry.getKey(), entry.getValue(), user.getUsername());
		}
		user.emptyCart();
	}
	/**
	 * Add a new book
	 * @param isbn
	 * @param title
	 * @param publisher
	 * @param pub_year
	 * @param price
	 * @param category
	 * @param threshold
	 * @return 0 if book added successfully, 1 if ISBN already exists or category is invalid, 2 if publisher does not exist, 3 if user is not a manager
	 */
	public int addBook(int isbn, String title, String publisher, String pub_year, Double price, String category, int threshold, ArrayList<String> authors) {
		return user.addBook(isbn, title, publisher, pub_year, price, category, threshold, authors);
	}
	/**
	 * Add a new publisher 
	 * @param name Publisher's name
	 * @param address Publisher's address
	 * @param phone Publisher's phone
	 * @return true if added successfully, false otherwise
	 */
	public boolean addPublisher(String name, String address, String phone) {
		return user.addPublisher(name, address, phone);
	}
	/**
	 * Return a book's price
	 * @param isbn ISBN of the book
	 * @return The book's price
	 */
	public double getBookPrice(int isbn) {
		return db.getBookPrice(isbn);
	}
	/**
	 * Place order for books
	 * @param isbn
	 * @param amount
	 * @param publisher
	 * @return true if user is a manager and order placed, false otherwise
	 */
	public boolean placeOrderManager(int isbn, int amount, String publisher) {
		return user.placeOrderManager(isbn, amount, publisher);
	}
	/**
	 * Confirm a book order
	 * @param id Order's id
	 * @return true if user is a manager, false otherwise
	 */
	public boolean confirmOrder(int id, String isbn, String stock) {
		dbm.incStock(isbn, stock);
		return user.confirmOrder(id);
	}
	/**
	 * Update a certain book
	 * @param isbn Book's ISBN
	 * @param title
	 * @param publisher
	 * @param pub_year
	 * @param price
	 * @param category
	 * @param threshold
	 * @return true is updated successful, false otherwise
	 */
	public boolean updateBook(String isbn, String title, String publisher, String pub_year, String price, String category, String threshold, String stock) {
		return user.updateBook(isbn, title, publisher, pub_year, price, category, threshold, stock);
	}
	/**
	 * Get level of current user
	 * @return Level of the current user
	 */
	public int getLevel() {
		return user.getLevel();
	}
	/**
	 * Search for a book given a search key, search attribute and category 
	 * @param key Search key
	 * @param att Search attribute
	 * @param cat Category 
	 * @return ArrayList containing each book's ISBN, title and publisher
	 */
	public ArrayList<String[]> searchBooks(String key, String att, String cat){
		return db.searchBooks(key, att, cat);
	}
	/**
	 * Fetch details of a certain book
	 * @param isbn Book's ISBN
	 * @return Book's details
	 */
	public ArrayList<String> getBookDetails(String isbn){
		return db.getBookDetails(isbn);
	}
	/**
	 * Return manager' orders with the given status
	 * @param status
	 * @return
	 */
	public ArrayList<String []> getOrders(String status){
		return dbm.getOrders(status);
	}
	public boolean deleteOrder(String id) {
		return dbm.deleteOrder(id);
	}
	public ArrayList<String[]> searchBooks2(String key, String att, String cat){
		return db.searchBooks2(key, att, cat);
	}
}
