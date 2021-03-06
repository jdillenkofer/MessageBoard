package de.htwsaar.wirth.client.controller;

import java.util.Optional;

import de.htwsaar.wirth.client.ClientImpl;
import de.htwsaar.wirth.client.gui.component.UserCell;
import de.htwsaar.wirth.client.util.UIConstants;
import de.htwsaar.wirth.remote.model.Status;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Pair;

/**
 * Class {@code UserCellController} initializes the UI-Elements and controls the 
 * actions to display a {@code UserCell}
 */
public class UserCellController {
	
	@FXML
	private Label lblStatus;
	@FXML
	private Label lblUsername;
	@FXML
	private Button btnDeleteUser;
	
	private static final Tooltip TOOLTIP_DELETE_USER = new Tooltip("Benutzer löschen");
	
	private MainViewController mainView;
	private UserCell parent;
	
	public void initCell(MainViewController mainView, Pair<String, Status> item, UserCell parent) {
		this.mainView = mainView;
		this.parent = parent;
		lblUsername.setText(item.getKey());
		setStatus(item.getValue());
		btnDeleteUser.setVisible(false);
		btnDeleteUser.setDisable(true);
		btnDeleteUser.setTooltip(TOOLTIP_DELETE_USER);
		if(ClientImpl.getInstance().isGroupLeader()) {
			initDeleteButton(item.getKey());
		}
	}
	
	/**
	 * {@code setStatus} sets the color of the status indicator.
	 * @param status
	 */
	private void setStatus(Status status) {
		switch(status) {
        case ONLINE:
        	lblStatus.setText(UIConstants.STATUS_SYMBOL_FILLED);
        	lblStatus.setTextFill(UIConstants.STATUS_GREEN);
        	break;
        case AWAY:
        	lblStatus.setText(UIConstants.STATUS_SYMBOL_FILLED);
        	lblStatus.setTextFill(UIConstants.STATUS_YELLOW);
        	break;
        case BUSY:
        	lblStatus.setText(UIConstants.STATUS_SYMBOL_FILLED);
        	lblStatus.setTextFill(UIConstants.STATUS_ORANGE);
        	break;
        default:
        case OFFLINE:
        	lblStatus.setText(UIConstants.STATUS_SYMBOL_EMPTY);
        	lblStatus.setTextFill(UIConstants.STATUS_RED);
        	break;
        }
	}
	
	/**
	 * {@code initDeleteButton} initializes the delete button that is used
	 * by the administrator to delete users.
	 * @param username
	 */
	private void initDeleteButton(String username) {
		boolean isAdmin = ClientImpl.getInstance().getUsername().equals(username);
		if (!isAdmin) {
			btnDeleteUser.setDisable(false);
			parent.setOnMouseEntered(e -> {
				btnDeleteUser.setVisible(true);
			});
			parent.setOnMouseExited(e -> {
				btnDeleteUser.setVisible(false);
			});

			btnDeleteUser.setOnMouseClicked((e) -> {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Benutzer Löschen");
				alert.setContentText("Möchten Sie diesen Benutzer dauerhaft löschen?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					Task<Void> deleteTask = ClientImpl.getInstance().deleteUser(username);
					mainView.getExecutorService().submit(deleteTask);
				}
			});
		} else {
			// reset everything in order to prevent the delete button to show up
			// in an admin user cell
			btnDeleteUser.setVisible(false);
			btnDeleteUser.setDisable(true);
			parent.setOnMouseEntered(null);
		}
	}
}
