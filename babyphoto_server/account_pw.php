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
  $password =$_POST['password'];

if(isset($password) && isset($idaccount)){
 $query="UPDATE Account SET password = '$password' WHERE idaccount = '$idaccount'";
 $dataupdate = mysqli_query($con,$query);
}

 if ($dataupdate) {
 $code = "code13";
  $msg = "successfully";
  $array= array();
  $object = new Data($code,$msg,$array);
  }else{
    $code = "code22";
  $msg = "connect error";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror);
  }
  echo json_encode($object);
?>