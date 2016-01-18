var canvas = document.createElement("canvas");
var ctx = canvas.getContext("2d");
canvas.width = 512;
canvas.height = 512;

document.body.appendChild(canvas);

	// ctx.fillstyle = "rgb(0,0,0)";
	// ctx.font = "Bold 24px Helvetica";
	// ctx.textAlign = "left";
	// ctx.textBaseline = "top";
	// ctx.fillText("Press Enter to start!",100,200);
	// ctx.fillText("Controls: ",100,220);
	// ctx.fillText("W, S, A, D - Movement",100,240);
	// ctx.fillText("Mouse - Aim and shoot",100,260);
	// ctx.fillText("There are waves of enemies coming at you",100,280);
	// ctx.fillText("Each round lasts 2 and a half minutes and if you reach the end, there is a boss level",100,300);
	// ctx.fillText("You gain experience and increase your stats when you level up - damage, range, projectile speed",100,320);
	// ctx.fillText("You need to balance your stats to beat the boss! Good luck!",100,340);


//// LOADING IMAGES AND TILES
var grassRdy = false;
var grassTile = new Image();
grassTile.onload= function(){
grassRdy=true;
};
grassTile.src = "img/tile_grass.png";

var stoneRdy = false;
var stoneTile = new Image();
stoneTile.onload = function(){
stoneRdy=true;
};
stoneTile.src = "img/tile_stone.png";

var stoneRedRdy = false;
var stoneRedTile = new Image();
stoneRedTile.onload = function(){
stoneRedRdy=true;
};
stoneRedTile.src = "img/tile_redstone.png";

var woodRdy = false;
var woodTile = new Image();
woodTile.onload = function(){
woodRdy=true;
};
woodTile.src = "img/tile_wood.png";

var treeRdy = false;
var treeTile = new Image();
treeTile.onload = function(){
treeRdy = true;
};
treeTile.src = "img/tile_tree.png";

var wallRdy = false;
var wallTile = new Image();
wallTile.onload = function(){
wallRdy = true;
};
wallTile.src = "img/tile_wall.png";

var heroRdy = false;
var heroImg = new Image();
heroImg.onload = function(){
heroRdy=true;
};
heroImg.src = "img/batman_idle.png";

var heroWalkingRightRdy=false;
var heroWalkingRightImg = new Image();
heroWalkingRightImg.onload = function(){
heroWalkingRightRdy = true;
};
heroWalkingRightImg.src = "img/batman_walking.png";

var heroWalkingLeftRdy=false;
var heroWalkingLeftImg = new Image();
heroWalkingLeftImg.onload = function(){
heroWalkingLeftRdy = true;
};
heroWalkingLeftImg.src = "img/batman_walking_reversed.png";

var heroShootingRdy = false;
var heroShootingImg = new Image();
heroShootingImg.onload = function(){
heroShootingRdy = true;
};
heroShootingImg.src = "img/batman_shooting.png";

var projectileRdy = false;
var projectileImg = new Image();
projectileImg.onload = function(){
projectileRdy = true;
};
projectileImg.src = "img/projectile.png";

var enemyOneRdy = false;
var enemyOneImg = new Image();
enemyOneImg.onload = function(){
enemyOneRdy = true;
};
enemyOneImg.src = "img/dragon.png";

var enemyBulletsRdy = false;
var enemyBulletImg = new Image();
enemyBulletImg.onload = function(){
enemyBulletRdy = true;
};
enemyBulletImg.src = "img/fire.png";

var bossRdy = false;
var bossImg = new Image();
bossImg.onload = function(){
bossRdy = true;
};
bossImg.src = "img/sasho.png";

var snd = new Audio("sound.mp3");
var throwSnd = new Audio("throw.mp3");
var drgDeath = new Audio("dragon_death.mp3");

snd.play();
var musicPlay = true;

var mapOne = [
[4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4],
[4,3,0,1,1,3,3,1,0,0,0,0,0,0,0,4],
[4,3,0,0,0,0,0,1,0,0,0,1,1,3,3,4],
[4,3,0,0,0,0,0,1,0,0,0,3,3,3,0,4],
[4,3,0,0,0,0,0,1,0,0,0,0,0,0,0,4],
[4,3,0,0,0,0,0,0,0,0,0,0,0,0,0,4],
[4,1,1,1,1,0,3,3,3,3,1,1,1,1,0,4],
[4,0,0,0,0,0,1,1,1,1,1,1,0,0,0,4],
[4,0,0,3,0,0,0,0,0,1,0,0,0,0,0,4],
[4,0,3,1,0,0,0,0,0,1,0,0,3,0,0,4],
[4,3,0,1,0,0,0,0,0,0,3,3,0,3,0,4],
[4,1,1,1,0,0,0,0,0,0,0,0,0,0,0,4],
[4,0,0,0,0,0,0,1,1,1,0,0,0,3,1,4],
[4,3,0,3,0,0,0,1,0,3,0,3,3,0,1,4],
[4,3,0,3,0,0,0,1,0,0,3,0,0,0,1,4],
[4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4]
];

var mapTwo = [
[4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4],
[4,2,2,2,1,2,2,2,2,2,1,2,2,2,2,4],
[4,2,2,2,1,2,2,2,2,2,2,2,2,2,2,4],
[4,2,2,2,1,2,2,1,2,2,2,2,2,2,2,4],
[4,2,2,2,2,2,2,1,2,2,2,2,1,1,1,4],
[4,1,2,2,2,2,1,2,2,2,2,2,2,2,2,4],
[4,2,2,2,2,1,2,2,2,2,2,2,2,2,2,4],
[4,2,2,2,2,2,2,2,1,1,1,1,2,2,2,4],
[4,2,2,2,2,2,2,2,2,2,2,2,1,2,2,4],
[4,1,1,1,1,1,2,2,2,2,2,2,1,2,2,4],
[4,2,2,2,2,2,2,2,2,2,2,2,1,2,2,4],
[4,2,2,2,2,2,2,2,2,2,2,2,2,2,1,4],
[4,2,2,2,1,2,2,2,1,2,2,2,2,2,2,4],
[4,2,2,2,1,2,2,2,2,1,2,2,2,2,2,4],
[4,2,2,2,1,2,2,2,2,1,1,2,2,2,2,4],
[4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4]
];

var mapThree = [
[4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4],
[4,2,2,2,5,2,2,2,2,2,2,2,2,5,2,4],
[4,2,2,5,2,2,5,5,5,5,5,5,2,2,5,4],
[4,2,5,2,2,5,2,2,2,2,2,2,5,2,2,4],
[4,5,2,2,5,2,2,5,5,5,5,2,2,5,2,4],
[4,5,2,5,2,2,5,2,2,2,2,5,2,2,5,4],
[4,5,2,5,2,5,2,2,5,5,2,2,5,2,5,4],
[4,5,2,5,2,5,2,5,2,2,5,2,5,2,5,4],
[4,5,2,5,2,5,2,2,2,2,5,2,5,2,5,4],
[4,5,2,5,2,2,5,2,2,5,2,2,5,2,5,4],
[4,5,2,2,5,2,2,5,5,2,2,5,2,2,5,4],
[4,2,5,2,2,5,2,2,2,2,5,2,2,5,2,4],
[4,2,2,5,2,2,5,5,5,5,2,2,5,2,2,4],
[4,5,2,2,5,2,2,2,2,2,2,5,2,2,5,4],
[4,2,5,2,2,5,5,5,5,5,5,2,2,5,2,4],
[4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4]
];