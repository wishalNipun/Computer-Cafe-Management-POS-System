package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;


public class OwnerDashBoardFormController {
    public AnchorPane loadContext;
    public  Label lblTime;
    public  Label lblDate;
    public Label lblUserName;

    public void initialize(){
        try {
            loadDateAndTime();
            openContext();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    private void openContext() throws IOException {
        setUI("MemberManagementForm");

    }

    public void btnMemberManagerOnAction(ActionEvent actionEvent) throws IOException {
        setUI("MemberManagementForm");

    }

    public void btnPackageManagementOnAction(ActionEvent actionEvent) throws IOException {
        setUI("PackageManagementForm");

    }

    public void btnComputerManagerOnAction(ActionEvent actionEvent) throws IOException {
        setUI("ComputerForm");

    }

    public void btnSupplierManagerOnAction(ActionEvent actionEvent) throws IOException {
        setUI("SupplierForm");

    }

    public void btnUserManagerOnAction(ActionEvent actionEvent) throws IOException {
        setUI("UserForm");

    }
    private void setUI(String URI) throws IOException {
        loadContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+URI+".fxml"));
        loadContext.getChildren().add(parent);
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage1= (Stage)loadContext.getScene().getWindow();
        stage1.close();
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage2= new Stage();
        stage2.setTitle("Computer Cafe Management System");
        Image image = new Image("/assert/images/logo.png");
        stage2.getIcons().add(image);
        stage2.setScene(scene);
        stage2.centerOnScreen();
        stage2.show();
    }

    public void btnComputerSupplierDetailOnAction(ActionEvent actionEvent) throws IOException {
        setUI("ComputerSupplierDetailForm");
    }

    public void btnPackageComputerDetailOnAction(ActionEvent actionEvent) throws IOException {
        setUI("PackageComputerDetailForm");
    }


    public void btnPaymentReport(ActionEvent actionEvent) throws IOException {
        setUI("ReportForm");
    }
}
