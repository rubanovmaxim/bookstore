package ru.bookstore.domain;


import javax.persistence.*;

@Entity
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(generator = "order_id_seq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "STATUS")
    private String status;

    public Order() {
    }

    public Order(Long userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
