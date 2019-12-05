package ru.bookstore.domain;


import javax.persistence.*;

@Entity
@Table(name="basket")
public class Basket {

    @Id
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "BOOK_ID")
    private Long bookId;


    public Basket() {
    }


    public Basket(Long orderId, Long bookId) {
        this.orderId = orderId;
        this.bookId = bookId;
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
