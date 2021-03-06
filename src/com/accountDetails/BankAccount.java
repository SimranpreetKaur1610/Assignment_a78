package com.accountDetails;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BankAccount {

    private String accountNo;

    public BankAccount(){
        this.accountNo = "";
    }

    public BankAccount(String accountNumber){
        this();
        this.accountNo = accountNumber;
    }

    public String getAccountNumber(){
        return this.accountNo;
    }

    public static BankAccount open(String name) throws IOException {
        String randomId = (int) (Math.random() * 10000)+"";
        while(true){
            if(!accountExists(randomId)){
                File f = new File(randomId+".txt");
                f.createNewFile();
                FileWriter writer = new FileWriter(f);
                writer.write(name+"-"+0);
                writer.close();

                return new BankAccount(randomId);
            }
            randomId = (int) (Math.random() * 10000)+"";
        }
    }

    public String[] getAccountDetails() throws FileNotFoundException {
        String[] details = {};
        if(accountExists(this.accountNo)){
            File file = new File(this.accountNo+".txt");
            Scanner read = new Scanner(file);
            String data = "";
            while (read.hasNextLine()) {
                data = read.nextLine().trim();
                if(!data.equals(""))
                    break;
            }
            details = data.split("-");
        }

        return details;
    }

    public void processTransaction(Transaction transaction) throws IOException {
        String[] details = getAccountDetails();
        String name = details[0];
        double balance = Double.parseDouble(details[1]);

        double updatedBalance = balance + transaction.getAmount();

        if(updatedBalance < 0){
            System.out.println("Balance is not sufficient !");
            return ;
        }

        File file = new File(this.accountNo+".txt");
        FileWriter writer = new FileWriter(file, false);
        writer.write(name+"-"+updatedBalance);
        writer.close();
    }

    public static boolean accountExists(String accountNumber){
        return new File(accountNumber + ".txt").exists();
    }

}

class Transaction{
    private double amount;

    public Transaction(double amount){
        this.amount = amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }
    public double getAmount(){
        return this.amount;
    }
}
