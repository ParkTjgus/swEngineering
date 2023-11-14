document.addEventListener("DOMContentLoaded", function () {
    const serverUrl = 'http://43.202.35.94:8080';

    function postInfo(serverUrl){
        const uri = `/notice`;
        const noticeContent = document.querySelector('.noticeContent').value; // 요소 가져오기

        fetch(serverUrl + uri, {
            method : 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                noticeContent: noticeContent
            })
        })

        .then(response=>{
            if (!response.ok) {
                throw new Error('공지사항 등록 실패');
            }
            // 성공적으로 등록되었을 때의 처리
            window.alert('공지사항 등록 성공');
            window.location.href = serverUrl + 'info_manage';
        })
        .catch(error =>{
            console.error('등록 오류:', error);
        })

    }

    // 버튼 클릭시 post요청
    const btnRegister = document.getElementById('btn_register');
    btnRegister.addEventListener('click', postInfo(serverUrl));

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