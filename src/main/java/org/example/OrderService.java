package org.example;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class OrderService {

    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    String processOrder(Order order) throws NoSuchElementException {
        try{
            int id = orderRepository.saveOrder(order);
            if(id == order.getId()){
                return "Order processed successfully";
            }
            else return "Order processing failed";
        }
        catch (NoSuchElementException e){
            throw new NoSuchElementException();
        }

    }

    double calculateTotal(int id){
        Optional<Order> order = orderRepository.getOrderById(id);
        if (order.isPresent()){
            return order.get().getTotalPrice();
        }
        else return 0;
    }
}
