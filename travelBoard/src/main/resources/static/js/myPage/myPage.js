/* 프로필 이미지 */

// 프로필 이미지 페이지 form 태그
const profile = document.querySelector("#profile")

let status = 1;
let backupInput;

if(profile!=null){ // 프로필 이미지 존재할때
  
  // img
  const profileImg = document.querySelector("#profileImg")
  // input type =  file
  const imageInput = document.querySelector("#imageInput")
  // X 버튼 
  const deleteImage = document.querySelector("#deleteImage");


  // 값변화시 동작함수
  const changeImpageFn = e =>{


    // 업로드된 파일 배열 저장
    const file = e.target.files[0];

    // -- 업로드 파일 없음
    if(file == undefined){
      const some = backupInput.cloneNode(true);

      imageInput.after(backupInput);

      imageInput.remove(); 

      imageInput.addEventListener("change",changeImpageFn);

      backupInput = some;

      return;
    }
    // -- 선택 이미지 미리보기
    const reader = new FileReader();

    reader.readAsDataURL(file);

    // 읽어오기 끝났을때 (load) 동작
    reader.addEventListener("load", e =>{
      const url = e.target.result;

      profileImg.setAttribute("src",url);

      status = 1;

      backupInput = imageInput.cloneNode(true);
    })
  }
  
  
  imageInput.addEventListener("change", changeImpageFn);
  // X 버튼 클릭시 기본이미지 변경
  deleteImage.addEventListener("click", () =>{
    
    profileImg.src="/images/profile.jpg"
    
  })

  profile.addEventListener("submit", e =>{

    let flag = true;

    // 기존 프로필 이미지가 없다가 새 이미지가 선택된 경우
    if(loginMemberProfileImg == null && status == 1) flag=false;

    // 기존 프로필 이미지가 있다가 삭제한 경우
    if(loginMemberProfileImg != null && status == 0) flag=false;
    
    // 기존 프로필 이미지가 있다가 새 이미지가 선택된 경우
    if(loginMemberProfileImg != null && status == 1) flag=false;


    if(flag){ // flag 값이 true인 경우 

      e.preventDefault();
      alert("이미지 변경후 클릭하세요");
    }

  })

}
// -------------------------------[프로필 이미지]----------------------------------



/* 회원정보 수정 */
// form태그
const updateInfo = document.querySelector("#updateInfo");

//
if(updateInfo!= null){

  updateInfo.addEventListener("submit", e =>{

    const memberNickname = document.querySelector("#memberNickname");
    const memberTel = document.querySelector("#memberTel");
    const memberAddress = document.querySelectorAll("#memberAddress");

    // 닉네임 유효성 검사
    if(memberNickname.ariaValueMax.trim().length == 0){
      alert("닉네임 입력해 주세요");
      e.preventDefault(); // 제출막기
      return;
    }

    // 닉네임 정규식
    let regExp = /^[가-힣a-zA-Z0-9]{2,10}$/;
    if(!regExp.test(memberNickname.value)){
      alert("닉네임 형식 오류")
      e.preventDefault(); // 제출 막기
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

    
  })
}


//-------------[회원 정보 변경]---------------

/* 비밀번호 변경 */

const changePw = document.querySelector("#changePw");

if(changePw != null){

  const nowPw = document.querySelector("#newPw");
  const newPw = document.querySelector("#newPw");
  const newPwCh = document.querySelector("#newPwCh");

  //
  if( nowPw.value.trim().length == 0 )  str = "현재 비밀번호를 입력해 주세요";
  else if( newPw.value.trim().length == 0 ) str = "새 비밀번호를 입력 해주세요";
  else if( newPwCh.value.trim().length == 0 ) str = "새 비밀번호 확인을 입력 해주세요";

}



