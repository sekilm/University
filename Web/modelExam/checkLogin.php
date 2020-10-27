<?php
     $username = $_REQUEST['username'];
     $password = $_REQUEST['password'];
     mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);
     $connection = mysqli_connect("localhost", "root");
     if(!$connection){
         die('Nem connection -> ' . mysqli_error());
     }
     $database = mysqli_select_db($connection, 'examModel');
     $sql = "SELECT * FROM users WHERE username" . " = \"" . $username . "\"". "AND password = \"".$password."\"";

     $result = mysqli_query($connection, $sql);
     $row = mysqli_fetch_array($result)
     $data = array("true");
     if (empty($result)) {
        $data = array("false");
        session_start();
        $_SESSION["UserId"] = $row[0];
     }
     echo json_encode($data);
?>