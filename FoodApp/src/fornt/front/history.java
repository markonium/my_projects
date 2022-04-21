package fornt.front;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Back.App;
import Back.Item;
import Back.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class history {
	@FXML
	private ComboBox<String> f_box;
	@FXML
	private Label id1;
	@FXML
	private Label id2;
	@FXML
	private Label id3;
	@FXML
	private Label id4;
	@FXML
	private Label id5;
	@FXML
	private Label t1;
	@FXML
	private Label t2;
	@FXML
	private Label t3;
	@FXML
	private Label t4;
	@FXML
	private Label t5;
	@FXML
	private Label s1;
	@FXML
	private Label s2;
	@FXML
	private Label s3;
	@FXML
	private Label s4;
	@FXML
	private Label s5;
	@FXML
	private Label p1;
	@FXML
	private Label p2;
	@FXML
	private Label p3;
	@FXML
	private Label p4;
	@FXML
	private Label p5;
	private Label[] id = new Label[5];
	private Label[] status = new Label[5];
	private Label[] time = new Label[5];
	private Label[] price = new Label[5];
	ArrayList<Order> orders = new ArrayList<Order>();
	private int last=0;
	private App app;
	public void set(App app2){
        this.app = app2;
        orders = app.getOrderHistory();
        id[0] = id1;
        id[1] = id2;
        id[2] = id3;
        id[3] = id4;
        id[4] = id5;
        status[0] = s1;
        status[1] = s2;
        status[2] = s3;
        status[3] = s4;
        status[4] = s5;
        time[0] = t1;
        time[1] = t2;
        time[2] = t3;
        time[3] = t4;
        time[4] = t5;
        price[0] = p1;
        price[1] = p2;
        price[2] = p3;
        price[3] = p4;
        price[4] = p5;
        ArrayList<String> list = new ArrayList<String>();
        list.add("All");
        list.add("Waiting orders");
        list.add("In progress orders");
        list.add("Completed orders");
        ObservableList<String> filters = FXCollections.observableArrayList(list);
	    f_box.setItems(filters);
        load();
    }
	public void filter(ActionEvent e) {
		orders = app.getOrderHistory();
		String key = "";
		if(f_box.getValue() == null || f_box.getValue().equals("All")) {
			last = 0;
			load();
			return;
		}else if(f_box.getValue().equals("Waiting orders")) {
			key = "WAITING";
		}else if(f_box.getValue().equals("In progress orders")) {
			key = "In progress";
		}else {
			key = "Completed";
		}
		ArrayList<Order> new_orders = new ArrayList<Order>();
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getState().equals(key)) {
				new_orders.add(orders.get(i));
			}
		}
		orders = new_orders;
		last = 0;
		load();
	}
	public void load(){
    	if(orders.size() == 0) {
    		for(int i = 0; i < 5; i++) {
    			id[i].setText("");
        		status[i].setText("");
        		time[i].setText("");
        		price[i].setText("");
    		}
    		return;
    	}
        if(last == orders.size()){
            return;
        }
        int i = 0;
        int j = 0;
        for(i = last; i < last + 5 && i < orders.size(); i++){
            id[j].setText(String.valueOf(orders.get(i).getId()));
            status[j].setText(orders.get(i).getState());
            time[j].setText(orders.get(i).getTime());
            DecimalFormat df = new DecimalFormat("#.##");
            price[j].setText(df.format(orders.get(i).getCost()) + " EGP");
            j++;
        }
        last = i;
        for(j = j; j < 5; j++){
            id[j].setText("");
            status[j].setText("");
            time[j].setText("");
            price[j].setText("");
        }

    }
	public void next(ActionEvent e){
        load();
    }
	public void prev(ActionEvent e){
        int temp = last - (last % 5);
        if(last == temp){
            temp = temp - 10;
        }else{
            temp = temp - 5;
        }

        if(temp < 0){
            return;
        }
        last = temp;
        load();
    }
	public void back(ActionEvent e) throws IOException {
		if(app.getUsername().equals("admin")) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("menuEdit.fxml"));
            Parent parent = (Parent) loader.load();
            MenuEdit controller = loader.getController();
            controller.set(app);
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.initStyle(StageStyle.UNDECORATED);
            s.show();
            final Node source = (Node) e.getSource();
            final Stage curr = (Stage) source.getScene().getWindow();
            curr.close();
		}else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
	        Parent parent = (Parent) loader.load();
	        Menu controller = loader.getController();
	        controller.set(app);
	        Stage s = new Stage();
	        s.setScene(new Scene(parent));
	        s.initStyle(StageStyle.UNDECORATED);
	        s.show();
	        final Node source = (Node) e.getSource();
	        final Stage curr = (Stage) source.getScene().getWindow();
	        curr.close();
		}
    }
	
	public void d1(ActionEvent e) throws IOException{
		if(id1.getText().equals("")) {
			return;
		}
       Order order = getOrder(id[0].getText());
       ArrayList<Item> items= order.getItems();
       ArrayList<Integer> items_no= order.getItemsNum();
       String det = "";
       if(app.getUsername().equals("admin")) {
    	   det = det + "Username: " + order.getUsername() + "\n\n";
       }
       for(int i = 0; i < items.size(); i++) {
    	   det = det + items_no.get(i) + " x " + items.get(i).getName() + " | " + items.get(i).getPrice() * items_no.get(i) + " EGP" + "\n";
       }
       sc(det);
    }
	public void d2(ActionEvent e) throws IOException{
		if(id2.getText().equals("")) {
			return;
		}
		Order order = getOrder(id[1].getText());
	       ArrayList<Item> items= order.getItems();
	       ArrayList<Integer> items_no= order.getItemsNum();
	       String det = "";
	       if(app.getUsername().equals("admin")) {
	    	   det = det + "Username: " + order.getUsername() + "\n\n";
	       }
	       for(int i = 0; i < items.size(); i++) {
	    	   det = det + items_no.get(i) + " x " + items.get(i).getName() + " | " + items.get(i).getPrice() * items_no.get(i) + " EGP" + "\n";
	       }  
	       sc(det);
    }
	public void d3(ActionEvent e) throws IOException{
		if(id3.getText().equals("")) {
			return;
		}
		Order order = getOrder(id[2].getText());
	       ArrayList<Item> items= order.getItems();
	       ArrayList<Integer> items_no= order.getItemsNum();
	       String det = "";
	       if(app.getUsername().equals("admin")) {
	    	   det = det + "Username: " + order.getUsername() + "\n\n";
	       }
	       for(int i = 0; i < items.size(); i++) {
	    	   det = det + items_no.get(i) + " x " + items.get(i).getName() + " | " + items.get(i).getPrice() * items_no.get(i) + " EGP" + "\n";
	       }
	       sc(det);
    }
	public void d4(ActionEvent e) throws IOException{
		if(id4.getText().equals("")) {
			return;
		}
		Order order = getOrder(id[3].getText());
	       ArrayList<Item> items= order.getItems();
	       ArrayList<Integer> items_no= order.getItemsNum();
	       String det = "";
	       if(app.getUsername().equals("admin")) {
	    	   det = det + "Username: " + order.getUsername() + "\n\n";
	       }
	       for(int i = 0; i < items.size(); i++) {
	    	   det = det + items_no.get(i) + " x " + items.get(i).getName() + " | " + items.get(i).getPrice() * items_no.get(i) + " EGP" + "\n";
	       }
	       sc(det);
    }
	public void d5(ActionEvent e) throws IOException{
		if(id5.getText().equals("")) {
			return;
		}
		Order order = getOrder(id[4].getText());
	       ArrayList<Item> items= order.getItems();
	       ArrayList<Integer> items_no= order.getItemsNum();
	       String det = "";
	       if(app.getUsername().equals("admin")) {
	    	   det = det + "Username: " + order.getUsername() + "\n\n";
	       }
	       for(int i = 0; i < items.size(); i++) {
	    	   det = det + items_no.get(i) + " x " + items.get(i).getName() + " | " + items.get(i).getPrice() * items_no.get(i) + " EGP" + "\n";
	       }
	       sc(det);
    }
	private void sc(String det) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("order_det.fxml"));
		Parent parent = (Parent) loader.load();
		order_det controller = loader.getController();
		controller.set(det);
		Stage s = new Stage();
		s.setScene(new Scene(parent));
		s.show();
	}
	private Order getOrder(String idd) {
		int id = Integer.valueOf(idd);
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getId() == id) {
				return orders.get(i);
			}
		}
		return null;
	}
	public void refresh(ActionEvent e) {
		last = 0;
		set(app);
	}
}
