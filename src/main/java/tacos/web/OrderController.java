package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepo;
import tacos.data.UserRepository;

import javax.validation.Valid;

@Slf4j
//@Controller
//@RequestMapping("/orders")
//@SessionAttributes("order")
//@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {

//    private int pageSize=20;
//
//    public int getPageSize() {
//        return pageSize;
//    }
//
//    public void setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//    }
    private final OrderProps orderProps;
    private final OrderRepo orderRepository;
    private final UserRepository userRepository;

    public OrderController( OrderRepo orderRepository
            , UserRepository userRepository
            , OrderProps orderProps) {
        this.orderProps = orderProps;
        this.orderRepository = orderRepository;
        this.userRepository=userRepository;
    }

    @GetMapping("/current")
    public String orderForm(Model model){
        model.addAttribute("order", new Order());
        return "orderForm";
    }
    @PostMapping
    public String processOrder(@Valid Order order
            , Errors errors, SessionStatus  sessionStatus
                               //, Principal principal
            // , Authentication authentication
            , @AuthenticationPrincipal User user){
        //User user=userRepository.findByUsername(principal.getName());
       // User user=(User)authentication.getPrincipal();

//        Authentication authentication =
//                SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();

        System.out.println("---------------"+user);
        if(errors.hasErrors()){
            return "orderForm";
        }
        log.info("Order submitted: " + order);


        order.setUser(user);

        System.out.println("---------------"+order);
        orderRepository.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

//    @GetMapping
//    public String ordersForUser(@AuthenticationPrincipal User user
//            ,Model model){
//        model.addAttribute("orders"
//                ,orderRepository.findByUserOrderByPlaceAtDesc(user));
//        return "orderList";
//    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user
            ,Model model){
        System.out.println("pageSize: " + orderProps.getPageSize());
        Pageable pageable= PageRequest.of(0,orderProps.getPageSize());
        model.addAttribute("orders",
                orderRepository.findByUserOrderByPlaceAtDesc(user,pageable));
        return "orderList";
    }
}
