package com.matkoc.library.book;

import com.matkoc.library.bookdetails.BookDetails;
import com.matkoc.library.model.BaseEntity;
import com.matkoc.library.reservation.Reservation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bd_id")
  private BookDetails bookDetails;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private BookStatus bookStatus;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "book")
  private Reservation reservation;

  public boolean isAvailable(){
    return this.getBookStatus() == BookStatus.AVAILABLE;
  }
  public boolean isReserved() {return this.getBookStatus() == BookStatus.RESERVED;};
}
