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
 $firstname =$_POST['firstname'];
 $lastname =$_POST['lastname'];
 $idaccount =$_POST['idaccount'];

if(isset($email) && isset($firstname) && isset($lastname) && isset($idaccount)){
 $query="UPDATE Account SET email = '$email' ,firstname = '$firstname' ,lastname = '$lastname' WHERE idaccount = '$idaccount'";
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