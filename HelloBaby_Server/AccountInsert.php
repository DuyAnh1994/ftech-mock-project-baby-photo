<?php

 require "Connect.php";

 $email =$_POST['email'];
 $password =$_POST['password'];
 $firstname =$_POST['firstname'];
 $lastname =$_POST['lastname'];
 $idaccount =$_POST['idaccount'];

 	$queryinsert = "INSERT INTO Account VALUES ('$email','$password','$firstname','$lastname','$idaccount')";
 	$datainsert = mysqli_query($con,$queryinsert);

  if ($datainsert) {
  	echo "Success";
  }else{
  	echo "Fail";
  }
?>