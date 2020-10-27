using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using lab9.Models;
using MySql.Data.MySqlClient;

namespace lab9.Repositories
{
    public class AssetRepo
    {
        public List<Asset> getAssets(int uid)
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "server=localhost;uid=root;pwd=K!4w8m8f;database=exam;";
            List<Asset> assets = new List<Asset>();

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;

                cmd.CommandText = "select * from assets where userid = @uid";
                cmd.Parameters.AddWithValue("@uid", uid);

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    Asset asset = new Asset();
                    asset.name = myreader.GetString("name");
                    asset.description = myreader.GetString("description");
                    asset.value = myreader.GetInt32("value");
                    assets.Add(asset);
                }
                myreader.Close();
                conn.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return assets;
        }
    }
}