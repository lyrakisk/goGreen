package frontend.controllers;

import backend.data.User;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import frontend.Requests;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FriendspageController implements Initializable {

    private static User thisUser;

    @FXML
    private Label goGreen;

    @FXML
    private Line underline;

    @FXML
    private AnchorPane centerPane;

    @FXML
    private JFXTreeTableView friendsPane;

    @FXML
    private JFXHamburger menu;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Button getFriends;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            NavPanelController.setup(drawer, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillFriendsTreeView();

    }

    public void fillFriendsTreeView() {

        JFXTreeTableColumn<UserItem, String> usernameColumn = new JFXTreeTableColumn<>("Friends");
        usernameColumn.setCellValueFactory(param -> param.getValue().getValue().username);

        JFXTreeTableColumn<UserItem, String> lastActivityColumn = new JFXTreeTableColumn<>("Recent Activity");
        lastActivityColumn.setCellValueFactory(param -> param.getValue().getValue().lastActivity);

        JFXTreeTableColumn<UserItem, String> totalCarbonSavedColumn = new JFXTreeTableColumn<>("Total carbon Saved");
        totalCarbonSavedColumn.setCellValueFactory(param -> param.getValue().getValue().carbonSaved);

        totalCarbonSavedColumn.setPrefWidth(150);
        usernameColumn.setPrefWidth(150);
        lastActivityColumn.setPrefWidth(300);

        ObservableList<UserItem> friendsList = getTableData();
        final TreeItem<UserItem> root = new RecursiveTreeItem<>(friendsList, RecursiveTreeObject::getChildren);
        friendsPane.getColumns().setAll(usernameColumn, lastActivityColumn, totalCarbonSavedColumn);
        friendsPane.setRoot(root);
        friendsPane.setShowRoot(false);
    }

    private ObservableList<UserItem> getTableData() {
        ObservableList<UserItem> friendsList = FXCollections.observableArrayList();
        for (String username : thisUser.getFriends()) {
            User tmpFriend = Requests.getUserRequest(username);
            System.out.println(tmpFriend.toString());
            String activity = "This user has no activities";
            if (tmpFriend.getActivities().size() != 0) {
                activity = tmpFriend.getActivities().get(tmpFriend.getActivities().size() - 1).getName();
            }
            String carbonSaved = Double.toString(tmpFriend.getTotalCarbonSaved());
            friendsList.add(new UserItem(username, activity, carbonSaved));
        }
        return friendsList;
    }




    //Used for constructing TreeView
    private class UserItem extends RecursiveTreeObject<UserItem> {
        StringProperty username;
        StringProperty lastActivity;
        StringProperty carbonSaved;

        public UserItem(String username, String lastActivity, String carbonSaved) {
            this.username = new SimpleStringProperty(username);
            this.lastActivity = new SimpleStringProperty(lastActivity);
            this.carbonSaved = new SimpleStringProperty(carbonSaved);

        }
    }

    public static void setUser(User user) {
        thisUser = user;
    }
}
