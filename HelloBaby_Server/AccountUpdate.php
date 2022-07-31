<?php

 require "Connect.php";

 $password =$_POST['password'];
 $firstname =$_POST['firstname'];
 $lastname =$_POST['lastname'];
 $idaccount =$_POST['idaccount'];

if( empty($password)){
     if(empty($firstname)){
        if (empty($lastname)) {
           echo "FAIL";
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
    echo "Success";
  }else{
    echo "Fail";
  }
?>