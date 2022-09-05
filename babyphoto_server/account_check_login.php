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

  $email_check = $_POST['email'];
  $password_check = $_POST['password'];

  if (isset($email_check) && isset($email_check)) {
  	  $query = "SELECT  idaccount FROM Account WHERE email = '$email_check' AND password = '$password_check' ";
  $data = mysqli_query($con,$query);

  if($data){
  $row = mysqli_fetch_assoc($data);
  $idaccount = $row['idaccount'];
  if( is_null($idaccount)){

    $code = "code13";
    $msg = "Thông tin tài khoản hoặc mật khẩu không chính xác";
    $array= array();
    $object = new Data($code,$msg,$array);

   }else{
    $code = "code12";
    $msg = "Thông tin tài khoản chính xác";
    $array= array();
    array_push($array, $idaccount)
    $object = new Data($code,$msg,$array);
   }
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
    $object =  new Data($code,$msg,$arrayerror);
    echo json_encode($object);
  }

?>