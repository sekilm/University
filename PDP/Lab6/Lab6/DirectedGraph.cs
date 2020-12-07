using System.Collections.Generic;

namespace Lab6
{
    public class DirectedGraph
    {
        public int size;
        public List<int> nodes = new List<int>();
        public List<List<int>> neighbours = new List<List<int>>();
        
        public DirectedGraph(int nodesNumber)
        {
            size = nodesNumber;
            for (int i = 0; i < nodesNumber; i++)
            {
                nodes.Add(i);
                neighbours.Add(new List<int>());
            }
        }

        public void addEdge(int source, int destination)
        {
            var found = false;
            for (int i = 0; i < neighbours[source].Count; i++)
                if (destination == neighbours[source][i])
                    found = true;
            
            if (found == false)
                neighbours[source].Add(destination);
        }

        public List<int> getNeighbours(int node)
        {
            return neighbours[node];
        }
    }
}