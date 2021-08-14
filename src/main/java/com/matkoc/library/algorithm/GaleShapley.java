package com.matkoc.library.algorithm;

import com.matkoc.library.book.Book;
import com.matkoc.library.book.BookService;
import com.matkoc.library.bookdetails.BookDetails;
import com.matkoc.library.reader.Reader;
import com.matkoc.library.reader.ReaderService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GaleShapley {

  @Autowired ReaderService readerService;
  @Autowired BookService bookService;

  int size;
  List<Reader> readers;
  List<Book> books;
  Long[][] readersPref;
  Long[][] booksPref;
  HashMap<Reader, Book> matches;

  public void initialize() {
    readers = readerService.getAllReaders();
    size = readers.size();
    books = bookService.getRandomAvailableBooks((long) size);
    booksPref = new Long[size][size];
    readersPref = new Long[size][size];
    setPreferencesForBooks();
    setPreferencesForReaders();
    matches = new HashMap<>();
  }

  public void runAlgorithm() {
    initialize();
    fillMatchesFromIndexes(findMatchesIndexes());
  }

  private void fillMatchesFromIndexes(HashMap<Integer, Integer> indexes) {
    indexes.forEach((k, v) -> matches.put(readers.get(v), books.get(k)));
  }

  public HashMap<Integer, Integer> findMatchesIndexes() {
    HashMap<Integer, Integer> matchesIndexes = new HashMap<>();
    for (int i = 0; i < size; i++) {
      matchesIndexes.put(i, null);
    }
    Set<Integer> unmatchedReaders = new HashSet<>();
    for (int i = 0; i < size; i++) {
      unmatchedReaders.add(i);
    }

    int unmatchedReadersCount = unmatchedReaders.size();
    while (unmatchedReadersCount > 0) {
      int unmatchedReader = unmatchedReaders.iterator().next();
      System.out.println("\nReader " + unmatchedReader + " is looking for a book now-");
      for (int b = 0; b < readersPref[unmatchedReader].length; b++) {
        if (matchesIndexes.get(b) == null) {
          matchesIndexes.put(b, unmatchedReader);
          System.out.println("Book " + b + " has ACCEPTED the reader: " + unmatchedReader);
          unmatchedReaders.remove(unmatchedReader);
          break;
        } else {
          int alreadyMatchedReader = matchesIndexes.get(b);
          if (willChangePartner(unmatchedReader, alreadyMatchedReader, b)) {
            matchesIndexes.put(b, unmatchedReader);
            unmatchedReaders.add(alreadyMatchedReader);
            unmatchedReaders.remove(unmatchedReader);
            System.out.println("Book " + b + " has DUMPED the reader: " + alreadyMatchedReader);
            System.out.println("Book " + b + " has ACCEPTED the reader: " + unmatchedReader);
            break;
          }
        }
      }
      unmatchedReadersCount = unmatchedReaders.size();
    }
    return matchesIndexes;
  }

  boolean willChangePartner(int unmatchedReader, int alreadyMatchedReader, int currentBook) {
    int pref_unmatchedReader = -1;
    int pref_alreadyMatchedReader = -1;
    for (int i = 0; i < booksPref[currentBook].length; i++) {
      if (booksPref[currentBook][i] == unmatchedReader) pref_unmatchedReader = i;
      if (booksPref[currentBook][i] == alreadyMatchedReader) pref_alreadyMatchedReader = i;
    }
    if (pref_unmatchedReader < pref_alreadyMatchedReader) return true;
    return false;
  }

  private void setPreferencesForReaders() {
    for (int i = 0; i < size; i++) {
      Reader reader = readers.get(i);
      List<Pair<Long, Double>> sortedByScoreIdBooks = new ArrayList<>();
      for (long j = 0; j < size; j++) {
        Book bookToBeScored = books.get((int) j);
        sortedByScoreIdBooks.add(Pair.of(j, getBookScoreForReader(reader, bookToBeScored)));
      }
      sortedByScoreIdBooks.sort(Comparator.comparingDouble(p -> -p.getRight()));
      for (int k = 0; k < size; k++) {
        readersPref[i][k] = sortedByScoreIdBooks.get(k).getKey();
      }
    }
  }

  private double getBookScoreForReader(Reader reader, Book book) {
    double score = 0;
    BookDetails bookDetails = book.getBookDetails();
    Set<BookDetails> rentedBooksDetails =
        reader.getRentals().stream()
            .map(r -> r.getBook().getBookDetails())
            .collect(Collectors.toSet());

    if (rentedBooksDetails.contains(bookDetails)) return score;
    System.out.println("zzz");
    if (onSetIsBookWithGenre(rentedBooksDetails, bookDetails.getGenre())) score += 0.25;
    if (onSetIsBookWithPublisher(rentedBooksDetails, bookDetails.getPublisher())) score += 0.5;
    // zrobic wydawnictwo i ew data

    return score;
  }

  private boolean onSetIsBookWithGenre(Set<BookDetails> books, String genre) {
    for (BookDetails bd : books) {
      if (bd.getGenre().equals(genre)) return true;
    }
    return false;
  }

  private boolean onSetIsBookWithPublisher(Set<BookDetails> books, String publisher) {
    for (BookDetails bd : books) {
      if (bd.getPublisher().equals(publisher)) return true;
    }
    return false;
  }

  private void setPreferencesForBooks() {
    List<Reader> sortedReadersByRentalsSize =
        readers.stream()
            .sorted(Comparator.comparingInt(Reader::getReaderRentalsSize).reversed())
            .collect(Collectors.toList());
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        booksPref[i][j] = getIndexOfReaderById(sortedReadersByRentalsSize.get(j).getId());
      }
    }
  }

  private Long getIndexOfReaderById(Long id) {
    for (long i = 0; i < size; i++) {
      if (readers.get((int) i).getId().equals(id)) return i;
    }
    throw new IllegalArgumentException("Id of reader is not on list");
  }
}
