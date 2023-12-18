$(document).ready(function() {
						 $('.eventBodyBtn').on('click', function() {
							$('#event_body').val($(this).data('event-dody'));
					    	$('#eventBodyModal').modal('show');
					    });
					    
	
	
					$('#dataTableContainer').show();
					
					 $('#EventsModal').on('click', function() {
					    	$('#EventsModal').modal('show');
					    	
					    	 
					    }); 
					    $('#EventsConfirm').off('click').on('click', function() {						 
					                $.ajax({
					                    type: 'POST',
					                    url: '/updateEvents',
					                    contentType: 'application/json',
					                    data: JSON.stringify({
					                        title: $('#EventsTitle').val(),
					                        body: $('#EventsBody').val(),
					                        event_or_announcement: "이벤트"
					                        
					                    }),
					                    success: function(data) {
													$('#EventsModal').modal('hide');
					                                if (data === 'success') {
					                                    // 성공 시 모달 닫고 새로고침
					                                    location.reload(true);
					                                    
					                                  
					                      
					                                } else if (data === 'failed:session_closed') {
					                                    // 세션 종료 시 다른 처리
					                                    $('#session_alert_user').modal('show');
					                                }
					                            },
					                    error: function(error) {
					                        // 요청에 실패했을 때 수행할 동작
					                        console.error('승인 요청 실패:', error);
					                    },   
					                });
					            
					        }); 
					        
					     $('#dataTable').DataTable({
					    	 "order": [[0, 'desc']]
						});		    
});