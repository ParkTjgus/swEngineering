document.addEventListener("DOMContentLoaded", function(event) {

    const serverUrl = 'http://43.202.35.94:8080';
    
    // 서버로부터 공지사항 목록 동적으로 가져오는 함수
    function getNotice() {
        const uri = '/notice';
        
        fetch(serverUrl + uri, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response=>{
            if (!response.ok) {
                throw new Error('공지사항 목록 가져오기 실패');
            }
            return response.json();
        })
        .then(data=>{
            // 서버 응답 데이터를 처리하여 공지사항 목록에 추가
            data.forEach((response) => {
                // 서버 응답 데이터를 반복하며 공지사항 목록 생성 및 화면에 추가하기

                // info_list 요소 선택
                const info_list = document.querySelector(".info_list");

                // 새로운 공지사항 나타내는 요소 생성
                const infoDiv = document.createElement("div");
                infoDiv.classList.add("info"); // 각 요소를 클래스에 추가하고 텍스트 내용 채우기
        
                // 내용 표시하는 요소 생성
                const noticeContent = document.createElement("div");
                noticeContent.classList.add("noticeContent");
                noticeContent.textContent = `${response.noticeContent}`;
        
                // 등록시간 표시하는 요소 생성
                const noticeTime = document.createElement("div");
                noticeTime.classList.add("noticeTime");
                noticeTime.textContent = `${response.noticeTime}`;

                // 조회 버튼 생성
                const btn_detail = document.createElement("button");
                btn_detail.classList.add("btn_detail");
                btn_detail.textContent = "풀이 보기";
                btn_detail.addEventListener("click", () => {
                    // 클릭 시 페이지 이동
                    localStorage.setItem('noticeId', response.id);
                    window.location.href = serverUrl + 'info_detail';
                });
                
                // 만든 요소들을 리스트에 추가하기
                infoDiv.appendChild(noticeContent);
                infoDiv.appendChild(noticeTime);
                infoDiv.appendChild(btn_detail);
                info_list.appendChild(infoDiv);
            })
        })
        .catch (error =>{
            console.error("데이터를 가져오는 중 오류 발생:", error);
        });
    }

    getNotice();

    // 상세조회 버튼 클릭 시
    document.getElementsByClassName('btn_detail').addEventListener('click', function() {
        window.location.href = serverUrl + 'mem_detail';
    })

    // 로그아웃 버튼 클릭 시
    document.getElementById('btn_logout').addEventListener('click', function() {
        // 쿠키 제거
        document.cookie = "userToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        // 로컬 스토리지 클리어
        localStorage.clear();
        alert('로그아웃되었습니다.');
        window.location.href = serverUrl + 'login';
    });
});