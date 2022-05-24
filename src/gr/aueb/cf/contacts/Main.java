package gr.aueb.cf.contacts;

import gr.aueb.cf.contacts.controller.MobileContactController;
import gr.aueb.cf.contacts.model.MobileContact;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author L. Stavropoulos
 */
public class Main {

    private static final MobileContactController controller = new MobileContactController();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = 0;

        do {
            printMenu();
            choice = getInputChoice();
            manageChoice(choice);
        } while (choice != 5);
    }

    private static void manageChoice(int choice) {
        switch (choice) {
            case 1: insertHandler(); break;
            case 2: getContactAndPrintHandler(); break;
            case 3: updateHandler(); break;
            case 4: deleteHandler();break;
            case 5:
                System.out.println("QUIT!"); break;
            default:
                System.out.println("Invalid Choice!"); break;
        }
    }

    private static void printMenu() {
        System.out.println("Please select one of the following:");
        System.out.println("1. Insert mobile contact.");
        System.out.println("2. Search for and Print mobile contact");
        System.out.println("3. Replace mobile contact");
        System.out.println("4. Delete mobile contact");
        System.out.println("5. Quit");
    }

    private static int getInputChoice() {
        String strChoice;
        int choice;

        strChoice = sc.nextLine();
        if (isInt(strChoice)) {
            choice = Integer.parseInt(strChoice);
        } else {
            choice = -1;
        }

        return choice;
    }

    private static String getInputFirstname() {
        String firstname;
        printMessage(System.out, "Please insert firstname");
        firstname =  sc.nextLine();     // We could do some validation checks here!

        return firstname;
    }

    private static String getInputLastname() {
        String lastname;
        printMessage(System.out, "Please insert lastname");
        lastname =  sc.nextLine();     // We could do some validation checks here!

        return lastname;
    }

    private static String getInputPhoneNumber() {
        String phoneNumber;
        printMessage(System.out, "Please insert phone number");
        phoneNumber =  sc.nextLine();     // We could do some validation checks here!

        return phoneNumber;
    }

    private static void printContact(MobileContact contact) {
        printMessage(System.out, contact.convertToString());
    }

    public static void insertHandler() {
        String firstname;
        String lastname;
        String phoneNumber;

        firstname = getInputFirstname().trim();
        lastname =getInputLastname().trim();
        phoneNumber = getInputPhoneNumber().trim();

        if (controller.insertController(firstname, lastname, phoneNumber)) {
            printMessage(System.out, "Contact inserted successfully");
        } else {
            printMessage(System.out, "Contact already exists");
        }
    }

    public static void getContactAndPrintHandler() {
        String phoneNumber;
        MobileContact contact = new MobileContact();

        phoneNumber = getInputPhoneNumber().trim();

        contact = controller.getContactController(phoneNumber);

        if (contact == null) {
            printMessage(System.out, "Contact not found");
            return;
        }

        printContact(contact);
    }

    public static void updateHandler() {
        String firstname;
        String lastname;
        String phoneNumber;
        String inputPhoneNumber;
        MobileContact contact;

        inputPhoneNumber = getInputPhoneNumber().trim();
        contact = controller.getContactController(inputPhoneNumber);

        if (contact != null) {
            firstname = getInputFirstname().trim();
            lastname = getInputLastname().trim();
            phoneNumber = getInputPhoneNumber().trim();

            if (controller.updateController(inputPhoneNumber, firstname, lastname, phoneNumber)) {
                printMessage(System.out, "Contact updated");
            } else {
                printMessage(System.out, "Contact not found");
            }
        } else {
            printMessage(System.out, "Contact not found");
        }
    }

    public static void deleteHandler() {
        String phoneNumber;
        MobileContact contact;

        phoneNumber = getInputPhoneNumber().trim();
        contact = controller.getContactController(phoneNumber);

        if (contact != null) {
            if (controller.deleteController(phoneNumber)) {
                printMessage(System.out, "Contact deleted");
            } else {
                printMessage(System.out, "Contact not deleted");
            }
        } else {
            printMessage(System.out, "Contact not found");
        }
    }

    private static void printMessage(PrintStream ps, String message) {
        ps.println(message);
    }

    private static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
