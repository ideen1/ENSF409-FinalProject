package edu.ucalgary.ensf409;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;


public class Request{

	private ArrayList<Hamper> hampers = new ArrayList<Hamper>();
    private LocalDate date;
    private String nameRequest;

    public Request(String nameRequest, LocalDate date){
        this.nameRequest = nameRequest;
        this.date = date;

    }

    public void addHamper(String clientName, int numAdultMales, int numAdultFemales, int numChildUnder8, int numChildOver8){
        if (numAdultFemales < 0 && numAdultMales < 0 && numChildOver8 < 0 && numChildUnder8 < 0){
            GUIViewController.genericError("Amount of people must be 0 or greater.");
        }

        Hamper addHamper = new Hamper(clientName, numAdultMales, numAdultFemales, numChildUnder8, numChildOver8);
        hampers.add(addHamper);
    }

    public  void genarateOrderForm(){

    }

    public void createOrderFile(){

    }

    public ArrayList<Hamper> getHampers(){
        return hampers;
    }
}