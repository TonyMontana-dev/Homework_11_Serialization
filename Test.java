/* This program recreates the given program made on the youtube video with the given requirements implemented: https://www.youtube.com/watch?v=vcV0DK45P-Q
 * This program creates the object Person and using a menu with while and if statements loops it retrieves the necessary information to process input and output streams of serialization.
 * The user gets to choose the name of file along with extension, the information inserted and has completed flexibility of the Person object.
 * 
 * Name: Andrea Marcelli
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

class Person implements Serializable {
	/**
	 *  Creating the class for the object
	 */
	private static final long serialVersionUID = -101600639687592027L;
	String name;
	String phoneNumber;
	String dateOfBirth;
	String emailAddress;
	
	// Class constructor
	public Person(String name, String phoneNumber, String dateOfBirth, String emailAddress) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.emailAddress = emailAddress;
	}
	
	@Override
	public String toString() {
		return "\n\n--- Person information ---"
				+ "\n[name = " + name 
				+ "\nPhone number = " + phoneNumber
				+ "\nDate of birth = " + dateOfBirth
				+ "\nEmail address = " + emailAddress
				+ "]";
	}
}

/*
 *  1. Add information into a file
 *  2. Retrieve information from a file and display them
 *  3. Delete information.
 *  4. Update information
 *  5. Exit
 */

public class Test {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in); // Integers
		Scanner input2 = new Scanner(System.in); // Strings
		
		// Create array of Person objects
		ArrayList<Person> peopleList = new ArrayList<>();
		
		int choice = 1; // Set default value for the while loop
		while (choice != 0) {
			// Creating the menu with the required options
			System.out.println("\n--- PERSON FILE MENU ---\n\nSelect the action to perform:"
					+ "\n 1. Write - Add information into a file"
					+ "\n 2. Read - Retrieve information from a file and display them"
					+ "\n 3. Delete information"
					+ "\n 4. Update information"
					+ "\n 0. Exit"
					+ "\n\nEnter choice: ");
			
			// Save the user's selection in a variable
			choice = input.nextInt();
			
			// Add information into a file
			if (choice == 1) 
			{
				// Retrieve required information to create the object 
				System.out.println("\nPlease enter the name with the file format extension to save the information (example: Person.bin)");
				String fileName = input2.nextLine();
				System.out.println("\nEnter the name of the person to insert: "); // Ask for the name
				String name = input2.nextLine(); // Save it in the variable
				System.out.println("Enter phone number of " + name + ": ");
				String phoneNumber = input2.nextLine();
				System.out.println("Date of birth of " + name + "(mm/dd/yyyy): ");
				String dateOfBirth = input2.nextLine();
				System.out.println("Email address of " + name + "(name@example.com): ");
				String email = input2.nextLine();
				
				Person personName = new Person(name, phoneNumber, dateOfBirth, email); // Create object with all saved information
				
				peopleList.add(personName); // Add it to the arrayList
				
				// Try/Catch the existence of the file in order to write the new information inside
				try {
					try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
						objectOutputStream.writeObject(peopleList);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // End of IF 1
			
			// Retrieve information from a file and display them
			else if (choice == 2) {
				// Ask the user which file wants to display
				System.out.println("\nPlease enter the name of the file to read(example: Person.bin): ");
				String fileName;
				fileName = input2.nextLine();
				
				// Read using stream of serialization
				try {
					try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
						peopleList = (ArrayList<Person>) objectInputStream.readObject();
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				System.out.println(peopleList);
			} // End of IF 2
			
			// Delete information
			else if (choice == 3) {
				// Ask the user which file wants to edit
				System.out.println("\nPlease enter the name of the file to read(example: Person.bin): ");
				String fileName;
				fileName = input2.nextLine();
				System.out.println("\nEnter the name of the person to delete: ");
                String nameToDelete = input2.nextLine();
                boolean found = false;
                int indexToDelete = 0;

                for (int i = 0; i < peopleList.size(); i++) {
                    if (peopleList.get(i).name.equalsIgnoreCase(nameToDelete)) {
                        found = true;
                        indexToDelete = i;
                        break;
                    }
                }
                
                if (found) {
                	peopleList.remove(indexToDelete);
                    try {
                    	FileOutputStream fos = new FileOutputStream(fileName);
                    	ObjectOutputStream oos = new ObjectOutputStream(fos);
                    	oos.writeObject(peopleList);
                    	oos.close();
                    	fos.close();
                    	System.out.println("\nOPERATION SUCCESFULL | Information deleted successfully");
                    } catch (IOException e) {
                    	System.out.println(e.getMessage());
                    }
                 }
			} // End of IF 3
			
			// Update information
			else if (choice == 4) {
				// Ask the user which file wants to update
				System.out.println("\nPlease enter the name of the file to read(example: Person.bin): ");
				String fileName;
				fileName = input2.nextLine();
			    System.out.println("\nEnter the name of the person to update: ");
			    String name = input2.nextLine();

			    // Find the person with the given name
			    int index = -1;
			    for (int i = 0; i < peopleList.size(); i++) {
			        Person person = (Person) peopleList.get(i);
			        if (person.name.equalsIgnoreCase(name)) {
			            index = i;
			            break;
			        }
			    }

			    // If the person is found, ask for updated information
			    if (index != -1) {
			    	// Update the information of the selected person
			        System.out.println("\nEnter new phone number for " + name + ": ");
			        String phoneNumber = input2.nextLine();
			        System.out.println("\nEnter new date of birth for " + name + " (mm/dd/yyyy): ");
			        String dateOfBirth = input2.nextLine();
			        System.out.println("\nEnter new email address for " + name + " (email@example.com): ");
			        String email = input2.nextLine();

			        // Update the person's information and save to file
			        Person person = (Person) peopleList.get(index);
			        person.phoneNumber = phoneNumber;
			        person.dateOfBirth = dateOfBirth;
			        person.emailAddress = email;
			        
			        // Try/Catch the file to update the new information
			        try {
			            FileOutputStream fileOut = new FileOutputStream(fileName);
			            ObjectOutputStream out = new ObjectOutputStream(fileOut);
			            out.writeObject(peopleList);
			            out.close();
			            fileOut.close();
			            System.out.println("\nOPERATION SUCCESFULL | [Person information updated]");
			        } catch (IOException i) {
			            System.out.println("\nError updating information: " + i.getMessage());
			        }
			    } else {
			    	// If the object does not exist, return the error message
			        System.out.println("\nApologies, we cannot finalize the operation. Error message: [Person not found.]");
			    }
			} // End of IF 4
			
			// Exit the program with any other value
			else {
				System.out.println("\nThank you for using the program. Goodbye!");
			}
		} // End of while loop
		
		input.close();
		input2.close();
	} // End of main class
}
