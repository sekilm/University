using System;
using System.Collections.Generic;

namespace lab4
{
    class Program
    {
        static void Main(string[] args)
        {
            DirectCallbacks.run(hosts);
            //TaskMechanism.run(hosts);
            //AsyncTaskMechanism.run(hosts);
        }

        private static readonly List<string> hosts = new List<string>
        {
            "www.google.ro",
            "www.alwayshttp.com",
            "www.google.com/generate_204"
        };
    }
}