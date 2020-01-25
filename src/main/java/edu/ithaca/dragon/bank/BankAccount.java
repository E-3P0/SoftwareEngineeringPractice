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
        //if there is nothing in the domain return false.
        else if(stringArray[1].length()<1){
            return false;
        }
        //Checking that prefix is valid

        //check every character of the prefix with a for loop
        for (int i=0; i<stringArray[0].length(); i++){
            //check if the character is valid, else return false.
            if ((Character.isDigit(stringArray[0].charAt(i))) || (Character.isLetter(stringArray[0].charAt(i))) || (stringArray[0].charAt(i)=='_') || (stringArray[0].charAt(i)=='.') || (stringArray[0].charAt(i)=='-')){
                //If the character is a symbol...
                if((stringArray[0].charAt(i)=='_') || (stringArray[0].charAt(i)=='.') || (stringArray[0].charAt(i)=='-')){
                    //and it is the last character of the prefix return false
                    if((1+i)>=stringArray[0].length()){
                        return false;
                    }
                    //otherwise if it is followed by another character also return false
                    else{
                        if (stringArray[0].charAt(i+1)=='_' || stringArray[0].charAt(i+1)=='.' || stringArray[0].charAt(i+1)=='-'){
                            return false;
                        }
                    }
                }
            }
            //the character is not valid
            else{
                return false;
            }
        }
        //splitting domain into two parts
        // if the 'dot' does not exist, return false
        if(stringArray[1].indexOf('.') == -1){
            return false;
        }
        //split the domain at the 'dot'
        String[] domainArray=stringArray[1].split(".", 2);
        //if either side of the domain is less than 1 then return false.
        if(domainArray[0].length()<1){
            return false;
        }
        if(domainArray[1].length()<1){
            return false;
        }

        //checking that first part of the domain is valid
        for (int i=0; i<domainArray[0].length(); i++){
            //is the character valid
            if ((Character.isDigit(domainArray[0].charAt(i))) || (Character.isLetter(domainArray[0].charAt(i))) || (domainArray[0].charAt(i)=='-')){
                //is the character a symbol
                if((domainArray[0].charAt(i)=='-')){
                    //is the character the last part of the string, if so return false
                    if((i+1)>=domainArray[0].length()){
                        return false;
                    }
                    // if the character is followed by another symbol return false.
                    else{
                        if (domainArray[0].charAt(i+1)=='-'){
                            return false;
                        }
                    }
                }
            }
            //the character is not valid return false
            else{
                return false;
            }
        }
        //checking the second part of the domain.
        for (int i=0; i<domainArray[1].length(); i++){
            //is the character length too short.
            if (domainArray[1].length()<2){
                return false;
            }
            //are the characters valid
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
            //the character is not valid
            else{
                return false;
            }
        }
        //If nothing returned false up to this point, the email must be valid, therefor return true.
        return true;

    }
}
