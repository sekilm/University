using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading;

namespace Lab6
{
    public class Attempt
    {
        private DirectedGraph graph;
        private int startNode;
        private List<int> path = new List<int>();

        public Attempt(DirectedGraph graph, int startNode)
        {
            this.graph = graph;
            this.startNode = startNode;
        }

        private void visit(int node)
        {
            path.Add(node);
            
            /*lock (Program.mutex)
            {
                if (Program.result.Count == 0)
                    return;
            }*/

            if (path.Count == graph.size)
            {
                for (int i = 0; i < graph.getNeighbours(node).Count; i++)
                {
                    if (startNode == graph.getNeighbours(node)[i])
                    {
                        path.Add(startNode);
                        lock (Program.mutex)
                        {
                            Program.result = path;
                        }
                    }
                }
                return;
            }

            var found = false;
            for (int i = 0; i < graph.getNeighbours(node).Count; i++)
            {
                for (int j = 0; j < path.Count; j++)
                {
                    if (graph.getNeighbours(node)[i] == path[j])
                        found = true;
                }

                if (found == false)
                    visit(i);
            }
        }

        public void run()
        {
            visit(startNode);
        }
    }
}