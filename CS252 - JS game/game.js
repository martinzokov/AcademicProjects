

var restartGame = function(){
	hero.gameLevel=1,
	hero.speed=256,
	hero.level=1,
	hero.health=100,
	hero.exp=0,
	hero.faceDirection=1,
	hero.rateOfFire=700,
	hero.projectileRange=200,
	hero.projectileSpeed=300,
	hero.projectileDamage=50,
	hero.statIncr=0,
	hero.currentWave=0,
	hero.x=150,
	hero.y=850
	enemies=[];
	bullets=[];
	enemyBullets=[];
	enemiesRemaining = 0;
	numOfEnemies = 5;
	bossAlive = false;
};

var processInput = function(modifier){
	if (76 in keysDown){
		if(musicPlay){
		snd.pause();
		musicPlay = false;
		}else{
		snd.play();
		musicPlay=true;
		}
	}
	if (87 in keysDown && hero.y >= 60){
		hero.y-=hero.speed*modifier;
		hero.faceDirection = 2;
		moveRight();
	}
	if(83 in keysDown && hero.y <= 870){
		hero.y+=hero.speed*modifier;
		hero.faceDirection = 4;
		moveRight();
	}
	if(65 in keysDown  && hero.x >= 60){
		hero.x-=hero.speed*modifier;
		hero.faceDirection = 3;
		moveLeft();
	}
	if(68 in keysDown && hero.x <= 890){
		hero.x+=hero.speed*modifier;
		hero.faceDirection = 1;
		moveRight();
	}if(49 in keysDown && hero.statIncr > 0){
		hero.projectileDamage += 25;
		hero.statIncr--;
	}if(50 in keysDown && hero.statIncr > 0){
		hero.projectileSpeed += 75;
		hero.statIncr--;
	}if(51 in keysDown && hero.statIncr > 0){
		hero.projectileRange += 40;
		hero.statIncr--;
	}if(52 in keysDown && hero.statIncr > 0){
		hero.rateOfFire -= 75;
		hero.statIncr--;
	}
	
	if('mousedown' in keysDown && Date.now() - lastFire > hero.rateOfFire){
		var x = hero.x + charWidth/2;
		var y = hero.y + charHeight/2;
		
		var box = canvas.getBoundingClientRect();
		
		
		if(charQuadrant == 1){
		var a = Math.atan2(mouseX - hero.x, mouseY - hero.y);
		}else if(charQuadrant == 2){
		var a = Math.atan2((512 + mouseX) - hero.x, mouseY - hero.y);
		}else if(charQuadrant ==3){
		var a = Math.atan2(mouseX - hero.x, (512+mouseY) - hero.y);
		}else if(charQuadrant == 4){
		var a = Math.atan2( (512+mouseX) - hero.x, (512+mouseY) - hero.y);
		}
		  
		throwSnd.play();
		bullets.push({pos:[x,y], old:[x,y], target:[mouseX,mouseY],angle:a});
		lastFire = Date.now();
	}
	
		if( !(87 in keysDown) && !(83 in keysDown) && !(65 in keysDown) && !(68 in keysDown)){
	stayIdle();
	}
	
};

var checkHeroQuadrant = function(){
	if(hero.x >= 0 && hero.x < 512
		&& hero.y >= 0 && hero.y < 512){
		charQuadrant = 1;
	}else if(hero.x >= 512 && hero.x <=1024
	    && hero.y >= 0 && hero.y < 512){
		charQuadrant = 2;
	}else if(hero.x >=0 && hero.x < 512
		&& hero.y >= 512 && hero.y <= 1024){
		charQuadrant = 3;
	}else if(hero.x >=512 && hero.x <= 1024
		&& hero.y >=512 && hero.y <= 1024){
		charQuadrant = 4;
	}
};

var enemyBullets = [];
var shootPlayer = function(enemy){
if(!enemy.boss){
if( Date.now() - enemy.lastBullet > 1000){
		var x = enemy.pos[0] + 30;
		var y = enemy.pos[1] + 30;
		
		var box = canvas.getBoundingClientRect();
		
		
		if(charQuadrant == 1){
		var a = Math.atan2(hero.x - enemy.pos[0], hero.y - enemy.pos[1]);
		}else if(charQuadrant == 2){
		var a = Math.atan2((hero.x) - enemy.pos[0], hero.y - enemy.pos[1]);
		}else if(charQuadrant ==3){
		var a = Math.atan2(hero.x - enemy.pos[0], (hero.y) - enemy.pos[1]);
		}else if(charQuadrant == 4){
		var a = Math.atan2( (hero.x) - enemy.pos[0], (hero.y) - enemy.pos[1]);
		}
		  
		if(enemy.boss){
		enemyBullets.push({pos:[x,y], old:[x,y], target:[hero.x,hero.y],angle:a,bossBullet:true});
		}else{
		enemyBullets.push({pos:[x,y], old:[x,y], target:[hero.x,hero.y],angle:a,bossBullet:false});
		}
		enemy.lastBullet = Date.now();
}
}else{
if( Date.now() - enemy.lastBullet > 625){
		var x = enemy.pos[0] + 30;
		var y = enemy.pos[1] + 30;
		
		var box = canvas.getBoundingClientRect();
		
		
		if(charQuadrant == 1){
		var a = Math.atan2(hero.x - enemy.pos[0], hero.y - enemy.pos[1]);
		}else if(charQuadrant == 2){
		var a = Math.atan2((hero.x) - enemy.pos[0], hero.y - enemy.pos[1]);
		}else if(charQuadrant ==3){
		var a = Math.atan2(hero.x - enemy.pos[0], (hero.y) - enemy.pos[1]);
		}else if(charQuadrant == 4){
		var a = Math.atan2( (hero.x) - enemy.pos[0], (hero.y) - enemy.pos[1]);
		}
		  
		if(enemy.boss){
		enemyBullets.push({pos:[x,y], old:[x,y], target:[hero.x,hero.y],angle:a,bossBullet:true});
		}else{
		enemyBullets.push({pos:[x,y], old:[x,y], target:[hero.x,hero.y],angle:a,bossBullet:false});
		}
		enemy.lastBullet = Date.now();
}
}


};

