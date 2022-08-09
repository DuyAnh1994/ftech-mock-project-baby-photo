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

 $password =$_POST['password'];
 $firstname =$_POST['firstname'];
 $lastname =$_POST['lastname'];
 $idaccount =$_POST['idaccount'];

if( empty($password)){
     if(empty($firstname)){
        if (empty($lastname)) {
  $code = "code23";
  $msg = "no data";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror);
  echo json_encode($object);
    }else{
      $queryupdatepw = "UPDATE Account SET lastname = '$lastname' WHERE idaccount = '$idaccount'";
      $datainsert = mysqli_query($con,$queryupdatepw);
    }
  }else{
      $queryupdatepw = "UPDATE Account SET firstname = '$firstname' WHERE idaccount = '$idaccount'";
      $datainsert = mysqli_query($con,$queryupdatepw); 
  }
}else{
      $queryupdatepw = "UPDATE Account SET password = '$password' WHERE idaccount = '$idaccount'";
      $datainsert = mysqli_query($con,$queryupdatepw);
}

 if ($datainsert) {
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