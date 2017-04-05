<?php  

include("db.php");
$result = array();

$conn = mysqli_connect($host, $user, $pass, $database);  
if ($conn) {

if(isset($_POST["username"],$_POST["password"])){
    $username = $_POST["username"];
    $password = $_POST["password"];
}else{
    $username = "";
    $password = "";
}

$sql = 'SELECT * FROM prof_profile';  
$retval=mysqli_query($conn, $sql);  
  
if(mysqli_num_rows($retval) > 0){
  $i=0; 
    while($row = mysqli_fetch_assoc($retval)){ 
	    if($row['username']==$username && $row['password']==$password){
	        $i=1; 
	        $result[0]['name']=$row['name']; 
	        $result[0]['id']=$row['prim']; 
	        $result[0]['rank']=$row['rank']; 
	        $result[0]['username']=$row['username']; 
	        $result[0]['password']=$row['password']; 
	        $result[0]['duty_amount']=$row['duty_amount'];
	        $result[0]['type']=$row['type'];
			break;
		}	
	}
}

if($i==1){

	$result[0]['error'] = 'false';
	$result[0]['message'] = 'success';
	//$result = "success:".$prof_username.":".$prof_password.":".$prof_id.":".$prof_name.":".$prof_rank.":".$prof_duty_amount;
	//$result = "success:".$prof_username.":".$prof_password.":".$prof_id.":".$prof_name.":".$prof_rank.":".$prof_duty_amount;
/*	$sql = 'SELECT * FROM prof_det WHERE prof_id="'.$prof_id.'"';
	$retval=mysqli_query($conn, $sql);
	if(mysqli_num_rows($retval) > 0){
		while($row = mysqli_fetch_assoc($retval)){
			$result = $result . "%" . $row['department'] . "_" . $row['subject'] . "_" . $row['year'];
		}
	}*/
	//echo $result;
	//echo json_encode($result);
}
else{

	$result[0]['error'] = 'true';
	$result[0]['message'] = 'Either emailid or password is incorrect. Please Try again!';
	//echo "Either emailid or password is incorrect. Please Try again!" ;
}

	echo json_encode($result);
    mysqli_close($conn); 
}
else {
	print "Connection NOT established or Database NOT Found ";
} 

?>