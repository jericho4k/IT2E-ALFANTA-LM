package it2e.alfanta.lm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RECORDS {
    
    private config conf;

    public RECORDS() {
        conf = new config();
    }

    public void recordsMenu() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("\n------------------------");
            System.out.println("Welcome to Loan Records Panel");
            System.out.println("------------------------");
            System.out.println("1. Specific Loan Record");
            System.out.println("2. General Loan Report");
            System.out.println("3. Exit");

            System.out.print("Enter Selection: ");
            int choice = sc.nextInt();
            RECORDS lr = new RECORDS();

            switch (choice) {
                case 1:
                    lr.generateSpecificLoanRecord();
                    break;
                case 2:
                    lr.generateGeneralLoanReport();
                    break;
                case 3:
                    System.out.println("Exiting Loan Records Panel...");
                    return; // Exit the menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.print("Do you want to continue? (Yes/No): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("Yes"));
    }

    public void generateSpecificLoanRecord() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Loan ID: ");
        int loanId = sc.nextInt();

        // Check if the Loan ID exists
        while (conf.getSingleValue("SELECT Loan_ID FROM TBL_Loans WHERE Loan_ID = ?", loanId) == 0) {
            System.out.print("Loan ID doesn't exist, try again: ");
            loanId = sc.nextInt();
        }

        // SQL query to fetch loan details
        String specificQuery = "SELECT l.Loan_ID, a.Name, l.Loan_Amount, l.Interest_Rate, l.Loan_Status, l.Loan_Term, l.Loan_Type, l.Disbursal_Date " +
                               "FROM TBL_Loans l " +
                               "JOIN TBL_Applicants a ON l.Applicant_ID = a.Applicant_ID " +
                               "WHERE l.Loan_ID = ?";

        try (Connection conn = conf.connectDB();
             PreparedStatement findRow = conn.prepareStatement(specificQuery)) {
            findRow.setInt(1, loanId);

            try (ResultSet result = findRow.executeQuery()) {
                // Print the header
                System.out.println("\n----------------------------------------------------------------------------------------------------");
                System.out.printf("| %-10s | %-30s | %-15s | %-15s | %-10s | %-10s | %-10s | %-15s |\n",
                                  "Loan ID", "Applicant Name", "Loan Amount", "Interest Rate", "Status", "Term (months)", "Loan Type", "Disbursal Date");
                System.out.println("----------------------------------------------------------------------------------------------------");

                if (result.next()) {
                    int loanID = result.getInt("Loan_ID");
                    String applicantName = result.getString("Name");
                    double loanAmount = result.getDouble("Loan_Amount");
                    double interestRate = result.getDouble("Interest_Rate");
                    String loanStatus = result.getString("Loan_Status");
                    int loanTerm = result.getInt("Loan_Term");
                    String loanType = result.getString("Loan_Type");
                    String disbursalDate = result.getString("Disbursal_Date");

                    System.out.printf("| %-10d | %-30s | %-15.2f | %-15.2f | %-10s | %-10d | %-10s | %-15s |\n",
                                      loanID, applicantName, loanAmount, interestRate, loanStatus, loanTerm, loanType, disbursalDate);
                } else {
                    System.out.println("| No records found for the given Loan ID.");
                }
                System.out.println("----------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void generateGeneralLoanReport() {
        // SQL query to fetch all loans and their applicants
        String generalQuery = "SELECT l.Loan_ID, a.Name, l.Loan_Amount, l.Interest_Rate, l.Loan_Status, l.Loan_Term, l.Loan_Type, l.Disbursal_Date " +
                              "FROM TBL_Loans l " +
                              "JOIN TBL_Applicants a ON l.Applicant_ID = a.Applicant_ID";

        String[] generalHeaders = {"Loan ID", "Applicant Name", "Loan Amount", "Interest Rate", "Status", "Term (months)", "Loan Type", "Disbursal Date"};
        String[] generalColumns = {"Loan_ID", "Name", "Loan_Amount", "Interest_Rate", "Loan_Status", "Loan_Term", "Loan_Type", "Disbursal_Date"};

        conf.viewRecords(generalQuery, generalHeaders, generalColumns);
    }
}
