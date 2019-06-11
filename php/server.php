<?php

$request = file_get_contents('php://input');

$input = (array) json_decode($request);


$input['code'].$input['transaction_timestamp'].$input['msisdn'].$input['linkid'].$input['message'];

//Use the array input to capture data for your application
$payload = array();
$payload['response'] = "SUCCESS";
$payload['response_status'] = "00";

echo json_encode($payload);
?>
