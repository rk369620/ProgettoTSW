package model.Order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private String customer; 
    private Timestamp orderDate;
    private double totalPrice;
    private List<OrderItem> orderItems;

    // Costruttore e altri metodi della classe

    public List<OrderItem> getOrderItems() {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
