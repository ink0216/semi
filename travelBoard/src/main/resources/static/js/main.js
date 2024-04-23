
const selectContinent = document.getElementById("selectContinent");
const connectBoard = document.querySelector("#connectBoard");



connectBoard.addEventListener("submit",()=>{

    if(selectContinent == "대륙 선택"){

        alert("제출할 수 없습니다.");

        return;
    }
});