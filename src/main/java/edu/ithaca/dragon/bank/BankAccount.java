package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
        if (isAmountValid(startingBalance)){
            this.balance = startingBalance;
        }
        else{
            throw new IllegalArgumentException("Starting balance: " + startingBalance + "is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is greater than 0 and smaller than balance
     * @throws IllegalArgumentException if amount is negative or 0
     * @throws InsufficientFundsException if amount is larger than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException, IllegalArgumentException {
            //Elias's implementation
            if (!(isAmountValid(amount)) || amount <= 0) { //if amount is invalid or 0
                throw new IllegalArgumentException("Invalid withdraw amount, cannot withdraw");
            }
            else if (amount > getBalance()) {
                throw new InsufficientFundsException("Invalid amount, cannot withdraw more than current balance");
            }
            balance -= amount;

        //Ioan's implementation
//        if (amount < 0.01){
//            throw new IllegalArgumentException("Withdraw amount: " + amount + " is invalid, cannot withdraw");
//        }
//        if (amount <= balance){
//            balance -= amount;
//        }
//        else {
//            throw new InsufficientFundsException("Not enough money, cannot withdraw: " + amount);
//        }
    }

    /**
     * Deposits money into bank account balance
     * @param amount (double) amount of money to deposit into account
     * @throws IllegalArgumentException if amount is invalid
     */
    public void deposit (double amount) throws IllegalArgumentException {

    }


    public static boolean isEmailValid(String email){
        //If the at symbol does not exist return false
        if (email.indexOf('@') == -1){
            return false;
        }
        //Splits into prefix and domain and held in a list of strings
        String[] stringArray=email.split("@", 2);

        //If there is nothing for the prefix return false
        if(stringArray[0].length()<1){
            return false;
        }
        else{
            for (int i=0; i<stringArray[0].length(); i++){
                if ((Character.isDigit(stringArray[0].charAt(i))) || (Character.isLetter(stringArray[0].charAt(i))) || (stringArray[0].charAt(i)=='_') || (stringArray[0].charAt(i)=='.') || (stringArray[0].charAt(i)=='-')){
                    if((stringArray[0].charAt(i)=='_') || (stringArray[0].charAt(i)=='.') || (stringArray[0].charAt(i)=='-')){
                        if((i+1)>=stringArray[0].length()){
                            return false;
                        }
                        else{
                            if (stringArray[0].charAt(i+1)=='_' || stringArray[0].charAt(i+1)=='.' || stringArray[0].charAt(i+1)=='-'){
                                return false;
                            }
                        }
                    }
                }
                else{
                    return false;
                }
            }
        }
        //if there is nothing in the domain return false.
        if(stringArray[1].length()<1){
            return false;
        }
        else if(stringArray[1].indexOf('.') == -1){
            return false;
        }
        else {
            String[] domainArray = stringArray[1].split("\\.", 2);

            //if either side of the domain is less than 1 then return false.
            if (domainArray[0].length() < 1) {
                return false;
            }
            else {
                //checking that first part of the domain is valid
                for (int i = 0; i < domainArray[0].length(); i++) {
                    //is the character valid
                    if ((Character.isDigit(domainArray[0].charAt(i))) || (Character.isLetter(domainArray[0].charAt(i))) || (domainArray[0].charAt(i) == '-')) {
                        //is the character a symbol
                        if ((domainArray[0].charAt(i) == '-')) {
                            //is the character the last part of the string, if so return false
                            if ((i + 1) >= domainArray[0].length()) {
                                return false;
                            }
                            // if the character is followed by another symbol return false.
                            else {
                                if (domainArray[0].charAt(i + 1) == '-') {
                                    return false;
                                }
                            }
                        }
                    }
                    //the character is not valid return false
                    else {
                        return false;
                    }
                }
            }
            if (domainArray[1].length() < 1) {
                return false;
            }
            //is the character length too short.
            if (domainArray[1].length() < 2) {
                return false;
            } else {
                //checking the second part of the domain.
                for (int i = 0; i < domainArray[1].length() - 1; i++) {
                    //are the characters valid
                    if ((Character.isDigit(domainArray[1].charAt(i))) || (Character.isLetter(domainArray[1].charAt(i))) || (domainArray[1].charAt(i) == '-')) {
                        if (domainArray[1].charAt(i) == '-') {
                            if ((i + 1) > domainArray[1].length() - 1) {
                                return false;
                            } else {
                                if (domainArray[1].charAt(i + 1) == '_' || domainArray[1].charAt(i + 1) == '.' || domainArray[1].charAt(i + 1) == '-') {
                                    return false;
                                }
                            }
                        }
                    }
                    //the character is not valid
                    else {
                        return false;
                    }
                }
            }
        }
        //If nothing returned false up to this point, the email must be valid, therefor return true.
        return true;

    }

    /**
     * @param amount (double) amount of money to check if it is valid
     * The amount is invalid if it is negative or has more than 2 decimal places
     * @return a boolean, true if amount is valid, false otherwise
     */
    public static boolean isAmountValid(double amount){
        String amountStr = Double.toString(amount);
        amountStr = amountStr.substring(amountStr.indexOf('.') + 1);

        return amountStr.length() < 3 && amount >= 0; //returns true if >=0 and no more than two decimal places otherwise false
    }


}
