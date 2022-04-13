# ENSF409-FinalProject
This repository contains our ENSF 409 final project for the Winter 2022 term.

# Members:
| Name              | UCID     |             Email              |
| ----------------- |:--------:|:------------------------------:|
| Ideen Banijamali  | 30117190 | ideen.banijamali@ucalgary.ca   |
| Tannish Datta     | 30107335 | tanish.datta@ucalgary.ca       |
| Mary (Ga) Mo      | 10131867 | ga.mo@ucalgary.ca 		        | 

# Usage:
- Ensure the following files are placed in lib/ at the working directory level
    - hamcrest-core-1.3.jar
    - junit-4.13.2.jar
    - mysql-connector-java-8.0.23.jar
- Default Database Connection information:
    - Username: student
    - Password: ensf
    - Host: localhost
    - Database: food_inventory

# Warning:
- During order processing, this program can take 30-40 seconds per hamper depending on your machine.
- This is due to very lengthy and complex arithmatic (orders of ~2^20).
- Multiple loading screens will appear during this process. Please wait for the loading screens to disappear on their own before continuing
