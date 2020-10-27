using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using lab9.Repositories;

namespace lab9.Controllers
{
    public class EditController : Controller
    {
        // GET: Edit
        public ActionResult Index()
        {
            return View("EditItem");
        }

        public string getNews()
        {
            var title = Request.Params["title"];
            Console.WriteLine(title);
            var text = Request.Params["text"];
            var producer = Request.Params["producer"];
            var publish_date = Request.Params["publish_date"];
            var category = Request.Params["category"];

            string result = "<br><input class=\"form-control col-5\" id=\"title\" type=\"text\" value=\"" + title + "\" /><br>" +
                "<input class=\"form-control col-5\" id=\"text\" type=\"text\" value=\"" + text + "\" /><br>" +
                "<input class=\"form-control col-5\" id=\"producer\" type=\"text\" value=\"" + producer + "\" /><br>" +
                "<input class=\"form-control col-5\" id=\"publish_date\" type=\"date\" value=\"" + publish_date + "\" /><br>" +
                "<input class=\"form-control col-5\" id=\"category\" type=\"text\" value=\"" + category + "\" /><br>";

            return result;
        }

        public string DeleteNews()
        {
            var title = Request.Params["title"];
            var text = Request.Params["text"];
            var producer = Request.Params["producer"];

            var repo = new NewsRepo();

            repo.deleteNews(title, text, producer);

            return "Delete successful!";
        }

        public string UpdateNews()
        {
            var title = Request.Params["title"];
            var text = Request.Params["text"];
            var producer = Request.Params["producer"];
            var publish_date = Convert.ToDateTime(Request.Params["publish_date"]);
            var category = Request.Params["category"];

            var repo = new NewsRepo();

            repo.updateNews(title, text, producer, publish_date, category);

            return "Update successful!";
        }
    }
}