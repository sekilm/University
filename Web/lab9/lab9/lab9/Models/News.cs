using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace lab9.Models
{
    public class News
    {
        public string title { get; set; }
        public string text { get; set; }
        public string producer { get; set; }
        public DateTime date { get; set; }
        public string category { get; set; }
    }
}