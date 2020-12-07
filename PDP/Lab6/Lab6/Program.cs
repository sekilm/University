using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Threading;

namespace Lab6
{
    class Program
    {
        public static Mutex mutex = new Mutex();
        public static List<int> result = new List<int>();
        public static int threadNumber = 1;
        
        static void Main(string[] args)
        {
            int size = 5;
            var graph = new DirectedGraph(size);
            var nodes = graph.nodes;
            var shuffledNodes = nodes.OrderBy(x => Guid.NewGuid()).ToList();
            
            for (int i = 1; i < shuffledNodes.Count; i++)
                graph.addEdge(shuffledNodes[i - 1], shuffledNodes[i]);
            
            graph.addEdge(shuffledNodes.Last(), shuffledNodes[0]);

            var r = new Random();
            for (int i = 0; i < size * 2; i++)
                graph.addEdge(r.Next(0, size - 1), r.Next(0, size - 1));

            for (int i = 0; i < graph.nodes.Count; i++)
            {
                Console.Write("{0} -> ", i);   

                for (int j = 0; j < graph.neighbours[i].Count; j++)
                    Console.Write("{0}, ", graph.neighbours[i][j]);
                
                Console.WriteLine();
            }

            hamiltonianPath(graph);
        }

        private static void hamiltonianPath(DirectedGraph graph)
        {
            lock (mutex)
            {
                Program.result = new List<int>();
                
                /*for (int i = 0; i < graph.nodes.Count; i++)
                    ThreadPool.QueueUserWorkItem(obj => new Attempt(graph, i).run());*/
                
                List<Thread> threads = new List<Thread>();
                for (int i = 0; i < threadNumber; i++)
                {
                    for (int j = 0; j < graph.nodes.Count - 1; j++)
                    {
                        Attempt newAttempt = new Attempt(graph, j);
                        Thread newThread = new Thread(() => newAttempt.run());
                        newThread.Start();
                        threads.Add(newThread);
                    }
                }
                for (int i = 0; i < threadNumber; i++)
                    threads[i].Join();
                
                for (int i = 0; i < result.Count; i++)
                    Console.WriteLine(result[i]);
            }
        }
    }
}