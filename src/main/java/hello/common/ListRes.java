package hello.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ListRes<T> {

    private List<T> list;
    private long totalElements;
    private int totalPages;

}
