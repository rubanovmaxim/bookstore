package ru.bookstore.domain;


import javax.persistence.*;

@Entity
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;


    @Column(name = "STATUIS")
    private String statuis;


    public Order() {
    }

    public Order(Long userId, String statuis) {
        this.userId = userId;
        this.statuis = statuis;
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

    public String getStatuis() {
        return statuis;
    }

    public void setStatuis(String statuis) {
        this.statuis = statuis;
    }
}
