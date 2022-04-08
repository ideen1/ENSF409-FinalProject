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
            throw new InvalidParameterException("Number of people must not be below 0");
        }

        Hamper addHamper = new Hamper(clientName, numAdultMales, numAdultFemales, numChildUnder8, numChildOver8);
        hampers.add(addHamper);
    }

/* Example Order Form
Example Food Bank
Hamper Order Form

Name:
Date:

Original Request
Hamper 1: 1 Adult Female, 2 Child under 8
Hamper 2: 1 Adult Male, 1 Child over 8

Hamper 1 Items:
34	Avocado, dozen
35	Avocado, dozen
54	Soy protein, 1 kg
55	Turkey, whole
56	Mixed nuts, 1 kg
105	Teff flour, 2 kg
142	Whey powder, large jar
143	Oranges, bag
145	Raisins, 40 g
147	Lettuce, 1 head
151	Peaches, crate


Hamper 2 Items:
51	Cantaloupe, dozen
57	Lentils, 1 kg
59	Tofu, 1 kg
70	Banana, bunch 6
86	Eggs, 1 kg
92	Yam, 1 kg
95	Lentils, 1 kg
97	Protein shake, 10 cans
98	Soy burger, 20
101	Beyond Breakfast sausage
103	Horse gram, 1 kg
104	Green peas, 1 pound
118	Frozen blueberries, 2100 g
124	Tinned sardines, pack of 5
134	Salmon, 5 filets
139	Pearl barley, 1 pound
146	Dates, container
152	Peaches, crate
169	Black fungus, 200 g
 */

    public  void genarateOrderForm(){
        
    }

    public void createOrderFile(){

    }

    public ArrayList<Hamper> getHampers(){
        return hampers;
    }
}