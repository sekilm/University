using System;
using System.Collections.Generic;
using System.Threading;

namespace lab3
{
    class Program
    {
        private static int n = 250;
        private static int k = 100;
        private static int m = 300;

        private static int threadNumber = 3;
        
        private static int[,] firstMatrix = new int[n, k];
        private static int[,] secondMatrix = new int[k, m];
        private static int[,] resultMatrix = new int[n, m];

        private static void initalizeMatrix(int[,] matrix, int lines, int columns)
        {
            Random r = new Random();

            for (int i = 0; i < lines; i++)
                for (int j = 0; j < columns; j++)
                    matrix[i, j] = r.Next(0, 5);
        }
        
        private static void workerForRows(int threadIndex)
        {
            int startIndex = n * m / threadNumber * threadIndex;

            int endIndex;
            if (threadIndex != threadNumber - 1)
                endIndex = n * m / threadNumber + startIndex;
            else
                endIndex = n * m;

            for (int idx = startIndex; idx < endIndex; idx++)
            {
                int i = idx / m;
                int j = idx % m;
                int cellValue = 0;

                if (i < n && j < m)
                {
                    for (int aux = 0; aux < k; aux++)
                        cellValue += firstMatrix[i, aux] * secondMatrix[aux, j];

                    resultMatrix[i, j] = cellValue;
                }
            }
        }
        
        private static void workerForColumns(int threadIndex)
        {
            int startIndex = n * m / threadNumber * threadIndex;

            int endIndex;
            if (threadIndex != threadNumber - 1)
                endIndex = n * m / threadNumber + startIndex;
            else
                endIndex = n * m;

            for (int idx = startIndex; idx < endIndex; idx++)
            {
                int i = idx % n;
                int j = idx / n;
                int cellValue = 0;

                if (i < n && j < m)
                {
                    for (int aux = 0; aux < k; aux++)
                        cellValue += firstMatrix[i, aux] * secondMatrix[aux, j];

                    resultMatrix[i, j] = cellValue;
                }
            }
        }
        
        private static void workerForSteps(int threadIndex)
        {
            int startIndex = threadIndex;

            int endIndex = n * m;

            for (int idx = startIndex; idx < endIndex; idx = idx + threadNumber)
            {
                int i = idx / m;
                int j = idx % m;
                int cellValue = 0;

                if (i < n && j < m)
                {
                    for (int aux = 0; aux < k; aux++)
                        cellValue += firstMatrix[i, aux] * secondMatrix[aux, j];

                    resultMatrix[i, j] = cellValue;
                }
            }
        }

        private static void showMatrix(int[,] matrix, int lines, int columns)
        {
            for (int i = 0; i < lines; i++)
            {
                for (int j = 0; j < columns; j++)
                    Console.Write(matrix[i, j] + " ");
                Console.WriteLine();
            }
            Console.WriteLine();
        }
        
        static void Main(string[] args)
        {
            initalizeMatrix(firstMatrix, n, k);
            initalizeMatrix(secondMatrix, k, m);
            
            showMatrix(firstMatrix, n, k);
            showMatrix(secondMatrix, k, m);
            
            List<Thread> threads = new List<Thread>();
            for (int i = 0; i < threadNumber; i++)
            {
                int idx = i;
                Thread newThread = new Thread(() => workerForColumns(idx));
                newThread.Start();
                threads.Add(newThread);
            }
            
            for (int i = 0; i < threadNumber; i++)
                threads[i].Join();
            
            Console.WriteLine("Threads");
            showMatrix(resultMatrix, n, m);

            for (int i = 0; i < threadNumber; i++)
                ThreadPool.QueueUserWorkItem(obj => workerForColumns(i));
            
            Console.WriteLine("Thread pool");
            showMatrix(resultMatrix, n, m);
        }
    }
}