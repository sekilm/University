using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Lab5
{
    public class Polynomial
    {
        private int[] coefficients;
        public Polynomial(int[] coefficients)
        {
            this.coefficients = coefficients;
        }

        public int getMaxDegree()
        {
            return this.coefficients.Length - 1;
        }

        public int getLength()
        {
            return this.coefficients.Length;
        }

        public override string ToString()
        {
            String res = "";
            int degree = getMaxDegree();
            for (int i = degree; i >= 0; i--)
            {
                if (coefficients[i] != 0)
                {
                    res += " " + coefficients[i] + "x^" + degree + " +";
                    degree--;
                }
            }
            return res;
        }
    }
}