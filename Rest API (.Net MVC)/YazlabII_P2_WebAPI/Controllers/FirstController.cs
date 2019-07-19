using MySql.Data.MySqlClient;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using YazlabII_P2_WebAPI.Models;

namespace YazlabII_P2_WebAPI.Controllers
{
    public class FirstController : ApiController
    {
        
        public MySqlConnection conn = new MySqlConnection();
        public static bool done = false;
        [HttpGet]
        public string Get()
        {
            conn.ConnectionString = @"server=localhost;port=3306;uid=root;pwd=root;database=haberler;";
            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection(conn.ConnectionString);
                conn.Open();
                Debug.WriteLine("Bağlandı");
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Debug.WriteLine("HATA");
            }
            string sqlText = "Select * From dbhaber ";
            MySqlCommand sqlCmd = new MySqlCommand(sqlText, conn);
            MySqlDataReader reader = sqlCmd.ExecuteReader();
            string res = ToJson(reader);
            return res;
        }
        public static String ToJson(MySqlDataReader rdr)
        {
            StringBuilder sb = new StringBuilder();
            StringWriter sw = new StringWriter(sb);

            using (JsonWriter jsonWriter = new JsonTextWriter(sw))
            {
                jsonWriter.WriteRaw("{\"haber\": ");
                jsonWriter.WriteStartArray();
                
                while (rdr.Read())
                {
                    jsonWriter.WriteStartObject();

                    int fields = rdr.FieldCount;
                    for (int i = 0; i < fields; i++)
                    {
                        jsonWriter.WritePropertyName(rdr.GetName(i));
                        jsonWriter.WriteValue(rdr[i]);
                    }
                    jsonWriter.WriteEndObject();
                }

                jsonWriter.WriteEndArray();
                jsonWriter.WriteRaw("}");

                return sw.ToString();
            }
        }
        public void Up(int colNum,int Id)
        {
            String[] col = { "LikeNum", "UnlikeNum", "ViewNum" };
            conn.ConnectionString = @"server=localhost;port=3306;uid=root;pwd=root;database=haberler;";
            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection(conn.ConnectionString);
                conn.Open();
                Debug.WriteLine("Bağlandı");
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Debug.WriteLine("HATA");
            }
            string sqlText = "Update dbhaber Set " + col[colNum] + "=" + col[colNum] + "+1 Where Id =" + Id;
            MySqlCommand sqlCmd = new MySqlCommand(sqlText, conn);
            int i = sqlCmd.ExecuteNonQuery();
            conn.Close();
        }
        [HttpPost]
        public void Post([FromBody]FirstModel item)
        {
            conn.ConnectionString = @"server=localhost;port=3306;uid=root;pwd=root;database=haberler;";
            try
            {
                conn = new MySql.Data.MySqlClient.MySqlConnection(conn.ConnectionString);
                conn.Open();
                Debug.WriteLine("Bağlandı");
            }
            catch (MySql.Data.MySqlClient.MySqlException ex)
            {
                Debug.WriteLine("HATA");
            }
            string sqlText = "Insert into dbhaber (Id,tur,HaberHeader,HaberContent,LikeNum,UnlikeNum,ViewNum) values(@Id,@tur,@HaberHeader,@HaberContent,@LikeNum,@UnlikeNum,@ViewNum)";
            MySqlCommand sqlCmd = new MySqlCommand(sqlText, conn);
            string NextId = "Select MAX(Id) from dbhaber";
            MySqlCommand getId = new MySqlCommand(NextId,conn);
            int NextIdValue;
            if (Convert.IsDBNull(getId.ExecuteScalar()))
            {
                NextIdValue = 0;
            }
            else
                NextIdValue = Convert.ToInt32(getId.ExecuteScalar())+1;
            sqlCmd.Parameters.AddWithValue("@Id", NextIdValue);
            sqlCmd.Parameters.AddWithValue("@tur", item.tur);
            sqlCmd.Parameters.AddWithValue("@HaberHeader", item.HaberHeader);
            sqlCmd.Parameters.AddWithValue("@HaberContent", item.HaberContent);
            sqlCmd.Parameters.AddWithValue("@LikeNum", 0);
            sqlCmd.Parameters.AddWithValue("@UnlikeNum", 0);
            sqlCmd.Parameters.AddWithValue("@ViewNum", 0);
            int i = sqlCmd.ExecuteNonQuery();
            conn.Close();
            done = true;
            if (i >= 1)
            {
                Debug.WriteLine( "Haber Eklendi");

            }
        }
    }
}
