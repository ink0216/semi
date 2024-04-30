

/* 비밀번호 재설정 화면에서 유효성검사하기 */

const checkObj = {

    "memberPwReset" : false,
    "memberPwResetConfirm" :false
};


const memberPwReset = document.querySelector("#memberPwReset");
const memberPwResetConfirm = document.querySelector("#memberPwResetConfirm");
const pwMessage = document.querySelector("#pwMessage");




const checkPw = () => {

    if(memberPwReset.value === memberPwResetConfirm.value){

    pwMessage.innerText = "유효한 비밀번호가 서로 같게 입력되었습니다.";

    pwMessage.classList.add("confirm");

    pwMessage.classList.remove("error");

    checkObj.memberPwResetConfirm = true; 

    return;
    }

    pwMessage.innerText = "입력한 비밀번호가 서로 불일치합니다.";

    pwMessage.classList.add("error");

    pwMessage.classList.remove("confirm");

    checkObj.memberPwResetConfirm = false; 

}




memberPwReset.addEventListener("input",e => {
    const inputPwReset = e.target.value

    if (inputPwReset.trim().length === 0){

        pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";

        pwMessage.classList.remove('confirm');

        pwMessage.classList.remove('error'); // confirm, error시 발생하는 요소들 제거(=검은색 글씨로 만듬)

        checkObj.memberPwReset = false;

        memberPwReset.value = ""; // 첫 띄어쓰기 입력을 못하도록 막음

        return; // 아래의 유효성 검사를 막하게 하고 끝냄
    }



    // 비밀번호 정규식
    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;


    // 입력받은 비밀번호가 정규식에 맞지 않음
    if(!regExp.test(inputPwReset)) {
        pwMessage.innerText = "비밀번호가 유효하지 않습니다.";

        pwMessage.classList.add("error");

        pwMessage.classList.remove("confirm");

        checkObj.memberPwReset = false;

        return;
    }



    // 정규식까지 다 지키고 유효한 비밀번호 입력 끝

    pwMessage.innerText = "유효한 비밀번호 형식입니다. 비밀번호 확인을 입력하세요.";

    pwMessage.classList.add("confirm");

    pwMessage.classList.remove("error");

    checkObj.memberPwReset = true;



    // 비밀번호 입력시에도 확인하고 비교하는 함수 추가하기
    if(memberPwResetConfirm.value.length > 0 ){
        
        checkPw();
    }

});


// 비밀번호가 유효할 때, 유효성 검사를 실행함
memberPwResetConfirm.addEventListener("input", () => {

    if(checkObj.memberPwReset){ 
      checkPw(); 
      return;
    }
  
 
    checkObj.memberPwResetConfirm = false;
  });


const pwReset = document.querySelector("#pwReset");





pwReset.addEventListener("submit",e => {
    for(let key in checkObj){

    if(!checkObj[key]){
        let str;
        
        switch(key){
            case "memberPwReset" : str = "비밀번호가 유효하지 않습니다."; break;
            case "memberPwResetConfirm" : str = "비밀번호 확인이 유효하지 않습니다."; break;

        }
        alert(str);

        e.preventDefault();

        return;

    }
}
}

);
