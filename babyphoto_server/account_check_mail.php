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

  if (isset($email_check)) {
  	  $query = "SELECT  count(idaccount) as count_email FROM Account WHERE email = '$email_check'";
  $data = mysqli_query($con,$query);

  if($data){
  $row = mysqli_fetch_assoc($data);
  $count_email = $row['count_email'];
  if($count_email == 1){
    $code = "code12";
    $msg = "Email đã trùng vui lòng thử lại";
    $array= array();
    $object = new Data($code,$msg,$array);
   }else{
    $code = "code13";
    $msg = "successfully";
    $array= array();
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