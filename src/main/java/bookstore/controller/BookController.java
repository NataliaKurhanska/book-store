package bookstore.controller;

import bookstore.dto.book.BookDto;
import bookstore.dto.book.BookSearchParameters;
import bookstore.dto.book.CreateBookRequestDto;
import bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book", description = "Manage books")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books", description = "Get a list of all available books")
    public List<BookDto> getAll(@PageableDefault(size = 20, sort = {"id"}) Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Get book by id")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Get books by parameters",
            description = "Get a list of all available books by parameters")
    public List<BookDto> searchBooks(BookSearchParameters searchParameters,
                                     @PageableDefault(size = 20, sort = {"id"}) Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new book", description = "Create and save book to DB")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Update book by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto updateBook(@PathVariable Long id,
                              @RequestBody CreateBookRequestDto bookRequestDto) {
        return bookService.update(id, bookRequestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by id", description = "Delete book by its id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
