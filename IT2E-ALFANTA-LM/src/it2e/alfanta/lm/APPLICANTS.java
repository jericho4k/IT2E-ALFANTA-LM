package it2e.alfanta.lm;

import java.util.Scanner;

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
        System.out.print("Contact Info (Email/Phone): ");
        String contactInfo = sc.next();
        System.out.print("Address: ");
        String address = sc.next();
        System.out.print("Date of Application (YYYY-MM-DD): ");
        String dateOfApplication = sc.next();
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String dateOfBirth = sc.next();
        System.out.print("Occupation: ");
        String occupation = sc.next();
        System.out.print("Income: ");
        double income = sc.nextDouble();
        

        String qry = "INSERT INTO TBL_Applicants (Name, Contact_Info, Address, Date_of_Application, Date_of_Birth, Occupation, Income) VALUES (?, ?, ?, ?, ?, ?, ?)";
        config conf = new config();

        conf.addRecord(qry, name, contactInfo, address, dateOfApplication, dateOfBirth, occupation, income);
        System.out.println("Applicant added successfully.");
    }

    public void viewApplicants() {
        String applicantQuery = "SELECT * FROM TBL_Applicants";
        String[] applicantHeaders = {"ID", "Name", "Contact Info", "Address", "Date of Application", "Date of Birth", "Occupation", "Income"};
        String[] applicantColumns = {"Applicant_ID", "Name", "Contact_Info", "Address", "Date_of_Application", "Date_of_Birth", "Occupation", "Income"};
        config conf = new config();
        conf.viewRecords(applicantQuery, applicantHeaders, applicantColumns);
    }

    public void updateApplicant() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT Applicant_ID FROM TBL_Applicants WHERE Applicant_ID=?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.print("Select Applicant ID again: ");
            id = sc.nextInt();
        }

        System.out.print("New Applicant Name: ");
        String name = sc.next();
        System.out.print("New Contact Info (Email/Phone): ");
        String contactInfo = sc.next();
        System.out.print("New Address: ");
        String address = sc.next();
        System.out.print("New Date of Application (YYYY-MM-DD): ");
        String dateOfApplication = sc.next();
        System.out.print("New Date of Birth (YYYY-MM-DD): ");
        String dateOfBirth = sc.next();
        System.out.print("New Occupation: ");
        String occupation = sc.next();
        System.out.print("New Income: ");
        double income = sc.nextDouble();

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
