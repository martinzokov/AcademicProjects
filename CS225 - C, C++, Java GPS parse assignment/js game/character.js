var hero = {
	gameLevel:1,
	speed:256,
	level:1,
	health:100,
	exp:0,
	faceDirection:1,
	rateOfFire:700,
	projectileRange:200,
	projectileSpeed:300,
	projectileDamage:50,
	statIncr:0,
	currentWave:0,
	x:150,
	y:850
};


var monster = {
	speed:100,
	x:0,
	y:0
};

var charWidth = 75;
var charHeight = 89;

var keysDown={};
var mouseX;
var mouseY;

addEventListener("keydown",function(e){
		keysDown[e.keyCode] = true;
},false);

addEventListener("keyup",function(e){
	delete keysDown[e.keyCode];
},false);

addEventListener("mousedown", function (e) {

    keysDown['mousedown'] = true;
}, false);

addEventListener("mouseup", function (e) {
	
    delete keysDown['mousedown'];
}, false);

addEventListener("mousemove", function (e) {
	var canvasRect = canvas.getBoundingClientRect();
    mouseX = (e.clientX - 40) - canvasRect.left;
    mouseY = (e.clientY - 40) - canvasRect.top;
}, false);

//// Player move state
var idle = true;
var movingRight = false;
var movingLeft = false;

function moveRight(){
idle = false;
movingRight = true;
}

function moveLeft(){
idle = false;
movingLeft = true;
}

function stayIdle(){
idle = true;
movingRight = false;
movingLeft = false;
}

var charQuadrant;
///////////////////////////

var isRunning = true;
var bossAlive = false;

var enemyBulletSpeed = 230;
var enemyBulletRange = 270;
var enemySpeed = 40;

var timePlayed = 0;

var bullets = [];
var enemies = [];

var lastFire = Date.now();

var generateLevelOneEnemies = function(numOfEnemies){
for(var i = 0; i < numOfEnemies; i++){
enemies.push({pos:[getRandomInt(60,870),getRandomInt(60,870)],health:100,lastBullet:0,boss:false});
}
};

var generateLevelTwoEnemies = function(numOfEnemies){
for(var i = 0; i < numOfEnemies; i++){
enemies.push({pos:[getRandomInt(60,870),getRandomInt(60,870)],health:170,lastBullet:0,boss:false});
}
};

var spawnBoss = function(){
enemies=[];
enemies.push({pos:[0,0],health:4000,lastBullet:0,boss:true});
};


function getRandomInt (min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

