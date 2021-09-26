package Server;

import org.apache.log4j.Logger;

import java.util.Scanner;


public class Response {

    public static void main(String[] args) {

        Scanner sc= new Scanner(System.in);
        System.out.print("Enter a string: ");
        String str= sc.nextLine();
        System.out.print("You have entered: "+str);
    }

}
