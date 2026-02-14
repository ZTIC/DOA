package jewlry;

import java.util.List;

public interface Searchable<T> {
    List<T> search(T searchCriteria);
}
