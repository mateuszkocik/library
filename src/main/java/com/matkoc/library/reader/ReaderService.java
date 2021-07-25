package com.matkoc.library.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderService{

    ReaderRepository readerRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository){
        this.readerRepository = readerRepository;
    }
}
