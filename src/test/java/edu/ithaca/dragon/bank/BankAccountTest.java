package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException, IllegalArgumentException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance()); //boundary, balance  is correct
        bankAccount.withdraw(50); assertEquals(150, bankAccount.getBalance()); //equivalence, balance is correct after withdrawal
        bankAccount.withdraw(0.01); assertEquals(149.99, bankAccount.getBalance()); //boundary, balance is correct after smallest withdrawal

    }

    @Test
    void withdrawTest() throws InsufficientFundsException, IllegalArgumentException {
        //Only need double precision up to .01 (cents)

        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());
        //above three tests are just equivalence

        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(200.01)); //Boundary - insufficient funds
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); //Equivalence - insufficient funds

        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0)); //boundary - amount not greater than 0.01
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100)); //equivalence - amount not greater than 0.01
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-0.01)); //equivalence - amount not greater than 0.01, small negative


        bankAccount.withdraw(0.01); assertEquals(99.99, bankAccount.getBalance()); //boundary - lowest possible withdrawal
        bankAccount.withdraw(20.99); assertEquals(79.00, bankAccount.getBalance()); //equivalence, withdrawal with decimals
        bankAccount.withdraw(1); assertEquals(78.00, bankAccount.getBalance()); //equivalence, smallest non-decimal withdrawal
        bankAccount.withdraw(79); assertEquals(0, bankAccount.getBalance()); //boundary, amount == balance
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //boundary - valid email
        assertTrue(BankAccount.isEmailValid("testemail@gmail.com")); //Equivalence - valid email
        assertTrue(BankAccount.isEmailValid("a.b@c.com")); //boundary - . in prefix
        assertFalse(BankAccount.isEmailValid("")); //Boundary - No @, or empty string
        assertFalse(BankAccount.isEmailValid("@")); //boundary - domain and/or prefix empty
        assertFalse(BankAccount.isEmailValid("a@"));//Equivalence - domain empty
        assertFalse(BankAccount.isEmailValid("@b.com")); //Equivalence - prefix empty
        assertFalse(BankAccount.isEmailValid("a@b")); //Boundary - domain  . missing
        assertFalse(BankAccount.isEmailValid("a@.com")); //Boundary - domain empty before .
        assertFalse(BankAccount.isEmailValid("a@."));    //Equivalence - domain . empty on both sides
        assertFalse(BankAccount.isEmailValid("a#@b.com")); //Boundary - special symbols
        assertFalse(BankAccount.isEmailValid("a@b%.com")); //Boundary - special symbols
        assertFalse(BankAccount.isEmailValid("?a#@b&%*.com")); //Equivalence - special symbols
        assertFalse(BankAccount.isEmailValid("a@b.c")); //boundary - fewer than two chars after .
        assertTrue(BankAccount.isEmailValid("a@b.io")); //boundary - only two chars after .
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}