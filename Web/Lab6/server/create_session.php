<?php
    session_start();
    $_SESSION['validUser'] = 1;
    $_SESSION['user'] = $_POST['user'];
    header("Location: admin_page.php");
?>