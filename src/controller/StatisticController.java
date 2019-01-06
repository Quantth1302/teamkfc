package controller;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Invoice;
import service.InvoiceService;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticController implements Initializable {

    /*statistic invoice start*/
    @FXML
    private TextField tf_invoiceSearchContent;

    @FXML
    private TableView<Invoice> invoiceTable;

    @FXML
    private TableColumn<Invoice, String> col_invoiceId;

    @FXML
    private TableColumn<Invoice, String> col_customer;

    @FXML
    private TableColumn<Invoice, String> col_creator;

    @FXML
    private TableColumn<Invoice, String> col_createdTime;

    @FXML
    private TableColumn<Invoice, Double> col_totalPrice;

    @FXML
    private TableColumn<Invoice, Double> col_payPrice;

    @FXML
    private TableColumn<Invoice, Button> col_detail;

    @FXML
    private Pagination invoicePagination;
    /*statistic invoice end*/

    /*sales report start*/

    @FXML
    private TextField tf_s_name;

    @FXML
    private TextField tf_s_sale;

    @FXML
    private DatePicker tf_s_date;

    @FXML
    private TableView<?> salesReportTable;

    @FXML
    private TableColumn<?, ?> col_s_id;

    @FXML
    private TableColumn<?, ?> col_s_name;

    @FXML
    private TableColumn<?, ?> col_s_type;

    @FXML
    private TableColumn<?, ?> col_s_creator;

    @FXML
    private TableColumn<?, ?> col_s_total;

    @FXML
    private TableColumn<?, ?> col_s_revenue;

    @FXML
    private TableColumn<?, ?> col_s_sale;

    @FXML
    private Pagination salesReportPagination;

    /*sales report end*/

    private static String searchContent;

    @FXML
    void invoiceSearchAction(MouseEvent event) {

    }

    private ObservableList<Invoice> initData(int p, String text) {
        ObservableList<Invoice> invoices = null;
        InvoiceService invoiceService = new InvoiceService();

        try {
            invoices = invoiceService.getAllInvoice_p(null, text, p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    private Node createInvoicePage(int p) {
        String text = null;
        if (searchContent != null) {
            text = searchContent;
        }
        ObservableList<Invoice> invoices = initData(p, text);
        invoiceTable.setItems(FXCollections.observableList(invoices));
        return invoiceTable;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        indexAction();
    }

    private void indexAction() {
        Connection connection = DbConnection.getInstance().getConnection();
        int count = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from invoice");
            rs.first();
            count = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        col_invoiceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        col_creator.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        col_createdTime.setCellValueFactory(new PropertyValueFactory<>("createdTime"));
        col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        col_payPrice.setCellValueFactory(new PropertyValueFactory<>("payPrice"));
        col_detail.setCellValueFactory(new PropertyValueFactory<>("payPrice"));

        int pageCount = (count / 10) + 1;
        invoicePagination.setPageCount(pageCount);

        invoicePagination.setPageFactory(this::createInvoicePage);
    }
}
