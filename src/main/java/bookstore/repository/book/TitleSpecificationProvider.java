package bookstore.repository.book;

import bookstore.model.Book;
import bookstore.repository.SpecificationProvider;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    private static final String PROVIDER_KEY = "title";
    private static final String FIELD_NAME = "title";

    @Override
    public String getKey() {
        return PROVIDER_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.disjunction();
            for (String value : params) {
                predicate = criteriaBuilder.or(predicate,
                        criteriaBuilder.like(root.get(FIELD_NAME), "%" + value + "%"));
            }
            return predicate;
        });
    }
}
