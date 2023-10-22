package hello.service;

import hello.common.Result;
import hello.model.request.StaffRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface StaffService {

    Result getStaffs(Pageable pageable, String searchText);

    Result createStaff(@Valid StaffRequest staffRequest);

    Result updateStaff(int id, @Valid StaffRequest staffRequest);

    Result deleteStaff(int id);

}
