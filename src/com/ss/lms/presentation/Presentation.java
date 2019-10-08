package com.ss.lms.presentation;

import java.util.Scanner;

import com.ss.lms.service.ServiceAdmin;
import com.ss.lms.service.ServiceBorrower;
import com.ss.lms.service.ServiceLibrarian;

public abstract class Presentation 
{
	protected Scanner scanner;
	protected ServiceAdmin administrator;
	protected ServiceLibrarian librarian;
	protected ServiceBorrower userBorrower;
	
	public Presentation(ServiceAdmin administrator)
	{
		this.scanner = new Scanner(System.in);
		this.administrator = administrator;
		this.menu();
		this.administrator.closeConnection();
	}
	
	public Presentation(ServiceLibrarian librarian)
	{
		this.scanner = new Scanner(System.in);
		this.librarian = librarian;
		this.menu();
		this.librarian.closeConnection();
	}
	
	public Presentation(ServiceBorrower borrower)
	{
		this.scanner = new Scanner(System.in);
		this.userBorrower = borrower;
		this.menu();
		this.userBorrower.closeConnection();
	}
	
	public abstract void menu();
	
	// Forces the user to input a String. replaces "N/A" with "%"
	public String getStringFieldFromUser(String fieldName) 
	{
		System.out.println("Insert data for " + fieldName + ". Enter \"quit\" to go back to operation screen");
		
		do
		{
			return getNextLine().replaceAll("N/A", "%").trim();
		}
		while(this.scanner.hasNextLine());
	}
	
	// Forces the user to input an integer, "N/A" maps to -1, "quit" maps to Integer.MIN_VALUE
	public Integer getIntegerFieldFromUser(String fieldName) 
    {
        System.out.println("Insert data for " + fieldName + ". Enter \"quit\" to go back to operation screen");
        String line;
        Integer output;
        
        while(true) 
        {
            line = getNextLine().trim();
            
            try 
            {
                output = Integer.parseInt(line);
                //System.out.println("Found: " + output);
                return output;
            }
            catch(Exception e) 
            {
                if("quit".equals(line)) 
                {
                    return Integer.MIN_VALUE;
                }
                
                if("N/A".equals(line)) 
                {
                    return -1;
                }
                
                System.out.println("Input not recognized.");
                continue;
            }
        }
    }
	
	/*
	 * This function returns the next line while skipping over the next line feed, return carriage, etc
	 * */
	public String getNextLine() 
	{
		// regex pattern thanks to: https://archie94.github.io/blogs/skip-newline-while-reading-from-scanner-class
		// this tells scanner to skip past the next new line for all operating systems
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
		return scanner.nextLine();
	}
}
