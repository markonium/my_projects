package Back;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;

import javax.mail.*;
import javax.mail.internet.*;

import Database.*;

public class App {
public Account currUser;
DBUser userSession = new DBUser();
DBMenuOrder dbmo = new DBMenuOrder();
/**
 * Login the user with username, password specified
 * @param username the user's username
 * @param password the user's password
 * @return true if login was successful, false otherwise 
 */
public boolean login(String username, String password) {
	if(username==null||password==null||username.trim().isEmpty()||password.trim().isEmpty()) {
		return false;
	}
	Cryptography c = new Cryptography();
	// if login failed the it may be wrong username, password, both or user already logged in
	if((currUser = userSession.login(username.trim(), c.hashPassword(password.trim(), username.trim()))) != null) 
		return true;
	// if no such user
	return false;
}
/**
 * Sign out the current user
 */
public void signout() {
	userSession.signout(currUser.getUsername());
	currUser = null;
}
/**
 * Perform mandatory checks for signup
 * @param username User's username
 * @param email User's e-mail
 * @return 0 if username and email already available, 1 if username already taken, 2 if e-mail already taken
 */
public String signupCheck(String email, String username) {
	int ecode = userSession.checkSignup(email, username);
	if(ecode == 0) {
		return sendEmail(email, username);
	}
	return String.valueOf(ecode);
}
/**
 * This method sends confirmation code to user's email
 * @param rEmail User's email
 * @param username User's username
 * @return
 */
private static String sendEmail(String rEmail, String username) {
	String sEmail = "foodcirclesverifsupp@outlook.com";
	String sPass = "kk262000";
	String subject = "E-mail Verification";
	
	Random rnd = new Random();
    int number = rnd.nextInt(999999);
    String code = String.format("%06d", number);
	
	String msg = "Hello " + username + ",\n\tYour confirmation code is: " + code + "\nRegards";
	Properties props = new Properties();    
	props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp-mail.outlook.com");
    props.put("mail.smtp.port", "587");  
    //get Session   
    Session session = Session.getDefaultInstance(props,    
     new javax.mail.Authenticator() {    
     protected PasswordAuthentication getPasswordAuthentication() {    
     return new PasswordAuthentication(sEmail, sPass);  
     }    
    });      
    try {    
     MimeMessage message = new MimeMessage(session);    
     message.addRecipient(Message.RecipientType.TO, new InternetAddress(rEmail));    
     message.setSubject(subject);    
     message.setText(msg); 
     message.setFrom(new InternetAddress("foodcirclesverifsupp@outlook.com"));
     Transport.send(message);    
     System.out.println("Verification e-mail sent successfully!");
     return code;
    } catch (MessagingException e) {System.out.println(e);}   
    return "";
}
/**
 * This method create a new user account
 * @param username The new account data
 * @return true if account created successfully, false if user already exists
 */
public boolean signUp(String username, String password, String email, String phone) {
	if(username==null || password==null ||email==null||phone==null ||
			username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()) {
		return false;
	}
	// assume level zero is normal user
	Cryptography c = new Cryptography();
	User created = new User(username.trim(), c.hashPassword(password.trim(), username.trim()), email.trim(), 0, phone.trim());
	// check if user can be added successfully
	if(!userSession.addUser(created))
		return false;
	// if user added successfully, ask acknowledge to user and ask user to login
	return true;
}
/**
* Getter for username
* @return Username
*/
public String getUsername() {
	return currUser.getUsername();
}
/**
 * Change user's username
 * @param newUsername new username
 */
public boolean setUsername(String newUsername) {
	return this.currUser.setUsername(newUsername);
}
/**
 * Getter for user's phone
 * @return User's phone
 */
public String getPhone() {
	return ((User)this.currUser).getPhone();
}
/**
 * Update user's phone
 * @param phone The new phone
 */
public void updatePhone(String phone) {
	((User)this.currUser).updatePhone(phone);
}
/**
 * Change user's password
 * @param oldPassword old password
 * @param newPassword new password
 * @return true if password changed successfully, false if old password is not correct 
 */
public boolean setPassword(String oldPassword, String newPassword) {
	if(oldPassword==null || newPassword==null ||
			oldPassword.trim().isEmpty()||newPassword.trim().isEmpty()) {
		return false;
	}
	Cryptography c = new Cryptography();
	return this.currUser.setPassword(c.hashPassword(oldPassword, currUser.getUsername()), c.hashPassword(newPassword, currUser.getUsername()));
}
/**
 * Reset password of a user when it is forgotten
 * @param username user's username
 * @param password new password
 */
public void resetPassword(String username, String password) {	
	Cryptography c = new Cryptography();
	userSession.setPassword(c.hashPassword(password, username), username);
}
/**
 * Send confirmation code to user's email to reset password
 * @param username user's username
 * @return The confirmation code to be compared
 */
public String sendResetEmail(String username) {
	String email = userSession.getEmail(username);
	return sendEmail(email, username);
}
/**
 * Checks if username already exists
 * @param username The username to be checked
 * @return true if username already exists, false otherwise
 */
public boolean checkUser(String username) {
	if(username==null || username.trim().isEmpty()) {
		return false;
	}
	if(!userSession.checkUser(username)) {
		return false;
	}return true;
}
/**
 * Change user's email
 * @param email the new email
 * @return true if changed, false if email already taken
 */
public boolean setEmail(String email) {
	return this.currUser.setEmail(email);
}
/**
 * Get current user's address
 * @return current user's addresses
 */
public ArrayList<String> getAddress() {
	return ((User)this.currUser).getAddresses();
}
/**
 * Add an address to current user
 * @param address the address to be added
 * @return true if added, false if already exists
 */
public boolean addAddress(String address) {
	if(address==null || address.trim().isEmpty()) {
		return false;
	}
	return ((User)this.currUser).addAddress(address.trim());
}
/**
 * Get current user's cards
 * @return current user's cards
 */
public ArrayList<String> getCreditCards() {
	return ((User)this.currUser).getCreditCards();
}
/**
 * Add a card to current user
 * @param card the card to be added
 * @return true if added, false if already exists
 */
public boolean addCreditCard(String card) {
	if(card==null ||card.trim().isEmpty()) {
		return false;
	}
	return ((User)this.currUser).addCreditCard(card.trim());
}
/**
 * Delete an address for current user
 * @param address Address to be deleted
 */
public void deleteAddress(String address) {
	((User)this.currUser).deleteAddress(address);
}
/**
 * Delete a card for current user
 * @param card Card to be deleted
 */
public void deleteCard(String card) {
	((User)this.currUser).deleteCreditCard(card);
}
/**
 * Return users cart's items
 * @return The item's id in user's cart and corresponding number of items for each item
 */
public ArrayList<String[]> getCart() {
	ArrayList<String[]> arr = new ArrayList<String[]>();
	HashMap<Integer, Integer> map = ((User)this.currUser).getCart().getMap();
	for (Entry<Integer, Integer> entry : map.entrySet()) {
		ArrayList<String> details = getItemDetails(entry.getKey());
	    arr.add(new String[] {entry.getKey().toString(), details.get(0), entry.getValue().toString()});
	}
	return arr;
}
/**
 * Fetch details of an item
 * @param item_id Id of the item
 * @return The item's details
 */
public ArrayList<String> getItemDetails(int item_id){
	return userSession.getItemDetails(item_id);
}
/**
 * Add an item to user's cart
 * @param id Id of the item
 */
public void addItemToCart(int id) {
	((User)this.currUser).addToCart(id);
}
/**
 * Remove an item from user's cart
 * @param id Id of the item
 */
public void removeFromCart(int id) {
	((User)this.currUser).removeFromCart(id);
}
/**
 * Empty the users cart
 */
public void emptyCart() {
	((User)this.currUser).emptyCart();
}
/**
 * Returns user's order history
 * @return User's order history
 */
public ArrayList<Order> getOrderHistory(){
	ArrayList<Order> orders;
	try {
		orders = ((User)this.currUser).getOrderHistory();
	}catch(Exception e) {
		orders = dbmo.getOrder("admin");
	}
	return orders;
}
/**
 * Place a user order, order consists of items in the cart
 * @param address The address to deliver the order to
 * @return True if placed successfully, false if any error encountered
 */
public void placeOrder(String address) {
	 ((User)this.currUser).placeOrder(address);
	 ((User)this.currUser).loadOrderHistory(dbmo.getOrder(getUsername()));
}
/**
 * Return menu of the restaurant
 * @return Menu
 */
public ArrayList<Item> getMenu(){
	return dbmo.getMenu();
}
/**
 * Update a menu item
 * @param id Item's id
 * @param name Item's name
 * @param type Item's type
 * @param description Item's description
 * @param price Item's price
 */
public boolean updateItem(int id, String name, String type, String description, double price) {
	if(currUser.getLevel() == 2) {
		if(name==null||type==null||description==null||name.trim().isEmpty()||description.trim().isEmpty()||type.trim().isEmpty()) {
			return false;
		}
		Item item = new Item(id, name.trim(), type.trim(), description.trim(), price);
		((Admin)currUser).updateItem(item);
		return true;
	}else {
		return false;
	}
}
/**
 * Delete a menu item
 * @param id Id of the item
 * @return True if deleted successfully, false otherwise;
 */
public boolean deleteItem(int id) {
	if(currUser.getLevel() == 2) {
		userSession.deleteFromAllCarts(id);
		return ((Admin)currUser).deleteItem(id);
	}else {
		//Current user's is not an admin
		return false;
	}
}
public void markOrderInProgress(String id) {
	ArrayList<Order> orders=((OrderTaker)currUser).getQueue();
	int Id=Integer.parseInt(id);
	for(Order ord:orders) {
		if(ord.getId()==Id) {
			((OrderTaker)currUser).markInProgress(ord);
			break;
		}
	}
}

public void markOrderDeliverd(String id) {
	ArrayList<Order> orders=((OrderTaker)currUser).getQueue();
	int Id=Integer.parseInt(id);
	for(Order ord:orders) {
		if(ord.getId()==Id) {
			((OrderTaker)currUser).markDone(ord);
			break;
		}
	}
}

/**
 * admin sets the flag in database whether the menu is being modified or not
 * @param flag
 */
public void set_flag_in_database(int flag) {
	if(currUser.getLevel() == 2) {
		((Admin)currUser).set_flag_in_database(flag);
	}
}

/**
 * gets the flag from database to let the user know whether the menu is being modefied or not
 * @return
 */
public int get_flag_from_database() {
	return ((User)currUser).get_flag_from_database();
}

/**
 * retrieves the queue of the ordertaker from database
 * @return
 */
public ArrayList<Order> refreshforOT() {
	((OrderTaker)currUser).set_ordertaker_queue();
	return ((OrderTaker)currUser).getQueue();
}
public String getCartPrice() {
	DecimalFormat df = new DecimalFormat("#.##");
	return df.format(((User)currUser).getCartPrice());
}
public String getPhone(String username) {
	return userSession.getPhone(username);
}

}
