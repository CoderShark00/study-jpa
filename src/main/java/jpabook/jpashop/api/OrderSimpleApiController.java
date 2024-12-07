package jpabook.jpashop.api;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;


/**
 * xtoOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RequiredArgsConstructor
@RestController
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기화
            //이렇게 해도 쓰레기같음
            //엔티티 반환 하지마
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        return orderRepository.findAll(new OrderSearch()).stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
    }

    /**
     * fetch join을 사용한 v3 버전 이전 v2 버전에서 api 조회시 쿼리가 5개 나왔다면
     * v3에서는 쿼리가 1개만 나옴
     * 성능 최적화
     */
    @GetMapping("/api/v3/simple-orders") // 엔티티를 조회 변경 가능
    public List<SimpleOrderDto> ordersV3(){
        return orderRepository.findAllWithMemberDelivery().stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
    }

    @GetMapping("/api/v4/simple-orders") // DTO를 조회한거라 변경은 안됨.
    public List<OrderSimpleQueryDto> ordersV4(){
       return orderRepository.findOrderDtos();
    }

    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;


        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // Lazy 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //Lazy 초기화
        }
    }



}
