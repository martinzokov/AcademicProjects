
var renderMap = function(map){

var cameraX = Math.floor(hero.x/64);
var cameraY = Math.floor(hero.y/64);

if(cameraY >= 0 && cameraY <= 7){
	if(cameraX >= 0 && cameraX <= 7){
		for(var i = 0; i < map.length; i++){
		for(var j = 0; j <map[i].length; j++){
			
			if(map[i][j] == 0){
				ctx.drawImage(grassTile, posX, posY,64,64);
			}
			if(map[i][j] == 1){
				ctx.drawImage(woodTile, posX, posY,64,64);
			}
			if(map[i][j] == 2){
				ctx.drawImage(stoneTile, posX, posY,64,64);
			}
			if(map[i][j] == 3){
				ctx.drawImage(treeTile,posX,posY,64,64);
			}
			if(map[i][j] == 4){
				ctx.drawImage(wallTile,posX,posY,64,64);
			}
			if(map[i][j] == 5){
				ctx.drawImage(stoneRedTile,posX,posY,64,64);
			}
			
			posX+=64;
		}
		posX = 0;
		posY+=64;
	}
	}else if(cameraX >= 8 && cameraX <= 15){
		for(var i = 0; i <= 7; i++){
		for(var j = 8; j <= 15; j++){
			
			if(map[i][j] == 0){
				ctx.drawImage(grassTile, posX, posY,64,64);
			}
			if(map[i][j] == 1){
				ctx.drawImage(woodTile, posX, posY,64,64);
			}
			if(map[i][j] == 2){
				ctx.drawImage(stoneTile, posX, posY,64,64);
			}
			if(map[i][j] == 3){
				ctx.drawImage(treeTile,posX,posY,64,64);
			}
			if(map[i][j] == 4){
				ctx.drawImage(wallTile,posX,posY,64,64);
			}
			if(map[i][j] == 5){
				ctx.drawImage(stoneRedTile,posX,posY,64,64);
			}
			
			posX+=64;
		}
		posX = 0;
		posY+=64;
	}
	}

}else if( cameraY >= 8 && cameraY <= 15){
	if(cameraX >= 0 && cameraX <= 7){
		for(var i = 8; i <= 15; i++){
		for(var j = 0; j <= 7; j++){
			
			if(map[i][j] == 0){
				ctx.drawImage(grassTile, posX, posY,64,64);
			}
			if(map[i][j] == 1){
				ctx.drawImage(woodTile, posX, posY,64,64);
			}
			if(map[i][j] == 2){
				ctx.drawImage(stoneTile, posX, posY,64,64);
			}
			if(map[i][j] == 3){
				ctx.drawImage(treeTile,posX,posY,64,64);
			}
			if(map[i][j] == 4){
				ctx.drawImage(wallTile,posX,posY,64,64);
			}
			if(map[i][j] == 5){
				ctx.drawImage(stoneRedTile,posX,posY,64,64);
			}
			
			posX+=64;
		}
		posX = 0;
		posY+=64;
	}
	}else if(cameraX >= 8 && cameraX <= 15){
		for(var i = 8; i <= 15; i++){
		for(var j = 8; j <= 15; j++){
			
			if(map[i][j] == 0){
				ctx.drawImage(grassTile, posX, posY,64,64);
			}			
			if(map[i][j] == 1){
				ctx.drawImage(woodTile, posX, posY,64,64);
			}
			if(map[i][j] == 2){
				ctx.drawImage(stoneTile, posX, posY,64,64);
			}
			if(map[i][j] == 3){
				ctx.drawImage(treeTile,posX,posY,64,64);
			}
			if(map[i][j] == 4){
				ctx.drawImage(wallTile,posX,posY,64,64);
			}
			if(map[i][j] == 5){
				ctx.drawImage(stoneRedTile,posX,posY,64,64);
			}
			
			posX+=64;
		}
		posX = 0;
		posY+=64;
	}
	}

}
posX = 0;
posY = 0;
};

	var xpos = 0;
	var index = 0;
var renderHero = function(delta){
	var numFrames = 4;
	
	if(index >= numFrames){
	xpos = 0;
	ypos = 0;
	index = 0;
	} 
		
	if(charQuadrant == 1){
	ctx.drawImage(heroImg,xpos,0, 75, 89,hero.x,hero.y,charWidth,charHeight);
	}else if(charQuadrant == 2){
	ctx.drawImage(heroImg,xpos,0, 75, 89,hero.x-512,hero.y,charWidth,charHeight);
	}else if(charQuadrant == 3){
	ctx.drawImage(heroImg,xpos,0, 75, 89,hero.x,hero.y-512,charWidth,charHeight);
	}else if(charQuadrant == 4){
	ctx.drawImage(heroImg,xpos,0, 75, 89,hero.x-512,hero.y-512,charWidth,charHeight);
	}
	
	if(delta<5){
	xpos+=75;
	index++;
	}
};

