using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Web;
using System.Web.Mvc;

using lab9.Repositories;

namespace lab9.Controllers
{
    public class LoginController : Controller
    {
        // GET: Login
        public ActionResult Index()
        {
            return View("Login");
        }

        public string LoginUser()
        {
            var username = Request.Params["username"];
            var password = Request.Params["password"];

            var repo = new LoginRepository();

            var loginUser = repo.GetUser(username, password);
            if (loginUser == null)
            {
                Session["user"] = "";
                return "Error: invalid username or password";
            }
            Session["user"] = username;

            return "Login successful!";
        }

        public string RegisterUser()
        {
            var username = Request.Params["username"];

            var repo = new LoginRepository();

            var loginUser = repo.checkUser(username);
            if (loginUser == true)
                return "Error: username already exists";
            else if (username == "" || Request.Params["password"] == "")
                return "Error: fields cannot be empty";
            else
            {
                var password = Request.Params["password"];
                repo.addUser(username, password);
                return "Register successful!";
            }
        }

        public void logout()
        {
            HttpContext.Session.Abandon();
        }

        public string getLoggedInUser()
        {
            string loggedUser = Convert.ToString(System.Web.HttpContext.Current.Session["user"]) ?? "";
            if (loggedUser == "")
                return "";
            else
                return loggedUser;
        }
    }
}