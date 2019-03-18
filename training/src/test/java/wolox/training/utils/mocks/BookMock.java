package wolox.training.utils.mocks;

import org.apache.commons.lang3.RandomStringUtils;
import wolox.training.models.Book;

import java.util.Random;

public final class  BookMock {
    public static Book createBook() {
        Book book = new Book();
        Random rand = new Random();
        book.setTitle(RandomStringUtils.randomAlphabetic(10));
        book.setAuthor(RandomStringUtils.randomAlphabetic(10));
        book.setImage(RandomStringUtils.randomAlphabetic(10));
        book.setSubtitle(RandomStringUtils.randomAlphabetic(10));
        book.setPublisher(RandomStringUtils.randomAlphabetic(10));
        book.setPages(new Integer((int)(Math.random() * 3000 + 1)));
        book.setYear(RandomStringUtils.randomNumeric(5));
        book.setIsbn(RandomStringUtils.randomAlphabetic(10));
        book.setGenre(RandomStringUtils.randomAlphabetic(10));
        return  book;
    }
}
