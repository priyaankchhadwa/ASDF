<?php

include("db.php");
$username = $_POST['username'];
$response = array();

$conn = mysqli_connect($host, $user, $pass, $database);  

if ($conn) {

	$sql = 'SELECT * FROM `prof_profile` WHERE username = "'.$username.'"';  
	$retval=mysqli_query($conn, $sql);    
	if(mysqli_num_rows($retval) > 0){
  		
  		$i = 0; 
    	while($row = mysqli_fetch_assoc($retval)){

    		$response[$i]['prim'] = $row['prim'];
    		$response[$i]['name'] = $row['name'];
    		$response[$i]['rank'] = $row['rank'];
    		$response[$i]['username'] = $row['username'];
    		$response[$i]['password'] = $row['password'];
    		$response[$i]['duty_amount'] = $row['duty_amount'];

    		$sql2 = 'SELECT * FROM `notifications`';
    		$retval2 = mysqli_query($conn,$sql2);
    		if(mysqli_num_rows($retval2) > 0){
    			while($row2 = mysqli_fetch_assoc($retval2)){

    				$i++;
    				$response[$i]['date'] = $row2['date'];
    				$response[$i]['message'] = $row2['message'];
    			}
    		}

	    	$i++;
		}

	}else{
		$response[0]["prim"] = "none";
	}

	echo json_encode($response);

	mysqli_close($conn);

}else{
	//Connection error	
}

?>