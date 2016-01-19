<?php
session_save_path('../../web/sessions');
session_start();
if(!isSet($_SESSION['gpadmin'])){
		header("Location: logon.php");	
	}
?>

<!doctype html>
<html lang="en">
<head>
	<title>Add info to Database </title>
</head>
<body>
	<h1> Walks</h1>
	<form action="add_fields.php" method="post">

		<p> Title </p><input type="textbox" name="title"/>
		<p> Short Desc </p><input type="textbox" name="sh_de"/>
		<p> Long Desc </p><input type="textbox" name="l_de"/>
		<p> Hours </p><input type="textbox" name="hours"/>
		<p> Distance </p><input type="textbox" name="distance"/>

	<h1> Location </h1>
	<form action="add_fields.php" method="post">


		<p> Latt </p><input type="textbox" name="latt"/>
		<p> Longitude </p><input type="textbox" name="long"/>
		<p> Time Stamp </p><input type="textbox" name="time"/>

	<h1> Place Desc </h1>
	<form action="add_fields.php" method="post">
		<p> desc </p><input type="textbox" name="desc"/>
		<p> name </p><input type="textbox" name="place_name"/>
	<h1> Photo </h1>
	<form action="add_fields.php" method="post">

		<p> photoname </p><input type="textbox" name="photo"/>
		<p><input type="submit"/></p>
	</form>
	
	<a href = "list_table.php">View Tables</a><br>
</body>
</html>
