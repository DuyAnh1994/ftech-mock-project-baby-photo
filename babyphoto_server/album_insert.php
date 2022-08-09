<?php

 require "Connect.php";

 $idalbum =$_POST['idalbum'];
 $idaccount =$_POST['idaccount'];
 $urlimage =$_POST['urlimage'];
 $name =$_POST['name'];
 $gender =$_POST['gender'];
 $birthday =$_POST['birthday'];
 $relation =$_POST['relation'];
 $amountimage = $_POST['amountimage'];

    $queryinsert = "INSERT INTO Album 
                VALUES ('$idalbum','$idaccount','$urlimage','$name','$gender','$birthday','$relation','$amountimage')";
    $datainsert = mysqli_query($con,$queryinsert);

  class Data
  {
    function Data($code,$msg,$data){
      $this -> code = $code;
      $this -> msg = $msg;
      $this -> data =$data;
    }
  }

  if ($datainsert) {
  $code = "code32";
  $msg = "insert successfully";
  $array = array();
  $object = new Data($code,$msg,$array);
  }else{
  $code = "code22";
  $msg = "connect error";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror);
  }
 echo json_encode($object);
?>