package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Scanner;

public class PrimeTests {

    @Test
    public void testPrime() {
        Assert.assertTrue(Primes.isPrime(Integer.MAX_VALUE));
    }

    @Test
    public void testPrimeFast() {
        Assert.assertTrue(Primes.isPrimeFast(Integer.MAX_VALUE));
    }

    @Test (enabled = false)
    public void testPrimeLong() {
        long n = Integer.MAX_VALUE;

        Assert.assertTrue(Primes.isPrime(n));
    }

    @Test
    public void testNotPrime() {
        Assert.assertFalse(Primes.isPrime(Integer.MAX_VALUE - 2));
    }
}
