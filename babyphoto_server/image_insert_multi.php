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


$countfiles = count($_FILES['file']['name']);
$file = $_FILES['file']['name'][0]; // getting first file
$idalbum = $_REQUEST['idalbum'];
$description = $_REQUEST['description'];
$timeline = $_REQUEST['timeline'];

if(!isset($file) || !isset($idalbum) || !isset($description) || !isset($timeline))
{
    // if file is empty show error
  $code = "code12";
  $msg = "Please enter enough information";
  $array = array();
  $object =  Data($code,$msg,$array);
  echo json_encode($object);
}
else
{

$upload_path = 'image/'; // declare file upload path
$valid_extensions = array('jpeg', 'jpg', 'png', 'gif'); // valid image extensions - file extensions

// Looping all files 
for($i=0;$i<$countfiles;$i++){
    $fileName = $_FILES['file']['name'][$i];
    $tempPath = $_FILES['file']['tmp_name'][$i];
    $fileSize  =  $_FILES['file']['size'][$i];

    $fileExt = strtolower(pathinfo($fileName,PATHINFO_EXTENSION)); // get image extension

           // check if the files are contain the vALID  extensions
    if(in_array($fileExt, $valid_extensions))
  {
    //check file not exist our upload folder path
    if(!file_exists($upload_path . $fileName))
  {
        move_uploaded_file($tempPath, $upload_path . $fileName);

 $urlimage = "https://vuquoccuong.000webhostapp.com/ServerHelloBaby/$upload_path/$fileName";
 
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
  }
}
  
  else
  {   
     $code = "code25";
  $msg = "Sorry, only JPG, JPEG, PNG & GIF files are allowed";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror); 
  }
   
   }
   echo json_encode($object);
}

?>