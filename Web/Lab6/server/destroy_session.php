<?php
    session_start();
    if(isset($_SESSION['validUser']))
        unset($_SESSION['validUser']);     
    session_destroy();
?>