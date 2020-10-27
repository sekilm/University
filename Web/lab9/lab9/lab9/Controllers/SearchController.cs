using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

using lab9.Models;
using lab9.Repositories;

namespace lab9.Controllers
{
    public class SearchController : Controller
    {
        // GET: Search
        public ActionResult Index()
        {
            return View("FilterNews");
        }
        public string getAllNews()
        {
            NewsRepo repo = new NewsRepo();

            List<News> newsList = repo.getAllNews();

            ViewData["studentList"] = newsList;

            string result = "";

            foreach (News n in newsList)
                result += "<tr onclick=\"seeItem(this);\"><td>" + n.title + "</td><td>" + n.text + "</td><td>" + n.producer + "</td><td>" + n.date + "</td><td>" + n.category + "</td><td></tr>";

            return result;
        }
        public string filterNews()
        {
            NewsRepo repo = new NewsRepo();
            List<News> newsList = new List<News>();

            string category = Request.Params["category"];
            if (Request.Params["start_date"] != "" && Request.Params["end_date"] != "")
            {
                DateTime start_date = Convert.ToDateTime(Request.Params["start_date"]);
                DateTime end_date = Convert.ToDateTime(Request.Params["end_date"]);
                newsList = repo.filterNews(category, start_date, end_date);
            }
            else
                newsList = repo.filterNews(category, DateTime.MinValue, DateTime.MaxValue);
            ViewData["studentList"] = newsList;

            string result = "";

            foreach (News n in newsList)
                result += "<tr onclick=\"seeItem(this);\"><td>" + n.title + "</td><td>" + n.text + "</td><td>" + n.producer + "</td><td>" + n.date + "</td><td>" + n.category + "</td><td></tr>";

            return result;
        }
    }
}