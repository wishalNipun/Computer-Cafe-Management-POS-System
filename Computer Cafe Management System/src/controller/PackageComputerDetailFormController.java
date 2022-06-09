package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Computer;
import model.ComputerSupplierDetail;
import model.Package;
import model.PackageComputerDetail;
import view.tm.ComputerSupplerDetailTM;
import view.tm.PackageComputerDetailTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PackageComputerDetailFormController {
    public JFXTextField txtPackagePrice;
    public JFXTextField txtPackageName;
    public TableView<PackageComputerDetailTM> tblPackageComputer;
    public TableColumn colPackageId;
    public TableColumn colComputerId;
    public TableColumn colDelete;
    public JFXComboBox <String>cmbPackageId;
    public JFXTextField txtComputerPrice;
    public JFXTextField txtComputerType;
    public JFXComboBox <String>cmbComputerId;

    public void initialize(){
        colPackageId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        colComputerId.setCellValueFactory(new PropertyValueFactory<>("computerId"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setComputerIds();
        setPackageIds();
        try {
            loadPackageComputerDetails();
        }  catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbPackageId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setPackageDetails(newValue);
            }  catch (SQLException|ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        });

        cmbComputerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            try {
                setComputerDetails(newValue);
            } catch (SQLException|ClassNotFoundException exception) {
                exception.printStackTrace();
            }

        });


    }

    private void loadPackageComputerDetails() throws SQLException, ClassNotFoundException {
        ResultSet result = PackageComputerCRUDController.getPackageComputerDetail();
        ObservableList <PackageComputerDetailTM>obList = FXCollections.observableArrayList();
        while (result.next()){
            Button btn = new Button("Delete");
            String p  = result.getString(1);
            String c  = result.getString(2);
            obList.add(
                    new PackageComputerDetailTM(
                            p,
                            c,
                            btn
                    )
            );
            btn.setOnAction(e->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are You Sure?",
                        ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)){

                    try {
                        PackageComputerCRUDController.DeletePackageComputerDetail(p,c);
                        loadPackageComputerDetails();
                    } catch (SQLException|ClassNotFoundException exception) {
                        exception.printStackTrace();
                    }
                }
            });

        }
        tblPackageComputer.setItems(obList);
    }

    private void setPackageDetails(String SelectedPackage) throws SQLException, ClassNotFoundException {
        Package pack = PackageComputerCRUDController.getPackage(SelectedPackage);
        if (pack != null){
            txtPackageName.setText(pack.getPackageName());
            txtPackagePrice.setText(String.valueOf(pack.getPackagePrice()));
        }
    }

    private void setComputerDetails(String SelectedComputer) throws SQLException, ClassNotFoundException {
        Computer computer = PackageComputerCRUDController.getComputer(SelectedComputer);
        if (computer != null) {
            txtComputerType.setText(computer.getComputerType());
            txtComputerPrice.setText(String.valueOf(computer.getComputerPrice()));

        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    private void setPackageIds() {
        ObservableList<String> pIdObList = null;
        try {
            pIdObList = FXCollections.observableArrayList(
                    PackageComputerCRUDController.PackageId()
            );
            cmbPackageId.setItems(pIdObList);
        } catch (SQLException |ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private void setComputerIds() {
        ObservableList<String> cIdObList = null;
        try {
            cIdObList = FXCollections.observableArrayList(
                    PackageComputerCRUDController.ComputerId()
            );
            cmbComputerId.setItems(cIdObList);
        } catch (SQLException |ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        PackageComputerDetail pcd = new PackageComputerDetail(
                cmbPackageId.getValue(),
                cmbComputerId.getValue()
        );
        try {
            PackageComputerCRUDController.setPackageComputerDetail(pcd);
            loadPackageComputerDetails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
