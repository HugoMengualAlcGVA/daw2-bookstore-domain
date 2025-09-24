package es.cesguiro.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class Book {

    private String isbn;
    private String titleEs;
    private String titleEn;
    private String synopsisEs;
    private String synopsisEn;
    private BigDecimal basePrice;
    private double discountPercentage;
    private BigDecimal price;
    private String cover;
    private LocalDate publicationDate;
    private Publisher publisher;
    private List<Author> authors;

    public Book(
            String id,
            String isbn,
            String titleEs,
            String titleEn,
            String synopsisEs,
            String synopsisEn,
            BigDecimal basePrice,
            double discountPercentage,
            String cover,
            LocalDate publicationDate,
            Publisher publisher,
            List<Author> authors
    ) {
        if(isbn == null || isbn.isBlank()){
            //throw new ValidationException("Isbn is blank");
        }if(!isbn.matches("\\d{13}")){
            //throw new ValExceptionException("Isbn must be 13 digits");
        }
        this.isbn = isbn;
        this.titleEs = titleEs;
        this.titleEn = titleEn;
        this.synopsisEs = synopsisEs;
        this.synopsisEn = synopsisEn;
        this.basePrice = basePrice;
        this.discountPercentage = discountPercentage;
        this.price = calculateFinalPrice();
        this.cover = cover;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitleEs() {
        return titleEs;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public String getSynopsisEs() {
        return synopsisEs;
    }

    public String getSynopsisEn() {
        return synopsisEn;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCover() {
        return cover;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public BigDecimal calculateFinalPrice() {
        BigDecimal discount = basePrice
                .multiply(BigDecimal.valueOf(discountPercentage))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return basePrice.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

}