var renderHeroWalkingRight = function(delta){
var numFrames = 6;
if(index >= numFrames){
xpos = 0;
ypos = 0;
index=0;
}

ctx.drawImage(heroWalkingRightImg,xpos,0,75,89,hero.x,hero.y,charWidth,charHeight);

if(charQuadrant == 1){
	ctx.drawImage(heroWalkingRightImg,xpos,0, 75, 89,hero.x,hero.y,charWidth,charHeight);
	}else if(charQuadrant == 2){
	ctx.drawImage(heroWalkingRightImg,xpos,0, 75, 89,hero.x-512,hero.y,charWidth,charHeight);
	}else if(charQuadrant == 3){
	ctx.drawImage(heroWalkingRightImg,xpos,0, 75, 89,hero.x,hero.y-512,charWidth,charHeight);
	}else if(charQuadrant == 4){
	ctx.drawImage(heroWalkingRightImg,xpos,0, 75, 89,hero.x-512,hero.y-512,charWidth,charHeight);
	}


if(delta<12){
xpos+=75;
index++;
}

};

var renderHeroWalkingLeft = function(delta){
var numFrames = 6;
if(index >= numFrames){
xpos = 0;
ypos = 0;
index=0;
}

ctx.drawImage(heroWalkingLeftImg,xpos,0,75,89,hero.x,hero.y,charWidth,charHeight);

	if(charQuadrant == 1){
	ctx.drawImage(heroWalkingLeftImg,xpos,0, 75, 89,hero.x,hero.y,charWidth,charHeight);
	}else if(charQuadrant == 2){
	ctx.drawImage(heroWalkingLeftImg,xpos,0, 75, 89,hero.x-512,hero.y,charWidth,charHeight);
	}else if(charQuadrant == 3){
	ctx.drawImage(heroWalkingLeftImg,xpos,0, 75, 89,hero.x,hero.y-512,charWidth,charHeight);
	}else if(charQuadrant == 4){
	ctx.drawImage(heroWalkingLeftImg,xpos,0, 75, 89,hero.x-512,hero.y-512,charWidth,charHeight);
	}

if(delta<12){
xpos+=75;
index++;
}

};

var renderBullets = function(){
if(bullets.length >0){
for(var i = 0; i<bullets.length;i++){
	
	if(charQuadrant == 1){
	ctx.drawImage(projectileImg,bullets[i].pos[0],bullets[i].pos[1],30,20);
	}else if(charQuadrant == 2){
	ctx.drawImage(projectileImg,bullets[i].pos[0]-512,bullets[i].pos[1],30,20);
	}else if(charQuadrant == 3){
	ctx.drawImage(projectileImg,bullets[i].pos[0],bullets[i].pos[1]-512,30,20);
	}else if(charQuadrant == 4){
	ctx.drawImage(projectileImg,bullets[i].pos[0]-512,bullets[i].pos[1]-512,30,20);
	}
}
}
};

var renderEnemyBullets = function(){
if(enemyBullets.length > 0){
for(var i = 0; i < enemyBullets.length; i++){
var bulletQuadrant = 0;

	if(enemyBullets[i].pos[0] >= 0 && enemyBullets[i].pos[0] < 512
		&& enemyBullets[i].pos[1] >= 0 && enemyBullets[i].pos[1] < 512){
		bulletQuadrant = 1;
	}else if(enemyBullets[i].pos[0] >= 512 && enemyBullets[i].pos[0] <=1024
	    && enemyBullets[i].pos[1] >= 0 && enemyBullets[i].pos[1] < 512){
		bulletQuadrant = 2;
	}else if(enemyBullets[i].pos[0] >=0 && enemyBullets[i].pos[0] < 512
		&& enemyBullets[i].pos[1] >= 512 && enemyBullets[i].pos[1] <= 1024){
		bulletQuadrant = 3;
	}else if(enemyBullets[i].pos[0] >=512 && enemyBullets[i].pos[0] <= 1024
		&& enemyBullets[i].pos[1] >=512 && enemyBullets[i].pos[1] <= 1024){
		bulletQuadrant = 4;
	}
	
	if(bulletQuadrant == 1 && bulletQuadrant == charQuadrant){
	ctx.drawImage(enemyBulletImg,enemyBullets[i].pos[0],enemyBullets[i].pos[1],30,20);
	}else if(bulletQuadrant == 2 && bulletQuadrant == charQuadrant){
	ctx.drawImage(enemyBulletImg,enemyBullets[i].pos[0]-512,enemyBullets[i].pos[1],30,20);
	}else if(bulletQuadrant == 3 && bulletQuadrant == charQuadrant){
	ctx.drawImage(enemyBulletImg,enemyBullets[i].pos[0],enemyBullets[i].pos[1]-512,30,20);
	}else if(bulletQuadrant == 4 && bulletQuadrant == charQuadrant){
	ctx.drawImage(enemyBulletImg,enemyBullets[i].pos[0]-512,enemyBullets[i].pos[1]-512,30,20);
	}
	
	
	}
}
};

