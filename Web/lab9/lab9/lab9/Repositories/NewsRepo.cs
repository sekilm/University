using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using lab9.Models;
using MySql.Data.MySqlClient;

namespace lab9.Repositories
{
    public class NewsRepo
    {
        public List<News> getAllNews()
        {
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=K!4w8m8f;database=newsservice;";
            List<News> newsList = new List<News>();

            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;

                cmd.CommandText = "select * from news";

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    News news = new News();
                    news.title = myreader.GetString("title");
                    news.text = myreader.GetString("news_text");
                    news.producer = myreader.GetString("producer");
                    news.date = myreader.GetDateTime("publish_date");
                    news.category = myreader.GetString("category");
                    newsList.Add(news);
                }
                myreader.Close();
                conn.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return newsList;
        }

        public List<News> filterNews(string category, DateTime start, DateTime end)
        {
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=K!4w8m8f;database=newsservice;";
            List<News> newsList = new List<News>();

            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;

                if (category == "")
                {
                    if (start == null || end == null)
                        cmd.CommandText = "select * from news";
                    else
                    {
                        cmd.CommandText = "select * from news where publish_date between @start_date and @end_date";
                        cmd.Parameters.AddWithValue("@start_date", start);
                        cmd.Parameters.AddWithValue("@end_date", end);
                    }
                }

                if (category != "")
                {
                    if (start == null || end == null)
                    {
                        cmd.CommandText = "select * from news where category = @category";
                        cmd.Parameters.AddWithValue("@category", category);
                    }
                    else
                    {
                        cmd.CommandText = "select * from news where category = @category and publish_date between @start_date and @end_date";
                        cmd.Parameters.AddWithValue("@category", category);
                        cmd.Parameters.AddWithValue("@start_date", start);
                        cmd.Parameters.AddWithValue("@end_date", end);
                    }
                }

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    News news = new News();
                    news.title = myreader.GetString("title");
                    news.text = myreader.GetString("news_text");
                    news.producer = myreader.GetString("producer");
                    news.date = myreader.GetDateTime("publish_date");
                    news.category = myreader.GetString("category");
                    newsList.Add(news);
                }
                myreader.Close();
                conn.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return newsList;
        }

        public void addNews(string title, string text, string producer, DateTime publish_date, string category)
        {
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=K!4w8m8f;database=newsservice;";

            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;

                cmd.CommandText = "insert into news(news_text, title, producer, publish_date, category) values (@news_text, @title, @producer, @publish_date, @category)";
                cmd.Parameters.AddWithValue("@news_text", text);
                cmd.Parameters.AddWithValue("@title", title);
                cmd.Parameters.AddWithValue("@producer", producer);
                cmd.Parameters.AddWithValue("@publish_date", publish_date);
                cmd.Parameters.AddWithValue("@category", category);

                MySqlDataReader myreader = cmd.ExecuteReader();

                myreader.Close();
                conn.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
        }

        public void updateNews(string title, string text, string producer, DateTime publish_date, string category)
        {
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=K!4w8m8f;database=newsservice;";

            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;

                cmd.CommandText = "update news set news_text = @news_text, producer = @producer, publish_date = @publish_date, category = @category where title = @title";
                cmd.Parameters.AddWithValue("@news_text", text);
                cmd.Parameters.AddWithValue("@title", title);
                cmd.Parameters.AddWithValue("@producer", producer);
                cmd.Parameters.AddWithValue("@publish_date", publish_date);
                cmd.Parameters.AddWithValue("@category", category);

                MySqlDataReader myreader = cmd.ExecuteReader();

                myreader.Close();
                conn.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
        }

        public void deleteNews(string title, string text, string producer)
        {
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=K!4w8m8f;database=newsservice;";

            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;

                cmd.CommandText = "delete from news where news_text = @news_text and title = @title and producer = @producer";
                cmd.Parameters.AddWithValue("@news_text", text);
                cmd.Parameters.AddWithValue("@title", title);
                cmd.Parameters.AddWithValue("@producer", producer);

                MySqlDataReader myreader = cmd.ExecuteReader();

                myreader.Close();
                conn.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }
        }
    }
}