<?php

  require "Connect.php";

  $idalbum = $_POST['$idalbum'];
  $query = "SELECT  *FROM Image WHERE idalbum = '$idalbum'";
  $data = mysqli_query($con,$query);

  class Image
  {
    function Image($idimage,$idalbum,$urlimage,$description,$timeline)
    {
      $this -> idimage = $idimage;
      $this -> idalbum = $idalbum;
      $this -> urlimage =$urlimage;
      $this -> description = $description;
      $this -> timeline = $timeline;
    }
  }

  $arrayImage= array();
  while($row = mysqli_fetch_assoc($data)){
    array_push($arrayImage, new Image($row['idimage'],
      $row['idalbum'],
      $row['urlimage'],
      $row['description'],
      $row['timeline']));
  }

  
  class Data
  {
    function Data($code,$msg,$data){
      $this -> code = $code;
      $this -> msg = $msg;
      $this -> data =$data;
    }
  }

if ($data) {
  $code = "code12";
  $msg = "successfully";
  $object = new Data($code,$msg,$arrayImage);
}else{
  $code = "code22";
  $msg = "connect error";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror);
}
 echo json_encode($object);
?>