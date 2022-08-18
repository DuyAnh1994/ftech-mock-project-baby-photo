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

$file_path = "image/";
 $file = $file_path . basename($_FILES['file']['name']);
 $idalbum = $_REQUEST['idalbum'];
$description = $_REQUEST['description'];
$timeline = $_REQUEST['timeline'];

  if(move_uploaded_file($_FILES['file']['tmp_name'], $file)){
 $fileName = $_FILES['file']['name'];

 $urlimage = "https://vuquoccuong.000webhostapp.com/ServerHelloBaby/$file_path/$fileName";

 $query = "SELECT * FROM Image ORDER BY idimage DESC Limit 1";
 $data = mysqli_query($con,$query);
 $row = mysqli_fetch_assoc($data);
 $idimage = $row['idimage'];
 $idimage = $idimage +1;
 
 $queryupdate = "SELECT * FROM Album WHERE idalbum = '$idalbum'";
 $dataupdate = mysqli_query($con,$queryupdate);
 $row = mysqli_fetch_assoc($dataupdate);
 $amountimage = $row['amountimage'];
 $amountimage = $amountimage +1;
 $queryupdate1 = "UPDATE Album SET amountimage = '$amountimage' WHERE idalbum = '$idalbum'";
 mysqli_query($con,$queryupdate1);

 $queryinsert = "INSERT INTO Image VALUES ('$idimage','$idalbum','$urlimage','$description','$timeline')";
 $datainsert = mysqli_query($con,$queryinsert);

if($datainsert)
{ 
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

  } else{
      $code = "code12";
  $msg = "Please enter enough information";
  $array = array();
  $object =  Data($code,$msg,$array);
  echo json_encode($object);
  }


?>