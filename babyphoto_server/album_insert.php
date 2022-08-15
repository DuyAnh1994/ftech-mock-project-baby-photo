<?php

 require "Connect.php";

 class Data
  {
    function Data($code,$msg,$data){
      $this -> code = $code;
      $this -> msg = $msg;
      $this -> data =$data;
    }
  }

 $idaccount =$_POST['idaccount'];
 $urlimage =$_POST['urlimage'];
 $name =$_POST['name'];
 $gender =$_POST['gender'];
 $birthday =$_POST['birthday'];
 $relation =$_POST['relation'];
 $amountimage = $_POST['amountimage'];

 $query = "SELECT * FROM Album ORDER BY idalbum DESC Limit 1";
 $data = mysqli_query($con,$query);
 $row = mysqli_fetch_assoc($data);
 $idalbum = $row['idalbum'];
 $idalbum = $idalbum +1;
 
    $queryinsert = "INSERT INTO Album VALUES ('$idalbum','$idaccount','$urlimage','$name','$gender','$birthday','$relation','$amountimage')";
    $datainsert = mysqli_query($con,$queryinsert);

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