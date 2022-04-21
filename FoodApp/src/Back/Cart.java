package Back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Database.DBUser;

public class Cart {
    private String username;
    private ArrayList<Integer> items;
    private HashMap<Integer,Integer> no_of_items; // storing index of item in the array corresponding to item id
    DBUser db = new DBUser();
    
    public Cart(String user){
    	this.username = user;
    	items = new ArrayList<Integer>();
    	no_of_items = new HashMap<Integer,Integer>();
    }
    
    public Cart(String user, ArrayList<Integer> items, HashMap<Integer,Integer> no_of_items){
    	this.username = user;
    	this.items = items;
    	this.no_of_items = no_of_items;
    }
    /**
     * Return items in the cart
     * @return Items in the cart
     */
    public ArrayList<Integer> getItems(){
    	return items;
    }
    /**
     * Getter for the map
     * @return the map that contains the number of each item
     */
    public HashMap<Integer, Integer> getMap(){
    	return this.no_of_items;
    }
    /**
     * Add an item to the cart
     * @param id the item to be added
     */
    public void addItem(Integer id) {
    	// if the element already in cart
        if(no_of_items.containsKey(id)) {
        	no_of_items.put(id, no_of_items.get(id) + 1);
        	db.incItemCart(id, username);
        }
        // if the item is not in the cart
        else {
        	no_of_items.put(id, 1);
        	items.add(id);
        	db.addItemCart(id, username);
        }
    }
    /**
     * Remove the an item from the cart
     * @param id The id of item to be removed
     */
    public void removeFromCart(int id) {
        // Note that item id will be
    	int no = no_of_items.get(id);
    	if(no == 1) {
    		items.remove(items.indexOf(id));
    		no_of_items.remove(id);
    		db.removeItemCart(id, username);
    	}else {
    		no_of_items.put(id, no - 1);
    		db.decItemCart(id, username);
    	}
    }
    /**
     * Clear the cart
     */
    public void emptyCart() {
        this.items.clear();
        this.no_of_items.clear();
        db.emptyCart(username);
        // database changing logic
    }
    /**
     * Check if the cart is clear
     * @return True if clear, false otherwise
     */
    public boolean isEmpty() {
    	return items.isEmpty();
    }    
    public double getCartPrice() {
    	Double cost = 0.0;
    	for (Entry<Integer, Integer> entry : no_of_items.entrySet()) {
			double price;
			ArrayList<String> details = db.getItemDetails(entry.getKey());
			price = Double.parseDouble(details.get(2));
			cost += price * (double)entry.getValue();
		}
    	return cost;
    }
}
