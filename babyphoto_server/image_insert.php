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
 $urlimage =$_POST['urlimage'];
 $description =$_POST['description'];
 $timeline =$_POST['timeline'];
 
  $query = "SELECT * FROM Image ORDER BY idimage DESC Limit 1";
 $data = mysqli_query($con,$query);
 $row = mysqli_fetch_assoc($data);
 $idimage = $row['idimage'];
 $idimage = $idimage +1;

if(is_null($idalbum) || is_null($urlimage)
    || is_null($description) || is_null($timeline)){
  $code = "code21";
  $msg = "Incorrect transmission data ";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror);
  echo json_encode($object);
}else{
  $queryinsert = "INSERT INTO Image VALUES ('$idimage','$idalbum','$urlimage','$description','$timeline')";
  $datainsert = mysqli_query($con,$queryinsert);

  if ($datainsert) {
  $code = "code13";
  $msg = "insert successfully";
  $array= array();
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