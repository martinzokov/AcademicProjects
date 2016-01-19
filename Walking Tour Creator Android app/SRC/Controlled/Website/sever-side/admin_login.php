<?php 
session_save_path('../../web/sessions');
session_start();
	if(!isSet($_SESSION['gpadmin'])){
		header("Location: logon.php");	
	}
?>
<!DOCTYPE html> 


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	
	
	<title>Table Control</title>	
	</head>
	
	<body> 
		<p>This will create the following tables
					<br>Location
					<br>List of Walks
					<br>Place Description
					<br>Photos</p>
				
			

			<p><form action = "c_tables.php" method = "post" name = "tableForm"> 
				<input type = "submit" name = "table_create" value = "Create Tables and Join" />
			</form>
			</p>
		<a href = "list_table.php">View Tables</a><br>
		<a href = "field_form.php">Add Data to Fields</a><br>
		<a href = "logon.php">Logout Page</a>
	</body>
</html>
