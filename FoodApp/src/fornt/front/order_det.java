package fornt.front;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class order_det {
	@FXML
	private Label desc;
	
	public void set(String description) {
		desc.setText(description);
	}
}
