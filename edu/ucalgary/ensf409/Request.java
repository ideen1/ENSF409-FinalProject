package edu.ucalgary.ensf409;

import java.time.LocalDate;
import java.util.ArrayList;

public class Request{

	private static ArrayList<Hamper> hampers = new ArrayList<Hamper>();
    private LocalDate date;
    private String nameRequest;

    public Request(String nameRequest, LocalDate date){
        this.nameRequest = nameRequest;
        this.date = date;

    }

    public static void addHamper(){
    }

    public static void genarateOrderForm(){

    }

    public static void createOrderFile(){

    }

    public static ArrayList<Hamper> getHampers(){
        return hampers;
    }
}