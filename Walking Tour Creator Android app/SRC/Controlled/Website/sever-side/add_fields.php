<?php
session_save_path('../../web/sessions');
session_start();
if(!isSet($_SESSION['gpadmin'])){
                header("Location: logon.php");        
        }

        $walk_id; 
        $walk_title; 
        $walk_short; 
        $walk_long; 
        $walk_hours;
        $walk_distance;
        $walk_Latt;
        $walk_Longitude;
        /**
         * checks to see if it's set, no point in doing it if it's not
         */
         $con=mysqli_connect("db.dcs.aber.ac.uk","csadmgp07","c54admgp07","csgp07_13_14") or die("Couldn't connect");

        if (isset($_POST["title"])){
                        $walk_title = mysqli_real_escape_string($con,$_POST['title']);
        }
        if (isset($_POST["sh_de"])){
                        $walk_short = mysqli_real_escape_string($con,$_POST['sh_de']);
        }
        if (isset($_POST["l_de"])){
                        $walk_long = mysqli_real_escape_string($con,$_POST['l_de']);
        }
        if (isset($_POST["hours"])){
                        $walk_hours = mysqli_real_escape_string($con,$_POST['hours']);
        }
        if (isset($_POST["distance"])){
                        $walk_distance = mysqli_real_escape_string($con,$_POST['distance']);
        }
        if (isset($_POST["latt"])){
                        $walk_Latt = mysqli_real_escape_string($con,$_POST['latt']);     
        }
        if (isset($_POST["long"])){
                        $walk_Longitude = mysqli_real_escape_string($con,$_POST['long']);
        }
       if (isset($_POST["time"])){
                        $walk_time = mysqli_real_escape_string($con,$_POST['time']);
        }
		if (isset($_POST['desc'])){
					$desc = mysqli_real_escape_string($con, $_POST['desc']);
		}
		if (isset($_POST['photo'])){
					$photo = mysqli_real_escape_string($con, $_POST['photo']);
		}
		if (isset($_POST['place_name'])){
					$name = mysqli_real_escape_string($con, $_POST['place_name']);
		}
        
        /**
         * Adds the information as new records to the Walks Table
         */
        $query = "INSERT INTO List_of_Walks ( title, shortDesc, longDesc, hours, distance) VALUES 
        ('$walk_title','$walk_short', '$walk_long', '$walk_hours', '$walk_distance')";
				
        if (mysqli_query($con,$query)){
                echo "Sent to the Database";
        }
		$walkID = mysqli_insert_id($con);
		 $queryLoc = "INSERT INTO Location (latitude, longitude, timestamp, walkID) VALUES 
        ('$walk_Latt','$walk_Longitude','$walk_time', '$walkID')";
		
		if(mysqli_query($con,$queryLoc)){
			echo "Sent to the Database";
		}
		$locID = mysqli_insert_id($con);
		$queryDesc = "INSERT INTO Place_description (description, locationId, name) VALUES 
        ('$desc', '$locID', '$name')";
		if(mysqli_query($con, $queryDesc)){
			echo "sent to Datbase";
		}
		$placeId = mysqli_insert_id($con);
		$queryPhoto = "INSERT INTO Photo (photoName, placeId) VALUES 
        ('$photo', '$placeId')";
		if(mysqli_query($con, $queryPhoto)){
			echo "sent";
		}
        else{
                echo "Could not add data" . mysqli_error($con);
        }
                echo'<p><a href = "list_table.php">View Tables</a><br>
                <a href = "field_form.php">Add Data to Fields</a><br>
                <a href = "logon.php">Logout Page</a></p>';
                mysqli_close($con);        
?>
