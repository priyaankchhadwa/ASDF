<?php

include("db.php");
$prim = $_POST['prim'];
$type = $_POST['type'];
$response = array();

$conn = mysqli_connect($host, $user, $pass, $database);  

if ($conn) {

    if($type == 'user')
    {
        $sql = 'SELECT * FROM `allotment` WHERE prof_id = "'.$prim.'"';
    }
    else if($type == 'admin')
    {
        $sql = 'SELECT * FROM `allotment`';   
    }
    else
    {
        $sql = 'SELECT * from allotment where allotment.prof_id in (Select prof_profile.prim from prof_profile where dept = "'.$type.'")';
    }
    
  
	$retval=mysqli_query($conn, $sql);    
	if(mysqli_num_rows($retval) > 0){
  		
  		$i = 0; 
    	while($row = mysqli_fetch_assoc($retval)){

    		$response[$i]['prim'] = $row['prim'];
    		$response[$i]['prof_id'] = $row['prof_id'];
    		$response[$i]['position'] = $row['position'];
    		$response[$i]['exam_id'] = $row['exam_id'];
    		$response[$i]['room'] = $row['room'];

            $sql2 = 'SELECT `name`, `rank`, `dept` FROM `prof_profile` WHERE prim = "'.$response[$i]['prof_id'].'"';
            $retval2 = mysqli_query($conn,$sql2);
            if(mysqli_num_rows($retval2) > 0){
                while($row2 = mysqli_fetch_assoc($retval2)){

                    $response[$i]['name'] = $row2['name'];
                    $response[$i]['rank'] = $row2['rank'];
                    $response[$i]['dept'] = $row2['dept'];
                }
            }

            $sql3 = 'SELECT `scode`, `edate`, `etime` FROM `exam` WHERE prim = "'.$response[$i]['exam_id'].'"';
            $retval3 = mysqli_query($conn,$sql3);
            if(mysqli_num_rows($retval3) > 0){
                while($row3 = mysqli_fetch_assoc($retval3)){

                    $response[$i]['scode'] = $row3['scode'];
                    $response[$i]['edate'] = $row3['edate'];
                    $response[$i]['etime'] = $row3['etime'];
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