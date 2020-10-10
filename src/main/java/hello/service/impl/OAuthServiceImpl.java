package hello.service.impl;

import org.springframework.stereotype.Service;
import hello.common.Result;
import hello.service.OAuthService;

@Service
public class OAuthServiceImpl implements OAuthService {

    @Override
    public Result signin() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * This checking is just for demo!
     * Tất cả API có annotation RequireNonsense đều phải gửi kèm 1 header nonsense
     * là 1 string bắt đầu = 'tuzaku'
     */
    @Override
    public boolean checkNonsense(String nonsense) {
        return nonsense.startsWith("tuzaku");
    }

}
