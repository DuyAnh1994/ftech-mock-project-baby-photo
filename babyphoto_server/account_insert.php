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

 $query = "SELECT * FROM Account ORDER BY idaccount DESC Limit 1";
 $data = mysqli_query($con,$query);
 $row = mysqli_fetch_assoc($data);
 $idaccount = $row['idaccount'];
 $idaccount = $idaccount +1;

if(is_null($email) || is_null($password) || is_null($firstname)
    || is_null($lastname)){
  $code = "code21";
  $msg = "Incorrect transmission data ";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror);
  echo json_encode($object);
}else{
  $queryinsert = "INSERT INTO Account VALUES ('$idaccount','$email','$password','$firstname','$lastname')";
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