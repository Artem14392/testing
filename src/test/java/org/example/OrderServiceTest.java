package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderRepository orderRepositoryMock;
    private OrderService orderService;

    @BeforeEach
    void setUp(){
        orderRepositoryMock = mock(OrderRepository.class);
        orderService = new OrderService(orderRepositoryMock);
    }

    @Test
    void processOrderTest(){
        Order order = new Order(4, "milk", 4, 120);

        when(orderRepositoryMock.saveOrder(order)).thenReturn(4);
        String status = orderService.processOrder(order);
        Assertions.assertEquals("Order processed successfully", status);
        verify(orderRepositoryMock, times(1)).saveOrder(order);
    }

    @Test
    void processOrderTestFailure(){
        Order order = new Order(5, "book", 3, 340);

        when(orderRepositoryMock.saveOrder(order)).thenReturn(333);
        Assertions.assertEquals("Order processing failed", orderService.processOrder(order));
        verify(orderRepositoryMock, times(1)).saveOrder(order);
    }

    @Test
    void processOrderTestHandlingException(){
        Order order = new Order(5, "book", 3, 340);

        when(orderRepositoryMock.saveOrder(order)).thenThrow(new NoSuchElementException());
        Assertions.assertThrows(NoSuchElementException.class, () -> orderService.processOrder(order));
        verify(orderRepositoryMock, times(1)).saveOrder(order);
    }

    @Test
    void calculateOrderTest(){
        Order order = new Order(23, "milk", 4, 100);
        when(orderRepositoryMock.getOrderById(23)).thenReturn(Optional.of(order));
        double res = orderService.calculateTotal(23);
        Assertions.assertEquals(400, res);
        verify(orderRepositoryMock, times(1)).getOrderById(23);

    }
    @Test
    void calculateOrderTest0(){
        Order order = new Order(23, "milk", 0, 0);
        when(orderRepositoryMock.getOrderById(23)).thenReturn(Optional.of(order));
        double res = orderService.calculateTotal(23);
        Assertions.assertEquals(0, res);
        verify(orderRepositoryMock, times(1)).getOrderById(23);

    }

    @Test
    void calculateOrderTest_Failure(){
        Assertions.assertEquals(0, orderService.calculateTotal(34));
        verify(orderRepositoryMock).getOrderById(34);
    }
}
