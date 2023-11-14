document.addEventListener("DOMContentLoaded", function(event) {      
    // localStorage에서 memberId
    const memberId = localStorage.getItem('memberId');
    const serverUrl = 'http://43.202.35.94:8080';
   
    // 서버로부터 정보를 가져오는 함수
    function getMemberDetail(serverUrl) {
        const uri = `/member/${memberId}/detail`;

        // fetch API를 사용하여 데이터 가져오기
        fetch(serverUrl + uri, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                console.log("서버응답 : ", response); // 토큰을 콘솔에 출력
                throw new Error("데이터 가져오기 실패: " + response.status + " " + response.statusText);
                //response.status = 응답상태코드
            }
            return response.json(); // JSON 형식의 응답 데이터를 파싱
        })
        .then(data => {
            // 데이터를 화면에 표시
            document.querySelector(".memberName").textContent = data.memberName;
            document.querySelector(".memberId").textContent = data.memberId;
            document.querySelector(".memberMajor").textContent = data.memberMajor;
            document.querySelector(".memberBirth").textContent = data.memberBirth;
            document.querySelector(".createTime").textContent =  data.createTime;
            document.querySelector(".memberState").textContent = data.memberState;
        })
        .catch(error => {
            console.error("데이터 가져오기 실패:", error);
        });
    }

    // 회원삭제함수
    function deleteMember(serverUrl) {
        const uri = `/member/${memberId}/delete`;

        // fetch API를 사용하여 데이터 가져오기
        fetch(serverUrl + uri, {
            method: "DELETE"
        })
        .then(response => {
            if (!response.ok) {
                console.log("서버응답 : ", response); // 토큰을 콘솔에 출력
                throw new Error("데이터 가져오기 실패: " + response.status + " " + response.statusText);
            }
            window.alert("회원삭제 성공");
            window.location.href = serverUrl + 'mem_manage';
        })
        .catch(error => {
            console.error("데이터 가져오기 실패:", error);
        });
    }


    // 페이지가 로드될 때 데이터 가져오기 함수 호출
    getMemberDetail(serverUrl);

    // 회원삭제 버튼 클릭 시
    document.getElementById('btn_delete').addEventListener('click', function() {
        deleteMember(serverUrl);  // 회원삭제함수실행
        window.alert("회원이 삭제됐습니다");
        // url수정하기
        window.location.href = serverUrl + 'mem_manage';
        // window.location.href = 'http://127.0.0.1:5500/' + 'mem_manage.html';
    })

    // 로그아웃 버튼 클릭 시
    document.getElementById('btn_logout').addEventListener('click', function() {
        // 쿠키 제거
        // 과거의 날짜로 설정하여 쿠키를 즉시 만료
        document.cookie = "Authorization=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        // 로컬 스토리지 클리어
        localStorage.clear();
        alert('로그아웃되었습니다.');
        window.location.href = serverUrl + 'signin';
    });
});