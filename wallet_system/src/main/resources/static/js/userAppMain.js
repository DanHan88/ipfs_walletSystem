$(document).ready(function() {
						$('#dataTableContainer').show();
						  $('#alert_modal').on('hidden.bs.modal', function (e) {
							 if ($('#alert_header').hasClass("bg-success")){
								 location.reload(true);
							 } 
					      });
						 $('#payout_fil_request').on('click', function() {
							if($('#payout_fil_address').val()=='' ||$('#payout_fil_amount').val()==''){
								$('#alert3_title').text('입력값을 확인해주세요');
								$('#alert3_modal').modal('show');
								return;
							}else if(parseFloat($('#available_balance').data('available-balance'))<parseFloat($('#payout_fil_amount').val())){
								$('#alert3_title').text('가용잔액을 초과하셨습니다');
								$('#alert3_modal').modal('show');
								
								return;
							}		
							$('#request_wallet_address').text($('#payout_fil_address').val());
							$('#request_fil_balance').text($('#payout_fil_amount').val());
					    	$('#payout_request_modal').modal('show');
					    });
						
						 $('#payout_confirm_button').on('click', function() {	
							 let fil_amount= $('#payout_fil_amount').val();
							 let user_id = $('#payout_fil_request').val();
							 let wallet_address = $('#payout_fil_address').val(); 
					       $.ajax({
			                    type: "POST",
			                    url: "/addWalletWithdrawal",
			                    contentType: "application/json",
			                    data: JSON.stringify({
							        fil_amount: fil_amount,
							        user_id: user_id,
							        wallet_address : wallet_address
							    }),
			                    success: function (data) {
									$('#payout_request_modal').modal('hide');
									if(data=='success'){
										if ($('#alert_header').hasClass("bg-danger")) 
											{
							            		$('#alert_header').removeClass("bg-danger").addClass("bg-success");
							       		 	} 
										$('#alert_title').text("송금신청 완료");
										$('#alert_modal').modal('show');
			                        }
			                        else if(data=='failed:session_closed'){
										$('#session_alert_investment').modal('show');
									}
			                        else{
										if ($('#alert_header').hasClass("bg-success")) 
										{
								            $('#alert_header').removeClass("bg-success").addClass("bg-danger");
							       		} 		
							       		 $('#alert_title').text("송금신청 실패");
								            $('#alert_modal').modal('show');	
									}	
			                    }
			                });
					    });
						
					     $('#dataTable').DataTable({
					    	 "order": [[0, 'desc']]
						});
					    
});