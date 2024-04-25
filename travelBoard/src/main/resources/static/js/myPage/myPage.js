/* 프로필 이미지 */
const profileImg = document.querySelector("#profileImg")
// X 버튼 클릭시
const deleteImage = document.querySelector("#deleteImage");

deleteImage.addEventListener("click", () =>{

  profileImg.src="/images/profile.jpg"

})