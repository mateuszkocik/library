package com.matkoc.library.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderService{

    @Autowired
    ReaderRepository readerRepository;
}
