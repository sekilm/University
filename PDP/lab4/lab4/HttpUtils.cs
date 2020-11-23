using System;

namespace lab4
{
    public class HttpUtils
    {
        public static readonly int PORT = 443;

        public static string getResponseBody(string response)
        {
            var responseParts = response.Split(new[] {"\r\n\r\n"}, StringSplitOptions.RemoveEmptyEntries);

            if (responseParts.Length > 1)
                return responseParts[1];
            
            return "";
        }

        public static bool responseHeader(string response)
        {
            return response.Contains("\r\n\r\n");
        }

        public static int getResponseLength(string response)
        {
            var length = 0;
            var responseLines = response.Split('\r', '\n');

            foreach (var line in responseLines)
            {
                var header = line.Split(':');

                if (header[0] == "Content-Length")
                    length = int.Parse(header[1]);
            }

            return length;
        }

        public static string getRequest(string hostName, string endPoint)
        {
            return "GET " + endPoint + " HTTP/1.1\r\n" +
                   "Host: " + hostName + "\r\n" +
                   "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0\r\n" +
                   "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,#1#*;q=0.8\r\n" +
                   "Accept-Language: en-US,en;q=0.9,ro;q=0.8\r\n" +
                   "Accept-Encoding: deflate\r\n" +
                   "Connection: keep-alive\r\n" +
                   "Upgrade-Insecure-Requests: 1\r\n" +
                   "Pragma: no-cache\r\n" +
                   "Cache-Control: no-cache\r\n" +
                   "Content-Length: 0\r\n\r\n";
        }
    }
}