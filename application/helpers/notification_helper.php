<?php

/**
 * Description of FCM
 *
 * @author Amal Abdulraouf
 */
class NotificationHelper {
	
	const MSG = 1; 
	const ACTION = 2;

    function __construct() {
    }
	
    public function send_notification_to_device($registatoin_ids, $message, $data, $os, $title , $type) {
        $message_object = array(
            'ntf_title' => $title, 
            "ntf_text" => $message,
            "ntf_body" => $data,
            'ntf_type'=>$type
        );
        $url = 'https://fcm.googleapis.com/fcm/send';
        if ($os == 2) // ios
            $fields = array(
                'registration_ids' => $registatoin_ids,
                'data' => $message_object,
                'content_available' => true,
                'priority' => 'high',
                'notification' => array('sound' => 'default', 'badge' => 0,'title'=>$title ,'body' => $message)
            );
        else 
            $fields = array(
                'registration_ids' => $registatoin_ids,
                'data' => $message_object
            );

        $headers = array(
            'Authorization: key=' . "AAAANaXF6qQ:APA91bEJaqvEJDS2smRFmIZwN-HoWEUCpoxvqueFPNb8fhQyMiLtA08V-YDKNJHuSGVi6QsHGhVtCpBQRW1lmr_QUFEBWMebx-Jnhj7xRZ4qzDcPkoy0gD8I_MoeYalRar_SOrwGHRXe",
            'Content-Type: application/json'
        );
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        // Disabling SSL Certificate support temporarly
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

        // Execute post
        $result = curl_exec($ch);
		
        $res = json_decode($result);
	    //dump($message_object);
	    // var_dump("amal",$result); die();
        // Close connection
        curl_close($ch);
        
        if (!$res->success) {
            return 0;
        } else {
            return 1;
        }
    }

}

?>
