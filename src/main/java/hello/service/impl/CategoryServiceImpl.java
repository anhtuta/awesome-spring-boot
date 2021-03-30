package hello.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hello.common.Result;
import hello.common.StatusType;
import hello.entity.Category;
import hello.repository.CategoryRepository;
import hello.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Result getAllCategories() {
        Result result = new Result();
        List<Category> categoryList = categoryRepository.findAll();
        result.setData(categoryList);
        result.setStatus(StatusType.SUCCESS);
        return result;
    }

}
