<!DOCTYPE html>
<html lang="en">
<head>
        <title>List of walks</title>
       <link rel="stylesheet" href="../css/style.css" media="screen" />
        <link rel="stylesheet" href="../css/main.css" media="screen" />
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">  
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBMaDwxzucsIfiT1sYyFKWIvDljXOWSeM0&sensor=false"></script>
    <!-- Lightbox to display images, http://shadowbox-js.com/, used un the Non-commercial Licence agreement -->
    <link rel="stylesheet" type="text/css" href="shadowbox-3.0.3/shadowbox.css">
        <script type="text/javascript" src="shadowbox-3.0.3/shadowbox.js"></script>
        <script type="text/javascript">
                Shadowbox.init();
        </script>
   
</head>
<body>
        <header>
                <h1>
					<?php
						include "database_layer.php";
						///creates a new instance of the database walk option
						$database = new DatabaseWalk();
						$database->connect();
						$walk = $database->santise_item($_GET['walk']);
						$walk_id = $database->santise_item($_GET['walk_id']);
						echo "<h2>" .  $walk. "</h2>"; 
					?>
				</h1>
        </header>
        <nav>
                <ul>
                        <li><a href="index.php">Home</a></li>
                        <li><a href="list_walks.php">List Walks</a></li>
                </ul>
        </nav>
        <div id="container">
				       
					<?php
						       
						$query = "SELECT * from List_of_Walks WHERE title='$walk' AND id='$walk_id'";
						$database->prepare_query($query);
						//sends the query
						$database->send_query($database->get_query());
						//outputs the results
						// An option to make it OO with having a class called Walk, and in here creating a new Walk...to which would be outputted in the same way
					
					echo '<div id = "sideBarleft">';
							while ($walk = mysqli_fetch_array($database->get_result())){
							echo "this is the short desc " . $walk['shortDesc'];
							echo "<br/>";
							echo "This is the long walk for the walk " .  $walk['longDesc'];
							echo "<br/>";
							echo "this is the time taken for the walk ". $walk['hours'];
							echo "<br/>";
							echo "This is the distance for the walk " . $walk['distance'];
							echo "<br/>";
						}
						echo '</div>';
						//get longitude and latitude
						$query = "SELECT l.latitude, l.longitude FROM Location l JOIN List_of_Walks lw ON (lw.id = l.walkID) WHERE l.walkID='$walk_id'";
						$database->prepare_query($query);
						$database->send_query($database->get_query());
						$lat = array();
						$long = array();
						$title = array ();
						$name = array();
						$shortDesc = array();
						$longDesc = array();
						$placeId = array();
						while ($value = mysqli_fetch_array($database->get_result())){
							$lat[] = $value['latitude'];
							$long[] = $value['longitude'];
																															 
						}
						$query = "SELECT p.name, p.description, p.id FROM Place_description p JOIN Location l ON (p.locationID = l.id) JOIN List_of_Walks lw ON (lw.id = l.walkID) WHERE l.walkID='$walk_id'";
						$database->prepare_query($query);
						$database->send_query($database->get_query());
						while ($result = mysqli_fetch_array($database->get_result())){
							$title[] = $result['name'];
							$shortDesc[] = $result['description'];
							$placeId[] = $result['id'];
						}      
	
						$query = "SELECT p.photoName, p.placeId FROM Photo p JOIN Place_description pd on(pd.id = p.placeId) JOIN Location l ON (pd.locationID = l.id) JOIN List_of_Walks lw ON (lw.id = l.walkID) WHERE l.walkID='$walk_id'";
						$database->prepare_query($query);
						$database->send_query($database->get_query());                                                      
						$imageid = array();
						$placeIdImage = array();
						while ($value = mysqli_fetch_array($database->get_result())) {
							$imageid[] = $value['photoName'];
							$placeIdImage[] = $value['placeId'];
						}
					
						//Initialized for EOF JavaScript below
						$latJ = json_encode($lat);
						$lngJ = json_encode($long);
						$titleJ = json_encode($title);
						$shortDescJ = json_encode($shortDesc);
						$imgJ = json_encode($imageid);
						$imgPID = json_encode($placeIdImage);
						$nameJ = json_encode($name);
						$placeIdJ = json_encode($placeId);
						
					echo <<<EOF
				<div id="map-canvas"> 
					 <script type="text/javascript">

						var map;
						//sets up the array of lat values from the php array
						var lat = $latJ;
						//sets up the array of long values from the php long array
						var lng = $lngJ;
						var title = $titleJ;
						var shortDesc = $shortDescJ;
						var img = $imgJ;
						var imgPlaceID = $imgPID;
						var descID = $placeIdJ;
						//var name = $nameJ;
						function initialize() {
						var mapOptions = {
							zoom: 15,
							center: new google.maps.LatLng(lng[0], lat[0])
						};
						//assigns the instance of map to the map canvas id element we use.
						var map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
						//this creates a new popup window when we click on a marker.
						var infowindow = new google.maps.InfoWindow();
						//An array to store the Lat and Long values
						var latLngValues = new Array();
						//var marker = new Array();
						for (i = 0; i < lat.length; i++){
							//populates the array with the correct google maps co-ordinates.
							latLngValues[i] = new google.maps.LatLng(lng[i], lat[i]);
						}
						// Reference http://stackoverflow.com/questions/11467070/how-to-set-a-popup-on-markers-with-google-maps-api for moving it 
						// inside the function to allow to. Based on the code above, changed to map with our application- Renamed methods to make it 
						// more readable, but code is based on the link above.
						for (j = 0;j < latLngValues.length; j++){
						  
							//sets a new marker
								if (title[j] != null){
									var POI = new google.maps.Marker({
										//sets the position to be the co-ords from the loop above
										position: latLngValues[j],
										map: map
									});
									//get all images from the database
									var images = "";
									for (l = 0; l < img.length; l++){
										if( imgPlaceID[l]==descID[j] ){
											images +="<img src='http://users.aber.ac.uk/mda/csgp07/images/"+img[l]+".jpg' height='87' width='156' ></img>";
										}
									}
									popupInfoWindow(map, infowindow, "<h4>"+title[j]+"</h4>"+"<p>"+shortDesc[j]+"</p>"+images, POI);
									
								}
						//adds a listener to each of the marker items
							

						}  
						function popupInfoWindow(map, infowindow, insideMarkerText, POI) {
									google.maps.event.addListener(POI, 'click', function() {
									//sets a standard message TODO: GRAB SOME PHP
									infowindow.setContent(insideMarkerText);
									//opens the info window on the marker on the map
									infowindow.open(map,POI);
							});
						}
						
						//TODO        
						
						var flightPath = new google.maps.Polyline({
								path: latLngValues,
								geodesic: true,
								strokeColor: '#FF0000',
								strokeOpacity: 1.0,
								strokeWeight: 2
						});

						flightPath.setMap(map);
						}
						google.maps.event.addDomListener(window, 'load', initialize);
					</script>
				</div>
				<div id = "sideBarRight">
					<div id = "PictureTable">
EOF;
							
							//display all images in the side bar
							echo "number of Images " . count($imageid);
							for ($i = 0; $i < count($imageid); $i++){
								echo "<div id='imagedisplay'>";	
									echo "<div id='thumb'>";
										echo "<a href='http://users.aber.ac.uk/mda/csgp07/images/".$imageid[$i].".jpg'rel='shadowbox'><img src='http://users.aber.ac.uk/mda/csgp07/images/".$imageid[$i].".jpg' height='87' width='156' ></img></a>";
									echo "</div>";
								echo "</div>";
							}
							$database->close_connection();
						?>
					</div>
				</div>
		</div>				
	</div> 
</body>
</html>
