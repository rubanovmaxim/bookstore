package ru.bookstore.domain.enums;

public enum OrderStatus {
    BASKET,//состояние заказа - корзина
    ORDER,// оформлен заказ
    ORDER_REJECTED, // отказ от заказа
    ORDER_FINISHED; // заказ получен
}
