
const inputEmail = document.querySelector("#inputEmail");
const inputTel = document.querySelector("#inputTel");
const inputBirth = document.querySelector("#inputBirth");

const selectEmailTelBirth = document.querySelector("#selectEmailTelBirth");


/* 이메일 인증번호 주고받기 */





const checkObj = {
    "inputEmail" : false,
    "authKey" : false
}






const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");
const authKey = document.querySelector("#authKey");
const authKeyMessage = document.querySelector("#authKeyMessage");
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");
const emailMessage = document.querySelector("#emailMessage");


// 입력한 이메일에 대한 유효성 검사

inputEmail.addEventListener("input",e =>{

    const inputEmail = e.target.value;

    // 입력한 이메일에 대한 유효성 검사

    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;


    // 입력한 이메일이 정규식과 일치하지 않음

    if(!regExp.test(inputEmail)) {

        emailMessage.innerText = "올바른 이메일 형식이 아닙니다.";

        emailMessage.classList.add('error');

        emailMessage.classList.remove('confirm');

        checkObj.inputEmail = false;

        return;



        
    }
        emailMessage.innerText = "유효한 이메일 형식입니다.";

        emailMessage.classList.add('confirm');

        emailMessage.classList.remove('error');

        checkObj.inputEmail = true;


       



});








/* 비밀번호 찾기를 위한 이메일 인증 */
// 타이머 설정하기
let authTimer;

const initMin = 4;
const initSec = 59;
const initTime = "05:00";

let min = initMin;
let sec = initSec;


// 인증번호 받기 버튼 클릭 시
sendAuthKeyBtn.addEventListener("click", () => {

    if(!checkObj.inputEmail){

        alert("유효한 이메일을 먼저 작성하세요.");

        return;
    }
    



    // 인증번호 받기 버튼 클릭하며 타이머가 초기화
    min = initMin;
    sec = initSec;



    clearInterval(authTimer);

    checkObj.authKey = false;


    // 서버에서 이메일을 보내기
    fetch("/email/signup",{
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : inputEmail.value
    })
    .then(response => response.text())
    .then(result => {
        if(result == 1){
            console.log("인증번호 발송 성공");
        }else{
            console.log("인증번호 발송 실패");
        }
    })




    authKeyMessage.innerText = initTime;

    authKeyMessage.classList.remove("confirm","error");

    alert("인증번호가 발송되었습니다.");

    authTimer = setInterval(() => {

        authKeyMessage.innerText = `${addZero(min)}:${addZero(sec)}`;

        if( min == 0 && sec == 0){

            // 타이머 0분 0초되면 인증실패+타이머 초기화
            checkObj.authKey = false;

            clearInterval(authTimer);

            authKeyMessage.classList.add("error");

            authKeyMessage.classList.remove("confirm");

            return;

        }

        if(sec == 0){

            sec = 60;
            min--;
           
        }
        sec--;

    }, 1000)

});


function addZero(number){
    
    if( number < 10)
    {
        return "0" + number;

    } else {

        return number;

    }
}


checkAuthKeyBtn.addEventListener("click",() => {

    if(min === 0 && sec === 0){
        alert("인증번호 입력 제한시간이 지났습니다. 다시 인증버튼을 눌러주세요.");
        return;
    }

    if(authKey.value.length < 6){
        alert("인증번호 형식이 일치하지 않습니다.");
        return;
    }


    const obj = {
        "email" : inputEmail.value,
        "authKey" : authKey.value
    };

    fetch("/email/checkAuthKey", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result == 0){
            alert("인증번호가 틀립니다.");

            checkObj.authKey = false;
            
            return;
        }
        
        

        alert("이메일이 인증되었습니다.");

        clearInterval(authTimer);

        authKeyMessage.innerText = "이메일이 인증되었습니다.";

        authKeyMessage.classList.remove("error");

        authKeyMessage.classList.add("confirm");

        checkObj.authKey = true; // 유효성 검사 인증키 여부를 true로 변환

    })



});






selectEmailTelBirth.addEventListener("submit", e => {

    if(document.querySelector("#inputEmail").value.trim().length ==0){

        alert("이메일이 입력되지 않았습니다.");
        e.preventDefault();
        return;
    }
    if(document.querySelector("#inputTel").value.trim().length ==0){

        alert("전화번호가 입력되지 않았습니다.");
        e.preventDefault();
        return;
    }
    if(document.querySelector("#inputBirth").value.trim().length ==0){

        alert("생년월일이 입력되지 않았습니다.");
        e.preventDefault();
        return;

    }

    for(let key in checkObj){

        let str;
    if(!checkObj[key]){
        
       switch(key) {
        case "inputEmail" : str = "올바른 이메일이 작성되지 않았습니다."; break;
        case "authKey" : str = "이메일이 인증되지 않았습니다."; break;
       }

       alert(str);
       e.preventDefault();
       return;
    }
}
    
    


});

