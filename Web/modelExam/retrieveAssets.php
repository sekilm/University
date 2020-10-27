<?php
    $connection = mysqli_connect("localhost", "root");
    if(!$connection){
        die('Nem connection -> ' . mysqli_error());
    }
    $database = mysqli_select_db($connection, 'users');
    $userId = $_SESSION["UserId"];
    $result = mysqli_query($connection, "SELECT * FROM assets WHERE userId =" . $userId);
    $data = array();
    $i = 0;
    while($row = mysqli_fetch_array($result))
    {
         $aux = array(0=>$row[0], 1=>$row[2], 2=>$row[3], 3=>$row[4], 4=>$row[5]);
         $data[$i] = $aux;
         $i = $i + 1;    
    }
    echo json_encode($data);
?>