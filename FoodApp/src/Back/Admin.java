package Back;


public class Admin extends Account{
private static Admin instance;

	private Admin(String userName, String password, String email, int lvl) {
		super(userName, password, email, lvl);
	}
	
	private Admin() {
	}

	public static Admin getInstance(){  
		   if (instance == null){  
		      synchronized(Admin.class){  
		        if (instance == null){  
		        	instance = new Admin();
		        }  
		    }}  
		  return instance;  
	}
	
	public static Admin getInstance(String userName, String password, String email, int lvl){  
		   if (instance == null){  
		      synchronized(Admin.class){  
		        if (instance == null){  
		        	instance = new Admin(userName,password,email,lvl);
		        }  
		    }}  
		  return instance;  
	}
	/**
	 * Delete an item from the menu
	 * @param id The item id
	 * @return True if deleted successfully, false otherwise;
	 */
	public boolean deleteItem(int id) {
		return dbmo.removeitem(id);
	}
	/**
	 * Update a menu item
	 * @param item to be updated
	 */
	public void updateItem(Item item) {
		dbmo.updateitem(item);
	}	
	
	/**
	 * set the flag whether the menu is being modified or not
	 * @param flag
	 */
	public void set_flag_in_database(int flag) {
		dbmo.set_menu_flag(flag);
	}
	
}
