/* 프로필 이미지 */

// 프로필 이미지 페이지 form 태그
const profile = document.querySelector("#profile")

// -1 : 초기 상태(변화없음)
  // 0 : 프로필 이미지 삭제
  //  1  : 새이미지 선택
  let statusCheck = -1;

  let backupInput;

if(profile!=null){ // 프로필 이미지 존재할때
  
  // img
  const profileImg = document.querySelector("#profileImg");
  // input type =  file
  let imageInput = document.querySelector("#imageInput");
  // X 버튼 
  const deleteImage = document.querySelector("#deleteImage");



  // 값변화시 동작함수
  const changeImpageFn = e =>{

    // 업로드 파일 최대크기 필터링
    const maxSize = 1024 * 1024 * 5; 

    // 업로드된 파일 배열 저장
    const file = e.target.files[0];

    // -- 업로드 파일 없음
    if(file == undefined){
      // console.log("파일 선택후 취소됨");

      const temp = backupInput.cloneNode(true);

      imageInput.after(backupInput);
      imageInput.remove(); 

      imageInput = backupInput;

      imageInput.addEventListener("change",changeImpageFn);

      backupInput = temp;

      return;
    }
    // 선택파일 최대크기 초과시

    if(file.size >maxSize){
      alert("5MB 이하의 이미지 파일 선택해주세요");

      if (statusCheck == -1){
        imageInput.value = '';
      }else{

      const temp = backupInput.cloneNode(true);

      imageInput.after(backupInput);
      imageInput.remove(); 

      imageInput = backupInput;

      imageInput.addEventListener("change",changeImpageFn);

      backupInput = temp;

      return;

      }
    }




    // -- 선택 이미지 미리보기
    const reader = new FileReader();

    reader.readAsDataURL(file);

    // 읽어오기 끝났을때 (load) 동작
    reader.addEventListener("load", e =>{
      const url = e.target.result;

      profileImg.setAttribute("src",url);

      // 새이미지선택
      statusCheck = 1;

      backupInput = imageInput.cloneNode(true);
    })
  }


  
  imageInput.addEventListener("change", changeImpageFn);

  // X 버튼 클릭시 기본이미지 변경
  deleteImage.addEventListener("click", () =>{
    
    profileImg.src="/images/profile.jpg"

    imageInput.value='';

    backupInput = '';

    statusCheck = 0;
    
  })

  profile.addEventListener("submit", e =>{

    let flag = true;

    // 기존 프로필 이미지가 없다가 새 이미지가 선택된 경우
    if(loginMemberProfileImg == null && statusCheck == 1) flag=false;

    // 기존 프로필 이미지가 있다가 삭제한 경우
    if(loginMemberProfileImg != null && statusCheck == 0) flag=false;
    
    // 기존 프로필 이미지가 있다가 새 이미지가 선택된 경우
    if(loginMemberProfileImg != null && statusCheck == 1) flag=false;


    if(flag){ // flag 값이 true인 경우 

      e.preventDefault();
      alert("이미지 변경후 클릭하세요");
    }

  })

}

/* ---------- 회원 정보 수정 ---------- */

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

// form태그
const updateInfo = document.querySelector("#updateInfo");

