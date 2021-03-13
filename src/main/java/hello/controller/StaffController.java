package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hello.service.StaffService;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    
    @Autowired
    private StaffService starService;

}
