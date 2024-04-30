/* 다음 주소 API를 활용함(복붙) */

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

           

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}


/* 주소 버튼 클릭시 실행되도록 변경*/
document.querySelector("#searchAddress").addEventListener("click",execDaumPostcode);









/* 유효성 검사를 위한 객체 */

const checkObj = {
    "memberEmail" : false,
    "memberPw" : false,
    "memberPwConfirm" : false,
    "memberNickname" : false,
    "memberTel" : false,
    "authKey" : false,
    "memberBirth" : false
};

const memberEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");




/* 이메일 중복 유효성 검사 */



/* 입력된 이메일이 없음 */

memberEmail.addEventListener("input", e => {

    const inputEmail = e.target.value;

  
    // 이메일 인증을 성공하고 이메일이 변경된 경우
    checkObj.authkey = false;
    document.querySelector("#authKeyMessage").innerText = "";

    // 공백을 제거한 이메일 입력이 없는 경우

    if(inputEmail.trim().length === 0){

        emailMessage.innerText = "메일을 받을 수 있는 메시지를 입력해 주세요.";


        emailMessage.classList.remove('confirm','error');


        checkObj.memberEmail = false;


        memberEmail.value = "";


        return;

    }


    // 입력한 이메일에 대한 유효성 검사

    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;


    // 입력한 이메일이 정규식과 일치하지 않음

    if(!regExp.test(inputEmail)) {

        emailMessage.innerText = "올바른 이메일 형식이 아닙니다.";

        emailMessage.classList.add('error');

        emailMessage.classList.remove('confirm');

        checkObj.memberEmail = false;

        return;
    }


    // 유효한 형식으로 작성된 이메일을 가지고 중복 검사를 수행

    fetch("/member/checkEmail?memberEmail=" + inputEmail)
    .then(response => response.text())
    .then(count => { 

        if(count == 1){
            emailMessage.innerText = "이미 가입한 이메일입니다."

            emailMessage.classList.add('error');

            emailMessage.classList.remove('confirm');

            checkObj.memberEmail=false;

            return;
        }
        if(count == 0){
            emailMessage.innerText = "사용 가능한 이메일입니다."

            emailMessage.classList.add('confirm');

            emailMessage.classList.remove('error');

            checkObj.memberEmail = true;

        }
    })
    .catch(e => {
        console.log(e)
    })



});



/* 비밀번호와 비밀번호 확인의 유효성 검사 */
const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");
const pwMessage = document.querySelector("#pwMessage");


// 비밀번호와 비밀번호확인이 같은지 검사
const checkPw = () => {

    if(memberPw.value === memberPwConfirm.value){

    pwMessage.innerText = "유효한 비밀번호가 서로 같게 입력되었습니다.";

    pwMessage.classList.add("confirm");

    pwMessage.classList.remove("error");

    checkObj.memberPwConfirm = true; 

    return;
    }

    pwMessage.innerText = "입력한 비밀번호가 서로 불일치합니다.";

    pwMessage.classList.add("error");

    pwMessage.classList.remove("confirm");

    checkObj.memberPwConfirm = false; 

}


/* 비밀번호가 입력되어있는지 확인하기 */
memberPw.addEventListener("input",e => {
    const inputPw = e.target.value

    if (inputPw.trim().length === 0){

        pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";

        pwMessage.classList.remove('confirm');

        pwMessage.classList.remove('error'); // confirm, error시 발생하는 요소들 제거(=검은색 글씨로 만듬)

        checkObj.memberPw = false;

        memberPw.value = ""; // 첫 띄어쓰기 입력을 못하도록 막음

        return; // 아래의 유효성 검사를 막하게 하고 끝냄
    }



    // 비밀번호 정규식
    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;


    // 입력받은 비밀번호가 정규식에 맞지 않음
    if(!regExp.test(inputPw)) {
        pwMessage.innerText = "비밀번호가 유효하지 않습니다.";

        pwMessage.classList.add("error");

        pwMessage.classList.remove("confirm");

        checkObj.memberPw = false;

        return;
    }



    // 정규식까지 다 지키고 유효한 비밀번호 입력 끝

    pwMessage.innerText = "유효한 비밀번호 형식입니다. 비밀번호 확인을 입력하세요.";

    pwMessage.classList.add("confirm");

    pwMessage.classList.remove("error");

    checkObj.memberPw = true;



    // 비밀번호 입력시에도 확인하고 비교하는 함수 추가하기
    if(memberPwConfirm.value.length > 0 ){
        
        checkPw();
    }

});


// 비밀번호가 유효할 때, 유효성 검사를 실행함
memberPwConfirm.addEventListener("input", () => {

    if(checkObj.memberPw){ 
      checkPw(); 
      return;
    }
  
 
    checkObj.memberPwConfirm = false;
  });




/* 닉네임 유효성 검사 */
const memberNickname = document.querySelector("#memberNickname");
const nickMessage = document.querySelector("#nickMessage");


