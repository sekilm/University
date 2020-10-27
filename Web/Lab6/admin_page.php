<?php
session_start();
if(!isset($_SESSION['validUser'])){
    header("Location: index.html");
}
?>

<!DOCTYPE html>

<html>
    <head>
        <title>Admin Page</title>
        <style>
            .container{
                display: flex;
                flex-direction: row;
                align-items: flex-start;
            }
    
            #addNewsSection, #updNewsSection{
                margin: 10px 20px 10px 20px;
            }
        </style>
        <script src="./JQuery/jquery-3.5.0.min.js"></script>
        <script language="javascript">
            function checkData(){
                if($("#title").val() == "" || $("#prod").val() == "" || $("#date").val() == "" || $("#cat").val() == "" || $("#desc").val()=="")
                {
                    return false;
                }
                return true;                
            }

            function card(id, title, prod, date, cat, desc){
                var content = "<div style='background-color: lightgrey'>";
                content+='<h1 id="tit'+id+'">'+title+"</h1>";
                content+='<h5 id="prod'+id+'">'+prod+", "+date+", "+cat+"</h5>";
                content+='<p id="desc'+id+'">'+desc+"</p>";
                content+='<button id="btn_updt'+id+'" onclick="getNewsInfo('+id+')">Update</button>'
                content+="</div>";
                return content;
            }

            function getAllNews(news){
                var content = "";
                news.forEach(element => {
                    content+=card(element[0], element[1], element[2], element[3], element[4], element[5])+"<br>";
                });
                $("#main").html(content);
            }
            
            function clearForms(){
                $("#title").val('');
                $("#prod").val('');
                $("#date").val('');
                $("#cat").val('')
                $("#desc").val('');
                
                $("#oldtitle").val('');
                $("#new_title").val('');
                $("#new_prod").val('');
                $("#new_date").val('');
                $("#new_cat").val('')
                $("#new_desc").val('');
            }

            function displayNews(ok){
                if(ok == 0){
                    alert("Nothing has been added!");
                }
                else{
                    $.getJSON("server/controller.php", {action: "getAllNews"}, getAllNews);
                    clearForms();
                }
            }

            function logout(){
                $.post("server/destroy_session.php", {});
                window.open("index.html");
                window.close();
            }

            window.displayNews();
            
            function split(str){
                var aux = "";
                aux = str.split(", ");
                return aux;
            }

            var idToUpdt;
            function getNewsInfo(id){
                var v = split($("#prod"+id).text());
                idToUpdt = id;
                $("#new_title").val($("#tit"+id).text());
                $("#new_prod").val(v[0]);
                $("#new_date").val(v[1]);
                $("#new_cat").val(v[2]);
                $("#new_desc").val($("#desc"+id).text());
            }

            $(function(){
                $("#btn_addNews").click(function(){
                    if(checkData()){
                        $.getJSON("server/controller.php", {action: "addNews", title: $("#title").val(), prod: $("#prod").val(),  date: $("#date").val(), cat: $("#cat").val(), desc: $("#desc").val()}, displayNews);
                    }
                    else{
                        alert("Please make sure that data is introduced correctly!");
                    }
                });

                $("#btn_updtNews").click(function(){
                    if($("#new_title").val() == "" && $("#new_prod").val() == "" && $("#new_date").val() == "" && $("#new_cat").val() == "" && $("#new_desc").val()=="")
                    {
                        alert("There is nothing to update!");
                    }
                    else{
                        console.log("News ID to update: "+idToUpdt);
                        $.getJSON("server/controller.php", {action: "updateNews",id: idToUpdt, title: $("#new_title").val(), prod: $("#new_prod").val(),  date: $("#new_date").val(), cat: $("#new_cat").val(), desc: $("#new_desc").val()}, displayNews);
                    }
                });
            });
        </script>
    </head>
    <body>
        <button onclick="logout()">Logout</button>
        <div class="container">
            <section id="addNewsSection">
                <h4>Add News</h4>
                <form>
                    Title: <input type="text" id="title" name="title"/><br>
                    Producer: <input type="text" id="prod" name="prod"/><br>
                    Date: <input type="date" id="date" name="date"/><br>
                    Category: <input type="text" id="cat" name="cat"/><br>
                    Text: <textarea id="desc" name="desc"></textarea>
                    <input type="button" id="btn_addNews" value="Add News"/>
                </form>
            </section>
            <section id="updNewsSection">
                <h4>Update News</h4>
                <form>
                    New Title: <input type="text" id="new_title" name="new_title"/><br>
                    New Producer: <input type="text" id="new_prod" name="new_prod"/><br>
                    New Date: <input type="date" id="new_date" name="new_date"/><br>
                    New Category: <input type="text" id="new_cat" name="new_cat"/><br>
                    New Text: <textarea id="new_desc" name="new_desc"></textarea>
                    <input type="button" id="btn_updtNews" value="Update News"/>
                </form>
            </section>
        </div>
        <section id="main"></section>
    </body>
</html>