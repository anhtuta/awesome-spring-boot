package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hello.service.StoreService;

@RestController
@RequestMapping("/api/store")
public class StoreController {
    
    @Autowired
    private StoreService storeService;

}
