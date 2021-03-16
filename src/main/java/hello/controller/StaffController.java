package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import hello.common.ApiPageable;
import hello.common.Result;
import hello.service.StaffService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/staff")
@PreAuthorize("hasAuthority('STORE_MANAGER')")
public class StaffController {
    
    @Autowired
    private StaffService starService;

    @GetMapping
    @ApiPageable
    public Result getStaffs(@ApiIgnore Pageable pageable,
            @RequestParam(required = false) String searchText) {
        return starService.getStaffs(pageable, searchText);
    }

}
