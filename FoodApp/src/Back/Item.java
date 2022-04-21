package Back;

public class Item {
    private int id;
    private String name;
    private String type;
    private double price;
    private String description;

    public Item(int id, String name, String type, String description, double price){
    	this.id = id;
    	this.name = name;
    	this.type = type;
    	this.description = description;
    	this.price = price;
    } 
    /**
     * Getter for item's id
     * @return item's id
     */
    public int getId() {
        return id;
    }
    /**
     * setter for item's id
     * @param id The item's id
     */
    public void setId(int id) {
    	this.id=id;
    }
    /**
     * Getter for item's name
     * @return Item's name
     */
    public String getName() {
        return name;
    }
    /**
     * setter for item's name
     * @param name The item's name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter for item's type
     * @return Item's type
     */
    public String getType() {
        return type;
    }
    /**
     * Setter for item's type
     * @param type The item's type
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Getter for item's price
     * @return Item's price
     */
    public double getPrice() {
        return price;
    }
    /**
     * Setter for item's price
     * @param price The item's price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Getter for item's description
     * @return Item's description
     */
    public String getDescription() {
        return description;
    }
    /**
     * Setter for item's description
     * @param description The item's description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
