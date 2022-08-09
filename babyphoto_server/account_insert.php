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

 $email =$_POST['email'];
 $password =$_POST['password'];
 $firstname =$_POST['firstname'];
 $lastname =$_POST['lastname'];
 $idaccount =$_POST['idaccount'];

if(is_null($email) || is_null($password) || is_null($firstname)
    || is_null($lastname) || is_null($idaccount)){
  $code = "code21";
  $msg = "Incorrect transmission data ";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror);
  echo json_encode($object);
}else{
  $queryinsert = "INSERT INTO Account VALUES ('$email','$password','$firstname','$lastname','$idaccount')";
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