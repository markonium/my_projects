package Back;

import java.util.ArrayList;
import java.util.HashMap;

import Database.*;

public class User {
	String username;
	String fname;
	String lname;
	String email;
	String password;
	String phone;
	String address;
	int level;
	Cart cart;
	DBCustomer db = new DBCustomer();
	DBManager dbm = new DBManager();
	public User(String username, String fname, String lname, String email, String password, String phone, String address, int level, Cart cart) {
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.level = level;
		this.cart = cart;
	}
	//Getters and setters for all fields
	public String getUsername() {
		return username;
	}
	public boolean setUsername(String username) {		
		if(db.updateUsername(username, this.username)) {
			this.username = username;
			return true;
		}return false;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		db.updateFname(fname, this.username);
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		db.updateLname(lname, this.username);
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public boolean setEmail(String email) {
		if(db.updateEmail(email, this.username)) {
			this.email = email;
			return true;
		}return false;
	}
	public String getPassword() {
		return password;
	}
	public boolean setPassword(String password) {
		if(password.equals(this.password)) {
			return false;
		}
		db.updatePassword(password, this.username);
		this.password = password;
		return true;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		db.updatePhone(phone, this.username);
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		db.updateAddress(address, this.username);
		this.address = address;
	}
	public int getLevel() {
		return level;
	}
	public boolean upgradeLevel(String user) {
		if(this.level == 1) {
			dbm.upgradeLevel(user);
			return true;
		}return false;
	}
	public boolean downgradeLevel(String user) {
		if(this.level == 1) {
			dbm.downgradeLevel(user);
			return true;
		}return false;
	}
	public HashMap<Integer, Integer> getCart(){
		return cart.getMap();
	}
	public int addBook(int isbn, String title, String publisher, String pub_year, Double price, String category, int threshold, ArrayList<String> authors) {
		if(this.level == 1) {
			return dbm.addBook(isbn, title, publisher, pub_year, price, category, threshold, authors);
		}else {
			return 3;
		}
	}
	public boolean addPublisher(String name, String address, String phone) {
		if(this.level == 1) {
			return dbm.addPublisher(name, address, phone);
		}return false;
	}
	public boolean placeOrderManager(int isbn, int amount, String publisher) {
		if(this.level == 1) {
			return dbm.placeOrderManager(isbn, amount, publisher);
		}return false;
	}
	public boolean confirmOrder(int id) {
		if(this.level == 1) {
			dbm.confirmOrder(id);
			return true;
		}else {
			return false;
		}
	}
	public boolean updateBook(String isbn, String title, String publisher, String pub_year, String price, String category, String threshold, String stock) {
		if(this.level == 1) {
			return dbm.updateBook(isbn, title, publisher, pub_year, price, category, threshold, stock);
		}
		return false;
	}
	public void addToCart(int isbn) {
		cart.addToCart(isbn, username);
	}
	public void removeFromCart(int isbn) {
		cart.removeFromCart(isbn, username);
	}
	public void emptyCart() {
		db.emptyCart(this.username);
		this.cart = new Cart(new HashMap<Integer, Integer>());
	}
}
