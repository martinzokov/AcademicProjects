<?php
session_save_path('../../web/sessions');
session_start();

	if(!isSet($_SESSION['gpadmin'])){
		header("Location: logon.php");	
	}
$con=mysqli_connect("db.dcs.aber.ac.uk","csadmgp07","c54admgp07","csgp07_13_14");

if(! $con ){
  die('Could not connect: ' . mysqli_error());
}
/**Deletes the location table
*@param $con The database connection
*/
function deleteLocationTable($con){
	$sql = "DROP TABLE IF EXISTS csgp07_13_14 . Location";
	if(mysqli_query($con, $sql)){
		echo "Table Location deleted successfully\n";
	}
	else{
		echo "Could Not Delete Location Table" . mysqli_error($con);
	}
}
/**Deletes the List_of_Walks table
*@param $con The database connection
*/
function deleteListOfWalksTable($con){
	$sql = "DROP TABLE IF EXISTS csgp07_13_14 . List_of_Walks";
	if(mysqli_query($con, $sql)){
		echo "Table List of Walks deleted successfully\n";
	}
	else{
		echo "Could Not List of Walks Location Table" . mysqli_error($con);
	}	
}
/**Deletes the Place_description table
*@param $con The database connection
*/
function deletePlaceDescriptionTable($con){
	$sql = "DROP TABLE IF EXISTS csgp07_13_14 . Place_description";
	if(mysqli_query($con, $sql)){
		echo "Table Place Description deleted successfully\n";
	}
	else{
		echo "Could Not Delete Place Description Table" . mysqli_error($con);
	}
}
/**Deletes the Photos table
*@param $con The database connection
*/
function deletePhotoTable($con){
	$sql = "DROP TABLE IF EXISTS csgp07_13_14 . Photo";
	if(mysqli_query($con, $sql)){
		echo "Table Photo deleted successfully\n";
	}
	else{
		echo "Could Not Delete Photo Table" . mysqli_error($con);
	}	
}

if(isSet($_POST["table_delete"])){
	
			deletePhotoTable($con);	
			deletePlaceDescriptionTable($con);
			deleteLocationTable($con);
			deleteListOfWalksTable($con);

}
else{
	echo "No Info";
}

	

	mysqli_close($con);
	echo '<p><a href = "list_table.php">Continue </a></p>';
	echo '<p><a href = "admin_login.php">Create Tables</a></p>';

?>