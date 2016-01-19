<?php
	session_save_path('../../web/sessions');
	session_start();
	if(!isSet($_SESSION['gpadmin'])){
		header("Location: logon.php");	
	}
	$con=mysqli_connect("db.dcs.aber.ac.uk","csadmgp07","c54admgp07","csgp07_13_14");
	$LIST_OF_WALKS = "List_of_Walks";
	$LOCATION = "Location";
	$PHOTO = "Photo";
	$DESCRIPTION = "Place_description";
	send_table_query($LIST_OF_WALKS, $con);
	echo "<br>";
	send_table_query($LOCATION, $con);
	echo "<br>";
	send_table_query($PHOTO, $con);
	echo "<br>";
	send_table_query($DESCRIPTION, $con);
	
	
	function send_table_query($foo, $con){
		show_table(mysqli_query($con, "SELECT * FROM $foo"), $foo);
	}
	
	
	function show_table($show_value, $foo){
		if($show_value == NULL){
				echo("error showing this database");
		} else{
			echo "<h2>$foo</h2>";
			echo "<table border='1'>";
			while ($foo = mysqli_fetch_array($show_value, MYSQLI_ASSOC)){
				echo "<tr>";
				foreach($foo as $key => $value){
					echo "<td>" . $value . "</td>";
				}
			}
			echo "</tr>";
			echo "</table>";
		}
	}
	mysqli_close($con);
			echo <<<EOT
			<p>
			<form action = "d_tables.php" method = "post" name = "tableForm"> 
				<input type = "hidden" name = "table_delete">
				<input type = "submit" value = "Delete Tables" name = "table_delete"/>
			</form>
			</p>
			<a href = "field_form.php">Add Data to Fields</a><br>
			<a href = "admin_login.php">Create Tables</a><br>
			<a href = "logon.php">Logout Page</a>
EOT;
?>
