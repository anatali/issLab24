/*
* conwayio.js
*/
var reproductionTime = 500;
var timer;

function initIO(rows,cols){
    createHtmlTable(rows,cols);
    setupControlButtons(rows,cols);
    console.log("initIO DONE")
}

// Lay out the board
function createHtmlTable(rows,cols) {
    var gridContainer = document.getElementById('gridContainer');
    if (!gridContainer) {
         console.error("Problem: No div for the drid table!");
    }
    var table = document.createElement("table");
    for (var i = 0; i < rows; i++) {
        var tr = document.createElement("tr");
        for (var j = 0; j < cols; j++) {//
            var cell = document.createElement("td");
            cell.setAttribute("id", i + "_" + j);
            cell.setAttribute("class", "dead");
            cell.onclick = cellClickHandler;
            tr.appendChild(cell);
        }
        table.appendChild(tr);
    }
    gridContainer.appendChild(table);
    }

    //INPUT
    function cellClickHandler() {
        var rowcol = this.id.split("_");
        var row = rowcol[0];
        var col = rowcol[1];
//alert(row + " " + col + " " + this.getAttribute("class"))  //attributo della td
        var classes = this.getAttribute("class");
        if(classes.indexOf("live") > -1) {
            this.setAttribute("class", "dead");
            setCellState(row,col,0) //grid[row][col] = 0;
        } else {
            this.setAttribute("class", "live");
            setCellState(row,col,1) // grid[row][col] = 1;
        }
    }

    //OUTPUT
    
    function setCellColor( i,j,state ){
        var cell = document.getElementById(i + "_" + j);
    	if( state == 0 ) cell.setAttribute("class", "dead");
    	else cell.setAttribute("class", "live");
    }


function setupControlButtons(rows,cols) {
    // button to start
    var startButton = document.getElementById('start');
    startButton.onclick = startButtonHandler;

    // button to clear
    var clearButton = document.getElementById('clear');
    clearButton.onclick = clearTheGrid;
}

// clear the grid
function clearTheGrid() {
    //console.log("clearTheGrid: stop playing ");
    stopPlaying();    //in conwaycore.js playing = false;
    var startButton       = document.getElementById('start');
    startButton.innerHTML = "Start";
    clearTimeout(timer);  //timer in conwaycore.js

    var cellsList = document.getElementsByClassName("live");
    // convert to array first, otherwise, you're working on a live node list
    // and the update doesn't work!
    var cells = [];
    console.log("clearTheGrid cellsList.length=" + cellsList.length);
    for (var i = 0; i < cellsList.length; i++) {
        cells.push(cellsList[i]);
    }
    for (var i = 0; i < cells.length; i++) {
        cells[i].setAttribute("class", "dead");
    }
    resetGrids();  //in conwaycore.js
}

// start/pause/continue the game
function startButtonHandler() {
    if (isPlaying()) { // in conwaycore.js
        console.log("Pause the game");
        playing = false;
        this.innerHTML = "Continue";
        clearTimeout(timer);  //Necessario se togliamo if in doPlay
    } else {
        console.log("Continue the game");
        playing = true;
        this.innerHTML = "Pause";
        doPlay();
    }
}

function doPlay(){
    if( playing ){
         play();
         timer = setTimeout(doPlay, 500);
    }
}

// Start everything
window.onload = configureTheSystem;  //in conwaycore.js