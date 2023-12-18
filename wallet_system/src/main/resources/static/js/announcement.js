$(document).ready(function() {			
						 $('.announcementBodyBtn').on('click', function() {
							 
							 $('#announcement_body').val($(this).data('announce-dody'));
							 
					    	$('#announcementBodyModal').modal('show');
					    });     
						    
						 $('#dataTableContainer').show();
						 
						 $('#announcementModal').on('click', function() {
					    	$('#announcementModal').modal('show');
					    	
					    	 
					    }); 
					    $('#announcementConfirm').off('click').on('click', function() {						 
					                $.ajax({
					                    type: 'POST',
					                    url: '/updateAnnouncements',
					                    contentType: 'application/json',
					                    data: JSON.stringify({
					                        title: $('#announcementTitle').val(),
					                        body: $('#announcementBody').val(),
					                        event_or_announcement: "공지사항"
					                        
					                    }),
					                    success: function(data) {
													$('#announcementModal').modal('hide');
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