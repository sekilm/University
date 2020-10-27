using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using lab9.Models;
using MySql.Data.MySqlClient;

namespace lab9.Repositories
{
    public class LoginRepository
    {
        public User GetUser(string username, string password)
        {
            User user = null;

            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=K!4w8m8f;database=exam;";

            try {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select * from users where username = @username and password = @password";
                cmd.Parameters.AddWithValue("@username", username);
                cmd.Parameters.AddWithValue("@password", password);

                MySqlDataReader dataReader = cmd.ExecuteReader();

                if (dataReader.HasRows)
                {
                    dataReader.Read();

                    var aux = dataReader.GetName(0);

                    user = new User
                    {
                        id = Convert.ToInt32(dataReader["id"].ToString()),
                        username = dataReader["username"].ToString()
                    };
                }

                dataReader.Close();

                conn.Close();
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Console.Write(ex.Message);
            }

            return user;
        }

        public bool checkUser(string username)
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=K!4w8m8f;database=exam;";

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select * from users where username = @username";
                cmd.Parameters.AddWithValue("@username", username);

                MySqlDataReader dataReader = cmd.ExecuteReader();

                if (dataReader.HasRows)
                    return true;

                dataReader.Close();

                conn.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }

            return false;
        }

        public void addUser(string username, string password)
        {
            MySql.Data.MySqlClient.MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=K!4w8m8f;database=exam;";

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "insert into users(username, password) values (@username, @password)";
                cmd.Parameters.AddWithValue("@username", username);
                cmd.Parameters.AddWithValue("@password", password);

                MySqlDataReader dataReader = cmd.ExecuteReader();

                dataReader.Close();

                conn.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
        }
        public int GetUserID(string username)
        {
            int userid = -1;

            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=K!4w8m8f;database=exam;";

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select * from users where username = @username";
                cmd.Parameters.AddWithValue("@username", username);

                MySqlDataReader dataReader = cmd.ExecuteReader();


                if (dataReader.HasRows)
                {
                    dataReader.Read();

                    userid = Convert.ToInt32(dataReader["id"].ToString());
                }

                dataReader.Close();

                conn.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }

            return userid;
        }
    }
}