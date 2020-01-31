package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException, IllegalArgumentException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance()); //border, balance  is correct
        bankAccount.withdraw(50); assertEquals(150, bankAccount.getBalance()); //middle, balance is correct after withdrawal
        bankAccount.withdraw(0.01); assertEquals(149.99, bankAccount.getBalance()); //border, balance is correct after smallest withdrawal

    }

    @Test
    void withdrawTest() throws InsufficientFundsException, IllegalArgumentException {
        //Only need double precision up to .01 (cents)

        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());
        //above test is middle of a valid withdraw equivalence case

        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(200.01)); //Border - insufficient funds
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); //Middle - insufficient funds

        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0)); //border - amount not greater than 0.01
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100)); //middle - amount not greater than 0.01
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-0.01)); //middle - amount not greater than 0.01, small negative


        bankAccount.withdraw(0.01); assertEquals(99.99, bankAccount.getBalance()); //border - lowest possible withdrawal
        bankAccount.withdraw(20.99); assertEquals(79.00, bankAccount.getBalance()); //middle, withdrawal with decimals
        bankAccount.withdraw(1); assertEquals(78.00, bankAccount.getBalance()); //middle, smallest non-decimal withdrawal
        bankAccount.withdraw(78); assertEquals(0, bankAccount.getBalance()); //border, amount == balance

        BankAccount bankAccount2 = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(10.001)); //border double has more than two decimal places
        assertEquals(200, bankAccount2.getBalance()); //making sure balance is unchanged from bad withdrawal

        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(10.111)); //Middle - more than two decimal places
        assertEquals(200, bankAccount2.getBalance()); //making sure balance is unchanged from bad withdrawal

        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(-10.001)); //border negative and more than two decimal places
        assertEquals(200, bankAccount2.getBalance()); //making sure balance is unchanged from bad withdrawal

        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(200.0001)); //border, > balance and > two decimal places
        assertEquals(200, bankAccount2.getBalance()); //making sure balance is unchanged from bad withdrawal


    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //border - valid email
        assertTrue(BankAccount.isEmailValid("testemail@gmail.com")); //Middle - valid email
        assertTrue(BankAccount.isEmailValid("a.b@c.com")); //border - . in prefix
        assertFalse(BankAccount.isEmailValid("")); //Border - No @, or empty string
        assertFalse(BankAccount.isEmailValid("@")); //border - domain and/or prefix empty
        assertFalse(BankAccount.isEmailValid("a@"));//Middle - domain empty
        assertFalse(BankAccount.isEmailValid("@b.com")); //Middle - prefix empty
        assertFalse(BankAccount.isEmailValid("a@b")); //Border - domain  . missing
        assertFalse(BankAccount.isEmailValid("a@.com")); //Border - domain empty before .
        assertFalse(BankAccount.isEmailValid("a@."));    //Middle - domain . empty on both sides
        assertFalse(BankAccount.isEmailValid("a#@b.com")); //Border - special symbols
        assertFalse(BankAccount.isEmailValid("a@b%.com")); //Border - special symbols
        assertFalse(BankAccount.isEmailValid("?a#@b&%*.com")); //Middle - special symbols
        assertFalse(BankAccount.isEmailValid("a@b.c")); //border - fewer than two chars after .
        assertTrue(BankAccount.isEmailValid("a@b.io")); //border - only two chars after .
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com",-1));
    }

    @Test
    void isAmountValidTest(){

        assertTrue(BankAccount.isAmountValid(0.01)); //Border, lowest possible valid nonzero amount
        assertTrue(BankAccount.isAmountValid(0)); //Border, lowest possible valid amount, no decimal places
        assertTrue(BankAccount.isAmountValid(0.0)); //Border, lowest possible valid amount but with a decimal place
        assertTrue(BankAccount.isAmountValid(100.1)); //Middle, normal valid value with 1 decimal place
        assertTrue(BankAccount.isAmountValid(123456)); //Middle, large valid value with no decimal place

        assertFalse(BankAccount.isAmountValid(-0.0)); //Border, negative zero, weird but it's a thing and should be invalid
        assertFalse(BankAccount.isAmountValid(-0.01)); //Border, lowest possible invalid amount besides -0.0
        assertFalse(BankAccount.isAmountValid(-1)); //Border, negative and no decimal places
        assertFalse(BankAccount.isAmountValid(-666)); //Middle, negative, no decimal places
        assertFalse(BankAccount.isAmountValid(-1001.8)); //Middle, negative, 1 decimal place
        assertFalse(BankAccount.isAmountValid(-1451.82)); //Middle, negative, 2 decimal places

        assertFalse(BankAccount.isAmountValid(0.001)); //Border, more than two decimal places and positive
        assertFalse(BankAccount.isAmountValid(-0.001)); //Border, more than two decimal places and negative
        assertFalse(BankAccount.isAmountValid(0.001112131)); //Middle, more than two decimal places and positive
        assertFalse(BankAccount.isAmountValid(-0.11144)); //Middle, more than two decimal places and negative
        assertFalse(BankAccount.isAmountValid(1234.1234)); //Middle, more than two decimal places

    }

}