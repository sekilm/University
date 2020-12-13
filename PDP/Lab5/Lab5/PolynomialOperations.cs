using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace Lab5
{
    public class PolynomialOperations
    {
        public static Polynomial SequentialMultiplication(Polynomial p, Polynomial q)
        {
            int resultSize = p.getMaxDegree() + q.getMaxDegree() + 1;
            List<int> coefficients = new List<int>();
            
            for (int i = 0; i < resultSize; i++)
                coefficients.Add(0);

            for (int i = 0; i < p.getCoefficients().Count; i++)
            {
                for (int j = 0; j < q.getCoefficients().Count; j++)
                {
                    int idx = i + j;
                    int value = p.getCoefficients()[i] * q.getCoefficients()[j];
                    coefficients[idx] += value;
                }
            }
            return new Polynomial(coefficients);
        }

        public static Polynomial ParallelizedMultiplication(Polynomial p, Polynomial q, int threadNumber)
        {
            int resultSize = p.getMaxDegree() + q.getMaxDegree() + 1;
            List<int> coefficients = new List<int>();
            
            for (int i = 0; i < resultSize; i++)
                coefficients.Add(0);
            
            Polynomial result = new Polynomial(coefficients);
            
            List<Thread> threads = new List<Thread>();
            int step = 1, end;
            if (result.getLength() / threadNumber != 0)
                step = result.getLength() / threadNumber;
            
            for (int i = 0; i < threadNumber; i += step)
            {
                end = i + step;
                Thread newThread = new Thread(() => new MultiplicationTask(i, end, p, q, result));
                newThread.Start();
                threads.Add(newThread);
            }
            
            for (int i = 0; i < threadNumber; i++)
                threads[i].Join();

            return result;
        }

        public static Polynomial Shift(Polynomial p, int offset)
        {
            List<int> coefficients = new List<int>();
            for (int i = 0; i < offset; i++)
                coefficients.Add(0);
            
            for (int i = 0; i < p.getLength(); i++)
                coefficients.Add(p.getCoefficients()[i]);
            
            return new Polynomial(coefficients);
        }

        public static Polynomial Add(Polynomial p, Polynomial q)
        {
            int minDegree = Math.Min(p.getMaxDegree(), q.getMaxDegree());
            int maxDegree = Math.Max(p.getMaxDegree(), q.getMaxDegree());
            List<int> coefficients = new List<int>(maxDegree + 1);

            for (int i = 0; i <= minDegree; i++)
                coefficients.Add(p.getCoefficients()[i] + q.getCoefficients()[i]);
            
            return new Polynomial(coefficients);
        }

        public static void AddRemainingCoefficients(Polynomial p, Polynomial q, int minDegree, int maxDegree,
            List<int> coeff)
        {
            if (minDegree != maxDegree)
            {
                if (maxDegree == p.getMaxDegree())
                {
                    for (int i = minDegree + 1; i <= maxDegree; i++)
                        coeff.Add(p.getCoefficients()[i]);
                }
                else
                {
                    for (int i = minDegree + 1; i <= maxDegree; i++)
                        coeff.Add(q.getCoefficients()[i]);
                }
            }
        }

        public static Polynomial Subtract(Polynomial p, Polynomial q)
        {
            int minDegree = Math.Min(p.getMaxDegree(), q.getMaxDegree());
            int maxDegree = Math.Max(p.getMaxDegree(), q.getMaxDegree());
            List<int> coefficients = new List<int>(maxDegree + 1);

            for (int i = 0; i < minDegree; i++)
                coefficients.Add(p.getCoefficients()[i] - q.getCoefficients()[i]);
            
            AddRemainingCoefficients(p, q, minDegree, maxDegree, coefficients);

            int idx = coefficients.Count - 1;
            while (coefficients[idx] == 0 && idx > 0)
            {
                coefficients.RemoveAt(idx);
                idx--;
            }
            
            return new Polynomial(coefficients);
        }

        public static Polynomial KaratsubaSequentialMultiplication(Polynomial p, Polynomial q)
        {
            if (p.getMaxDegree() < 2 || q.getMaxDegree() < 2)
                return SequentialMultiplication(p, q);
            
            int len = Math.Max(p.getMaxDegree(), q.getMaxDegree()) / 2;
            Polynomial lowP = new Polynomial(p.getCoefficients().GetRange(0, len));
            Polynomial highP = new Polynomial(p.getCoefficients().GetRange(len, p.getLength() - len));
            
            Polynomial lowQ = new Polynomial(q.getCoefficients().GetRange(0, len));
            Polynomial highQ = new Polynomial(q.getCoefficients().GetRange(len, p.getLength() - len));

            Polynomial t1 = KaratsubaSequentialMultiplication(lowP, lowQ);
            Polynomial t2 = KaratsubaSequentialMultiplication(Add(lowP, highP), Add(lowQ, highQ));
            Polynomial t3 = KaratsubaSequentialMultiplication(highP, highQ);

            Polynomial r1 = Shift(t3, 2 * len);
            Polynomial r2 = Shift(Subtract(Subtract(t2, t3), t1), len);
            Polynomial res = Add(Add(r1, r2), t1);

            return res;
        }

        public static Polynomial KaratsubaParallelizedMultiplication(Polynomial p, Polynomial q, int depth)
        {
            if (depth > 4 || p.getMaxDegree() < 2 || q.getMaxDegree() < 2)
                return KaratsubaSequentialMultiplication(p, q);

            int len = Math.Max(p.getMaxDegree(), q.getMaxDegree()) / 2;
            Polynomial lowP = new Polynomial(p.getCoefficients().GetRange(0, len));
            Polynomial highP = new Polynomial(p.getCoefficients().GetRange(len, p.getLength() - len));
            
            Polynomial lowQ = new Polynomial(q.getCoefficients().GetRange(0, len));
            Polynomial highQ = new Polynomial(q.getCoefficients().GetRange(len, p.getLength() - len));

            var work = new BlockingCollection<Polynomial>();
            var producer1 = Task.Factory.StartNew(() => KaratsubaParallelizedMultiplication(lowP, lowQ, depth + 1));
            var producer2 = Task.Factory.StartNew(() => KaratsubaParallelizedMultiplication(Add(lowP, highP), Add(lowQ, highQ), depth + 1));
            var producer3 = Task.Factory.StartNew(() => KaratsubaParallelizedMultiplication(highP, highQ, depth));

            Polynomial t1 = producer1.Result;
            Polynomial t2 = producer2.Result;
            Polynomial t3 = producer3.Result;
            
            Polynomial r1 = Shift(t3, 2 * len);
            Polynomial r2 = Shift(Subtract(Subtract(t2, t3), t1), len);
            Polynomial res = Add(Add(r1, r2), t1);

            return res;
        }
    }
}