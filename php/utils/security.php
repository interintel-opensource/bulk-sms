<?php
define ('HMAC_SHA256', 'sha256');

	function urlsafe_b64encode($string)
	{
	    $data = base64_encode($string);
	    $data = str_replace(array('+', '/'), array('-', '_'), $data);
	    return $data;
	}

	function urlsafe_b64decode($string)
	{
	    $data = str_replace(array('-', '_'), array('+', '/'), $string);
	    $mod4 = strlen($data) % 4;
	    if ($mod4) {
		$data .= substr('====', $mod4);
	    }
	    return base64_decode($data);
	}

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
		/*
		$secretKey = trim(base64_decode($api_key));
		print $secretKey;
		$a = base64_encode(hash_hmac(HMAC_SHA256, $p1, $secretKey, true));
		*/
		$secretKey = trim(urlsafe_b64decode($api_key));
		print $secretKey;
		$a = urlsafe_b64encode(hash_hmac(HMAC_SHA256, $p1, $secretKey, true));
		$payload['sec_hash'] = $a;
		return $payload;
	}

?>

