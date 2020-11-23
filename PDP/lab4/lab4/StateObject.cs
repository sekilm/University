using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace lab4
{
    public class State
    {
        public const int BUFFER_SIZE = 512;
        public Socket socket = null;
        public byte[] buffer = new byte[BUFFER_SIZE];
        
        public StringBuilder response = new StringBuilder();

        public int id;
        public string host;
        public string serverPath;

        public IPEndPoint serverIpAddress;
        
        public ManualResetEvent connectDone = new ManualResetEvent(false);
        public ManualResetEvent sendDone = new ManualResetEvent(false);
        public ManualResetEvent receiveDone = new ManualResetEvent(false);
    }
}