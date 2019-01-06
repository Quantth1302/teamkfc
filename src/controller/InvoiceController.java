package controller;

import database.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import service.InvoiceService;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        List<HashMap> invoiceRs = null;
        txt_invoiceId.setText(invoiceId);
        try {
            invoiceRs = invoiceService.getInvoiceInfo(invoiceId, -1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setDataIntoInvoice(invoiceRs);
    }

    private void setDataIntoInvoice(List<HashMap> list){
        for (HashMap invoice : list) {

            Date createdTime = (Date) invoice.get("createdTime");
            String itemName = (String) invoice.get("itemName");
            String comboName = (String) invoice.get("comboName");
            String itemId = (String) invoice.get("itemId");
            int itemQuantity = (int) invoice.get("itemQuantity");
            String comboId = (String) invoice.get("comboId");
            int comboQuantity = (int) invoice.get("comboQuantity");
            int percent = (int) invoice.get("percent");
            double itemPrice = (double) invoice.get("itemPrice");
            double comboPrice = (double) invoice.get("comboPrice");
            double totalPrice = (double) invoice.get("totalPrice");
            double payPrice = (double) invoice.get("payPrice");

            txt_createdTime.setText(String.valueOf(createdTime));

            HBox hbItem = new HBox();
            hbItem.setAlignment(Pos.CENTER_LEFT);
            String name = null;
            String quantityStr = null;
            String sale = null;
            String price = null;
            if (itemId != null) {
                name = itemId +"-"+ itemName;
                quantityStr = String.valueOf(itemQuantity);
                sale = String.valueOf(percent)+ "%";
                price = String.valueOf(itemPrice);
            } else {
                name = comboId +" - "+ comboName;
                quantityStr = String.valueOf(comboQuantity);
                sale = "  ";
                price = String.valueOf(comboPrice);
            }
            Text txtName = new Text(name);
            HBox.setMargin(txtName, new Insets(5,5,5,5));
            hbItem.getChildren().add(txtName);

            HBox hbPrice = new HBox();
            hbPrice.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(hbPrice, Priority.ALWAYS);

            Text txtSale = new Text(sale+ "  ");
            Text txtQuantity = new Text(quantityStr+ "  ");
            Text txtPrice = new Text(price + "đ");

            hbPrice.getChildren().addAll(txtSale, txtQuantity, txtPrice);
            hbItem.getChildren().add(hbPrice);

            vb_invoiceContent.getChildren().add(hbItem);
            txt_totalInvoicePrice.setText(String.valueOf(totalPrice)+ "đ");
            txt_invoicePricePay.setText(String.valueOf(payPrice)+ "đ");

        }
    }
}
