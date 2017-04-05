<?php

include("db.php");
$message = $_POST['message'];
if(isset($message))
{
	$conn = mysqli_connect($host, $user, $pass, $database);  

if ($conn) {

	$sql = "INSERT INTO notifications (message) VALUES ('$message')";
	 
	mysqli_autocommit($conn, FALSE);
	mysqli_query($conn,$sql);
 	mysqli_commit($conn);
	if( mysqli_commit($conn)){  

		echo "success";  

	}else{  
		echo "failed";
	}  
  
	mysqli_close($conn);
	
}else{
	//Connection error	
}
}


?>