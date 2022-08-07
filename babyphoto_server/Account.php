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
  	array_push($mangAccount, new Account($row['email'],$row['password'],$row['firstname'],$row['lastname'],$row['idaccount']));
  }
  echo json_encode($arrayAccount);
?>