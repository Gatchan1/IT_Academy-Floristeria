## Floristeria - Java & Spring framework bootcamp

This program is an exercise for IT Academy's Java&Spring framework bootcamp, consisting in
the modeling and integration of Java classes and MySQL database.

It features a model for a flowershop management allowing different actions such as:  
Create a new product, delete a product, update product stock, show stock for each product category, show product stock, total stock value, sale ticket creation, ticket listing, total income. 

Each product falls into a different category: tree, flower or decoration, each one with a differential attribute that, combined with it's name, singles it out.


Further development could include features such as:
- Sale discount (by flat amount or % based)
- Client info
- Order shipment
- Sale ticket modifying
- MongoDB compatibility.



### Compilation

- Set the URL for your MySQL database ("jdbc:mysql://localhost:3306/" by default) on ConnectionMysql.java.
- Set your user and password on the config.properties file (in the resources folder).
- Acces your Terminal, CMD or command-line interpreter.
- Use the 'cd' command for selecting the directory that contains the program files.  
I.e.:  C:\Users\user1>cd big_folder\program_folder
- Execute the 'javac' command, followed by the path for java classes, with the class that contains de main class as the last one.  
I.e.: C:\Users\user1\cd big_folder\program_folder>javac -cp ["classpath" class.java] Main.java

### Execution
- Get your MySQL database up and running.
- Execute the program using the 'java' command from your command-line interpreter.  
I.e.: C:\Users\user1\cd big_folder\program_folder>javac -cp Main.java



This project was made by Raquel, Sergio and David,

Thanks for reading!
