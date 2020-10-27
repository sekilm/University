using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;

namespace lab1_pb3
{
    class Program
    {
        private static int THREADS = 4;
        private static Tree myTree = new Tree();
        private static bool stop = false;
        private static Mutex stopMutex = new Mutex();

        private static void thread_function()
        {
            while (true)
            {
                stopMutex.WaitOne();
                if (stop)
                {
                    stopMutex.ReleaseMutex();
                    return;
                }
                stopMutex.ReleaseMutex();
                
                Random r = new Random();
                int delta = r.Next(1, 10);
                
                int node = myTree.terminalPositions[r.Next(0, myTree.terminalPositions.Count - 1)];

                Node terminal = myTree.nodes[node];
                terminal.mutex.WaitOne();
                
                while (node != -1)
                {
                    myTree.nodes[node].value += delta;
                    
                    node = myTree.nodes[node].parent;
                }
                
                terminal.mutex.ReleaseMutex();
                
                Thread.Sleep(r.Next(500, 1500));
            }
        }

        private static void check()
        {
            while (true)
            {
                stopMutex.WaitOne();
                if (stop)
                {
                    stopMutex.ReleaseMutex();
                    return;
                }

                stopMutex.ReleaseMutex();

                List<Node> nodes = myTree.nodes.OrderBy(n => n.code).ToList();
                foreach (var node in nodes)
                    node.mutex.WaitOne();

                bool passed = true;
                for (int i = 0; i < myTree.terminalPositions.Count; i++)
                {
                    int node = myTree.terminalPositions[i];
                    node = myTree.nodes[node].parent;

                    while (node != -1)
                    {
                        int sum = 0;

                        for (int j = 0; j < myTree.nodes.Count; j++)
                            if (myTree.nodes[j].parent == node)
                                sum += myTree.nodes[j].value;

                        if (myTree.nodes[node].value != sum)
                        {
                            passed = false;
                            Console.WriteLine("WARNING: A VALUE HAS NOT BEEN COMPUTED PROPERLY");
                            Console.WriteLine("Sum = {0}, Node value = {1}, Node position = {2}\n", sum,
                                myTree.nodes[node].value, node);
                        }
                        node = myTree.nodes[node].parent;
                        Console.WriteLine(myTree.printTree());
                    }
                }

                Random r = new Random();
                Thread.Sleep(r.Next(5000, 10000));
                if(passed)
                    Console.WriteLine("Check passed\n");
                
                foreach (var node in nodes)
                    node.mutex.ReleaseMutex();
            }
        }
        
        static void Main(string[] args)
        {
            Console.WriteLine(myTree.printTree());

            List<Thread> threads = new List<Thread>();
            for (int i = 0; i < THREADS; i++)
            {
                Thread newThread = new Thread(new ThreadStart(thread_function));
                newThread.Start();
                threads.Add(newThread);
            }

            Thread checkThread = new Thread(new ThreadStart(check));
            checkThread.Start();

            Console.ReadKey();
            
            stopMutex.WaitOne();
            stop = true;
            stopMutex.ReleaseMutex();

            for (int i = 0; i < THREADS; i++)
                threads[i].Join();

            checkThread.Join();
            
            Console.WriteLine(myTree.printTree());
        }
    }
}