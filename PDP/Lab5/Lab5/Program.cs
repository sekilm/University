using System;
using System.Collections;
using System.Collections.Generic;

namespace Lab5
{
    class Program
    {
        static void Main(string[] args)
        {
            Polynomial p = new Polynomial(20);
            Polynomial q = new Polynomial(22);
            
            Console.WriteLine("Polynom P: {0}", p);
            Console.WriteLine("Polynom Q: {0}", q);
            
            //Polynomial result = PolynomialOperations.SequentialMultiplication(p, q);
            //Polynomial result = PolynomialOperations.ParallelizedMultiplication(p, q, 5);
            Polynomial result = PolynomialOperations.KaratsubaSequentialMultiplication(p, q);
            //Polynomial result = PolynomialOperations.KaratsubaParallelizedMultiplication(p, q, 4);
            
            Console.WriteLine("Multiplication: {0}", result);
        }
    }
}