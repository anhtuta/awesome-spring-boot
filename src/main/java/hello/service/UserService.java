package hello.service;

import org.springframework.stereotype.Service;
import hello.common.Result;

@Service
public interface UserService {

    public Result getMe();

}
