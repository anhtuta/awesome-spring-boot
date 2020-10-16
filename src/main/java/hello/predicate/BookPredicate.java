package hello.predicate;

import org.springframework.util.StringUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import hello.entity.QBook;

/**
 * This class is for searching purpose
 * @author Anhtu
 *
 */
public class BookPredicate {

    public static Predicate getPredicate(String searchText) {
        if(StringUtils.isEmpty(searchText)) {
            BooleanExpression result = Expressions.asBoolean(true).isTrue();
            return result;
        }
        
        QBook qBook = QBook.book;
        Predicate predicate = qBook.title.containsIgnoreCase(searchText)
                .or(qBook.author.containsIgnoreCase(searchText));
        
        try {
            int price = Integer.valueOf(searchText);
            predicate = ((BooleanExpression) predicate).or(qBook.price.eq(price));
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        return predicate;
    }
}
