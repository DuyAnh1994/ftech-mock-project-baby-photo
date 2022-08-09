<?php

  require "Connect.php";

  $query = "SELECT  *FROM Account";
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
  $array= array();
  array_push($array,new Data($code,$msg,$arrayAccount));
}else{
  $code = "code22";
  $msg = "connect error";
  $arrayerror = array();
  $array= array();
  array_push($array,new Data($code,$msg,$arrayerror));
}
 echo json_encode($array);
?>