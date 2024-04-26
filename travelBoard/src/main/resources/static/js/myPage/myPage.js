/* 프로필 이미지 */

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

      imai

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


// -----------------------------------------------------------------
