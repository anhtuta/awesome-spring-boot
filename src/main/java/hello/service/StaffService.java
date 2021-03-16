package hello.service;

import org.springframework.data.domain.Pageable;
import hello.common.Result;

public interface StaffService {

    Result getStaffs(Pageable pageable, String searchText);

}
