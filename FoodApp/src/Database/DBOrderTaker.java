package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Back.Item;
import Back.Order;

public class DBOrderTaker extends DBManager {
	Connection con;
	public DBOrderTaker(){
		con = getConnection();
	}
	
	public ArrayList<Order> getqueue() {
		try {
			ArrayList<Order> orders = new ArrayList<Order>();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders where order_status = 'WAITING' OR order_status = 'In progress'");
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
			return orders;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
		
	}
	
	/**
	 * sets the state of an order in the database
	 * @param order_id
	 * @param state
	 */
	public void set_state_of_order(int order_id , String state) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update orders "
					+ "set order_status = '"+ state + "' where order_id = " + order_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
