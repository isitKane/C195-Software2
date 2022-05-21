/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import DAO.DBCountries;
import DAO.DBCustomers;
import DAO.DBFLDivisions;
import static controller.Alerts.appointmentAlertsEN;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

/**
 * FXML Controller class
 *
 * @author Kane
 */
public class AddCustomerFormController implements Initializable {

    @FXML
    private Label CustomerIDLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label Address1Label;

    @FXML
    private Label FLDivisionLabel;

    @FXML
    private Label PostalCodeLabel;

    @FXML
    private Label CountryLabel;

    @FXML
    private Label PhoneNumberLabel;

    @FXML
    private TextField CustomerIDTextField;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField Address1TextField;

    @FXML
    private ComboBox<FirstLevelDivisions> FLDivisionComboBox;

    @FXML
    private TextField PostalCodeTextField;

    @FXML
    private ComboBox<Countries> CountryComboBox;

    @FXML
    private TextField PhoneNumberTextField;

    @FXML
    private Button SaveButton;

    @FXML
    private Button CancelButton;

    @FXML
    private TableView<Customers> CustomerTableView;

    @FXML
    private TableColumn<Customers, Integer> CustomerIDColumn;

    @FXML
    private TableColumn<Customers, String> NameColumn;

    @FXML
    private TableColumn<Customers, String> Address1Column;

    @FXML
    private TableColumn<Customers, String> FLDivisionColumn;

    @FXML
    private TableColumn<Customers, String> PostalCodeColumn;

    @FXML
    private TableColumn<Customers, String> CountryColumn;

    @FXML
    private TableColumn<Customers, String> PhoneNumberColumn;

    private ObservableList<Customers> setCustomers;

    /**
     *
     */
    public static int id;

    /**
     * on action  save form
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        int customerID = id++;
        String customerName = NameTextField.getText();
        String address = Address1TextField.getText();
        String postalCode = PostalCodeTextField.getText();
        String phoneNumber = PhoneNumberTextField.getText();
        FirstLevelDivisions flDivision = FLDivisionComboBox.getValue();
        int divisionID = flDivision.getDivisionID();
        Countries country = CountryComboBox.getValue();

        if (CustomerIDTextField.getText().isEmpty() || NameTextField.getText().isEmpty() || Address1TextField.getText().isEmpty() || FLDivisionComboBox.getSelectionModel().isEmpty()
                || PostalCodeTextField.getText().isEmpty() || CountryComboBox.getSelectionModel().isEmpty() || PhoneNumberTextField.getText().isEmpty()) {
            appointmentAlertsEN(4);
        }

        DBCustomers.addCustomer(customerName, address, postalCode, phoneNumber, divisionID);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main");
        stage.show();
    }

    /**
     * on action select country in the country combo box
     * @param event the event
     * @throws SQLException the sql exception
     */
    @FXML
    void onActionSelectCountry(ActionEvent event) throws SQLException {

        int countryID = CountryComboBox.getValue().getCountryID();
        try {
            FLDivisionComboBox.setItems(DBFLDivisions.getDivisionsByCountryID(countryID));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * on action select country in the fld combo box
     * @param event the event
     * @throws SQLException the sql exception
     */
    @FXML
    void onActionSelectFLD(ActionEvent event) throws SQLException {

    }

    /**
     * on action back to the main screen
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main");
        stage.show();
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        // TODO
        CustomerIDTextField.setText(Integer.toString(id));

        //This is to populate the customer table view.
        try {
            setCustomers = DBCustomers.getAllCustomers();

            CustomerTableView.setItems(setCustomers);
            CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            NameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            Address1Column.setCellValueFactory(new PropertyValueFactory<>("address"));
            FLDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
            PostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            CountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            PhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

            CountryComboBox.setItems(DBCountries.getAllCountries());

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

}
