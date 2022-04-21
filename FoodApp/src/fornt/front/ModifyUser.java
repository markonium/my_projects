package fornt.front;

import Back.App;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class ModifyUser  {
    @FXML
    public ComboBox<String> address;
    @FXML
    public ComboBox<String> card;
    @FXML
    private TextField username;
    @FXML
    private TextField newAddress;
    @FXML
    private TextField newCard;
    @FXML
    private Label addressLabel;
    @FXML
    private Label cardLabel;
    @FXML
    private Label error;
    @FXML
    private PasswordField oldPass;
    @FXML
    private PasswordField newPass;

    /*@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> test=new ArrayList<>();
        test.add("testing");
        ObservableList<String> addresslist=FXCollections.observableArrayList(test);
        address.setItems(addresslist);
    }*/
    private App app;
    public void setApp(App app){
        this.app=app;
        ObservableList<String> addresslist=FXCollections.observableArrayList(app.getAddress());
        address.setItems(addresslist);
        ObservableList<String> cardslist=FXCollections.observableArrayList(app.getCreditCards());
        card.setItems(cardslist);
    }
    public void changeName(ActionEvent e){
        if(username.getText().equals(app.getUsername())||username.getText().equals("")||username.getText().charAt(0)==' '){
            error.setText("Invalid username");
        }else{
            boolean check=app.setUsername(username.getText());
            if(check){
                error.setText("Username changed successfully");
            }else{
                error.setText("Username already taken");
            }
        }
    }
    public void changePass(ActionEvent e){
        if(newPass.getText().length()<8){
            error.setText("Password length must be at least 8 characters");
            return;
        }if (newPass.getText().charAt(0) == ' '){
            error.setText("Password cannot start with a space");
            return;
        }if (newPass.getText().equals(oldPass.getText())){
            error.setText("New password can't be as old password");
            return;
        }
        boolean check = app.setPassword(oldPass.getText(),newPass.getText());
        if(check){
            error.setText("Password changed successfully");
        }else{
            error.setText("Old password is incorrect");
        }
    }
    public void chooseAddress(ActionEvent e){
        addressLabel.setText(address.getValue());
    }
    public void chooseCard(ActionEvent e){
        cardLabel.setText(card.getValue());
    }
    public void deleteAddress(ActionEvent e){
        if(addressLabel.getText().equals("Selected address")||addressLabel.getText().equals("")){
            error.setText("Choose an address first");
        }else{
            app.deleteAddress(addressLabel.getText());
            addressLabel.setText("Selected address");
            ObservableList<String> addresslist=FXCollections.observableArrayList(app.getAddress());
            address.setItems(addresslist);
            address.setPromptText("Address");
        }
        addressLabel.setText("Selected address");
        address.setPromptText("Address");
    }
    public void deleteCard(ActionEvent e){
        if(cardLabel.getText().equals("Selected card")||cardLabel.getText().equals("")){
            error.setText("Choose a card first");
        }else{
            app.deleteCard(cardLabel.getText());
            cardLabel.setText("Selected card");
            ObservableList<String> cardslist=FXCollections.observableArrayList(app.getCreditCards());
            card.setItems(cardslist);
            card.setPromptText("Cards");
        }
        addressLabel.setText("Selected card");
    }
    public void addAddress(ActionEvent e){
        if(newAddress.getText().equals("")){
            error.setText("Enter a valid address");
        }else {
            boolean check =app.addAddress(newAddress.getText());
            if(check){
                error.setText("Added successfully");
                ObservableList<String> addresslist=FXCollections.observableArrayList(app.getAddress());
                address.setItems(addresslist);
                addressLabel.setText("Selected address");
            }else{
                error.setText("Address already exists");
            }
        }
    }
    public void addCard(ActionEvent e){
        if(newCard.getText().length() != 16 || (newCard.getText().charAt(0) != '4' && newCard.getText().charAt(0) != '5')){
            error.setText("Enter a valid Card");
        }else {
        	try {
	    		Long.valueOf(newCard.getText());
	    	}catch(Exception ex) {
	    		error.setText("Card number and expiry date must be numeric");
	    		return;
	    	}
            boolean check = app.addCreditCard(newCard.getText());
            if(check){
                error.setText("Added successfully");
                ObservableList<String> cardslist=FXCollections.observableArrayList(app.getCreditCards());
                card.setItems(cardslist);
                cardLabel.setText("Selected card");
            }else{
                error.setText("Card already exists");
            }
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
}
