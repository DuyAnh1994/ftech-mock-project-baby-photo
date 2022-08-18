<?php

  require "Connect.php";

  $idaccount = $_POST['idaccount'];
  $query = "SELECT  *FROM Account WHERE idaccount = '$idaccount'";
  $data = mysqli_query($con,$query);

  class Account
  {
    function Account($email,$password,$firstname,$lastname,$idaccount)
    {
      $this -> email = $email;
      $this -> password = $password;
      $this -> firstname =$firstname;
      $this -> lastname = $lastname;
        $this -> idaccount = $idaccount;
    }
  }

  $arrayAccount = array();
  while($row = mysqli_fetch_assoc($data)){
    array_push($arrayAccount, new Account($row['email'],$row['password'],$row['firstname'],$row['lastname'],$row['idaccount']));
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
  $object = new Data($code,$msg,$arrayAccount);
}else{
  $code = "code22";
  $msg = "connect error";
  $arrayerror = array();
  $object = new Data($code,$msg,$arrayerror);
}
 echo json_encode($object);
?>