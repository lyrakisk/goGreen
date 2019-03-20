package frontend.gui;

import com.jfoenix.controls.JFXButton;
import data.Activity;
import data.BuyLocallyProducedFood;
import data.BuyNonProcessedFood;
import data.BuyOrganicFood;
import data.EatVegetarianMeal;
import data.User;
import frontend.controllers.ActivitiesController;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Events {

    /**
     * .
     * Add hover events to button
     *
     * @param button button to style
     */
    public static void addActivityHover(AnchorPane pane, JFXButton button) {
        pane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            button.setStyle("-fx-background-color: #00db00;");
            pane.setCursor(Cursor.HAND);
        });
        pane.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            button.setStyle("-fx-background-color: green;");
            pane.setCursor(Cursor.DEFAULT);
        });
    }

    /**.
     * Add activities to the user upon clicking
     * @param pane pane to be clicked
     * @param type type of activity
     * @param loggedUser user to update
     * @param activityTable table to set history to
     */
    public static void addActivityClick(AnchorPane pane, int type,
                                        User loggedUser, TableView<Activity> activityTable) {
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (type == 1) {
                EatVegetarianMeal meal = new EatVegetarianMeal();
                meal.performActivity(loggedUser);
            } else if (type == 2) {
                BuyLocallyProducedFood food = new BuyLocallyProducedFood();
                food.performActivity(loggedUser);
            } else if (type == 3) {
                BuyOrganicFood food = new BuyOrganicFood();
                food.performActivity(loggedUser);
            } else {
                if (type == 4) {
                    BuyNonProcessedFood food = new BuyNonProcessedFood();
                    food.performActivity(loggedUser);
                }
            }
            ObservableList<Activity> activities = ActivitiesController.getActivities(loggedUser);
            activityTable.setItems(activities);
        });
    }

    /**
     * .
     * Add logout event handling
     *
     * @param image     image to add logout option to
     * @param nextScene Scene after logout
     */
    public static void addLogout(ImageView image, Scene nextScene) {
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            image.setImage(new Image("frontend/Pics/logoutIconOpen.png"));
            ConfirmBox.logout(Main.getPrimaryStage(), nextScene, image, "Are"
                    + "you sure you want to logout?");
        });
    }

    /**
     * .
     * Add styledCursor on ImageViews
     *
     * @param image image to add events to
     */
    public static void addImageCursor(ImageView image) {
        image.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            image.setCursor(Cursor.HAND);
        });
        image.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            image.setCursor(Cursor.DEFAULT);
        });
    }

    /**
     * .
     * Add styledCursor on Buttons
     *
     * @param button button to add events to
     */
    public static void addButtonCursor(Button button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            button.setCursor(Cursor.HAND);
            button.setOpacity(1);
        });
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            button.setCursor(Cursor.DEFAULT);
            button.setOpacity(0.75);
        });
    }
//
//    /**
//     * .
//     * Add styledCursor on menu burger
//     *
//     * @param menu menu to add events to
//     */
//    public static void addMenuCursor(JFXHamburger menu) {
//        menu.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
//            menu.setCursor(Cursor.HAND);
//        });
//        menu.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
//            menu.setCursor(Cursor.DEFAULT);
//        });
//    }
}
