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
 
 $idaccount = $_REQUEST['idaccount'];
 $name =$_REQUEST['name'];
 $gender =$_REQUEST['gender'];
 $birthday =$_REQUEST['birthday'];
 $relation =$_REQUEST['relation'];
 $amountimage = $_REQUEST['amountimage'];

  if(move_uploaded_file($_FILES['file']['tmp_name'], $file)){
 $fileName = $_FILES['file']['name'];

 $urlimage = "https://vuquoccuong.000webhostapp.com/ServerHelloBaby/$file_path/$fileName";

 $query = "SELECT * FROM Album ORDER BY idalbum DESC Limit 1";
 $data = mysqli_query($con,$query);
 $row = mysqli_fetch_assoc($data);
 $idalbum = $row['idalbum'];
 $idalbum = $idalbum +1;
 
    $queryinsert = "INSERT INTO Album VALUES ('$idalbum','$idaccount','$urlimage','$name','$gender','$birthday','$relation','$amountimage')";
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