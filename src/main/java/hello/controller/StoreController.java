package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hello.common.Result;
import hello.service.StoreService;

@RestController
@RequestMapping("/api/store")
@PreAuthorize("hasAuthority('STORE_MANAGER')")
public class StoreController {
    
    @Autowired
    private StoreService storeService;

    @GetMapping(value = "/all")
    public Result getAllStores() {
        return storeService.getAllStores();
    }
}
