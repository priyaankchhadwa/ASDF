<?php

include("db.php");
$username = $_POST['username'];
$response = array();

$conn = mysqli_connect($host, $user, $pass, $database);  

if ($conn) {

	$sql = 'SELECT * FROM `exam` ORDER BY `edate`,`etime`';  
	$retval=mysqli_query($conn, $sql);    
	if(mysqli_num_rows($retval) > 0){
  		
  		$i = 0; 
    	while($row = mysqli_fetch_assoc($retval)){

    		$response[$i]['prim'] = $row['prim'];
    		$response[$i]['scode'] = $row['scode'];
    		$response[$i]['edate'] = $row['edate'];
    		$response[$i]['etime'] = $row['etime'];
    		$response[$i]['eslot'] = $row['eslot'];
    		$response[$i]['room'] = $row['room'];

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