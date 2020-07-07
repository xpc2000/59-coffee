package com.code59.caffemall.bean;

public class Order {
    private String id;
    private String id_food;
    private int num;
    private boolean order_type;
    private String time;
    private String deliver_address;
    private String id_guest;
    private String phone;
    private boolean be_over;
    private boolean be_deliver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_food() {
        return id_food;
    }

    public void setId_food(String id_food) {
        this.id_food = id_food;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isOrder_type() {
        return order_type;
    }

    public void setOrder_type(boolean order_type) {
        this.order_type = order_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDeliver_address() {
        return deliver_address;
    }

    public void setDeliver_address(String deliver_address) {
        this.deliver_address = deliver_address;
    }

    public String getId_guest() {
        return id_guest;
    }

    public void setId_guest(String id_guest) {
        this.id_guest = id_guest;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getBe_over() {
        return be_over;
    }

    public void setBe_over(boolean be_over) {
        this.be_over = be_over;
    }

    public boolean getBe_deliver() {
        return be_deliver;
    }

    public void setBe_deliver(boolean be_deliver) {
        this.be_deliver = be_deliver;
    }
}
