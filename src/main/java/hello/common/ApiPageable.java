package hello.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * ApiPagable to add params for the Pageable object. We use this annotation for swagger
 * because swagger adds params as the custom names rather than default name.
 * ex: swagger will use this: page -> pageNumber, size -> pageSize
 * 
 * @author Anhtu
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
    @ApiImplicitParam(name = "page", paramType = "query", defaultValue = "0"),
    @ApiImplicitParam(name = "size", paramType = "query", defaultValue = "10"),
    @ApiImplicitParam(name = "sort", paramType = "query", allowMultiple = true,
            value = "Sorting criteria in the format: property(,asc|desc). Default sort order is asc. Multiple sort criteria are supported")
})
public @interface ApiPageable {

}
