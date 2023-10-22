package hello.controller;

import hello.common.ApiPageable;
import hello.common.Result;
import hello.common.StatusType;
import hello.exception.RestException;
import hello.model.request.StaffRequest;
import hello.service.StaffService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
@PreAuthorize("hasAuthority('STORE_MANAGER')")
@Slf4j
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping
    @ApiPageable
    public Result getStaffs(@ApiIgnore Pageable pageable,
            @RequestParam(required = false) String searchText) {
        return staffService.getStaffs(pageable, searchText);
    }

    @PostMapping
    public Result createStaff(@Valid @RequestBody StaffRequest staffRequest,
            BindingResult bindingResult) {
        List<String> errorList = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            log.error(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            errorList.add(fieldError.getDefaultMessage());
        });
        if (errorList.size() != 0) {
            throw new RestException(StatusType.BAD_REQUEST, String.join(", ", errorList));
        }
        return staffService.createStaff(staffRequest);
    }

    @PutMapping("/{id}")
    public Result updateStaff(@PathVariable("id") int id,
            @Valid @RequestBody StaffRequest staffRequest, BindingResult bindingResult) {
        bindingResult.getFieldErrors().forEach(fieldError -> {
            log.error(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            throw new RestException(StatusType.BAD_REQUEST, fieldError.getDefaultMessage());
        });
        return staffService.updateStaff(id, staffRequest);
    }

    @DeleteMapping("/{id}")
    public Result updateStaff(@PathVariable("id") int id) {
        return staffService.deleteStaff(id);
    }
}
