using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Lab5
{
    public class Polynomial
    {
        private List<int> coefficients;
        public Polynomial(List<int> coefficients)
        {
            this.coefficients = coefficients;
        }

        public Polynomial(int degree)
        {
            coefficients = new List<int>(degree + 1);
            
            Random r = new Random();
            for (int i = 0; i < degree; i++)
                coefficients.Add(r.Next(10));
            coefficients.Add(r.Next(10) + 1);
        }

        public int getMaxDegree()
        {
            return coefficients.Count - 1;
        }

        public int getLength()
        {
            return coefficients.Count;
        }

        public List<int> getCoefficients()
        {
            return coefficients;
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