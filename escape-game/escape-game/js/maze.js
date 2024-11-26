document.addEventListener('DOMContentLoaded', function() {
    var board = document.getElementById("board");
    var cells = board.getElementsByTagName("td");
    var player = "X"; // 初始值X

    //对每一个单元格进行操作
    for (var i = 0; i < cells.length; i++) {
        cells[i].addEventListener("click", function() {
            if (this.innerHTML === "" && player === "X") {
                this.innerHTML = player;
                if (checkWin(player)) {
                    setTimeout(function() {
                        window.location.href = 'success.html'; // 玩家赢了，跳转到 success.html
                    }, 500);
                } else if (isBoardFull()) {
                    setTimeout(function() {
                        alert("MESSMESSMESS...");
                        window.location.href = 'maze.html'; // 平局，跳转到 draw.html
                    }, 500);
                } else {
                    player = "O";
                    setTimeout(machineMove, 500);
                }
            }
        });
    }

    // 重置
    var resetButton = document.getElementById("reset-button");
    resetButton.addEventListener("click", resetBoard);

    //机器玩家操作
    function machineMove() {
        var emptyCells = [];
        for (var i = 0; i < cells.length; i++) {
            if (cells[i].innerHTML === "") {
                emptyCells.push(cells[i]);
            }
        }

        //随机选择空格进行下棋
        if (emptyCells.length > 0) {
            var randomCell = emptyCells[Math.floor(Math.random() * emptyCells.length)];
            randomCell.innerHTML = "O";
            if (checkWin(player)) {
                setTimeout(function() {
                    alert("DISTRESS-MESS!!!");
                    window.location.href = 'failure.html'; // 机器赢了
                }, 500);
            } else if (isBoardFull()) {
                setTimeout(function() {
                    alert("MESSMESSMESS...");
                    window.location.href = 'maze.html'; // 平局
                }, 500);
            } else {
                player = "X";
            }
        }
    }

    //检查输赢
    function checkWin(currentPlayer) {
        var winPatterns = [
            [0, 1, 2], [3, 4, 5], [6, 7, 8], 
            [0, 3, 6], [1, 4, 7], [2, 5, 8], 
            [0, 4, 8], [2, 4, 6]             
        ];

        return winPatterns.some(function(pattern) {
            return pattern.every(function(index) {
                return cells[index].innerHTML === currentPlayer;
            });
        });
    }

    //棋盘满了-平局
    function isBoardFull() {
        for (var i = 0; i < cells.length; i++) {
            if (cells[i].innerHTML === "") {
                return false;
            }
        }
        return true;
    }

    //重置
    function resetBoard() {
        for (var i = 0; i < cells.length; i++) {
            cells[i].innerHTML = "";
        }
        player = "X";
    }
});
