/*
Shahmeer Khan
Feb. 28, 2024
Purpose: Write a program to simulate the growth of different types of bacteria in a given time frame. In ideal conditions, generally speaking, bacteria doubles its population every 20 minutes. However, the conditions are not always ideal in our simulator, therefore, the bacteria will increase by a factor of 1.5 - 2.5 every 20 minutes. Your simulator should display the bacteria population every 20 minutes within the specified time frame. At the end of the simulation, the program prints a report calculating the growth rate for each bacteria type. 
ADVANCED REQUIREMENTS
*/

//importing java packages
import java.util.*;
import java.io.*;

public class ShahmeerKhanBacteria{
  public static void main(String[] args) {

    //creating scanner object
    Scanner input = new Scanner(System.in);

    //variables
    final String BACTERIAFILE = "BacteriaData.txt", REPORTFILE = "Report.txt";
    final boolean APPEND = true;
    final int EVERY20 = 20;
    final double MIN = 1.5;
    final int EVERYBAR = 500;
    final int MILLISECONDS = 2000;
    final int PERCENT = 100;
    boolean validInput = false, found = false;
    int timePeriod=0;
    String timePeriodString = "";
    double random1=0,random2=0,random3=0;
    int numberOfBarLoops1=0,numberOfBarLoops2=0,numberOfBarLoops3=0;
    int numberOfLoops=0,currentTime=0;
    double initialPopulation1=0,initialPopulation2=0,initialPopulation3=0,currentPopulation1=0,currentPopulation2=0,currentPopulation3=0;
    double growthRate1=0,growthRate2=0,growthRate3=0;
    String bacteria1="",bacteria2="",bacteria3="";
    String line="",restart="";
    String bar1="|",bar2="|",bar3="|";

    //welcome message
    System.out.println("WELCOME TO THE BACTERIA GROWTH SIMULATOR!\n\n");
    
    do{//do while to repeat simulation if user wants to restart
      try{
        //creating file reader object
        FileReader fr = new FileReader(BACTERIAFILE);
        BufferedReader br = new BufferedReader(fr);

        //creating file writer object
        FileWriter fw = new FileWriter(REPORTFILE,APPEND);
        PrintWriter pw = new PrintWriter(fw);
        
        do{//do while to error check the user's input for time period
          System.out.print("\nEnter the time period: ");
          try{
            timePeriod = input.nextInt();
            validInput = true;
            if(timePeriod<=0){
              System.out.println("Please enter a positive integer time period.");
            }
          }
          catch(InputMismatchException e){
            System.out.println("Please enter a positive integer time period.");
            input.next();//flushing scanner
          }
        }while(!validInput||timePeriod<=0);//end of error checking do while

        timePeriodString = "" + timePeriod; //converting time period input to a string so that the program can parse through the whole file without any errors (specifically making the population parsing error-proof)

        //reading first line
        line=br.readLine();
        
        while(line!=null && !found){
          if(line.equals(timePeriodString)){
            found=true;
            numberOfLoops = timePeriod/EVERY20;

            line=br.readLine();
            bacteria1 = line;
            line=br.readLine();
            initialPopulation1 = Integer.parseInt(line);
            
            line=br.readLine();
            bacteria2 = line;
            line = br.readLine();
            initialPopulation2 = Integer.parseInt(line);
            
            line=br.readLine();
            bacteria3=line;
            line=br.readLine();
            initialPopulation3 = Integer.parseInt(line);

            currentPopulation1 = initialPopulation1;
            currentPopulation2 = initialPopulation2;
            currentPopulation3 = initialPopulation3;

            //for loop to output data from file
            for(int i=0;i<=numberOfLoops;i++){

              System.out.println("\n---------------------------------------------------------------------------------\n\nTime: " + currentTime + " minutes");
              currentTime+=EVERY20;

              numberOfBarLoops1 = (int)currentPopulation1/EVERYBAR;
              numberOfBarLoops2 = (int)currentPopulation2/EVERYBAR;
              numberOfBarLoops3 = (int)currentPopulation3/EVERYBAR;

              //typecasting the current populations to type int so that they can be rounded down to a whole number (not rounded up!):
              
              //outputting bacteria 1 data

              System.out.println("\n\n" + bacteria1 + " - Current Population: "  + (int)(currentPopulation1)); 
              for(int j=0;j<numberOfBarLoops1;j++){
                bar1+="*";
              }
              System.out.println(bar1);
              bar1="|";

              //outputting bacteria 2 data

              System.out.println("\n\n" + bacteria2 + " - Current Population: "  + (int)(currentPopulation2));
              for(int j=0;j<numberOfBarLoops2;j++){
                bar2+="*";
              }
              System.out.println(bar2);
              bar2="|";

              //outputting bacteria 3 data
              
              System.out.println("\n\n" + bacteria3 + " - Current Population: "  + (int)(currentPopulation3));
              for(int j=0;j<numberOfBarLoops3;j++){
                bar3+="*";
              }
              System.out.println(bar3);
              bar3="|";

              //calculating the new populations of each bacteria:

              random1 = (double)(Math.random()) + MIN;
              random2 = (double)(Math.random()) + MIN;
              random3 = (double)(Math.random()) + MIN;

              currentPopulation1 *= random1;
              currentPopulation2 *= random2;
              currentPopulation3 *= random3;
              
              //pausing for 2 seconds
              try{
                Thread.sleep(MILLISECONDS);
              }
              catch(InterruptedException e){
                System.out.println("Multiple threads running at once.");
                restart="Quit";
              }
              
            }//end of for loop which was outputing data from file

            //dividing each current population by its corresponding random variable so that the growth rate is calculated accurately and the current population is not incremented an extra time
            currentPopulation1/=random1;
            currentPopulation2/=random2;
            currentPopulation3/=random3;


            //calculating growth rates which will be outputted to text file at end of the simulation
            
            growthRate1 = ((currentPopulation1-initialPopulation1)/(double)initialPopulation1)*PERCENT;
            growthRate2 = ((currentPopulation2-initialPopulation2)/(double)initialPopulation2)*PERCENT;
            growthRate3 = ((currentPopulation3-initialPopulation3)/(double)initialPopulation3)*PERCENT;

            //outputting the data to the text file
            pw.println("$-----$\nREPORT:\n$-----$");
            pw.println("Time Period: " + timePeriod + " Minutes\n-----------------------");
            pw.println("Growth Rate of " + bacteria1 + ": " + Math.round(growthRate1) + "%");
            pw.println("Growth Rate of " + bacteria2 + ": " + Math.round(growthRate2) + "%");
            pw.println("Growth Rate of " + bacteria3 + ": " + Math.round(growthRate3) + "%");
            pw.println("\n");
            pw.close(); //closing writer
            
          }//end of if statement if the time period is found in the file

          line = br.readLine(); //reading the next line if the time period the user entered is not found on the current line
          
        }//end of while loop which is running through the file

        if(!found){//if statement for if the user's inputted time period is not found in the file
          System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nThe time period you entered could not be found in the file");
        }
        
        //resetting reader and variables so that the simulation can restart from scratch
        br.close();
        validInput=false;
        found=false;
        currentTime=0;

        //asking the user if they would like to restart or end the simulation
        System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nIf you would like to end the simulation, enter \"Quit\". If you would like to restart, enter anything else: ");
        restart = input.next();
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      
      }//end of try

    //assigning "Quit" to restart variable so that the program can end when it comes across these exceptions:
      
      catch(IOException e){
        System.out.println("File could not be found.");
        restart="Quit";
      }
      catch(NumberFormatException e){
        System.out.println("Error converting string to integer/double value.");
        restart="Quit";
      }

    }while(!restart.equalsIgnoreCase("Quit")); //end of do-while to restart the simulation if they don't enter quit
    
  }//end of main
}//end of program