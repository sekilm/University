using System;
using System.Collections;
using System.Collections.Generic;

namespace Lab5
{
    class Program
    {
        static void Main(string[] args)
        {
            int[] l1 = {5, 4, 2, 4};
            int[] l2 = {6, 3, 7};
            Polynomial p = new Polynomial(l1);
            Polynomial q = new Polynomial(l2);
            
            Console.WriteLine("Polynom P: {0}", p);
            Console.WriteLine("Polynom Q: {0}", q);
            
            
        }
    }
}