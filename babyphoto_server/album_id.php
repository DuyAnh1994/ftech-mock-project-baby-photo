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

  $idalbum = $_POST['idalbum'];
  if(isset($idalbum)){

  $query = "SELECT  *FROM Album WHERE idalbum = '$idalbum'";
  $data = mysqli_query($con,$query);

  class Album
  {
    function Album($idalbum,$idaccount,$urlimage,$name,$gender,$birthday,$relation,$amountimage)
    {
      $this -> idalbum = $idalbum;
      $this -> idaccount = $idaccount;
      $this -> urlimage =$urlimage;
      $this -> name = $name;
      $this -> gender = $gender;
      $this -> birthday =$birthday;
      $this -> relation = $relation;
      $this -> amountimage = $amountimage;
    }
  }

  $arrayAlbum = array();
  while($row = mysqli_fetch_assoc($data)){
    array_push($arrayAlbum, new Album($row['idalbum'],$row['idaccount'],$row['urlimage'],$row['name'],$row['gender'],$row['birthday'],$row['relation'],$row['amountimage']));
  }


if ($data) {
  $code = "code12";
  $msg = "successfully";
  $object = new Data($code,$msg,$arrayAlbum);
}else{
  $code = "code22";
  $msg = "connect error";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror);
}
 echo json_encode($object);

}else{
  $code = "code23";
  $msg = "Vui lòng điền đầy đủ thông tin";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror);
   echo json_encode($object);
 }

  
?>