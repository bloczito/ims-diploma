package pl.wiktrans.ims.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wiktrans.ims.model.User;

@RestController
public class HomeController {


    @GetMapping("/login")
    public User getLoginPage() {

        User user = new User();
        user.setUsername("Asd");
        user.setEmail("qwe");
        user.setFirstName("rty");
        user.setLastName("fghf");

        return user;
    }

}
