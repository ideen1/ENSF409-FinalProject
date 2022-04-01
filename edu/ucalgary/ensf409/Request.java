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

    public static void addHamper(String clientName, int numAdultMales, int numAdultFemales, int numChildUnder8, int numChildOver8){
        Hamper addHamper = new Hamper(clientName, numAdultMales, numAdultFemales, numChildUnder8, numChildOver8);
        hampers.add(addHamper);
    }

    public static void genarateOrderForm(){

    }

    public static void createOrderFile(){

    }

    public static ArrayList<Hamper> getHampers(){
        return hampers;
    }
}