using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Resources;
using System.Text;
using System.Threading;

namespace lab4
{
    public class DirectCallbacks
    {
        private static List<string> hosts;

        public static void run(List<string> _hosts)
        {
            hosts = _hosts;
            for (var i = 0; i < hosts.Count; i++)
            {
                StartClient(hosts[i], i);
                Thread.Sleep(1000);
            }
        }

        private static void StartClient(string host, int id)
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

            state.socket.BeginConnect(state.serverIpAddress, newConnection, state);
        }

        private static void newConnection(IAsyncResult asyncResult)
        {
            var state = (State) asyncResult.AsyncState;
            
            state.socket.EndConnect(asyncResult);
            Console.WriteLine("{0} --> Socket connected to {1} ({2})", state.id, state.host, state.socket.RemoteEndPoint);

            var bytes = Encoding.ASCII.GetBytes(HttpUtils.getRequest(state.host, state.serverPath));

            state.socket.BeginSend(bytes, 0, bytes.Length, 0, newSend, state);
        }

        private static void newSend(IAsyncResult asyncResult)
        {
            var state = (State) asyncResult.AsyncState;

            var bytesSent = state.socket.EndSend(asyncResult);
            Console.WriteLine("{0} --> Sent {1} bytes to server.", state.id, bytesSent);

            state.socket.BeginReceive(state.buffer, 0, State.BUFFER_SIZE, 0, newReceive, state);
        }

        private static void newReceive(IAsyncResult asyncResult)
        {
            var state = (State) asyncResult.AsyncState;

            try
            {
                var bytesRead = state.socket.EndReceive(asyncResult);

                state.response.Append(Encoding.ASCII.GetString(state.buffer, 0, bytesRead));

                if (!HttpUtils.responseHeader(state.response.ToString()))
                {
                    state.socket.BeginReceive(state.buffer, 0, State.BUFFER_SIZE, 0, newReceive, state);
                }
                else
                {
                    var responseBody = HttpUtils.getResponseBody(state.response.ToString());
                    var responseLength = HttpUtils.getResponseLength(state.response.ToString());
                    
                    if (responseBody.Length < responseLength)
                    {
                        state.socket.BeginReceive(state.buffer, 0, State.BUFFER_SIZE, 0, newReceive, state);
                    }
                    else
                    {
                        foreach(var i in state.response.ToString().Split('\r', '\n'))
                            Console.WriteLine(i);
                        Console.WriteLine("{0} --> Response received: Expected {1} characters in body, got {2}", state.id,
                            responseLength, HttpUtils.getResponseBody(state.response.ToString()).Length);
                        
                        state.socket.Shutdown(SocketShutdown.Both);
                        state.socket.Close();
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