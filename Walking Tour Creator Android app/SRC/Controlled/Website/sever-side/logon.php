<?php
session_save_path('../../web/sessions');
session_start();

	function logout(){
		unset($_SESSION['gpadmin']);
		session_destroy();
	}
	
	function login(){
		if(($_POST["name"] == "grp7admin") && ($_POST["pword"] == "Wc7gp")){	
		//variable = mysqli_escape_string($_POST["name"]);
			
			$_SESSION['gpadmin'] = $_POST["name"];
			header("Location: admin_login.php");
		}
		else{
			echo ("Invalid Logon");	
		}
	}
	
	if(isSet($_POST["name"])){
		login();	
	}
	elseif(isSet($_POST["logout"])){
		logout();
	}

if(isSet($_SESSION['gpadmin'])){
		echo '
		<form action = "' . $_SERVER["PHP_SELF"] .'" method = "post" name = "logoutForm">
		<input type = "hidden" value = "Logout" name = "logout"/>
		<input type = "submit" value = "Logout"/>
		</form>';
		
		echo '<p><a href = "list_table.php">List Tables </a></p>';
		echo '<p><a href = "admin_login.php">Create Tables</a></p>';
}
else{
		echo '
		<form action = "' . $_SERVER["PHP_SELF"] .'" method = "post" name = "logoutForm"> 
		<input type = "text" value = "User Name" name = "name"/>
		<input type = "password" value = "Password" name = "pword"/>
		<input type = "submit" value = "Login"/>
		</form>';
}
?>