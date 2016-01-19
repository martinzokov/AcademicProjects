<?php 
/**
 * A Database connection class. This reduces all common code with handling stuff with the database.
 */
	class DatabaseWalk {
		
		 private $connection;
		 private $query;
		 private $result;
		 private $sanitised_item;
		 
		
		/*
		 * Connects to the database, this sets the handle to the database connection
		 */
		public function connect(){
			$this->connection= mysqli_connect("db.dcs.aber.ac.uk", "csadmgp07","c54admgp07","csgp07_13_14") or die("Couldn't connect");
		}
		
		/**
		 * Prepares a query for the database, but it santises it first
		 * @param is the SQL query sent to the server
		 * @param uses the overloading way of checking whether there's any additional bits to the query.
		 */
		public function prepare_query($query, $query_extra=NULL){
			if($query_extra != NULL){
				$this->query = $query .mysqli_escape_string($this->get_connection(), $query_extra);
				echo "<br>" . $this->query;
			}
			else {
				$this->query = $query;
			}
		}
		
		/**
		 * Same as prepare_query but the query is santised and save for interaction with the database
		 * @param is the sanitised query
		 */
		public function send_query($query){
			$this->result = mysqli_query($this->get_connection(), $query) or die (mysqli_error($this->get_connection()));
		}	
		
		/**
		 * returns the connection from the database
		 */
		public function get_connection(){
				return $this->connection;
		}
		
		/** 
		 * Returns the sanitised query
		 */
		public function get_query (){
				return $this->query;
		}
		
		/**
		 * Returns the result from the Actual query with the database
		 */
		public function get_result(){
				return $this->result;
		}
		
		/**
		 * Closes a connection to a database
		 */
		 public function close_connection(){
			 mysqli_close($this->get_connection());
		 }
		 
		 /**
		  * Santises an item from SQL Injection
		  * @param is the object before sanitising it
		  * return the sanitised String value
		  */
		 public function santise_item($object){
				return $this->santise_item = strip_tags(mysqli_escape_string($this->get_connection(), $object));
		}
		
		
}
?>