memberNickname.addEventListener("input",e => {

    const inputNickname = e.target.value;

    // 닉네임을 입력 안함

    if(inputNickname.trim().length === 0){

        nickMessage.innerText = "한글,영어,숫자로만 2~10글자";

        nickMessage.classList.remove("confirm","error");

        checkObj.memberNickname = false;

        memberNickname.value = "";

        return;
    }


    // 닉네임 유효성 검사를 위한 정규식
    const regExp = /^[가-힣a-zA-Z0-9]{2,10}$/;


    // 입력한 닉네임이 정규식과 맞지 않음
    if(!regExp.test(inputNickname)){

        nickMessage.innerText = "유효하지 않는 닉네임 형식입니다.";

        nickMessage.classList.add("error");

        nickMessage.classList.remove("confirm");

        checkObj.memberNickname = false;

        return;

    }


    // 닉네임 중복검사하기
    fetch("/member/checkNickname?memberNickname=" + inputNickname)
    .then(response => response.text())
    .then(count =>{

        if(count >= 1){

            nickMessage.innerText = "이미 사용중인 닉네임입니다.";

            nickMessage.classList.add('error');

            nickMessage.classList.remove('confirm');

            checkObj.memberNickname = false;

            return;
        }

        if(count == 0){

        nickMessage.innerText = "사용 가능한 닉네임입니다.";

        nickMessage.classList.add('confirm');

        nickMessage.classList.remove('error');

        checkObj.memberNickname = true;

        }

    })



});



/* 전화번호 유효성 검사하기 */
const memberTel = document.querySelector("#memberTel");
const telMessage = document.querySelector("#telMessage");


memberTel.addEventListener("input", e => {

    const inputTel = e.target.value;

    // 전화번호 입력 안함

    if(inputTel.trim().length === 0){
        telMessage.innerText = "전화번호를 입력해주세요.(- 제외)";
        telMessage.classList.remove('confirm','error');
        checkObj.memberTel = false;
        memberTel.value = "";
        return;
    }

    // 전화번호 정규식
    const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;


    // 입력한 전화번호가 정규식과 맞지 않음
    if(!regExp.test(inputTel)){
        telMessage.innerText = "올바른 전화번호 형식이 아닙니다.";
        telMessage.classList.add('error');
        telMessage.classList.remove('confirm');
        checkObj.memberTel = false;
        return;
    }
        telMessage.innerText = "올바른 전화번호 형식입니다.";
        telMessage.classList.add('confirm');
        telMessage.classList.remove('error');
        checkObj.memberTel = true;

});


/* 이메일 인증 절차를 위한 JS 작성 공간 */

const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");
const authKey = document.querySelector("#authKey");
const authKeyMessage = document.querySelector("#authKeyMessage");
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");


// 타이머 설정하기
let authTimer;

const initMin = 4;
const initSec = 59;
const initTime = "05:00";

let min = initMin;
let sec = initSec;


// 인증번호 받기 버튼 클릭 시
sendAuthKeyBtn.addEventListener("click", () => {

    // 이메일 입력 상태를 알아보는 유효성 검사(유효한 경우에만 인증버튼 작동)
    if(!checkObj.memberEmail){

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
        body : memberEmail.value
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



/* 생년월일 관련 유효성 검사 */

const memberBirth = document.querySelector("#memberBirth");
const birthMessage = document.querySelector("#birthMessage");


memberBirth.addEventListener("input", e =>{

    const inputBirth = e.target.value;


    // 생년월일을 입력안했을 경우
    if(inputBirth.trim().length === 0) {

        birthMessage.innerText = "생년월일을 입력해주세요."
        birthMessage.classList.remove('confirm','error');
        checkObj.memberBirth = false;
        memberBirth.value = "";
        return;
    }


    // 생년월일 정규식
    const regExp = /^(19[0-9][0-9]|20\d{2})(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;


    // 입력한 생년월일이 정규식과 맞지않음

    if(!regExp.test(inputBirth)){

        birthMessage.innerText = "올바른 생년월일 형식이 아닙니다.";

        birthMessage.classList.add('error');

        birthMessage.classList.remove('confirm');

        checkObj.memberBirth = false;

        return;

    }
    birthMessage.innerText = "올바른 생년월일 형식입니다.";
    birthMessage.classList.add('confirm');
    birthMessage.classList.remove('error');
    checkObj.memberBirth = true;


});





// ------------------------------------------------------//

// 가입하기 버튼을 클릭하고 전체 유효성 검사 확인하기
const signUpForm = document.querySelector("#signUpForm");





signUpForm.addEventListener("submit",e => {
for(let key in checkObj){

   

    if(!checkObj[key]){
    // 메시지 출력용 변수
        let str; 

    switch(key){
        case "memberEmail" : str = "이메일이 유효하지 않습니다."; break;
        case "authKey" : str = "이메일이 인증되지 않았습니다."; break;
        case "memberPw" : str = "비밀번호가 유효하지 않습니다."; break;
        case "memberPwConfirm" : str = "비밀번호 확인이 유효하지 않습니다."; break;
        case "memberNickname" : str = "닉네임이 유효하지 않습니다."; break;
        case "memberTel" : str = "전화번호가 유효하지 않습니다."; break;
        case "memberBirth" : str = "생년월일이 유효하지 않습니다."; break;
    }
    alert(str);


    document.getElementById(key).focus();

    e.preventDefault();
    return;
}
}


}

);



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
        "email" : memberEmail.value,
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

        authKeyMessage.classList.innerText = "이메일이 인증되었습니다.";

        authKeyMessage.classList.remove("error");

        authKeyMessage.classList.add("confirm");

        checkObj.authKey = true; // 유효성 검사 인증키 여부를 true로 변환

    })



});



