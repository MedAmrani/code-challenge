package com.med.codechallenge.model;

import lombok.Data;
import lombok.Generated;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Data
public class Language  implements Comparable{
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);

    private int id ;
    private String name ;
    private int numberRepos;
    private List<String> repositories;

    public Language() {
        id = ID_GENERATOR.getAndIncrement();
        repositories = new ArrayList<>();
    }



    public void setRepository(String repository){
        repositories.add(repository);

    }


    @Override
    public int compareTo(Object o) {
        int compareNumber=((Language)o).getNumberRepos();
        /* For Ascending order*/
        return compareNumber-this.numberRepos;

    }

}
