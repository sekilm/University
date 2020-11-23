using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace lab4
{
    public class AsyncTaskMechanism
    {
        private static List<string> hosts;

        public static void run(List<string> _hosts)
        {
            hosts = _hosts;

            var tasks = new List<Task>();
            
            for (var i = 0; i < hosts.Count; i++)
                tasks.Add(Task.Factory.StartNew(start, i));

            Task.WaitAll(tasks.ToArray());
        }

        private static void start(object idObject)
        {
            var id = (int) idObject;
            StartClient(hosts[id], id);
        }

        private static async void StartClient(string host, int id)
        {
            var ipAddress = Dns.GetHostEntry(host.Split('/')[0]);
            var serverHost = ipAddress.AddressList[0];
            var serverIpAddress = new IPEndPoint(serverHost, HttpUtils.PORT);
            
            var client = new Socket(serverHost.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

            string path;
            if (host.Contains("/"))
                path = host.Substring(host.IndexOf("/", StringComparison.Ordinal));
            else
                path = "/";

            var state = new State
            {
                socket = client,
                id = id,
                host = host.Split('/')[0],
                serverPath = path,
                serverIpAddress = serverIpAddress
            };

            await ConnectWrapper(state);

            await SendWrapper(state, HttpUtils.getRequest(state.host, state.serverPath));

            await ReceiveWrapper(state);
            
            foreach(var i in state.response.ToString().Split('\r', '\n'))
                Console.WriteLine(i);
            Console.WriteLine("{0} --> Response received: Expected {1} characters in body, got {2}", id,
                HttpUtils.getResponseLength(state.response.ToString()), HttpUtils.getResponseBody(state.response.ToString()).Length);
            
            client.Shutdown(SocketShutdown.Both);
            client.Close();
        }

        private static async Task ConnectWrapper(State state)
        {
            state.socket.BeginConnect(state.serverIpAddress, ConnectCallback, state);

            await Task.FromResult<object>(state.connectDone.WaitOne());
        }

        private static void ConnectCallback(IAsyncResult asyncResult)
        {
            var state = (State) asyncResult.AsyncState;
            
            state.socket.EndConnect(asyncResult);
            
            Console.WriteLine("{0} --> Socket connected to {1} ({2})", state.id, state.host, state.socket.RemoteEndPoint);

            state.connectDone.Set();
        }

        private static async Task SendWrapper(State state, string data)
        {
            var bytes = Encoding.ASCII.GetBytes(data);

            state.socket.BeginSend(bytes, 0, bytes.Length, 0, SendCallback, state);

            await Task.FromResult<object>(state.sendDone.WaitOne());
        }

        private static void SendCallback(IAsyncResult asyncResult)
        {
            var state = (State) asyncResult.AsyncState;

            var bytesSent = state.socket.EndSend(asyncResult);
            
            Console.WriteLine("{0} --> Sent {1} bytes to the server", state.id, bytesSent);

            state.sendDone.Set();
        }

        private static async Task ReceiveWrapper(State state)
        {
            state.socket.BeginReceive(state.buffer, 0, State.BUFFER_SIZE, 0, ReceiveCallback, state);

            await Task.FromResult<object>(state.receiveDone.WaitOne());
        }

        private static void ReceiveCallback(IAsyncResult asyncResult)
        {
            var state = (State) asyncResult.AsyncState;

            try
            {
                var bytesRead = state.socket.EndReceive(asyncResult);

                state.response.Append(Encoding.ASCII.GetString(state.buffer, 0, bytesRead));

                if (!HttpUtils.responseHeader(state.response.ToString()))
                {
                    state.socket.BeginReceive(state.buffer, 0, State.BUFFER_SIZE, 0, ReceiveCallback, state);
                }
                else
                {
                    var responseBody = HttpUtils.getResponseBody(state.response.ToString());
                    var responseLength = HttpUtils.getResponseLength(state.response.ToString());

                    if (responseBody.Length < responseLength)
                    {
                        state.socket.BeginReceive(state.buffer, 0, State.BUFFER_SIZE, 0, ReceiveCallback, state);
                    }
                    else
                    {
                        state.receiveDone.Set();
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }
    }
}