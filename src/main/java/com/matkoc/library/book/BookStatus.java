package com.matkoc.library.book;

public enum BookStatus {
  AVAILABLE,
  BORROWED,
  RESERVED;

  public boolean isAvailable(BookStatus bs) {
    return bs == AVAILABLE;
  }
}
