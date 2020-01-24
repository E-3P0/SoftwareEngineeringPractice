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
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount)  {
        balance -= amount;

    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        //Splits into prefix and domain
        String[] stringArray=email.split("@", 2);
        if(stringArray[0].length()<1){
            return false;
        }
        else if(stringArray[1].length()<1){
            return false;
        }
        //Checking that prefix is valid
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
        //splitting domain into two parts
        if(stringArray[1].indexOf('.') == -1){
            return false;
        }

        String[] domainArray=stringArray[1].split(".", 2);
        if(domainArray[0].length()<1){
            return false;
        }
        if(domainArray[1].length()<1){
            return false;
        }

        //checking that first part of the domain is valid
        for (int i=0; i<domainArray[0].length(); i++){
            if ((Character.isDigit(domainArray[0].charAt(i))) || (Character.isLetter(domainArray[0].charAt(i))) || (domainArray[0].charAt(i)=='-')){
                if((domainArray[0].charAt(i)=='-')){
                    if((i+1)>=domainArray[0].length()){
                        return false;
                    }
                    else{
                        if (domainArray[0].charAt(i+1)=='-'){
                            return false;
                        }
                    }
                }
            }
            else{
                return false;
            }
        }
        for (int i=0; i<domainArray[1].length(); i++){
            if (domainArray[1].length()<2){
                return false;
            }
            if ((Character.isDigit(domainArray[1].charAt(i))) || (Character.isLetter(domainArray[1].charAt(i))) ||  (domainArray[0].charAt(i)=='-')){
                if (stringArray[0].charAt(i)=='-'){
                    if((i+1)>=domainArray[0].length()){
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

        return true;

    }
}
