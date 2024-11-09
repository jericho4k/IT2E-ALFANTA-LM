package it2e.alfanta.lm;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class APPLICANTS {

    public void applicantsMenu() {
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("\n------------------------");
            System.out.println("Welcome to Applicants Panel");
            System.out.println("------------------------");
            System.out.println("1. Add Applicant");
            System.out.println("2. View Applicants");
            System.out.println("3. Update Applicant");
            System.out.println("4. Delete Applicant");
            System.out.println("5. Exit");

            System.out.print("Enter Selection: ");
            int choice = sc.nextInt();
            APPLICANTS ap = new APPLICANTS();

            switch (choice) {
                case 1:
                    ap.addApplicant();
                    ap.viewApplicants();
                    break;
                case 2:
                    ap.viewApplicants();
                    break;
                case 3:
                    ap.viewApplicants();
                    ap.updateApplicant();
                    ap.viewApplicants();
                    break;
                case 4:
                    ap.viewApplicants();
                    ap.deleteApplicant();
                    ap.viewApplicants();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.print("Do you want to continue? (Yes/No): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("Yes"));
    }

   public void addApplicant() {
    Scanner sc = new Scanner(System.in);

    System.out.print("Applicant Name: ");
    String name = sc.next();
    while (name.trim().isEmpty()) {
        System.out.println("Name cannot be empty. Please enter a valid name.");
        System.out.print("Applicant Name: ");
        name = sc.next();
    }

    System.out.print("Contact Info (Email/Phone): ");
    String contactInfo = sc.next();
    while (contactInfo.trim().isEmpty()) {
        System.out.println("Contact Info cannot be empty. Please enter valid contact information.");
        System.out.print("Contact Info (Email/Phone): ");
        contactInfo = sc.next();
    }

    System.out.print("Address: ");
    String address = sc.next();

    String dateOfApplication;
    while (true) {
        System.out.print("Date of Application (YYYY-MM-DD): ");
        dateOfApplication = sc.next();
        if (isValidDate(dateOfApplication)) {
            break;
        } else {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
        }
    }

    String dateOfBirth;
    while (true) {
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        dateOfBirth = sc.next();
        if (isValidDate(dateOfBirth)) {
            break;
        } else {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
        }
    }

    System.out.print("Occupation: ");
    String occupation = sc.next();

    System.out.print("Income: ");
    while (!sc.hasNextDouble()) {
        System.out.println("Invalid input. Please enter a numeric income value.");
        sc.next();
        System.out.print("Income: ");
    }
    double income = sc.nextDouble();
    while (income < 0) {
        System.out.println("Income cannot be negative. Please enter a valid income.");
        System.out.print("Income: ");
        income = sc.nextDouble();
    }

    String qry = "INSERT INTO TBL_Applicants (Name, Contact_Info, Address, Date_of_Application, Date_of_Birth, Occupation, Income) VALUES (?, ?, ?, ?, ?, ?, ?)";
    config conf = new config();

    conf.addRecord(qry, name, contactInfo, address, dateOfApplication, dateOfBirth, occupation, income);
    System.out.println("Applicant added successfully.");
}

private boolean isValidDate(String date) {
    try {
        LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return true;
    } catch (DateTimeParseException e) {
        return false;
    }
}

    public void viewApplicants() {
    String applicantQuery = "SELECT COUNT(*) FROM TBL_Applicants";
    config conf = new config();

    double recordCount = conf.getSingleValue(applicantQuery);

    if (recordCount == 0) {
        System.out.println("No applicants found.");
        return;
    }

    applicantQuery = "SELECT * FROM TBL_Applicants";
    String[] applicantHeaders = {"ID", "Name", "Contact Info", "Address", "Date of Application", "Date of Birth", "Occupation", "Income"};
    String[] applicantColumns = {"Applicant_ID", "Name", "Contact_Info", "Address", "Date_of_Application", "Date_of_Birth", "Occupation", "Income"};
    conf.viewRecords(applicantQuery, applicantHeaders, applicantColumns);
}
    public void updateApplicant() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    int id = 0;
    while (true) {
        System.out.print("Enter ID to update: ");
        if (sc.hasNextInt()) {
            id = sc.nextInt();
            if (conf.getSingleValue("SELECT Applicant_ID FROM TBL_Applicants WHERE Applicant_ID=?", id) != 0) {
                break;
            } else {
                System.out.println("ID doesn't exist! Please try again.");
            }
        } else {
            System.out.println("Invalid ID! Please enter a numeric value.");
            sc.next();
        }
    }

    String name;
    while (true) {
        System.out.print("New Applicant Name: ");
        name = sc.next();
        if (!name.isEmpty()) {
            break;
        } else {
            System.out.println("Name cannot be empty. Please enter a valid name.");
        }
    }

    String contactInfo;
    while (true) {
        System.out.print("New Contact Info (Email/Phone): ");
        contactInfo = sc.next();
        if (!contactInfo.isEmpty()) {
            break;
        } else {
            System.out.println("Contact Info cannot be empty. Please enter a valid contact info.");
        }
    }

    String address;
    while (true) {
        System.out.print("New Address: ");
        address = sc.next();
        if (!address.isEmpty()) {
            break;
        } else {
            System.out.println("Address cannot be empty. Please enter a valid address.");
        }
    }

    String dateOfApplication;
    while (true) {
        System.out.print("New Date of Application (YYYY-MM-DD): ");
        dateOfApplication = sc.next();
        if (dateOfApplication.matches("\\d{4}-\\d{2}-\\d{2}")) {
            break;
        } else {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
        }
    }

    String dateOfBirth;
    while (true) {
        System.out.print("New Date of Birth (YYYY-MM-DD): ");
        dateOfBirth = sc.next();
        if (dateOfBirth.matches("\\d{4}-\\d{2}-\\d{2}")) {
            break;
        } else {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
        }
    }

    String occupation;
    while (true) {
        System.out.print("New Occupation: ");
        occupation = sc.next();
        if (!occupation.isEmpty()) {
            break;
        } else {
            System.out.println("Occupation cannot be empty. Please enter a valid occupation.");
        }
    }

    double income = 0;
    while (true) {
        System.out.print("New Income: ");
        if (sc.hasNextDouble()) {
            income = sc.nextDouble();
            if (income > 0) {
                break;
            } else {
                System.out.println("Income must be a positive number. Please enter a valid income.");
            }
        } else {
            System.out.println("Invalid input! Please enter a valid number for income.");
            sc.next();
        }
    }
    String qry = "UPDATE TBL_Applicants SET Name = ?, Contact_Info = ?, Address = ?, Date_of_Application = ?, Date_of_Birth = ?, Occupation = ?, Income = ? WHERE Applicant_ID = ?";
    conf.updateRecord(qry, name, contactInfo, address, dateOfApplication, dateOfBirth, occupation, income, id);
    System.out.println("Applicant updated successfully.");
}

    public void deleteApplicant() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT Applicant_ID FROM TBL_Applicants WHERE Applicant_ID=?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.print("Select Applicant ID again: ");
            id = sc.nextInt();
        }

        String qry = "DELETE FROM TBL_Applicants WHERE Applicant_ID=?";
        conf.deleteRecord(qry, id);
        System.out.println("Applicant deleted successfully.");
    }
}
