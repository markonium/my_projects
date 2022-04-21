package fornt.front;

import Back.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class Cart {
	@FXML
    private Label error;
    @FXML
    private Label name1;
    @FXML
    private Label name2;
    @FXML
    private Label name3;
    @FXML
    private Label name4;
    @FXML
    private Label name5;
    @FXML
    private Label price1;
    @FXML
    private Label price2;
    @FXML
    private Label price3;
    @FXML
    private Label price4;
    @FXML
    private Label price5;
    private int[] id = new int[5];
    private Label[] names = new Label[5];
    private Label[] price= new Label[5];
    private int last=0;
    private ArrayList<String[]> items;
    private App app;
    public void set(App app){
        this.app=app;
        items=app.getCart();
        id[0] = 0;
        id[1] = 0;
        id[2] = 0;
        id[3] = 0;
        id[4] = 0;
        names[0] = name1;
        names[1] = name2;
        names[2] = name3;
        names[3] = name4;
        names[4] = name5;
        price[0]=price1;
        price[1]=price2;
        price[2]=price3;
        price[3]=price4;
        price[4]=price5;
        load();
    }
    public void load(){
    	if(items.size() == 0) {
    		id[0] = 0;
    		price[0].setText("");
    		names[0].setText("Your cart is currently empty");
    		return;
    	}
        if(last==items.size()){
            return;
        }
        int i = 0;
        int j =0;
        for(i = last; i < last+5 && i < items.size(); i++){
            id[j]=Integer.parseInt(items.get(i)[0]);
            price[j].setText(items.get(i)[2]);
            names[j].setText(app.getItemDetails(id[j]).get(0));
            j++;
        }
        last=i;
        for( j=j ;j<5;j++){
            id[j]=0;
            price[j].setText("");
            names[j].setText("");
        }

    }
    public void next(ActionEvent e){
        load();
    }
    public void prev(ActionEvent e){
        int temp=last-(last%5);
        if(last == temp){
            temp = temp - 10;
        }else{
            temp = temp-5;
        }

        if(temp<0){
            return;
        }
        last=temp;
        load();
    }
    public void add1(ActionEvent e){
        if(id[0]==0){
            return;
        }
        else{
            app.removeFromCart(id[0]);
            items=app.getCart();
            last=0;
            load();
        }
    }
    public void add2(ActionEvent e){
        if(id[1]==0){
            return;
        }
        else{
            app.removeFromCart(id[1]);
            items=app.getCart();
            last=0;
            load();
        }
    }
    public void add3(ActionEvent e){
        if(id[2]==0){
            return;
        }
        else{
            app.removeFromCart(id[2]);
            items=app.getCart();
            last=0;
            load();
        }
    }
    public void add4(ActionEvent e){
        if(id[3]==0){
            return;
        }
        else{
            app.removeFromCart(id[3]);
            items=app.getCart();
            last=0;
            load();
        }
    }
    public void add5(ActionEvent e){
        if(id[4]==0){
            return;
        }
        else{
            app.removeFromCart(id[4]);
            items=app.getCart();
            last=0;
            load();
        }
    }
    public void back(ActionEvent e) throws IOException {
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
    public void checkout(ActionEvent e) throws IOException {
    	if(names[0].getText().equals("Your cart is currently empty")) {
    		error.setText("Your cart is currently empty!");
    		return;
    	}
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout.fxml"));
        Parent parent = (Parent) loader.load();
        checkout controller = loader.getController();
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
