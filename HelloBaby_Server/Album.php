<?php

  require "Connect.php";

  $query = "SELECT * FROM Album";
  $data = mysqli_query($con,$query);

  class Album
  {
  	function Album($idalbum,$idaccount,$urlimage,$name,$gender,$birthday,$relation,$amountimage)
  	{
  		$this -> idalbum = $idalbum;
  		$this -> idaccount = $idaccount;
  		$this -> urlimage =$urlimage;
  		$this -> name = $name;
      $this -> gender = $gender;
      $this -> birthday =$birthday;
      $this -> relation = $relation;
      $this -> amountimage = $amountimage;
  	}
  }

  $arrayAlbum = array();
  while($row = mysqli_fetch_assoc($data)){
  	array_push($arrayAlbum, new Album($row['idalbum'],
                                      $row['idaccount'],
                                      $row['urlimage'],
                                      $row['name'],
                                      $row['gender'],
                                      $row['birthday'],
                                      $row['relation'],
                                      $row['amountimage']));
  }
  echo json_encode($arrayAlbum);
?>