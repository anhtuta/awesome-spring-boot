package hello.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hello.entity.Role;
import hello.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        hello.entity.User user = userRepository.findByUsername(name);
        if (user == null) {
            System.err.println("Không tồn tại user nào!");
            throw new UsernameNotFoundException("User " + name + " was not found in the DB");
        }

        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roles != null && roles.size() != 0) {
            for (Role role : roles) {
                grantList.add(new SimpleGrantedAuthority(role.getName()));
            }
        } else {
            System.err.println("This user doesn't have any role!");
            throw new UsernameNotFoundException("User " + name + " doesn't have any role");
        }

        return new User(user.getUsername(), user.getPassword(), grantList);
    }
}
