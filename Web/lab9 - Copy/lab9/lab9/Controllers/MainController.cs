using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using lab9.Repositories;
using lab9.Models;

namespace lab9.Controllers
{
    public class MainController : Controller
    {
        // GET: Main
        public ActionResult Index()
        {
            return View("MainPage");
        }

        public string getAssets(string username)
        {
            AssetRepo repo = new AssetRepo();
            LoginRepository userRepo = new LoginRepository();
            Console.WriteLine(username);


            int id = userRepo.GetUserID(username);
            List<Asset> assets = repo.getAssets(id);

            ViewData["studentList"] = assets;

            string result = "";

            foreach (Asset n in assets)
            {
                if (n.value > 10)
                    result += "<tr bgcolor=\"red\"><td>" + n.name + "</td><td>" + n.description + "</td><td>" + n.value + "</td></tr>";
                else
                    result += "<tr><td>" + n.name + "</td><td>" + n.description + "</td><td>" + n.value + "</td></tr>";
            }

            return result;
        }
    }
}