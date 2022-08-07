<?php

 require "Connect.php";

 $idalbum =$_POST['idalbum'];
 $idaccount =$_POST['idaccount'];
 $urlimage =$_POST['urlimage'];
 $name =$_POST['name'];
 $gender =$_POST['gender'];
 $birthday =$_POST['birthday'];
 $relation =$_POST['relation'];
 $amountimage = $_POST['amountimage'];

 	$queryinsert = "INSERT INTO Album 
                    VALUES ('$idalbum','$idaccount','$urlimage','$name','$gender','$birthday','$relation','$amountimage')";
 	$datainsert = mysqli_query($con,$queryinsert);

  if ($datainsert) {
  	echo "Success";
  }else{
  	echo "Fail";
  }
?>