package edu.ucalgary.ensf409;

import java.time.LocalDate;

public class Request{
    private Hamper[] hampers;
    private LocalDate date;
    private String nameRequest;

    public Request(String nameRequest, LocalDate date){
        this.nameRequest = nameRequest;
        this.date = date;

    }

    public void addHamper(){
        
    }

    public void genarateOrderForm(){

    }

    public void createOrderFile(){

    }

    public Hamper[] getHampers(){
        return this.hampers;
    }
}