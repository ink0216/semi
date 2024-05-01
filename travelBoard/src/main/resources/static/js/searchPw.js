
const inputEmail = document.querySelector("#inputEmail");
const inputTel = document.querySelector("#inputTel");
const inputBirth = document.querySelector("#inputBirth");

const selectEmailTelBirth = document.querySelector("#selectEmailTelBirth");


/* 이메일 인증번호 주고받기 */


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
    


});



/* 비밀번호 찾기를 위한 이메일 인증 */

