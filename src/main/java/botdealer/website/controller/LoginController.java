package botdealer.website.controller;

import botdealer.website.ldap.client.LdapClient;
import botdealer.website.service.LdapAdminService;
import botdealer.website.service.loginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LdapClient ldapClient;

    @Autowired
    private LdapAdminService ldapAdminService;

////    @RequestMapping(value = "/login", produces = "application/json", method=RequestMethod.POST)
//    @PostMapping("/login")
//    public String login(@RequestBody loginRequest loginRequest) {
//        ldapAdminService.verifyAccount(loginRequest.getUsername(), loginRequest.getPassword());
//        return "home";
//    }

    @PostMapping("/check_login")
    public String login (@RequestBody loginRequest loginRequest){ 
        ldapAdminService.verifyAccount(loginRequest.getUsername(),loginRequest.getPassword());
        return "Home";
    }


//    @PostMapping("/test")
//    public  String getTest() {
//        return "Ok";
//    }

}