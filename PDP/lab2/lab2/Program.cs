using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Threading;

namespace lab2
{
    class Program
    {
        private static int vectorLength = 5;

        private static bool finished = false;
        private static Object conditionVariable = new Object();
        private static Object finishedLock = new Object();
        
        private static List<int> vector1 = new List<int>();
        private static List<int> vector2 = new List<int>();

        private static int vectorSum = 0;
        
        private static ConcurrentQueue<int> myQueue = new ConcurrentQueue<int>();

        private static void producerThread()
        {
            while (true)
            {
                lock (finishedLock)
                {
                    if (finished && myQueue.Count == 0)
                        return;
                }

                lock (conditionVariable)
                {
                    while (myQueue.Count == 0)
                        Monitor.Wait(conditionVariable);

                    int value;
                    myQueue.TryDequeue(out value);
                    vectorSum += value;
                }
            }
        }

        private static void consumerThread()
        {
            for (int i = 0; i < vectorLength; i++)
            {
                lock (conditionVariable)
                {
                    myQueue.Enqueue(vector1[i] * vector2[i]);
                    Monitor.Pulse(conditionVariable);
                }
            }

            lock (finishedLock)
            {
                finished = true;
            }
        }
        
        static void Main(string[] args)
        {
            Random r = new Random();
            for (int i = 0; i < vectorLength; i++)
            {
                vector1.Add(r.Next(0, 10));
                vector2.Add(r.Next(0, 10));
            }

            foreach (int i in vector1)
                Console.Write(i + " ");
            Console.WriteLine("\n");
            foreach (int i in vector2)
                Console.Write(i + " ");
            
            Thread producer = new Thread(new ThreadStart(producerThread)); 
            Thread consumer = new Thread(new ThreadStart(consumerThread));
            
            producer.Start();
            consumer.Start();

            producer.Join();
            consumer.Join();
            
            Console.WriteLine("\nThe scalar product of the two vectors is {0}", vectorSum);
        }
    }
}