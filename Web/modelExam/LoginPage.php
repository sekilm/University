<?php
    session_start();
    $connection = mysqli_connect("localhost", "root", "", "examModel");
    if(!$connection){
        die('Nem connection -> ' . mysqli_error());
    }
?>
<html>
<head>
<style>
        body {
            background-color: #C3B2D0;
            color: #220033;
            font-weight: 777;
            font-family: "Times New Roman", Times, serif;
            font-size: 120%;
        }

        input {
            opacity: 40%;
            width: 70%;
            height: 3%;
            border-radius: 10%;
        }

        input:focus {
            opacity: 90%;
        }
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
    $(document).ready(function () {
            $("#loginButton").click(function () {
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function(){
                    console.log("begin login check");
                    if(this.readyState == 4 && this.status == 200)
                    {
                        var data;
                         try{
                             console.log(this.responseText);
                            data = JSON.parse(this.responseText);
                            }
                         catch(err){
                             console.log("An error when json parsing from server occured");
                             console.log(err);
                         }
                        if(data[0] === "true")
                        {
                            window.open("http://localhost/modelExam/AssetsPage.php", "_self");
                        }
                        else
                        {
                            window.open("http://localhost/modelExam/ErrorPage.php", "_self");
                        }
                    }
                 };
                var username = document.getElementById("username_area").value;
                var password = document.getElementById("password_area").value;
                xmlhttp.open("GET", "checkLogin.php?username=" + username + "&password=" + password, true);
                xmlhttp.send();
                console.log(this.responseText);
            });
        });          
</script>
</head>

<body>
<div id="loginDiv">
    <label>Username</label><br>
    <input  type = "text" id = "username_area" name = "username"><br><br>
    <label>Password</label><br>
    <input  type = "password" id = "password_area" name = "password"><br><br>
    <button id="loginButton">Login</button>
</div>
</body>

</html>