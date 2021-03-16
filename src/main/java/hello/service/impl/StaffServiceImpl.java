package hello.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import hello.common.ListRes;
import hello.common.Result;
import hello.common.StatusType;
import hello.entity.Staff;
import hello.predicate.StaffPredicate;
import hello.repository.StaffRepository;
import hello.service.StaffService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Result getStaffs(Pageable pageable, String searchText) {
        Result result = new Result();
        log.debug("StaffServiceImpl.getStaffs");

        try {
            Page<Staff> staffPage =
                    staffRepository.findAll(StaffPredicate.getPredicate(searchText), pageable);
            List<Staff> staffList = staffPage.getContent();
            long totalElements = staffPage.getTotalElements();
            int totalPages = (int) Math.ceil(totalElements * 1.0 / pageable.getPageSize());

            result.successRes(new ListRes<Staff>(staffList, totalElements, totalPages));
        } catch (Exception e) {
            log.error("Fail getAllStaffs: ", e);
            result.setStatus(StatusType.FAIL, e.getMessage());
        }
        return result;
    }

}
