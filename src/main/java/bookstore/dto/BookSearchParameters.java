package bookstore.dto;

public record BookSearchParameters(String[] titles, String[] authors,
                                   String[] isbn, String[] prices) {
}
