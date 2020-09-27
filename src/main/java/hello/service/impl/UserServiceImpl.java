package hello.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import hello.common.Result;
import hello.entity.User;
import hello.repository.UserRepository;
import hello.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Result getMe() {
        Result result = new Result();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                User user = userRepository.findByUsername(currentUserName);
                result.successRes(user);
            }
        } catch (Exception e) {
            result.failRes(e.getMessage());
        }

        return result;
    }

}
