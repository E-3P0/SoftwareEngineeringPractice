package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest()  throws InsufficientFundsException, IllegalArgumentException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100));
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