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

 $idalbum =$_POST['idalbum'];
 $idaccount =$_POST['idaccount'];
 $urlimage =$_POST['urlimage'];
 $name =$_POST['name'];
 $gender =$_POST['gender'];
 $birthday =$_POST['birthday'];
 $relation =$_POST['relation'];
 $amountimage = $_POST['amountimage'];

if(is_null($idalbum) || is_null($idaccount) || is_null($urlimage)
    || is_null($name) || is_null($gender) || is_null($birthday) || is_null($relation) || is_null($amountimage) ){
  $code = "code21";
  $msg = "Incorrect transmission data ";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror);
  echo json_encode($object);
}

else{
    $queryinsert = "INSERT INTO Album 
                VALUES ('$idalbum','$idaccount','$urlimage','$name','$gender','$birthday','$relation','$amountimage')";
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
}
?>