package ru.bookstore.domain;


import javax.persistence.*;

@Entity
@Table(name = "ORDER_CONTENT")
public class OrderContent {

    @Id
    @GeneratedValue(generator = "order_content_id_seq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "BOOK_ID")
    private Long bookId;


    public OrderContent() {
    }

    public OrderContent(Long orderId,Long bookId) {
        this.orderId = orderId;
        this.bookId = bookId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}