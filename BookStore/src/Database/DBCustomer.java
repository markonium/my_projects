package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import Back.*;


public class DBCustomer extends DBAbstract{
	Connection con;
	public DBCustomer(){
		con = getConnection();
	}
	/**
	 * This function checks user's credentials to sign in the user
	 * @param username The user's username
	 * @param password The user's password
	 * @return true if sign successful, false otherwise
	 */
	public User signIn(String username, String password) {
		Boolean check = checkUser(username);
		try {
			if(check) {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from user where username = '" + username + "'");
				rs.next();
				if(password.equals(rs.getString(5))) {
					User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), password, rs.getString(6), rs.getString(7), rs.getInt(8), new Cart(getCart(username)));
					return user;
				}
				return null;
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}
	/**
	 * This method create a new user account
	 * @param user The new account data
	 * @return true if account created successfully, false if user already exists
	 */
	public boolean signUp(String username, String fname, String lname, String email, String password, String phone, String address) {
		try {
			if(!checkUser(username) && !checkEmail(email)) {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("insert into user values('"+ username + "','" + fname + "','" + lname + "','" + email +  "','" + password +  "','" + phone +  "','" + address + "', 0)");
				Statement stmt2 = con.createStatement();
				stmt2.executeUpdate("insert into user_purchase values ('" + username + "'," + 0 + ")");
				return true;
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	/**
	 * Checks if a user exists
	 * @param username the username to check
	 * @return true if exists, false otherwise
	 */
	public boolean checkUser(String username) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select exists(select * from user where username = '" + username + "')");
			rs.next();
			String s = rs.getString(1);
			return  s.equals("1")? true: false;
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	/**
	 * Checks if a email already taken by another user
	 * @param email the email to check
	 * @return true if exists, false otherwise
	 */
	private boolean checkEmail(String email) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select exists(select * from user where email = '" + email + "')");
			rs.next();
			String s = rs.getString(1);
			return  s.equals("1")? true: false;
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	/**
	 * Add book to user's cart
	 * @param isbn The ISBN of book to be add
	 * @param username User's username 
	 */
	public void addToCart(int isbn, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("insert into cart values('"+ username + "'," + isbn + "," + 1 +")");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Increment the number of book in the user's cart
	 * @param isbn The ISBN of book to increment number for
	 * @param username User's username
	 */
	public void incCart(int isbn, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update cart set amount = amount + 1 where username = '" + username +"' and ISBN = " + isbn);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Decrement the number of book in the user's cart
	 * @param isbn The ISBN of book to decrement number for
	 * @param username User's username
	 */
	public void decCart(int isbn, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update cart set amount = amount - 1 where username = '" + username +"' and ISBN = " + isbn);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Remove a book from the cart
	 * @param isbn the ISBN of the book to be removed
	 * @param username The username of cart owner
	 */
	public void removeFromCart(int isbn, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("delete from cart where username = '" + username + "' and ISBN = " + isbn);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Returns user's cart
	 * @param username The user's username
	 * @return The map of the cart
	 */
	private HashMap<Integer, Integer> getCart(String username) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from cart where username = '" + username + "'");
			while(rs.next()) {
				map.put(rs.getInt(2), rs.getInt(3));
			}
			return map;
		}catch(Exception e) {
			System.out.println(e);
		}return null;
	}
	/**
	 * Clear a certain user's cart
	 * @param username The username of the user to empty the cart for
	 */
	public void emptyCart(String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("delete from cart where username = '" + username + "'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Change a certain user's phone 
	 * @param phone New phone number
	 * @param username The user's username
	 */
	public void updatePhone(String phone, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update user set phone = '" + phone + "' where username = '" + username +"'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Change address for a certain user
	 * @param address the new address
	 * @param username the user's username
	 */
	public void updateAddress(String address, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update user set address = '" + address + "' where username = '" + username +"'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Change user's email
	 * @param newEmail the new email
	 * @param username the user's username
	 * @return true if changed, false if email already taken
	 */
	public boolean updateEmail(String newEmail, String username) {
		try {
			if(!checkEmail(newEmail)) {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("update user set email = '" + newEmail + "' where username = '" + username + "'");
				return true;
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	/**
	 * Change user's password
	 * @param newPass new password
	 * @param username the user's username
	 */
	public void updatePassword(String newPass, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update user set password = '" + newPass + "' where username = '" + username + "'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Change user's username
	 * @param newUsername new username
	 * @param oldUsername old username
	 * @return true is username changed successfully, false if username already taken
	 */
	public boolean updateUsername(String newUsername, String oldUsername) {
		try {
			if(checkUser(newUsername)) {
				return false;
			}
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update user set username = '" + newUsername + "' where username = '" + oldUsername + "'");
			return true;
		}catch(Exception e) {
			System.out.println(e);
		}return false;
	}
	/**
	 * Change user's first name
	 * @param fname new first name
	 * @param username the user's username
	 */
	public void updateFname(String fname, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update user set fname = '" + fname + "' where username = '" + username + "'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Change user's last name
	 * @param fname new last name
	 * @param username the user's username
	 */
	public void updateLname(String lname, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update user set lname = '" + lname + "' where username = '" + username + "'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Return a book's price
	 * @param isbn ISBN of the book
	 * @return The book's price
	 */
	public double getBookPrice(int isbn) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select price from book where ISBN = " + isbn);
			rs.next();
			Double price = rs.getDouble(1);
			return price;
		}catch(Exception e) {
			System.out.println(e);
		}return 0d;
	}
	/**
	 * Decrease stock number of a book by a certain amount
	 * @param isbn Book's ISBN
	 * @param amount Amount to update book stock with
	 */
	public void decreaseStock(int isbn, int amount, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update book set stock = stock - " + amount + " where ISBN = " + isbn);
			incBookSold(isbn, amount);
			incUserPurchase(username, amount);
			recordOrderMonth(isbn, amount);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	private void incUserPurchase(String username, int amount) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update user_purchase set amount = amount + " + amount + " where username = '" + username + "'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	private void incBookSold(int isbn, int amount) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update book_sold set amount = amount + " + amount + " where ISBN = " + isbn);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	private void recordOrderMonth(int isbn, int amount) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("insert into order_month values ('0'," + isbn + "," + amount + "," +  Calendar.getInstance().get(Calendar.MONTH) + ")");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Search for a book given a search key, search attribute and category 
	 * @param key Search key
	 * @param att Search attribute
	 * @param cat Category 
	 * @return ArrayList containing each book's ISBN, title and publisher
	 */
	public ArrayList<String[]> searchBooks(String key, String att, String cat){
		ArrayList<String[]> books = new ArrayList<String[]>();
		try {
			Statement stmt = con.createStatement();;
			ResultSet rs;
			if(!att.equals("author")) {
				if(cat.equals("All")){
					rs = stmt.executeQuery("select * from book where " + att + " = '" + key + "'");
				}else{
					rs = stmt.executeQuery("select * from book where " + att + " = '" + key + "' and category = '" + cat + "'");
				}
			}else {
				if(cat.equals("All")){
					rs = stmt.executeQuery("select * from book natural join book_author where " + att + " = '" + key + "'");
				}else{
					rs = stmt.executeQuery("select * from book natural join book_author where " + att + " = '" + key + "' and category = '" + cat + "'");
				}
			}
			while(rs.next()) {
				String[] arr = new String[3];
				arr[0] = String.valueOf(rs.getInt(1));
				arr[1] = rs.getString(2);
				arr[2] = String.valueOf(rs.getInt(8));
				books.add(arr);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		if(books.size() == 0) {
			String[] arr = new String[1];
			arr[0] = "No matches found";
			books.add(arr);
		}
		return books;
	}
	public ArrayList<String[]> searchBooks2(String key, String att, String cat){
		ArrayList<String[]> books = new ArrayList<String[]>();
		try {
			Statement stmt = con.createStatement();;
			ResultSet rs;
			if(!att.equals("author")) {
				if(cat.equals("All")){
					rs = stmt.executeQuery("select * from book where " + att + " = '" + key + "'");
				}else{
					rs = stmt.executeQuery("select * from book where " + att + " = '" + key + "' and category = '" + cat + "'");
				}
			}else {
				if(cat.equals("All")){
					rs = stmt.executeQuery("select * from book natural join book_author where " + att + " = '" + key + "'");
				}else{
					rs = stmt.executeQuery("select * from book natural join book_author where " + att + " = '" + key + "' and category = '" + cat + "'");
				}
			}
			while(rs.next()) {
				String[] arr = new String[3];
				arr[0] = String.valueOf(rs.getInt(1));
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				books.add(arr);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		if(books.size() == 0) {
			String[] arr = new String[1];
			arr[0] = "No matches found";
			books.add(arr);
		}
		return books;
	}
	public ArrayList<String> getBookDetails(String isbn) {
		ArrayList<String> arr = new ArrayList<String>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from book where ISBN = " + isbn);
			rs.next();
			arr.add(rs.getString(2));
			arr.add(rs.getString(3));
			arr.add(rs.getString(4));
			arr.add(String.valueOf(rs.getDouble(5)));
			arr.add(rs.getString(6));
			arr.add(String.valueOf(rs.getInt(7)));
			arr.add(String.valueOf(rs.getInt(8)));
		}catch(Exception e) {
			System.out.println(e);
		}
		return arr;
	}
	public String getBookTitle(int isbn) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select title from book where ISBN = " + isbn);
			rs.next();
			String title = rs.getString(1);
			return title;
		}catch(Exception e) {
			System.out.println(e);
		}return "";
	}
}
