package com.example.productstoreapp.transaction.order;

import com.example.productstoreapp.exception.PaymentException;
import com.example.productstoreapp.product.Product;
import com.example.productstoreapp.product.ProductDto;
import com.example.productstoreapp.product.ProductRepository;
import com.example.productstoreapp.transaction.payment.Payment;
import com.example.productstoreapp.transaction.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;


@Service
public class OrderServiceImpl implements OrderService{

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    public OrderServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        Order order = orderRequest.getOrder();
        order.setStatus("IN PROGRESS");
        String orderTrackingNumber = UUID.randomUUID().toString();
        order.setOrderTrackingNumber(orderTrackingNumber);
        order.setProducts(new HashSet<>(productRepository.findAllById(orderRequest.getProductIds())));
        orderRepository.save(order);
        Order foundOrder = orderRepository.findOrderByOrderTrackingNumber(order.getOrderTrackingNumber());
        for(Product product: order.getProducts()){
            product.setOrder(foundOrder);
            productRepository.save(product);
        }

        Payment payment = orderRequest.getPayment();

        if(!payment.getType().equals("DEBIT")){
            throw new PaymentException("Payment card type is not supported");
        }

        payment.setOrderId(order.getId());
        paymentRepository.save(payment);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderTrackingNumber(order.getOrderTrackingNumber());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setMessage("SUCCESS");
        return orderResponse;
    }


    @Override
    public List<OrderDetailsResponse> checkAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDetailsResponse> orderDetailsList = new ArrayList<>();

        for (Order order : orders) {
            OrderDetailsResponse orderDetails = new OrderDetailsResponse();
            orderDetails.setOrderId(order.getId());
            orderDetails.setOrderTrackingNumber(order.getOrderTrackingNumber());
            orderDetails.setTotalQuantity(order.getTotalQuantity());
            orderDetails.setTotalPrice(order.getTotalPrice());
            orderDetails.setStatus(order.getStatus());

            List<Product> products = productRepository.findByOrder(order);
            List<ProductDto> productDTOs = new ArrayList<>();

            for (Product product : products) {
                ProductDto productDTO = mapProductToDTO(product);
                productDTO.setOrderId(order.getId());
                productDTOs.add(productDTO);
            }

            orderDetails.setProducts(productDTOs);

            orderDetailsList.add(orderDetails);
        }

        return orderDetailsList;
    }

    private ProductDto mapProductToDTO(Product product) {
        ProductDto productDTO = new ProductDto();
        productDTO.setId(product.getId());
        productDTO.setSku(product.getSku());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setActive(product.isActive());
        productDTO.setImageUrl(product.getImageUrl());
       // productDTO.setOrder(product.getOrder());
        return productDTO;
    }

}