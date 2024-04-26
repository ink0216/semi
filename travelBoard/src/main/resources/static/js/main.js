
const selectContinent = document.querySelector("#selectContinent");
const connectBoard = document.getElementById("connectBoard");


const getCookie = key =>{

    const cookies = document.cookie;

    const cookieList = cookies.split(";").map(el => el.split("="));


    const obj = {};
    for(let i = 0 ; i < cookieList.length ; i++){
        const k = cookieList[i][0];
        const v = cookieList[i][1];
        obj[k] = v; 
    }
    return obj[key]; 
}


const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");



if(loginEmail != null) {

    const saveId = getCookie("saveId");


    if(saveId != undefined){
        loginEmail.value = saveId;  
        document.querySelector("input[name='saveId']").checked = true;
    }
}



const loginForm = document.querySelector("#loginForm");
    // const loginPw = document.querySelector("#loginForm input[name='memberPw']");

   
   

        loginForm.addEventListener("submit",e => {

            if(document.querySelector("#memberEmail").value.trim().length === 0){
                alert("ID를 입력해주십시오.");
                e.preventDefault();
                return;
            }
            
            if(document.querySelector("#memberPw").value.trim().length === 0){
                alert("비밀번호를 입력해주십시오.");
                e.preventDefault();
                return;
            }
            if(document.querySelector("#selectContinent").value=="selectContinent"){
                alert("접속하고자 하는 대륙을 선택하세요.");
                e.preventDefault(); 
                return;
            }
            
    

           
        });

    