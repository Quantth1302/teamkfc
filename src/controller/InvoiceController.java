package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InvoiceController {
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
}
