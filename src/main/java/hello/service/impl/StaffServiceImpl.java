package hello.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import hello.common.ListRes;
import hello.common.Result;
import hello.common.StatusType;
import hello.entity.Staff;
import hello.entity.Store;
import hello.exception.RestException;
import hello.model.request.StaffRequest;
import hello.predicate.StaffPredicate;
import hello.repository.StaffRepository;
import hello.repository.StoreRepository;
import hello.service.StaffService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public Result getStaffs(Pageable pageable, String searchText) {
        log.debug("StaffServiceImpl.getStaffs");
        Result result = new Result();
        Sort sort;
        if (pageable.getSort().isUnsorted()) {
            sort = Sort.by(Direction.DESC, "id");
        } else {
            sort = pageable.getSort();
        }
        PageRequest pageRequest =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Staff> staffPage =
                staffRepository.findAll(StaffPredicate.getPredicate(searchText), pageRequest);
        List<Staff> staffList = staffPage.getContent();
        long totalElements = staffPage.getTotalElements();
        int totalPages = (int) Math.ceil(totalElements * 1.0 / pageable.getPageSize());

        result.successRes(new ListRes<Staff>(staffList, totalElements, totalPages));
        return result;
    }

    @Override
    public Result createStaff(StaffRequest staffRequest) {
        Optional<Store> storeOp = storeRepository.findById(staffRequest.getStoreId());
        if (!storeOp.isPresent()) {
            throw new RestException(StatusType.STORE_NOT_FOUND);
        }

        Staff newStaff = new Staff();
        BeanUtils.copyProperties(staffRequest, newStaff);
        newStaff.setStore(storeOp.get());
        staffRepository.save(newStaff);
        return new Result().successRes(null);
    }

    @Override
    public Result updateStaff(int id, StaffRequest staffRequest) {
        Optional<Staff> staffOp = staffRepository.findById(id);
        if (!staffOp.isPresent()) {
            throw new RestException(StatusType.STAFF_NOT_FOUND);
        }

        Optional<Store> storeOp = storeRepository.findById(staffRequest.getStoreId());
        if (!storeOp.isPresent()) {
            throw new RestException(StatusType.STORE_NOT_FOUND);
        }

        Staff updateStaff = staffOp.get();
        BeanUtils.copyProperties(staffRequest, updateStaff);
        updateStaff.setStore(storeOp.get());
        staffRepository.save(updateStaff);
        return new Result().successRes(null);
    }

    @Override
    public Result deleteStaff(int id) {
        Optional<Staff> staffOp = staffRepository.findById(id);
        if (!staffOp.isPresent()) {
            throw new RestException(StatusType.STAFF_NOT_FOUND);
        }

        staffRepository.delete(staffOp.get());
        return new Result().successRes(null);
    }

}
