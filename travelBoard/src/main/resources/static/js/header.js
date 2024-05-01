const searchAll = document.querySelector(".searchAll"); //헤더 중앙의 검색 버튼 -> 클릭 시 전체 글에서 조회(대륙 가리지 않고)
if(searchAll !=null){
    searchAll.addEventListener("click", (e)=>{
    // /board/searchAll
    // previousElement 아니고 previousElementSibling이다!!!!
    const query = e.target.previousElementSibling.value;
    if(query.trim().length==0){
        alert("검색어를 입력해 주세요.");
        return;
    }
    const url = "/board/searchAll";

    const form = document.createElement("form");
    form.action= url;
    form.method = "POST";

    const input = document.createElement("input");
    input.type="hidden";
    input.name="query1";
    input.value=query;
    
    form.append(input);
    document.querySelector("body").append(form);
    form.submit();
});
}