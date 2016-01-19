<?php        
   if($_POST == null){
		   echo "No Data Received";        
	}
	else{
		$file;
		$json_array;
		$route;
		$sql;
		$walk_conn;
		$post = $_POST;

		$walk_conn=mysqli_connect("db.dcs.aber.ac.uk","csadmgp07","c54admgp07","csgp07_13_14");// or die("Cannot Connect");
				   
		$file = fopen('datafile.txt','ab');	
		$json = implode($_POST);

		$json_array = json_decode($json,true);

		//getting the json values
		$title = $json_array['title'];
		$short_desc = $json_array['short_desc'];
		$long_desc = $json_array['long_desc'];
		$hours = $json_array['hours'];
		$hours = $hours/((1000 * 60) * 60);
		$distance = $json_array['distance'];
		$route = $json_array['route'];
		
		
		fwrite($file, $title . "\n" . $short_desc . "\n" . $long_desc . "\n" . $hours . "\n" . $distance . "\n" . $route);
			
		//add data to table
		$sql = "INSERT INTO List_of_Walks
		(title, shortDesc, longDesc, hours, distance)
		VALUES('$title', '$short_desc', '$long_desc', '$hours', '$distance')"; 
	$queryWalk = mysqli_query($walk_conn,$sql);
		$walkID = mysqli_insert_id($walk_conn);

	

		foreach($route as $loc){
			//loop through every location
			$longitude = $loc['longitude'];
			$latitude = $loc['latitude'];
			$time = $loc['time'];
			
			$sql = "INSERT INTO Location(walkID, latitude, longitude, timestamp)	
			VALUES('$walkID', '$latitude','$longitude', '$time')";
		$queryLoc =	mysqli_query($walk_conn,$sql);
			$locID = mysqli_insert_id($walk_conn);
			
			if(isSet($loc['description'])){
				//If description, location is point of interest
				$description = $loc['description'];
				$name = $loc['title'];
				$sql = "INSERT INTO Place_description(description, locationId, name)
						values('$description', '$locID', '$name')";
				$queryPlace = mysqli_query($walk_conn,$sql);
				$placeId = mysqli_insert_id($walk_conn);
				if(isSet($loc['images'])){
				$photoCount = 0;
				//If there are images decode and rename them
					foreach($loc['images']as $image){
						$image = implode($image);
						$image = str_replace("\\", "", $image, $count);
						$image =  base64_decode($image);
						$photoName = $walkID . "_" . $locID . "_" . $placeId . "_" . $photoCount;
						file_put_contents("images/".$photoName . ".jpg",$image);
						$photoCount++;
						$sql = "INSERT INTO Photo(photoName, placeId)
						values('$photoName', '$placeId')";
						$queryPhoto =	mysqli_query($walk_conn,$sql);
					}
				}
			}
		fwrite($file, "\n" . $loc . " " . $latitude .  " " . $longitude);
		}

		fwrite($file, "\n" .$queryWalk ."\n" . $queryLoc ."\n" . $queryPlace ."\n" . $queryPhoto);
		

		fclose($file);
		mysqli_close($walk_conn);
		

   }
?>