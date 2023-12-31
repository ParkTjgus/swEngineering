document.addEventListener("DOMContentLoaded", function(event) {  

    // 서버 URL 및 URI
    const serverUrl = 'http://43.202.35.94:8080';
    // const serverUrl  = 'http://localhost:8080';


    // 목표시간 설정 함수
    function sendStudyGoal(studyGoal) {
        const uri = '/admin/studygoal';

        fetch(serverUrl+ uri, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                studyGoal: studyGoal
            })
        })

        // 서버 응답을 처리
        // 네트워크 오류가 발생한 경우
        .then((response) => {
            console.log(response.status);
            if (!response.ok) {
                throw new Error('네트워크 응답이 실패했습니다.'); 
            }
            return response.json();
        })

        .then((responseData) => {
            // 성공적으로 응답을 받았을 때 실행
            if (responseData.message) {
                // localStorage.setItem('adminID', adminID);
                const resultMessage = responseData.message;
                displayResult(resultMessage);
            } else {
                // 메시지 속성이 없는 경우 예외 처리
                displayResult('목표시간 설정 성공: 메시지 없음');
            }
            // 페이지 이동
            // window.location.href = serverUrl + '/html/';
        })
        // 요청 또는 응답처리 중에 오류가 발생한 경우
        .catch((error) => {
            // 오류 발생 시 실행되는 코드
            // const errorMessage = '오류 발생: ' + error.message;
            const errorMessage = '목표시간 설정 실패';

            displayResult(errorMessage);
        });
    }


    // 멤버요청 함수
    function getMember() {
        const uri = '/admin/memInfo';
        
        fetch(serverUrl + uri, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response=>{
            if (!response.ok) {
                throw new Error('멤버 목록 가져오기 실패');
            }
            return response.json();
        })
        .then(data=>{
            console.log('전체 멤버 출력!');
            // 서버 응답 데이터를 처리하여 문제 목록에 추가
            data.forEach((response) => {
                // 서버 응답 데이터를 반복하며 문제 목록 생성 및 화면에 추가하기
                console.log('response : ',response);

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

                // 출석시간 표시 요소 생성
                const totalRecordTime = document.createElement("div");
                totalRecordTime.classList.add("totalRecordTime");
                totalRecordTime.textContent = `${response.totalRecordTime}`;

                // 조회 버튼 생성
                const btn_detail = document.createElement("button");
                btn_detail.classList.add("btn_detail");
                btn_detail.textContent = "상세정보";
                btn_detail.addEventListener("click", () => {
                    // 클릭 시 페이지 이동
                    localStorage.setItem('memberId', response.memberId);
                    window.location.href = serverUrl + '/html/mem_detail';
                });

            
                // 만든 요소들을 리스트에 추가하기
                memberDiv.appendChild(memberName);
                memberDiv.appendChild(memberId);
                memberDiv.appendChild(totalRecordTime);

                memberDiv.appendChild(btn_detail);
                member_list.appendChild(memberDiv);
            })
        })
        .catch (error =>{
            console.error("데이터를 가져오는 중 오류 발생:", error);
        });
    }

    // 달성멤버 출력 함수
    function getAchieveMem(){
        const uri = '/admin/record';
        
        fetch(serverUrl + uri, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response=>{
            if (!response.ok) {
                throw new Error('멤버 목록 가져오기 실패');
            }
            return response.json();
        })
        .then(data=>{
            console.log('달성 멤버 출력!');
            console.log('data : ',data);

            // 서버 응답 데이터를 처리하여 문제 목록에 추가
            data.forEach((response) => {

                // 서버 응답 데이터를 반복하며 문제 목록 생성 및 화면에 추가하기
                console.log(response);
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

                // 출석시간 표시 요소 생성
                const totalRecordTime = document.createElement("div");
                totalRecordTime.classList.add("totalRecordTime");
                totalRecordTime.textContent = `${response.totalRecordTime}`;

                // 조회 버튼 생성
                const btn_detail = document.createElement("button");
                btn_detail.classList.add("btn_detail");
                btn_detail.textContent = "상세정보";
                btn_detail.addEventListener("click", () => {
                    // 클릭 시 페이지 이동
                    localStorage.setItem('memberId', response.memberId);
                    window.location.href = serverUrl + 'mem_detail';
                });

            
                // 만든 요소들을 리스트에 추가하기
                memberDiv.appendChild(memberName);
                memberDiv.appendChild(memberId);
                memberDiv.appendChild(totalRecordTime);

                memberDiv.appendChild(btn_detail);
                member_list.appendChild(memberDiv);
            })
        })
        .catch (error =>{
            console.error("데이터를 가져오는 중 오류 발생:", error);
        });

    }
    // 결과 표시 함수
    function displayResult(message) {
        alert(message);
    }

    getMember();

    // 체크박스 클릭
    document.getElementById('btn_check').addEventListener('change', function() {
        const memberList = document.querySelector(".member_list");

        // memberList 내의 title div 요소 유지
        const titleDiv = memberList.querySelector(".title");

        memberList.innerHTML = ''; // 기존의 멤버 목록을 비워줌
        memberList.appendChild(titleDiv); // titleDiv 다시 추가
        if (this.checked) {
            // 체크박스 클릭시 목표달성멤버만 출력
            getAchieveMem();
        } else {
            // 체크박스가 해제시 모든멤버 출력
            getMember();
        }
    });

    // 설정버튼 클릭시 input값 post
    document.querySelector('.btn_select').addEventListener('click', () => {
        const studyGoal = String(document.querySelector(".input_time").value).trim();
        sendStudyGoal(studyGoal);
    });

    // 로그아웃 버튼 클릭 시
    document.querySelector('.btn_logout').addEventListener('click', function() {
        // 쿠키 제거
        document.cookie = "userToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        // 로컬 스토리지 클리어
        localStorage.clear();
        alert('로그아웃되었습니다.');
        window.location.href = serverUrl + '/html/login';
    });

});

