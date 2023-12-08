$(document).ready(function() {
    $('#dataTableContainer').show();

    $('.request-approve').each(function() {
        if ($(this).parent().parent().find('#status').text() !='신청') {
            $(this).hide();
            $(this).parent().parent().find('.request-decline').hide();
        }
    });
    
    $('.request-approve').on('click', function() {
		var clickedButton = $(this);
        // 해당 행에서 '이메일/회원명' 열의 데이터를 가져옴
       
        var wallet_address = $(this).closest('tr').find('[id^="wallet_address"]').text().trim();
        var userStatus = $(this).closest('tr').find('#status').text().trim();            
         
        console.log('wallet_address:', wallet_address)
       // 여기서 AJAX 요청을 보내거나 필요한 작업을 수행
        // 상태가 '요청'인 경우에만 처리하도록 조건 추가
        if (userStatus === '신청') {
             $.ajax({
			        type: 'POST',  // 또는 'GET' 등 HTTP 메소드 지정
			        url: '/approveFundRequest',  // 실제로 처리할 URL로 변경
			        contentType: 'application/json',
			        data: JSON.stringify({
			            wallet_address: wallet_address
			        }),
			        success: function(response) {
			            // 성공적으로 응답을 받았을 때 수행할 동작
			            console.log('요청 승인 성공:', response);
			        },
			        error: function(error) {
			            // 요청에 실패했을 때 수행할 동작
			            console.error('요청 승인 실패:', error);
			        }
			    });
        } else {
            console.log('이 요청은 처리할 수 없습니다. 상태: ' + userStatus);
            debugger;
        }
    });

    $('#dataTable').DataTable({
        "order": [[0, 'desc']]
    });
});