// #updateInfo 요소가 존재 할 때만 수행
if(updateInfo!= null){
  
  /* 주소 버튼 클릭시 실행되도록 변경*/
  document.querySelector("#searchAd").addEventListener("click",execDaumPostcode);

  // form 제출시
  updateInfo.addEventListener("submit", e =>{

    const memberNickname = document.querySelector("#memberNickname");
    const memberTel = document.querySelector("#memberTel");
    const memberAddress = document.querySelectorAll("[name='memberAddress']");

    // 닉네임 유효성 검사
    if(memberNickname.value.trim().length == 0){
      alert("닉네임 입력해 주세요");
      e.preventDefault(); 
      return;
    }

    // 닉네임 정규식 : 한글,영어,숫자 중 2-10자리
    let regExp = /^[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,10}$/;
    if(!regExp.test(memberNickname.value)){
      alert("닉네임 형식 오류")
      e.preventDefault(); 
      return;
    }

    // 전화번호 유효성 검사
    if(memberTel.value.trim().length==0){
      alert("전화번호를 입력해 주세요");
      e.preventDefault();
      return;
    }

    // 정규식
    regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;
    if(!regExp.test(memberTel.value)){
      alert("전화번호 형식 오류");
      e.preventDefault();
      return;
    }

    // 주소 유효성 검사
    // 입력을 안하면 전부 안해야 되고
    // 입력하면 전부 입력해야 된다

    const addr0 = memberAddress[0].value.trim().length == 0; // t/f
    const addr1 = memberAddress[1].value.trim().length == 0; // t/f
    const addr2 = memberAddress[2].value.trim().length == 0; // t/f

    const result1 = addr0 && addr1 && addr2; // 아무것도 입력 안됐을때,
    // 셋전부다 입력 안됐을때 = 0일때 -> 다 입력 안되어있을때 true/ 하나라도 입력 되면 fasle

    const result2 = !(addr0 || addr1 || addr2); // 모두다 입력 할때
    // 셋중에 하나라도 0면 안될때 -> 하나라도 입력안되어있으면 false / 다 입력되면 true

    if(!( result1 || result2)){
      alert("주소를 모두 입력 또는 모두 미입력해주세요");
      e.preventDefault();
    }

    // 주소 입력시 정규식
    if( result2){
      regExp = /^[0-9]{5}$/
      if(!regExp.test(memberAddress[0].value)){
        alert("우편번호 형식오류");
        e.preventDefault();
        return;
      }
      
    }
    
  })
}


/* ---------- 비밀번호 변경 ---------- */

const changePw = document.querySelector("#changePw");

if( changePw != null){

  //제출 되었을 때
  changePw.addEventListener("submit", e =>{

    const nowPw = document.querySelector("#nowPw");
    const newPw = document.querySelector("#newPw");
    const newPwCh = document.querySelector("#newPwCh");

    // 현재, 새 비번, 확인 전부 입력하기
    let str;

    if( nowPw.value.trim().length == 0) str="현재 비밀번호를 입력해 주세요";
    else if ( newPw.value.trim().length == 0) str = "새 비밀번호를 입력해 주세요";
    else if( newPwCh.value.trim().length == 0 ) str = "새 비밀번호 확인을 입력 해주세요";

    if(str != undefined){
      alert(str);
      e.preventDefault();
      return;
    }

    // 비밀번호 정규식 : 소/대문자 영문, 숫자, 특수문자 중 6~20 자리
    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;
    if( !regExp.test(newPw.value)){
      alert("새 비밀번호가 유효하지 않습니다");
      e.preventDefault();
      return;
    }

    if(newPw.value != newPwCh.value){
      alert("새 비밀번호가 확인과 일치하지 않습니다");
      e.preventDefault();
      return;
    }

  });

}


/* ------- 회원 탈퇴 --------  */

const secession = document.querySelector("#secession");

if(secession != null){

  secession.addEventListener("submit", e =>{

    const memberId = document.querySelector("#memberId");
    const memberPw = document.querySelector("#memberPw");
    const agree = document.querySelector("#agree");

    let str;

    if(memberId.value.trim().length == 0) str("아이디를 입력해주세요");
    else if(memberPw.value.trim().length ==0) str("비밀번호를 입력해 주세요");

    if(str != undefined){
      alert(str);
      e.preventDefault();
      return;
    }

    if ( !agree.checked){
      alert("탈퇴 동의를 체크해 주세요");
      e.preventDefault();
      return;
    }

    if( !confirm("정말 탈퇴하시겠습니다?")){ //취소 선택시
      alert("취소 되었습니다");
      e.preventDefault();
      return;
    }


  })

}






