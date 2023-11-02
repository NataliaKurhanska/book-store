package bookstore.repository.book;

import bookstore.model.Book;
import bookstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String PROVIDER_KEY = "isbn";
    private static final String FIELD_NAME = "isbn";

    @Override
    public String getKey() {
        return PROVIDER_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) ->
                root.get(FIELD_NAME).in(Arrays.stream(params).toArray()));
    }
}
