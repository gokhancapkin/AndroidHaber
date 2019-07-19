using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using YazlabII_P2_WebAPI.Controllers;

namespace YazlabII_P2_Webapi.Controllers
{
    [RoutePrefix("api/Upload")]
    public class UploadController : ApiController
    {
        public MySqlConnection conn = new MySqlConnection();
        
        [Route("PostImage")]
        [AllowAnonymous]
        public async Task<HttpResponseMessage> PostImage()
        {
            Dictionary<string, object> dict = new Dictionary<string, object>();
            try
            {

                var httpRequest = HttpContext.Current.Request;

                foreach (string file in httpRequest.Files)
                {
                    HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created);

                    var postedFile = httpRequest.Files[file];
                    if (postedFile != null && postedFile.ContentLength > 0)
                    {

                        int MaxContentLength = 1024 * 1024 * 1; //Size = 1 MB  

                        IList<string> AllowedFileExtensions = new List<string> { ".jpg", ".gif", ".png" };
                        var ext = postedFile.FileName.Substring(postedFile.FileName.LastIndexOf('.'));
                        var extension = ext.ToLower();
                        if (!AllowedFileExtensions.Contains(extension))
                        {

                            var message = string.Format("Please Upload image of type .jpg,.gif,.png.");

                            dict.Add("error", message);
                            return Request.CreateResponse(HttpStatusCode.BadRequest, dict);
                        }
                        else if (postedFile.ContentLength > MaxContentLength)
                        {

                            var message = string.Format("Please Upload a file upto 1 mb.");

                            dict.Add("error", message);
                            return Request.CreateResponse(HttpStatusCode.BadRequest, dict);
                        }
                        else
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
                            string NextId = "Select MAX(Id) from dbhaber";
                            while (FirstController.done == false)
                            {
                                
                            }
                            MySqlCommand getId = new MySqlCommand(NextId, conn);
                            int ResimId = Convert.ToInt32(getId.ExecuteScalar());
                            var filePath = HttpContext.Current.Server.MapPath("~/HaberResimler/" + ResimId + extension);
                            postedFile.SaveAs(filePath);

                            string sqlText = "Update dbhaber Set HaberImg = '"+ Convert.ToString(filePath) + "' where id = "+ ResimId;
                            MySqlCommand sqlCmd = new MySqlCommand(sqlText, conn);
                            int i = sqlCmd.ExecuteNonQuery();
                            conn.Close();
                        }   
                    }

                    var message1 = string.Format("Image Updated Successfully.");
                    return Request.CreateErrorResponse(HttpStatusCode.Created, message1); ;
                }
                var res = string.Format("Please Upload a image.");
                dict.Add("error", res);
                return Request.CreateResponse(HttpStatusCode.NotFound, dict);
            }
            catch (Exception ex)
            {
                var res = string.Format("some Message");
                dict.Add("error", res);
                return Request.CreateResponse(HttpStatusCode.NotFound, dict);
            }
        }
    }
}