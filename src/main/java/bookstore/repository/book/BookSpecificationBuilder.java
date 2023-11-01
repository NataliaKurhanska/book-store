package bookstore.repository.book;

import bookstore.dto.BookSearchParameters;
import bookstore.model.Book;
import bookstore.repository.SpecificationBuilder;
import bookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> providerManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> sp = Specification.where(null);
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            sp = sp.and(providerManager.getSpecificationProvider("title")
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            sp = sp.and(providerManager.getSpecificationProvider("author")
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.isbn() != null && searchParameters.isbn().length > 0) {
            sp = sp.and(providerManager.getSpecificationProvider("isbn")
                    .getSpecification(searchParameters.isbn()));
        }
        if (searchParameters.prices() != null && searchParameters.prices().length > 0) {
            sp = sp.and(providerManager.getSpecificationProvider("price")
                    .getSpecification(searchParameters.prices()));
        }
        return sp;
    }
}
