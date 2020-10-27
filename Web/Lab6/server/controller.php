<?php

class Controller{
    private $con;
    public function __construct(){
        $this->con = mysqli_connect("localhost", "root", "", "newsservice");
        if(!$this->con){
            die("Could not connect to database".mysqli_error());
        }
    }

    public function service(){
        if(isset($_POST['action']) && !empty($_POST['action']) && $_POST['action'] == 'login'){
            $this->login($_POST['user'], $_POST['password']);
        }
        else 
        {
            if(isset($_GET['action']) && !empty($_GET['action'])){
                if($_GET['action'] == 'addNews'){
                    $this->addNews($_GET['title'], $_GET['prod'], $_GET['date'], $_GET['cat'], $_GET['desc']);
                }
                elseif($_GET['action'] == 'updateNews'){
                    $this->updateNews($_GET['id'], $_GET['title'], $_GET['prod'], $_GET['date'], $_GET['cat'], $_GET['desc']);
                }
                elseif($_GET['action'] == 'getAllNews'){
                    $this->getAllNews();
                }
                elseif($_GET['action'] == 'filter'){
                    $this->filter($_GET['dateFrom'], $_GET['dateTill'], $_GET['cat']);
                }
            }
        }
    }

    public function login($user, $passw){
        $result = mysqli_query($this->con, "SELECT * FROM users where Username='".$user."' and Password='".$passw."'");
        
        $ctr = count(mysqli_fetch_all($result));
        if($ctr == 0)
        {
            echo 0;
        }
        else{
            echo $user;
        }
    }

    public function addNews($title, $prod, $date, $cat, $desc){
        $result = mysqli_query($this->con, "INSERT INTO news (Title, Producer, Date, Category, Description) VALUES ('$title', '$prod', '$date', '$cat', '$desc')");
        if(!$result){
            echo 0;
        }
        else{
            echo 1;
        }
    }

    public function updateNews($id, $title, $prod, $date, $cat, $desc){
        $vals = array($title, $prod, $date, $cat, $desc);
        $query = "UPDATE news SET ";
        foreach($vals as $key=>$val){
            if($val != ""){
                switch ($key){
                    case 0:
                        $query.='Title="'.$val.'",';
                        break;
                    case 1:
                        $query.='Producer="'.$val.'",';
                        break;
                    case 2:
                        $query.='Date="'.$val.'",';
                        break;
                    case 3:
                        $query.='Category="'.$val.'",';
                        break;
                    case 4:
                        $query.='Description="'.$val.'",';
                        break;
                }
            }
        }
        $query = rtrim($query, ",");
        $query.=" WHERE ID=".$id;

        $result = mysqli_query($this->con, $query);
        if(!$result){
            echo 0;
        }
        else{
            echo 1;
        }
    }

    public function getAllNews(){
        $result = mysqli_query($this->con, "SELECT * FROM News");
        echo json_encode(mysqli_fetch_all($result));
    }

    public function filter($dateFrom, $dateTill, $cat){
        $query="";
        if($dateFrom != "" && $dateTill == ""){
            $query="SELECT * FROM news WHERE Date >='".$dateFrom."'";
        }
        elseif($dateFrom == "" && $dateTill != ""){
            $query="SELECT * FROM news WHERE Date <='".$dateTill."'";
        }
        elseif($dateFrom == "" && $dateTill == ""){
            $query = "SELECT * FROM news WHERE Category='".$cat."'";
        }
        else{
            $query = "SELECT * FROM news WHERE Date between '".$dateFrom."' AND '".$dateTill."'";
        }

        if(($dateFrom != "" || $dateTill != "") &&  $cat != ""){
            $query.= " AND Category='".$cat."'";
        }

        $result = mysqli_query($this->con, $query);
        echo json_encode(mysqli_fetch_all($result));
    }
}

$controller = new Controller();
$controller->service();

?>