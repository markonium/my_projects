package Database;

import java.sql.*;
import java.util.ArrayList;

import Back.*;

public class DBMenuOrder extends DBManager {
	Connection con;
	public DBMenuOrder(){
		con = getConnection();
	}
	
	
	/**
	 * add or update an item
	 * if item id = 0 then add new item 
	 * @param item
	 */
	public void updateitem(Item item) {
		try {
			Statement stmt = con.createStatement();
			if(item.getId()!=0) {
				stmt.executeUpdate("update items "
						+ "set name = '"+item.getName()+"' , type = '"+item.getType()+"' , price = '"+String.valueOf(item.getPrice())+"' , description = '"+item.getDescription()+"' "
								+ "where item_id = " + item.getId());
			}
			else {
				stmt.executeUpdate("insert into items (name , type , price , description) "
						+ "values('"+item.getName()+"', '"+item.getType()+"', '"+String.valueOf(item.getPrice())+"' , '"+item.getDescription()+"')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * removes an item from the menu
	 * @param id
	 * @return true if the delete is successful
	 */
	public boolean removeitem(int id) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("delete from items where item_id = "+ id );
			return true;
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
	
	
	/**
	 * gets the menu as an array of items
	 * @return Item[]
	 */
	public ArrayList<Item> getMenu() {
		try {
			ArrayList<Item> arr = new ArrayList<Item>();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from items ");
			while(rs.next()) {
				Item i = new Item(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(5),Double.valueOf(rs.getString(4)));
				arr.add(i);
			}
			
			
			return arr;
			
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	/**
	 * adds an order to the database
	 * @param order
	 * @return New order id if order added successfully, 0 if any error encountered
	 */
	public int addOrder(Order order) {
		try {
			String username = order.getUsername();
			String time = order.getTime();
			String address = order.getAddress();
			double total_cost = order.getCost();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("insert into orders (username , order_status , order_time , address, total_cost)"
						+"values('"+username+"', 'WAITING', '"+time+"' , '"+address+"'," + total_cost + ")" );
			
			//get id of added order
			ResultSet rs = stmt.executeQuery("Select order_id from orders where username = '" + username + "' and order_time = '"+time+"'");
			rs.next();
			int orderid = rs.getInt(1);
			
			ArrayList<Item> items = order.getItems();
			ArrayList<Integer> itemsno = order.getItemsNum();
			
			for(int i=0;i<items.size();i++) {
				Item it = items.get(i);
				int number = itemsno.get(i);
				
				stmt.executeUpdate("insert into order_items (order_id , item_id , number)"
						+"values("+orderid+","+it.getId()+", "+number+")" );
			}			
			return orderid;
		}catch(Exception e){
			System.out.println(e);
			return 0;
		}
	}
	/**
	 * Get order history of a certain user
	 * @param username The user's username
	 * @return The user's order history
	 */
	public ArrayList<Order> getOrder(String username){
		try {
			ArrayList<Order> orders = new ArrayList<Order>();
			Statement stmt = con.createStatement();
			ResultSet rs;
			if(!username.equals("admin")) {
				rs = stmt.executeQuery("select * from orders where username = '" + username + "'");
			}else {
				rs = stmt.executeQuery("select * from orders");
			}
			ArrayList<String[]> arr = new ArrayList<String[]>();
			while(rs.next()) {
				arr.add(new String[] {rs.getString(1), String.valueOf(rs.getInt(2)), rs.getString(3), rs.getString(4),  rs.getString(5), rs.getString(6), String.valueOf(rs.getDouble(7))});
			}
			
			for(int i = 0; i < arr.size(); i++) {
				ResultSet rs2 = stmt.executeQuery("select * from (items natural join order_items) where order_id = " + arr.get(i)[1] );
				ArrayList<Item> items = new ArrayList<Item>();
				ArrayList<Integer> itemnumber = new ArrayList<Integer>();
				while(rs2.next()) {
					int itemnu = rs2.getInt(7);
					items.add(new Item(rs2.getInt(1), rs2.getString(2), rs2.getString(3), rs2.getString(5), rs2.getDouble(4)));
					itemnumber.add(itemnu);
				}
				String[] temp = arr.get(i);
				Order o = new Order(Integer.parseInt(temp[1]), temp[0], temp[5], items, itemnumber, temp[3], Double.valueOf(temp[6]));
				o.setState(temp[2]);
				orders.add(o);
			}
			ArrayList<Order> new_order = new ArrayList<Order>();
			while(!orders.isEmpty()) {
				new_order.add(orders.get(orders.size() - 1));
				orders.remove(orders.size() - 1);
			}
			return new_order;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * set whether the menu is being modified or not
	 * @param to
	 */
	public void set_menu_flag(int to) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select flag from menu_being_mod_flag");
			rs.next();
			int flag = rs.getInt(1);
			
			if(flag==to) {
				return;
			}
			else {
				stmt.executeUpdate("update menu_being_mod_flag "
						+ "set flag = "+ to + " where flag = " + flag);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * gets the flag from the database to determine whether the menu is being modified or not
	 * @return
	 */
	public int get_menu_flag() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select flag from menu_being_mod_flag");
			rs.next();
			int flag = rs.getInt(1);
			return flag;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
}

