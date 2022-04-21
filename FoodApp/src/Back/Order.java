package Back;

import java.util.ArrayList;

public class Order {
    private int id;
    private String username;
    private String address;
    private ArrayList<Item> items;
    private ArrayList<Integer> itemNo;
	private String time;
	private double total_cost;
    
    private String state;

    public Order(int id, String username, String address, ArrayList<Item> items, ArrayList<Integer> itemNo, String time, double cost){
    	this.id=id;
    	this.username=username;
    	this.address=address;
    	this.items=items;
    	this.itemNo=itemNo;
    	this.time=time;
    	this.total_cost = cost;
    }
    /**
     * Getter for id
     * @return Order id
     */
    public int getId() {
        return id;
    }
    /**
     * Getter for username
     * @return Order username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Getter for username
     * @return Order username
     */
    public String getAddress() {
        return address;
    }
    /**
     * Getter for username
     * @return Order username
     */
    public ArrayList<Item> getItems() {
        return items;
    }
    /**
     * Getter for username
     * @return Order username
     */
    public ArrayList<Integer> getItemsNum(){
    	return itemNo;
    }
    /**
     * Getter for time
     * @return Order time
     */
    public String getTime() {
        return time;
    }
    /**
     * Getter for state
     * @return Order state
     */
    public String getState() {
        return state;
    }
    /**
     * Getter for total_cost
     * @return Order total_cost
     */
    public double getCost() {
    	return total_cost;
    }
    /**
     * Setter for state
     * @param state The new state
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * Setter for order id
     * @param id New id
     */
    public void setId(int id) {
    	this.id = id;
    }

}
