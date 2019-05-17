package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private final OrderRepo orderRepository;
    private final UserRepository userRepository;

    public OrderController(OrderRepo orderRepository,UserRepository userRepository) {
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
}