var updateBullets=function(modifier){
	for(var i = 0; i<bullets.length; i++){
		var bullet = bullets[i];
		
			var movX = hero.projectileSpeed * Math.cos(-bullet.angle - (Math.PI/2));
			var movY = hero.projectileSpeed * Math.sin(-bullet.angle - (Math.PI/2));
			
			bullet.pos[0] -= movX * modifier;
			bullet.pos[1] -= movY * modifier;
		
		if(bullet.pos[0] - bullet.old[0] > hero.projectileRange
			|| bullet.pos[0] - bullet.old[0] < -hero.projectileRange
			|| bullet.pos[1] - bullet.old[1] > hero.projectileRange
			|| bullet.pos[1] - bullet.old[1] < -hero.projectileRange){
		bullets.splice(i,1);
		i--;
		}
	}
};

var updateEnemyBullets = function(modifier){
	for(var i = 0; i<enemyBullets.length; i++){
		var bullet = enemyBullets[i];
		
		if(bullet.bossBullet){
			var movX = (enemyBulletSpeed*2) * Math.cos(-bullet.angle - (Math.PI/2));
			var movY = (enemyBulletSpeed*2) * Math.sin(-bullet.angle - (Math.PI/2));
		}else{
			var movX = enemyBulletSpeed * Math.cos(-bullet.angle - (Math.PI/2));
			var movY = enemyBulletSpeed * Math.sin(-bullet.angle - (Math.PI/2));
			}
			bullet.pos[0] -= movX * modifier;
			bullet.pos[1] -= movY * modifier;
		
		if(bullet.bossBullet){
			if(bullet.pos[0] - bullet.old[0] > (enemyBulletRange*2)
			|| bullet.pos[0] - bullet.old[0] < -(enemyBulletRange*2)
			|| bullet.pos[1] - bullet.old[1] > (enemyBulletRange*2)
			|| bullet.pos[1] - bullet.old[1] < -(enemyBulletRange*2)){
		enemyBullets.splice(i,1);
		i--;
		}
		}else{
		if(bullet.pos[0] - bullet.old[0] > enemyBulletRange
			|| bullet.pos[0] - bullet.old[0] < -enemyBulletRange
			|| bullet.pos[1] - bullet.old[1] > enemyBulletRange
			|| bullet.pos[1] - bullet.old[1] < -enemyBulletRange){
		enemyBullets.splice(i,1);
		i--;
		}
		}
	}
};

var moveEnemies = function(modifier){
	for(var i = 0; i < enemies.length; i++){
		if(!enemies[i].boss){
		if(charQuadrant == 1){
		var a = Math.atan2(hero.x - enemies[i].pos[0], hero.y - enemies[i].pos[1]);
		}else if(charQuadrant == 2){
		var a = Math.atan2((hero.x) - enemies[i].pos[0], hero.y - enemies[i].pos[1]);
		}else if(charQuadrant ==3){
		var a = Math.atan2(hero.x - enemies[i].pos[0], (hero.y) - enemies[i].pos[1]);
		}else if(charQuadrant == 4){
		var a = Math.atan2( (hero.x) - enemies[i].pos[0], (hero.y) - enemies[i].pos[1]);
		}
		
		var movX = enemySpeed * Math.cos(-a - (Math.PI/2));
		var movY = enemySpeed * Math.sin(-a - (Math.PI/2));
		
		enemies[i].pos[0] -= movX * modifier;
		enemies[i].pos[1] -= movY * modifier;
		}else{
				if(charQuadrant == 1){
		var a = Math.atan2(hero.x - enemies[i].pos[0], hero.y - enemies[i].pos[1]);
		}else if(charQuadrant == 2){
		var a = Math.atan2((hero.x) - enemies[i].pos[0], hero.y - enemies[i].pos[1]);
		}else if(charQuadrant ==3){
		var a = Math.atan2(hero.x - enemies[i].pos[0], (hero.y) - enemies[i].pos[1]);
		}else if(charQuadrant == 4){
		var a = Math.atan2( (hero.x) - enemies[i].pos[0], (hero.y) - enemies[i].pos[1]);
		}
		
		var movX = (enemySpeed*1.5) * Math.cos(-a - (Math.PI/2));
		var movY = (enemySpeed*1.5) * Math.sin(-a - (Math.PI/2));
		
		enemies[i].pos[0] -= movX * modifier;
		enemies[i].pos[1] -= movY * modifier;
		}
	}
};