var renderEnemies = function(enemies){

	for(var i = 0; i<enemies.length; i++){
		if(charQuadrant == 1){
	ctx.drawImage(enemyOneImg,enemies[i].pos[0],enemies[i].pos[1],60,60);
	}else if(charQuadrant == 2){
	ctx.drawImage(enemyOneImg,enemies[i].pos[0]-512,enemies[i].pos[1],60,60);
	}else if(charQuadrant == 3){
	ctx.drawImage(enemyOneImg,enemies[i].pos[0],enemies[i].pos[1]-512,60,60);
	}else if(charQuadrant == 4){
	ctx.drawImage(enemyOneImg,enemies[i].pos[0]-512,enemies[i].pos[1]-512,60,60);
	}
	}
};

var renderBoss = function(enemies){
	for(var i = 0; i<enemies.length; i++){
	if(charQuadrant == 1){
	ctx.drawImage(bossImg,enemies[i].pos[0],enemies[i].pos[1],150,150);
	}else if(charQuadrant == 2){
	ctx.drawImage(bossImg,enemies[i].pos[0]-512,enemies[i].pos[1],150,150);
	}else if(charQuadrant == 3){
	ctx.drawImage(bossImg,enemies[i].pos[0],enemies[i].pos[1]-512,150,150);
	}else if(charQuadrant == 4){
	ctx.drawImage(bossImg,enemies[i].pos[0]-512,enemies[i].pos[1]-512,150,150);
	}
	}
};



//////// RENDERING 
var posX = 0;
var posY = 0;
var render = function(delta){

	ctx.clearRect(0,0, canvas.width,canvas.height);

	if(hero.gameLevel == 1){
	renderMap(mapOne);
	}else if(hero.gameLevel == 2){
	renderMap(mapTwo);
	}else if(hero.gameLevel == 3){
	renderMap(mapThree);
	};
	
	if(idle){
	renderHero(delta);
	}else if(movingRight){
	renderHeroWalkingRight(delta);
	}else if(movingLeft){
	renderHeroWalkingLeft(delta);
	}
	
	renderBullets();
	renderEnemyBullets();
	if(!bossAlive){
	renderEnemies(enemies);
	}else{
	renderBoss(enemies);
	}
	displayPlayerInfo();
	displayLevelInfo();
	displayStats();
	
};

var displayPlayerInfo = function(){
	ctx.fillStyle = "rgb(0,0,0)";
	ctx.font = " Bold 24px Helvetica";
	ctx.textAlign = "left";
	ctx.textBaseline = "top";
	ctx.fillText("Health: "+ hero.health+" Experience: "+hero.exp ,32,32);
	ctx.fillText("Hero level: "+hero.level,32,55);
};

var displayLevelInfo = function(){
	ctx.fillstyle = "rgb(0,0,0)";
	ctx.font = "Bold 24px Helvetica";
	ctx.textAlign = "left";
	ctx.textBaseline = "top";
	ctx.fillText("Enemies in current wave: "+enemiesRemaining,32,450);
	ctx.fillText("Current wave: "+hero.currentWave,32,470);
	ctx.fillText("Stage: "+hero.gameLevel,400,470);
};

var displayStats = function(){
	ctx.fillstyle = "rgb(0,0,0)";
	ctx.font = "Bold 15px Helvetica";
	ctx.textAlign = "left";
	ctx.textBaseline = "top";
	ctx.fillText("Stat points: "+hero.statIncr,370, 20);
	ctx.fillText("Stats:",370,35);
	ctx.fillText("1 - Damage: " +hero.projectileDamage,370,50);
	ctx.fillText("2 - Bullet speed: " +hero.projectileSpeed,370,65);
	ctx.fillText("3 - Range: " +hero.projectileRange,370,80);
	ctx.fillText("4 - Fire delay: " +hero.rateOfFire,370,95);
};

