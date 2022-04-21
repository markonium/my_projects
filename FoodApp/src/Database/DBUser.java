package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import Back.*;


public class DBUser extends DBManager{
	Connection con;
	public DBUser(){
		con = getConnection();
	}
	/**
	 * This method create a new user account
	 * @param user The new account data
	 * @return true if account created successfully, false if user already exists
	 */
	public boolean addUser(User user) {
		try {
			if(!checkUser(user.getUsername()) && !checkEmail(user.getEmail())) {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("insert into users values('"+ user.getUsername() + "','" + user.getEmail() + "','" + user.getPassword() + "'," + user.getLevel() + ",0,'" + user.getPhone() + "')");
				return true;
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	/**
	 * Perform mandatory checks for signup
	 * @param username User's username
	 * @param email User's e-mail
	 * @return 0 if username and email already available, 1 if username already taken, 2 if e-mail already taken
	 */
	public int checkSignup(String email, String username) {
		if(checkUser(username)) {
			return 1;
		}if(checkEmail(email)) {
			return 2;
		}else {
			return 0;
		}
	}
	/**
	 * Delete user with the username given
	 * @param username The username to be deleted
	 * @return true if success, false if no such user
	 */
	public boolean deleteUser(String username) {
		try {
			boolean check = checkUser(username);	
			if(check) {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("delete from users where username = '" + username + "'");
				return true;
			}
			return false;
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
	/**
	 * Checks if a user exists
	 * @param username the username to check
	 * @return true if exists, false otherwise
	 */
	public boolean checkUser(String username) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select exists(select * from users where username = '" + username + "')");
			rs.next();
			String s = rs.getString(1);
			return  s.equals("1")? true: false;
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	/**
	 * Login the user with username, password specified
	 * @param username the user's username
	 * @param password the user's password
	 * @return The user data if success, null if password or username is wrong or if the user is already logged in 
	 */
	public Account login(String username, String password) {
		try {
			Boolean check = checkUser(username);
			if(check) {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from users where username = '" + username + "'");
				rs.next();
				int logged = rs.getInt(5);
				if(logged == 1) {
					//already logged in
					return null;
				}else {
					String pass = rs.getString(3);
					if(pass.equals(password)) {
						User user = new User(rs.getString(1), pass, rs.getString(2), rs.getInt(4), rs.getString(6));
						stmt.executeUpdate("update users set logged_in = 1 where username = '" + username + "'");
						//Loading addresses, cart and cards----------------
						ResultSet rs2 = stmt.executeQuery("select address from addresses where username = '" + username +"'");
						ArrayList<String> addresses = new ArrayList<String>();
						while(rs2.next()) {
							addresses.add(rs2.getString(1));
						}
						ResultSet rs3 = stmt.executeQuery("select card from cards where username = '" + username +"'");
						ArrayList<String> cards = new ArrayList<String>();
						while(rs3.next()) {
							cards.add(rs3.getString(1));
						}
						ResultSet rs4 = stmt.executeQuery("select item_id, number from cart where username = '" + username + "'");
						ArrayList<Integer> item_id = new ArrayList<Integer>();
						HashMap<Integer,Integer> no_of_items = new HashMap<Integer,Integer>();
						while(rs4.next()) {
							int id = rs4.getInt(1);
							item_id.add(id);
							no_of_items.put(id, rs4.getInt(2));
						}
						user.loadAddresses(addresses);
						user.loadCreditCards(cards);
						user.loadCart(new Cart(username, item_id, no_of_items));
						DBMenuOrder temp = new DBMenuOrder();
						user.loadOrderHistory(temp.getOrder(username));
						//----------------
						if(user.getLevel() == 0) {
							return user;
						}else if(user.getLevel() == 2) {
							Admin admin = Admin.getInstance(user.getUsername(), user.getPassword(), user.getEmail(), 2);
							return admin;
						}else {
							OrderTaker ot = new OrderTaker(user.getUsername(), user.getPassword(), user.getEmail(), 1);
							return ot;
						}
					}return null;
				}	
			}return null;
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}
	/**
	 * Sign out the current user
	 * @param username username of current user
	 */
	public void signout(String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update users set logged_in = 0 where username = '" + username + "'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Change user's username
	 * @param newUsername new username
	 * @param oldUsername old username
	 */
	public void setUsername(String newUsername, String oldUsername) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update users set username = '" + newUsername + "' where username = '" + oldUsername + "'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Change user's password
	 * @param newPass new password
	 * @param username the user's username
	 * @return true if password changed successfully, false if old password is not correct 
	 */
	public boolean setPassword(String newPass, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update users set password = '" + newPass + "' where username = '" + username + "'");
			return true;
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
			ResultSet rs = stmt.executeQuery("select exists(select * from users where email = '" + email + "')");
			rs.next();
			String s = rs.getString(1);
			return  s.equals("1")? true: false;
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	/**
	 * Change user's email
	 * @param newEmail the new email
	 * @param username the user's username
	 * @return true if changed, false if email already taken
	 */
	public boolean setEmail(String newEmail, String username) {
		try {
			if(!checkEmail(newEmail)) {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("update users set email = '" + newEmail + "' where username = '" + username + "'");
				return true;
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	/**
	 * Add new address for a certain user
	 * @param address the new address
	 * @param username the user's username
	 */
	public void addAddress(String address, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("insert into addresses values('" + username + "','" + address +"')");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Delete a user address
	 * @param address the address to be deleted
	 * @param username the user's username
	 */
	public void deleteAddress(String address, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("delete from addresses where address = '" + address + "' And username = '" + username + "'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	public String getEmail(String username) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select email from users where username = '" + username + "'");
			rs.next();
			String email = rs.getString(1);
			return email;
		}catch(Exception e) {
			System.out.println(e);
		}return "";
	}
	public String getPhone(String username) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select phone from users where username = '" + username + "'");
			rs.next();
			String phone = rs.getString(1);
			return phone;
		}catch(Exception e) {
			System.out.println(e);
		}return "";
	}
	/**
	 * Add new card for a certain user
	 * @param card the new card
	 * @param username the user's username
	 */
	public void addCard(String card, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("insert into cards values('" + username + "','" + card +"')");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Delete a user card
	 * @param card the card to be deleted
	 * @param username the user's username
	 */
	public void deleteCard(String card, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("delete from cards where card = '" + card + "' And username = '" + username + "'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Increment the number of item in the cart
	 * @param item_id The id of item 
	 * @param username The username of cart owner
	 */
	public void incItemCart(int item_id, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update cart set number = number + 1 where username = '" + username +"' and item_id = " + item_id);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Add an item in the cart
	 * @param item_id The id of item 
	 * @param username The username of cart owner
	 */
	public void addItemCart(int item_id, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("insert into cart values('"+ username + "'," + item_id + "," + 1 +")");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Decrement the number of item in the cart
	 * @param item_id The id of item 
	 * @param username The username of cart owner
	 */
	public void decItemCart(int item_id, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update cart set number = number - 1 where username = '" + username +"' and item_id = " + item_id);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Remove an item in the cart
	 * @param item_id The id of item 
	 * @param username The username of cart owner
	 */
	public void removeItemCart(int item_id, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("delete from cart where username = '" + username + "' and item_id = " + item_id);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Clear a certain user's cart
	 * @param username The username of that user
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
	 * Fetch details of an item
	 * @param item_id Id of the item
	 * @return The item's details
	 */
	public ArrayList<String> getItemDetails(int item_id){
		try {
			ArrayList<String> arr = new ArrayList<String>();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from items where item_id = " + item_id);
			rs.next();
			arr.add(rs.getString(2));
			arr.add(rs.getString(3));
			arr.add(rs.getString(4));
			arr.add(rs.getString(5));
			return arr;
		}catch(Exception e) {
			System.out.println(e);
		}return null;
	}
	/**
	 * Update a certain user's phone 
	 * @param phone New phone number
	 * @param username The user's username
	 */
	public void updatePhone(String phone, String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update users set phone = '" + phone + "' where username = '" + username +"'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Delete a certain item from all carts
	 * @param id Item's id
	 */
	public void deleteFromAllCarts(int id) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("delete from cart where item_id = " + id);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Update user's cart in case of menu modification
	 * @param user The user to update cart to
	 */
	public void updateCart(User user) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs4 = stmt.executeQuery("select item_id, number from cart where username = '" + user.getUsername() + "'");
			ArrayList<Integer> item_id = new ArrayList<Integer>();
			HashMap<Integer,Integer> no_of_items = new HashMap<Integer,Integer>();
			while(rs4.next()) {
				int id = rs4.getInt(1);
				item_id.add(id);
				no_of_items.put(id, rs4.getInt(2));
			}
			user.loadCart(new Cart(user.getUsername(), item_id, no_of_items));
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}

