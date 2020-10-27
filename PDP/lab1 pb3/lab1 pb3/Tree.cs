using System;
using System.Collections.Generic;
using System.IO;

namespace lab1_pb3
{
    public class Tree
    {
        public List<Node> nodes = new List<Node>();
        public List<int> terminalPositions = new List<int>();

        public Tree()
        {
            readFile();
            buildTree();
        }

        private void readFile()
        {
            StreamReader input = new StreamReader("E:\\School\\PDP\\lab1 pb3\\lab1 pb3\\input.txt");
            string line;

            int k = 1;
            while ((line = input.ReadLine()) != null)
            {
                string[] tokens = line.Split(',');
                int len = tokens.Length;
                int val = Int32.Parse(tokens[len - 1]);
                int parent = -1;
                
                for (int i = 0; i < tokens.Length - 1; i++)
                {
                    if (tokens[i] != "0")
                    {
                        parent = i;
                        break;
                    }
                }
                
                Node node = new Node(val, parent, k++);
                nodes.Add(node);
                
                if (val != 0)
                    terminalPositions.Add(nodes.Count - 1);
            }
        }

        private void buildTree()
        {
            for (int i = nodes.Count - 1; i > 0; i--)
            {
                int parent = nodes[i].parent;
                nodes[parent].value += nodes[i].value;
            }
        }

        public string printTree()
        {
            string res = "";
            for(int i = 0; i < nodes.Count; i++)
                res += i.ToString() + ": " + nodes[i].value.ToString() + "\n";
            return res;
        }
    }
}