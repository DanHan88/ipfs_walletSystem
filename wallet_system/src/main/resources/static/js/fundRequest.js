$(document).ready(function() {
	var wallet_withdrawals_id;
	var userStatus;
	var currentUrl;
    var currentPageNumber;

	
	

    $('#dataTableContainer').show();
    $('#alert_modal_requestfund').on('hidden.bs.modal', function (e) {
        if ($('#alert_header_user').hasClass("bg-success")){
            location.reload(true);
        }
    });      
    

    $('.request-approve').on('click', function() {
        var clickedButton = $(this);
        wallet_withdrawals_id = clickedButton.closest('tr').find('[data-wallet-withdrawals-id]').data('wallet-withdrawals-id');
        userStatus = clickedButton.closest('tr').find('#status').text().trim();
        var fil_amount = clickedButton.closest('tr').find('#fil_amount').text().trim();

        // 모달 내용 설정
        $('#alert_title_user').text("승인 하시겠습니까?");

                // 확인 버튼만 보이도록 조절
        $('#confirmModal').show();
        $('#confirmSuccessModalBtn').hide();

                // 확인 모달 표시
        $('#approvalModal').modal('show');
        

        // 모달 내 '확인' 버튼 클릭 처리
        $('#confirmModal').off('click').on('click', function() {
            if (userStatus === '신청') {
                $.ajax({
                    type: 'POST',
                    url: '/updateFundRequest',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        wallet_withdrawals_id: wallet_withdrawals_id,
                        is_request_state: "승인",
                        fil_amount: fil_amount
                    }),
                    success: function(data) {
                                if (data === 'success') {
                                    // 성공 시 모달 내용 변경
                                    $('#alert_header_user').removeClass("bg-danger").addClass("bg-success");
                                    $('#alert_title_user').text("승인 되었습니다");

                                    // 확인 버튼 대신 승인 완료 버튼 표시
                                    $('#approvalModal').hide();
                                    $('#confirmModal').hide();
                                    $('#cancelModal').hide();
                                    $('#confirmSuccessModal').modal('show');
                                    $('#confirmSuccessModalBtn').show();
                                    $('#confirmSuccessModalBtn').on('click', function() {
									     var currentPageNumber = $('#dataTable').DataTable().page.info().page;
	                                var newUrl = updateQueryStringParameter(window.location.href, 'page', currentPageNumber);
	                                window.location.href = newUrl;
	                                location.reload(true);

									    

								   
								    });

                                    // 승인 완료 모달 표시
                      
                                } else if (data === 'failed:session_closed') {
                                    // 세션 종료 시 다른 처리
                                    $('#session_alert_user').modal('show');
                                } else {
                                    // 실패 시 모달 내용 변경
                                    $('#alert_header_user').removeClass("bg-success").addClass("bg-danger");
                                    $('#alert_title_user').text("승인 실패");
                                }
                            },
                    error: function(error) {
                        // 요청에 실패했을 때 수행할 동작
                        console.error('승인 요청 실패:', error);
                    },
                    
                });
            } else {
                console.log('이 요청은 처리할 수 없습니다. 상태: ' + userStatus);
            }
        });
    });

    $('.request-decline').on('click', function() {
		$('#declineModal').modal('show');
		wallet_withdrawals_id = $(this).closest('tr').find('[data-wallet-withdrawals-id]').data('wallet-withdrawals-id');
	    userStatus = $(this).closest('tr').find('#status').text().trim();
    });
    
    $('#declineConfirmBtn').off('click').on('click', function() {
			var memo = $('#declineReason').val();
	        console.log(memo)

	        if (userStatus === '신청') {
	            $.ajax({
	                type: 'POST',
	                url: '/updateFundRequest',
	                contentType: 'application/json',
	                data: JSON.stringify({
	                    wallet_withdrawals_id: wallet_withdrawals_id,
	                    is_request_state: "거절",
	                    memo: memo
	                }),
	                success: function(data) {
                    // 성공 또는 실패에 따른 처리
                    if (data === 'success') {
                        // 성공 시
                        $('#decline_modal_header').removeClass("bg-danger").addClass("bg-success");
                        $('#declineModal').hide();
                        $('#declineSuccessModal').modal('show');
                        $('#declineSuccessModalBtn').show();
                        $('#declineSuccessModalBtn').on('click', function() {
								
								 location.reload(true);
								    });
                        
                        
                    } else if (data === 'failed:session_closed') {
                        // 세션 종료 시 다른 처리
                        $('#session_alert_user').modal('show');
                    } else {
                        // 실패 시
                        $('#decline_modal_header').removeClass("bg-success").addClass("bg-danger");
                        $('#decline_title_user').text("거절 실패");
                    }

                    // 모달 닫기
                    $('#declineModal').modal('hide');
                },
	                error: function(error) {
	                    // 요청에 실패했을 때 수행할 동작
	                    console.error('거절 요청 실패:', error);
	                }
	            });
	        } else {
	            console.log('이 요청은 처리할 수 없습니다. 상태: ' + userStatus);
	        }
       });
        $('.request-DeclineMemo').on('click', function() {
							var memo = $(this).data('decline-memo');
							console.log(memo);
							$('#declineMemoModal_body').text(memo);
							 
					    	$('#declineMemoModal').modal('show');
					    });

    $('#dataTable').DataTable({
        "order": [[0, 'desc']]
       
    });

});
function updateQueryStringParameter(uri, key, value) {
    var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
    var separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (uri.match(re)) {
        return uri.replace(re, '$1' + key + "=" + value + '$2');
    } else {
        return uri + separator + key + "=" + value;
    }
}