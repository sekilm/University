using System.Threading;

namespace lab1_pb3
{
    public class Node
    {
        public int code { get; set; }
        public int value { get; set; }
        public int parent { get; set; }
        public Mutex mutex { get; set; }

        public Node(int val, int par, int c)
        {
            code = c;
            value = val;
            parent = par;
            mutex = new Mutex();
        }
    }
}