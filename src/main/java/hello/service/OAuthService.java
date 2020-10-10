package hello.service;

import hello.common.Result;

public interface OAuthService {

    public Result signin();

    public boolean checkNonsense(String nonsense);

}
