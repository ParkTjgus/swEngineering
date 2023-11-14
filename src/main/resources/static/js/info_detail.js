document.addEventListener("DOMContentLoaded", function(event) {      
    // localStorage에서 noticeId
    const noticeId = localStorage.getItem('noticeId');
    const serverUrl = 'http://43.202.35.94:8080';
   
    // 서버로부터 정보를 가져오는 함수
    function getNoticeDetail(serverUrl) {
        const uri = `/notice/${noticeId}`;

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
            document.querySelector(".updateTime").textContent = data.updateTime;
            document.querySelector(".noticeContent").textContent = data.noticeContent;
        })
        .catch(error => {
            console.error("데이터 가져오기 실패:", error);
        });
    }

    // 공지사항삭제함수
    function deleteNotice(serverUrl) {
        const uri = `/notice/${noticeId}`;

        // fetch API를 사용하여 데이터 가져오기
        fetch(serverUrl + uri, {
            method: "DELETE"
        })
        .then(response => {
            if (!response.ok) {
                console.log("서버응답 : ", response); // 토큰을 콘솔에 출력
                throw new Error(" 실패: " + response.status + " " + response.statusText);
            }
            window.alert("공지사항 삭제 성공");
            window.location.href = serverUrl + 'info_manage';
        })
        .catch(error => {
            console.error("실패:", error);
        });
    }


    // 페이지가 로드될 때 데이터 가져오기 함수 호출
    getNoticeDetail(serverUrl);

    // 공지사항 삭제 버튼 클릭 시
    document.getElementById('btn_logout').addEventListener('click', function() {
        deleteNotice(serverUrl);  // 공지사항 삭제함수실행
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