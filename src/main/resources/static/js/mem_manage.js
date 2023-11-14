document.addEventListener("DOMContentLoaded", function(event) {

    const serverUrl = 'http://43.202.35.94:8080';
    
    // 서버로부터 회원 목록 동적으로 가져오는 함수
    function getMember() {
        const uri = '/member/info';
        
        fetch(serverUrl + uri, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response=>{
            if (!response.ok) {
                throw new Error('멤버 목록 가져오기 실패');
            }
            return response.json();
        })
        .then(data=>{
            // 서버 응답 데이터를 처리하여 문제 목록에 추가
            data.forEach((response) => {
                // 서버 응답 데이터를 반복하며 문제 목록 생성 및 화면에 추가하기

                // member_list 요소 선택
                const member_list = document.querySelector(".member_list");

                // 새로운 멤버 나타내는 요소 생성
                const memberDiv = document.createElement("div");
                memberDiv.classList.add("member"); // 각 요소를 클래스에 추가하고 텍스트 내용 채우기
        
                // 이름 표시하는 요소 생성
                const memberName = document.createElement("div");
                memberName.classList.add("memberName");
                memberName.textContent = `${response.memberName}`;
        
                // 학번 표시하는 요소 생성
                const memberId = document.createElement("div");
                memberId.classList.add("memberId");
                memberId.textContent = `${response.memberId}`;

                // 조회 버튼 생성
                const btn_detail = document.createElement("button");
                btn_detail.classList.add("btn_detail");
                btn_detail.textContent = "풀이 보기";
                btn_detail.addEventListener("click", () => {
                    // 클릭 시 페이지 이동
                    localStorage.setItem('memberId', response.memberId);
                    window.location.href = serverUrl + 'mem_detail';
                });

            
                // 만든 요소들을 리스트에 추가하기
                memberDiv.appendChild(memberName);
                memberDiv.appendChild(memberId);
                memberDiv.appendChild(btn_detail);
                member_list.appendChild(memberDiv);
            })
        })
        .catch (error =>{
            console.error("데이터를 가져오는 중 오류 발생:", error);
        });
    }

    getMember();

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