package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager extends DBAbstract{
	Connection con;
	public DBManager(){
		con = getConnection();
	}
	/**
	 * Upgrade a user level to manager
	 * @param username The user to upgrade
	 */
	public void upgradeLevel(String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update user set level = " + 1 + " where username = '" + username + "'");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Downgrade a user level to manager
	 * @param username The user to upgrade
	 */
	public void downgradeLevel(String username) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update user set level = " + 0 + " where username = '" + username + "'");
		}catch(Exception e) {
			System.out.println(e);
		}
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
	 * @return 0 if book added successfully, 1 if ISBN already exists or category is invalid, 2 if publisher does not exist
	 */
	public int addBook(int isbn, String title, String publisher, String pub_year, Double price, String category, int threshold, ArrayList<String> authors) {
		boolean checkPub = checkPublisher(publisher);
		if(checkPub) {
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("insert into book values(" + isbn + ",'" + title + "','" + publisher + "','" + pub_year + "'," + price + ",'" + category + "'," + threshold + "," + threshold + ")");
				stmt.executeUpdate("insert into book_sold values (' " + isbn + "'," + 0 + ")");
				for(int i = 0; i < authors.size(); i++) {
					stmt.executeUpdate("insert into book_author values (" + isbn + ",'" + authors.get(i) + "')");
				}
				return 0;
			}catch(Exception e) {
				System.out.println(e);
				//Error means either ISBN already exists or Category is invalid
				return 1;
			}
		}return 2;
	}
	/**
	 * Check if publisher exists
	 * @param pub Publisher's name
	 * @return true if exists, false otherwise
	 */
	private boolean checkPublisher(String pub) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select exists(select * from publisher where name = '" + pub + "')");
			rs.next();
			String s = rs.getString(1);
			return  s.equals("1")? true: false;
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	/**
	 * Add a new publisher 
	 * @param name Publisher's name
	 * @param address Publisher's address
	 * @param phone Publisher's phone
	 * @return true if added successfully, false otherwise
	 */
	public boolean addPublisher(String name, String address, String phone) {
		boolean checkPub = checkPublisher(name);
		if(!checkPub) {
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("insert into publisher values('" + name + "','" + address + "','" + phone + "')");
				return true;
			}catch(Exception e) {
				System.out.println(e);
				return false;
			}
		}
		return false;
	}
	/**
	 * Place order for books
	 * @param isbn
	 * @param amount
	 * @param publisher
	 * @return true if placed, false otherwise
	 */
	public boolean placeOrderManager(int isbn, int amount, String publisher) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("insert into book_order values('0'," + isbn + "," + amount + ",'waiting','" + publisher + "')");
			return true;
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	/**
	 * Confirm a book order
	 * @param id Order's id
	 */
	public void confirmOrder(int id) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update book_order set status = 'received' where id = " + id);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	public void incStock(String isbn, String stock) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update book set stock = stock + " + stock + " where ISBN = " + isbn);
		}catch(Exception e) {
			System.out.println(e);
		}
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
		try {
			if(!checkPublisher(publisher)) {
				return false;
			}
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update book set title = '" + title + "' where ISBN = " + isbn);
			stmt.executeUpdate("update book set publisher = '" + publisher + "' where ISBN = " + isbn);
			stmt.executeUpdate("update book set publication_year = '" + pub_year + "' where ISBN = " + isbn);
			stmt.executeUpdate("update book set price = " + price + " where ISBN = " + isbn);
			stmt.executeUpdate("update book set category = '" + category + "' where ISBN = " + isbn);
			stmt.executeUpdate("update book set threshold = " + threshold + " where ISBN = " + isbn);
			stmt.executeUpdate("update book set stock = " + stock + " where ISBN = " + isbn);
			return true;
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	/**
	 * Return manager' orders with the given status
	 * @param status
	 * @return
	 */
	public ArrayList<String []> getOrders(String status){
		ArrayList<String []> orders = new ArrayList<String []>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from book_order where status = '" + status + "'");
			while(rs.next()) {
				String[] arr = new String[5];
				arr[0] = String.valueOf(rs.getInt(1));
				arr[1] = String.valueOf(rs.getInt(2));
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
				arr[4] = rs.getString(5);
				orders.add(arr);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return orders;
	}
	public boolean deleteOrder(String id) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("delete from book_order where id = " + id);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
}
