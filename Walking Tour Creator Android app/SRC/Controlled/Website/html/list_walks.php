<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>List of walks</title>
	<link rel="stylesheet" href="../css/main.css" media="screen" />
	<link rel="stylesheet" href="../css/walk_list.css" media="screen" />
</head>
<body>
	<header>
		<h1>Aber Tour</h1>
	</header>
	<nav>
		<ul>
			<li><a href="index.php">Home</a></li>
		</ul>
	</nav>
	<section id="intro"></section>
	<div id="content">
				<article>
					<section>
						<h2>List Of Walks</h2>
						<p> Below is the list of walks currently available, please click on them to view the walk.</p>
					</section>
				</article>	
	</div>		
		<aside>
			<section>	
				<?php
					include "database_layer.php";
					///creates a new instance of the database walk option
					$database = new DatabaseWalk();
					//connects to the database
					$database->connect();
					//some Query
					$query ="SELECT * from List_of_Walks";
					//checks against SQL injection
					$database->prepare_query($query);
					//sends the query
					$database->send_query($database->get_query());
					//outputs the results
					// An option to make it OO with having a class called Walk, and in here creating a new Walk...to which would be outputted in the same way
					$database_count = new DatabaseWalk();
					$database_count->connect();
					echo "<div id='allwalks'>";
					//list all the walks in the database
					while ($walk = mysqli_fetch_array($database->get_result())){
					
						 $query = "SELECT * from Location l INNER JOIN Place_description d on l.id = d.locationID";
						 $database_count->prepare_query($query);
						 $database_count->send_query($database_count->get_query());
						 $count = mysqli_fetch_array($database_count->get_result());
						 $title = $walk["title"];
						 $walkIDA = $walk["id"];
						 $wTitle = $walk["title"];
						 $wShortDesc = $walk["shortDesc"];
							echo <<<EOT
						
						<div id="container">
							<div class="walk">
								<br/>
								<p>
									<a href='walk_details.php?walk=$title&amp;walk_id=$walkIDA'>$wTitle </a>
									<br/>
									<div class="walk" id="box">
										  $wShortDesc
									</div>
								</p>
							</div>
						</div>
EOT;
							
					}
					$database->close_connection();
				?>
			</section>
		</aside>
	<footer>
	</footer>
</body>
</html>