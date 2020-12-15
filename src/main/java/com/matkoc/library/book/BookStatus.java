package com.matkoc.library.book;

public enum BookStatus implements BookStatusOperations{

    AVAILABLE{
        public BookStatus changeStatus(){
            return null;
        }
    },

    UNAVAILABLE{
        public BookStatus changeStatus(){
            return null;
        }
    }

}
