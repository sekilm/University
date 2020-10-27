using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using lab9.Repositories;

namespace lab9.Controllers
{
    public class NewsController : Controller
    {
        // GET: News
        public ActionResult Index()
        {
            return View("AddItem");
        }

        public string AddNews()
        {
            var title = Request.Params["title"];
            var text = Request.Params["text"];
            var producer = Request.Params["producer"];
            var publish_date = Convert.ToDateTime(Request.Params["publish_date"]);
            var category = Request.Params["category"];

            var repo = new NewsRepo();

            repo.addNews(title, text, producer, publish_date, category);

            return "Add successful!";
        }
    }
}