<?php
require 'utils/security.php';
require 'utils/request.php';

define("API_KEY","<<API_KEY>>");
define("USERNAME","<<USERNAME>>");
define("PASSWORD","<<PASSWORD>>");


function send_batch_notification($recipients,$message,$code,$keyword="",$linkid="",$transid="", $schedule=""){
	$payload = init_payload();
	$credentials = array();
	$credentials['username'] = USERNAME;
	$credentials['password'] = PASSWORD;
	$payload["credentials"] = $credentials;
	$payload["recipients"] = $recipients;
	$payload["message"] = $message;
	$payload["alias"] = $code;
	$payload['linkid'] = $linkid;
	$payload["ext_outbound_id"] = $transid;
	$payload["keyword"] = $keyword;
	$payload["scheduled_send"] = $schedule; // d/m/Y H:M (am/pm)
	print_r($payload);
	print "";
	$payload = security($payload, API_KEY);
	return request($payload, 'SEND%20BATCH%20NOTIFICATION');
}


function send_sms($msisdn,$message,$code,$keyword="",$linkid="",$transid="", $schedule=""){
	$payload = init_payload();
	$credentials = array();
	$credentials['username'] = USERNAME;
	$credentials['password'] = PASSWORD;
	$payload["credentials"] = $credentials;
	$payload["MSISDN"] = $msisdn;
	$payload["message"] = $message;
	$payload["alias"] = $code;
	$payload['linkid'] = $linkid;
	$payload["ext_outbound_id"] = $transid;
	$payload["keyword"] = $keyword;
	$payload["scheduled_send"] = $schedule; // d/m/Y H:M (am/pm)
	print_r($payload);
	print "";
	$payload = security($payload, API_KEY);
	return request($payload, 'SEND%20SMS');
}

//*************************SEND BATCH NOTIFICATION************************************
//$result = send_batch_notification(RECIPIENT,MESSAGE,CODE,KEYWORD,LINKID,TRANSACTION_ID,SCHEDULED_SEND);
//SCHEDULED_SEND (format) = "17/09/2016 6:31 am" (d/m/Y H:M (am/pm))
//KEYWORD|LINKID|TRANSACTION_ID|SCHEDULED_SEND are optional
//A transaction can be referenced using the reference number number which is part of the result or the optional transaction id if was part of the request

$result = send_batch_notification(array(+2547XXXXXXXX,+2547XXXXXXXX),"Hello. This is a test SMS from InterIntel. Thank you","TEST","","","","");



//*************************SEND SMS************************************
//$result = send_sms(MSISDN,MESSAGE,CODE,KEYWORD,LINKID,TRANSACTION_ID,SCHEDULED_SEND);
//SCHEDULED_SEND (format) = "17/09/2016 6:31 am" (d/m/Y H:M (am/pm))
//KEYWORD|LINKID|TRANSACTION_ID|SCHEDULED_SEND are optional
//A transaction can be referenced using the reference number number which is part of the result or the optional transaction id if was part of the request

$result = send_sms("+2547XXXXXXXX","Hello. This is a test SMS from InterIntel. Please confirm that the SMS Name (ALIAS) is configured correctly. Thank you","ALIAS","","LINKID","MYTRANSID","17/09/2016 6:31 am");

print_r($result);


?>

