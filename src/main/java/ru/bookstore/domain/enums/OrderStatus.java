package ru.bookstore.domain.enums;

public enum OrderStatus {
    BASKET,//состояние заказа - корзина
    ORDER_FORMING,// перейти к оформлению
    ORDER_CONFIRMED,// заказ подтвержден
    ORDER_REJECTED, // отказ от заказа
    ORDER_FINISHED; // заказ получен
}
