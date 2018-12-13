package controller;

import database.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import service.InvoiceService;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class InvoiceController implements Initializable {
    @FXML
    private VBox vbInvoice;

    @FXML
    private VBox vb_invoiceHeader;

    @FXML
    private VBox vb_invoiceContent;

    @FXML
    private Text txt_totalInvoicePrice;

    @FXML
    private Text txt_invoicePricePay;

    @FXML
    private Text txt_invoiceId;

    @FXML
    private Text txt_createdTime;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String invoiceId = MenuController.getController();
        InvoiceService invoiceService = new InvoiceService();
        try {
            List<HashMap> in = invoiceService.getInvoiceInfo(invoiceId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
