<?php
define ('HMAC_SHA256', 'sha256');

	function security($payload, $api_key){
		$p = array();
		$new_payload = array();
		foreach ($payload as $key=>$value){
			if($key!='sec_hash' &&$key!='credentials'&&!is_array($value)&&!is_object($value)){
			//	print $key.'\n'.$value;
				$new_payload[strtolower($key)] = $value;
				}
			}
		ksort($new_payload);
		foreach($new_payload as $key=>$value){
			$p[] = $key.'='.$new_payload[$key];

			} 
		$p1 = implode("&",$p);
		print $p1;
		$secretKey = trim(base64_decode($api_key));
		print $secretKey;
		$a = base64_encode(hash_hmac(HMAC_SHA256, $p1, $secretKey, true));
		$payload['sec_hash'] = $a;
		return $payload;
	}

?>

