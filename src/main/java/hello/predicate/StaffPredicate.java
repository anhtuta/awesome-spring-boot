package hello.predicate;

import org.springframework.util.StringUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import hello.entity.QStaff;

public class StaffPredicate {

    public static Predicate getPredicate(String searchText) {
        if (StringUtils.isEmpty(searchText)) {
            BooleanExpression result = Expressions.asBoolean(true).isTrue();
            return result;
        }

        QStaff qStaff = QStaff.staff;
        Predicate predicate = qStaff.firstName.containsIgnoreCase(searchText)
                .or(qStaff.lastName.containsIgnoreCase(searchText))
                .or(qStaff.email.containsIgnoreCase(searchText))
                .or(qStaff.store.name.containsIgnoreCase(searchText));

        return predicate;
    }
}