var checkEnemies = function(){
	for(var i = 0; i < enemies.length; i++){
	 for(var j = 0; j < bullets.length; j++){
		if(bullets.length>0){
		if(bullets[j].pos[0] >= enemies[i].pos[0] && bullets[j].pos[0] <= enemies[i].pos[0]+60
		&& bullets[j].pos[1] >= enemies[i].pos[1] && bullets[j].pos[1] <= enemies[i].pos[1]+60){
		
		enemies[i].health -= hero.projectileDamage;
		bullets.splice(j,1);
		
		if(enemies[i].health <= 0){
		drgDeath.play();
		enemies.splice(i,1);
		enemiesRemaining--;
		enemyBulletsLast.splice(i,1);
		if(hero.health<100){
		hero.health+=3;
		}
		hero.exp+=50;
		checkLevelUp();
		i--;
		}
		}
	 }
	 }
	 
	}
};

var checkLevelUp = function(){
	
	if(hero.exp == hero.level * 100){
		hero.level++;
		hero.exp = 0;
		hero.statIncr+=1;
	}
	
};

var checkPlayerHit = function(){
for(var i = 0; i < enemyBullets.length; i++){
	if(enemyBullets.length>0){
		if(enemyBullets[i].pos[0] >= hero.x && enemyBullets[i].pos[0] <= hero.x + charWidth
		&& enemyBullets[i].pos[1] >= hero.y && enemyBullets[i].pos[1] <= hero.y + charHeight){
			
			if(enemyBullets[i].bossBullet){
			hero.health-=15;
			}else{
			hero.health -= 7;
			}
			enemyBullets.splice(i,1);
			if(hero.health <= 0){
				
				alert("You died! HA - HA! ");

				window.location.reload();
				restartGame();
			}
		}
		
	}
}
};

var checkPlayed = function(start, end){
totalTime = (end - start)/1000;
};

var enemyBulletsLast = [];

var enemyShoot = function(){
for(var i = 0; i < enemies.length;i++){
shootPlayer(enemies[i]);
}
};

var numOfEnemies = 5;
var enemiesRemaining = 0;

var newWave = function(){

	if(enemiesRemaining == 0){
	
	
		if(hero.gameLevel == 1){
			
			generateLevelOneEnemies(numOfEnemies);
			enemiesRemaining = numOfEnemies;
			hero.currentWave++;
			numOfEnemies++;
			if(hero.currentWave == 10){
			numOfEnemies = 5;
			hero.currentWave=0;
			hero.gameLevel++;
		}
		}
		
		if(hero.gameLevel == 2){
			enemies=[];
			generateLevelTwoEnemies(numOfEnemies);
			enemiesRemaining = numOfEnemies;
			hero.currentWave++;
			numOfEnemies++;
			if(hero.currentWave == 10){
				numOfEnemies = 1;
				enemiesRemaining = numOfEnemies;
				hero.currentWave=0;
				hero.gameLevel++;
				spawnBoss();
				bossAlive = true;
		}
		}
		if(hero.gameLevel ==3){
			if(enemiesRemaining==0){
			renderWin=true;
			
				endTime = Date.now();
				checkPlayed(startTime,endTime);
			
				if(supports_html5_storage()){
				if(localStorage.getItem("fastestTime") == null){
				localStorage["fastestTime"] = totalTime;
				alert("Congratulations! You win the game and it took you: "+totalTime+" seconds");
				}else if ( parseInt(localStorage["fastestTime"]) > totalTime){
				localStorage["fastestTime"] = totalTime;
				alert("Congratulations! You win the game faster than before! New high score is: "+totalTime+" seconds");
				}else{
				alert("Congratulations! You beat the game in: "+totalTime+"seconds. Current best time:" + localStorage["fastestTime"]+" seconds.");
				}
				}
			
			window.location.reload();
			restartGame();
			}
		}
	}
};

function supports_html5_storage() {
  try {
    return 'localStorage' in window && window['localStorage'] !== null;
  } catch (e) {
    return false;
  }
}

var update = function(modifier){
	
	checkHeroQuadrant();
	processInput(modifier);
	moveEnemies(modifier);
	updateBullets(modifier);
	enemyShoot();
	updateEnemyBullets(modifier);
	checkEnemies();
	checkPlayerHit();
	
	newWave();
	
};


var fastestTime = localStorage["fastestTime"];

var startTime = Date.now();
var endTime = 0;
var totalTime = 0;

var main = function(){
	var now = Date.now();
	var delta = now - then;
	
	update(delta/1000);
	
	render(delta);
	
	then = now;
};


var then = Date.now();
setInterval(main,1);

