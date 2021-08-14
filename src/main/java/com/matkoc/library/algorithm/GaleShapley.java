package com.matkoc.library.algorithm;

import com.matkoc.library.book.Book;
import com.matkoc.library.book.BookService;
import com.matkoc.library.reader.Reader;
import com.matkoc.library.reader.ReaderService;
import com.matkoc.library.rental.Rental;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GaleShapley {

  @Autowired
  ReaderService readerService;
  @Autowired
  BookService bookService;

  int size;
  List<Reader> readers;
  List<Book> books;
  Long[][] readersPref;
  Long[][] booksPref;

  public void initialize() {
    readers = readerService.getAllReaders();
    size = readers.size();
    books = bookService.getRandomAvailableBooks((long) size);
    booksPref = new Long[size][size];
    readersPref = new Long[size][size];
    setPreferencesForBooks();
    setPreferencesForReaders();
  }

  private void setPreferencesForReaders() {
    for(int i = 0; i < size; i++) {
      Reader reader = readers.get(i);
      List<Pair<Long, Integer>> sortedByScoreIdBooks = new ArrayList<>();
      for(long j = 0; j < size; j++) {
        Book bookToBeScored = books.get((int) j);
        sortedByScoreIdBooks.add(Pair.of(j, getBookScoreForReader(reader, bookToBeScored)));
      }
      sortedByScoreIdBooks.sort(Comparator.comparing(p -> -p.getRight()));
      for(int k = 0; k < size; k++) {
        readersPref[i][k] = sortedByScoreIdBooks.get(k).getKey();
      }
    }
  }

  private int getBookScoreForReader(Reader reader, Book book) {
    int score = 0;
    List<Book> rentedBooks = reader.getRentals().stream().map(Rental::getBook).collect(Collectors.toList());
    if(rentedBooks.contains(book))
      return score;
    if(onListIsBookWithGenre(rentedBooks, book.getBookDetails().getGenre()))
      score += 0.25;
    if(onListIsBookWithPublisher(rentedBooks, book.getBookDetails().getPublisher()))
      score += 0.5;
    //zrobic wydawnictwo i ew data

    return score;
  }

  private boolean onListIsBookWithGenre(List<Book> books, String genre) {
    for(int i = 0; i < books.size(); i++) {
      if(books.get(i).getBookDetails().getGenre().equals(genre))
        return true;
    }
    return false;
  }

  private boolean onListIsBookWithPublisher(List<Book> books, String publisher) {
    for(int i = 0; i < books.size(); i++) {
      if(books.get(i).getBookDetails().getPublisher().equals(publisher))
        return true;
    }
    return false;
  }

  private void setPreferencesForBooks() {
    List<Reader> sortedReadersByRentalsSize =
        readers.stream()
            .sorted(Comparator.comparingInt(Reader::getReaderRentalsSize).reversed())
            .collect(Collectors.toList());
    for (int i = 0; i < size; i++) {
      for(int j = 0; j < size; j++){
        booksPref[i][j] = getIndexOfReaderById(sortedReadersByRentalsSize.get(i).getId());
      }
    }
  }

  private Long getIndexOfReaderById(Long id) {
    for(long i = 0; i < size; i++) {
      if(readers.get((int) i).getId().equals(id))
        return i;
    }
    throw new IllegalArgumentException("Id of reader is not on list");
  }
}
