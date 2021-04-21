package hello.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hello.common.Result;
import hello.common.StatusType;
import hello.entity.Store;
import hello.repository.StoreRepository;
import hello.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public Result getAllStores() {
        Result result = new Result();
        List<Store> storeList = storeRepository.findAll();
        result.setData(storeList);
        result.setStatus(StatusType.SUCCESS);
        return result;
    }

}
